package de.unistuttgart.t2.common.scaling;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Data class to query the current CPU statistics.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
public final class CPUUsage {

    private final Optional<Duration> intervalLength;
    private final double minCPUUsage;

    // Not final as it is stated in the Javadoc that the number can change dynamically
    private int availableCores = Runtime.getRuntime().availableProcessors();

    /**
     * Creates a cpu usage object without any limits imposed
     */
    CPUUsage() {
        intervalLength = Optional.empty();
        minCPUUsage = -1;
    }

    /**
     * Creates a cpu usage object that optionally imposes any limits
     *
     * @param timeUnit         value accepted by {@link ChronoUnit#valueOf(String)}, case insensitive. null = Seconds
     * @param intervalLength   the length of the "waste" interval in {@code timeUnit} steps
     * @param minCPUPercentage percentage of CPU to "waste"
     */
    CPUUsage(String timeUnit, long intervalLength, long minCPUPercentage) {
        this.intervalLength = Optional.of(Duration.of(intervalLength,
            ChronoUnit.valueOf(Objects.requireNonNullElse(timeUnit, "SECONDS").toUpperCase())));
        minCPUUsage = minCPUPercentage;
    }

    /**
     * Asks the JVM (again), how many cores are available.
     */
    public void refreshAvailableCores() {
        availableCores = Runtime.getRuntime().availableProcessors();
    }

    /**
     * @return whether this usage actually imposes any limits
     */
    public boolean limitsPresent() {
        return intervalLength.isPresent()
            && Percentage.fromRealPercentage(minCPUUsage / availableCores, d -> {}) != Double.NaN;
    }

    /**
     * @return the length a 100% occupying task should run for per core in nanoseconds
     * @throws IllegalStateException if the requested CPU usage cannot be met under our laws of physics
     */
    public long limitInNanosecondsPerCore() {

        return (long) (intervalLength.orElse(Duration.ZERO).get(ChronoUnit.NANOS)
            * Percentage.fromRealPercentage(minCPUUsage / availableCores, invalid -> {
                throw new IllegalStateException(String.format(
                    "Cannot request a minimum of %f%% CPU usage with %d cores, which would mean %f%% CPU usage per core",
                    minCPUUsage, availableCores, invalid));
            }));
    }

    public int getAvailableCores() {
        return availableCores;
    }

    public Optional<Duration> getIntervalLength() {
        return intervalLength;
    }

    public double getMinCPUUsage() {
        return minCPUUsage;
    }

    /**
     * @return a new {@code CPUInfo} that has no CPU limits set
     * @see #CPUUsage()
     */
    public static CPUUsage newInfoWithoutLimits() {
        return new CPUUsage();
    }
}
