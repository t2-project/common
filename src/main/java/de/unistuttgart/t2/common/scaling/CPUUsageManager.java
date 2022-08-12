package de.unistuttgart.t2.common.scaling;

import java.util.*;
import java.util.concurrent.*;

/**
 * Manages how much CPU is consistently used as minimum.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
public class CPUUsageManager {

    private static CPUUsage status = CPUUsage.newInfoWithoutLimits();
    private static Optional<ExecutorService> taskExecutor;

    static {
        setup();
    }

    /**
     * Requires at least the usage given by {@code cpu}.
     *
     * @param cpu the CPU usage to use
     */
    public static void requireCPU(CPUUsage cpu) {
        status = Objects.requireNonNullElseGet(cpu, CPUUsage::newInfoWithoutLimits);
        if (status.limitsPresent())
            setupExecutor();
        taskExecutor.ifPresent(e -> addTasks());
    }

    /**
     * Flushes and deletes the runner, if present.
     */
    public static void stop() {
        taskExecutor.ifPresent(ExecutorService::shutdownNow);
        taskExecutor = Optional.empty();
    }

    /**
     * @return the current CPU status
     */
    public static CPUUsage getCurrentStatus() {
        status.refreshAvailableCores();
        return status;
    }

    private static void setup() {
        status.refreshAvailableCores();
        setupExecutor();
    }

    private static void setupExecutor() {
        stop();
        taskExecutor =
            status.limitsPresent() ? Optional.of(Executors.newScheduledThreadPool(status.getAvailableCores()))
                : Optional.empty();
    }

    /**
     * Adds {@link CPUUsage#getAvailableCores()}} tasks to the periodically running executor completely blocking one
     * core, each running for a duration of {@code requestedCPUPercentage * intervalLength / availableCores}.
     */
    private static void addTasks() {
        if (taskExecutor.isEmpty() || !status.limitsPresent())
            return;
        for (int i = 0; i < status.getAvailableCores(); ++i)
            taskExecutor.orElseThrow().execute(CPUUsageManager::simulateWork);

    }

    /**
     * Occupies one core completely for {@link CPUUsage#limitInNanosecondsPerCore()} nanoseconds.<br>
     * Callers must ensure to call this method after every {@link CPUUsage#getIntervalLength()}, so that the minimally
     * required CPU usage is {@link CPUUsage#getMinCPUUsage()}.
     */
    private static void simulateWork() {
        long busyTime = status.limitInNanosecondsPerCore();
        long start = System.nanoTime();
        while (System.nanoTime() <= start + busyTime) {}
    }
}
