package de.unistuttgart.t2.common.scaling.cpu;

import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * DTO in disguise.
 * <p>
 * Used so that users can configure the minimum CPU usage.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
public final class CPUUsageRequest {

    // i.e. SECONDS, HOURS, DAYS, ...
    String timeUnit;

    // Wrappers because they are optional
    Long intervalLength;
    Double cpuPercentage;

    /**
     * @return this object converted to the most fitting representation of a {@link CPUUsage}.<br>
     *         Fallbacks for missing values are:
     *         <ul>
     *         <li>{@link CPUUsage#DEFAULT_TIME_UNIT} if no time unit is given</li>
     *         <li>{@link CPUUsage#DEFAULT_INTERVAL_LENGTH} if no interval length is given</li>
     *         <li>{@link CPUUsage#DEFAULT_REQUESTED_CPU_PERCENTAGE} if no requested CPU usage is given</li>
     *         </ul>
     * @since 1.2.0
     */
    public CPUUsage convert() {
        ChronoUnit unit;
        try {
            unit = ChronoUnit.valueOf(timeUnit.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException e) {
            unit = CPUUsage.DEFAULT_TIME_UNIT;
        }

        return new CPUUsage(unit.name(), Objects.requireNonNullElse(intervalLength, CPUUsage.DEFAULT_INTERVAL_LENGTH),
            Objects.requireNonNullElse(cpuPercentage, CPUUsage.DEFAULT_REQUESTED_CPU_PERCENTAGE));
    }

    @Override
    public String toString() {
        return String.format("CPUUsageRequest [timeUnit=%s, intervalLength=%s, cpuPercentage=%s]", timeUnit,
            intervalLength, cpuPercentage);
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Long getIntervalLength() {
        return intervalLength;
    }

    public void setIntervalLength(Long intervalLength) {
        this.intervalLength = intervalLength;
    }

    public Double getCpuPercentage() {
        return cpuPercentage;
    }

    public void setCpuPercentage(Double cpuPercentage) {
        this.cpuPercentage = cpuPercentage;
    }
}
