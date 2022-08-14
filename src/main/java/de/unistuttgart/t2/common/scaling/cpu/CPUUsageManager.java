package de.unistuttgart.t2.common.scaling.cpu;

import java.util.*;
import java.util.concurrent.*;

/**
 * Manages how much CPU is consistently used as minimum.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
public final class CPUUsageManager {

    static CPUUsage status;
    static Optional<ScheduledExecutorService> taskExecutor = Optional.empty();

    static {
        setupExecutor();
    }

    /**
     * Requires at least the usage given by {@code cpu}.
     *
     * @param cpu the CPU usage to use
     */
    public static void requireCPU(CPUUsage cpu) {
        setupExecutor();
        status = Objects.requireNonNullElseGet(cpu, CPUUsage::newUsageWithoutLimits);
        taskExecutor.ifPresent(e -> addTasks());
    }

    /**
     * Flushes and deletes the runner, if present.
     */
    public static void stop() {
        taskExecutor.ifPresent(ExecutorService::shutdownNow);
        taskExecutor = Optional.empty();
        status = CPUUsage.newUsageWithoutLimits();
    }

    /**
     * @return the current CPU status
     */
    public static CPUUsage getCurrentStatus() {
        status.refreshAvailableCores();
        return status;
    }

    private static void setupExecutor() {
        stop();
        if (status.limitsPresent())
            taskExecutor = Optional.of(Executors.newScheduledThreadPool(status.getAvailableCores()));
    }

    /**
     * Adds {@link CPUUsage#getAvailableCores()}} tasks to the periodically running executor completely blocking one
     * core, each running for a duration of {@code requestedCPUPercentage * intervalLength / availableCores}.
     */
    private static void addTasks() {
        if (taskExecutor.isEmpty() || !status.limitsPresent())
            return;
        status.refreshAvailableCores();
        for (int i = 0; i < status.getAvailableCores(); ++i)
            taskExecutor.orElseThrow().scheduleAtFixedRate(CPUUsageManager::simulateWork, 0L,
                status.getInterval().toMillis(), TimeUnit.MILLISECONDS);

    }

    /**
     * Occupies one core completely for {@link CPUUsage#limitInNanosecondsPerCore()} nanoseconds.<br>
     * Callers must ensure to call this method after every {@link CPUUsage#getInterval()}, so that the minimally
     * required CPU usage is {@link CPUUsage#getMinCPUUsage()}.
     */
    private static void simulateWork() {
        long busyTime = status.limitInNanosecondsPerCore();
        long start = System.nanoTime();
        while (System.nanoTime() <= start + busyTime && !Thread.currentThread().isInterrupted()) {}
    }
}
