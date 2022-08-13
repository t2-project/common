package de.unistuttgart.t2.common.scaling.cpu;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import de.unistuttgart.t2.common.scaling.Percentage;

/**
 * Data class to query the current CPU statistics.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
public final class CPUUsage {

    static final ChronoUnit DEFAULT_TIME_UNIT = ChronoUnit.SECONDS;
    static final long DEFAULT_INTERVAL_LENGTH = 10;
    static final double DEFAULT_REQUESTED_CPU_PERCENTAGE = Double.NEGATIVE_INFINITY;

    final Duration interval;
    final double minCPUUsage;

    // Not final as it is stated in the Javadoc that the number can change dynamically
    int availableCores = Runtime.getRuntime().availableProcessors();

    /**
     * Creates a cpu usage object without any limits imposed
     */
    CPUUsage() {
        interval = Duration.of(DEFAULT_INTERVAL_LENGTH, DEFAULT_TIME_UNIT);
        minCPUUsage = DEFAULT_REQUESTED_CPU_PERCENTAGE;
    }

    /**
     * Creates a cpu usage object that optionally imposes any limits
     *
     * @param timeUnit         value accepted by {@link ChronoUnit#valueOf(String)}, case insensitive. null = Seconds
     * @param intervalLength   the length of the "waste" interval in {@code timeUnit} steps
     * @param minCPUPercentage percentage of CPU to "waste"
     */
    CPUUsage(String timeUnit, long intervalLength, double minCPUPercentage) {
        interval = Duration.of(intervalLength,
            ChronoUnit.valueOf(Objects.requireNonNullElse(timeUnit, DEFAULT_TIME_UNIT.name()).toUpperCase())).abs();
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
        return Percentage.fromRealPercentage(minCPUUsage / availableCores, d -> {}) > 0;
    }

    /**
     * @return the length a 100% occupying task should run for per core in nanoseconds
     * @throws IllegalStateException if the requested CPU usage cannot be met under our laws of physics
     */
    public long limitInNanosecondsPerCore() {

        return (long) (interval.get(ChronoUnit.NANOS)
            * Percentage.fromRealPercentage(minCPUUsage / availableCores, invalid -> {
                throw new IllegalStateException(String.format(
                    "Cannot request a minimum of %f%% CPU usage with %d cores, which would mean %f%% CPU usage per core",
                    minCPUUsage, availableCores, invalid));
            }));
    }

    public int getAvailableCores() {
        return availableCores;
    }

    public Duration getInterval() {
        return interval;
    }

    public double getMinCPUUsage() {
        return minCPUUsage;
    }

    /**
     * @return a new {@code CPUInfo} that has no CPU limits set
     * @see #CPUUsage()
     */
    public static CPUUsage newUsageWithoutLimits() {
        return new CPUUsage();
    }
}
