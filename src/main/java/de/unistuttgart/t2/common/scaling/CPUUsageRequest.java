package de.unistuttgart.t2.common.scaling;

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
    private String timeUnit;

    // Wrappers because they are optional
    private Long intervalLength;
    private Long cpuPercentage;

    /**
     * @return this object converted to the most fitting representation of a {@link CPUUsage}.<br>
     *         Fallbacks for missing values are:
     *         <ul>
     *         <li>seconds if no time unit is given</li>
     *         <li>10 if no interval length is given</li>
     *         <li>0 if no requested CPU usage is given</li>
     *         </ul>
     * @since 1.2.0
     */
    public CPUUsage convert() {
        ChronoUnit unit;
        try {
            unit = ChronoUnit.valueOf(timeUnit.toUpperCase());
        } catch (NullPointerException | IllegalArgumentException e) {
            unit = ChronoUnit.SECONDS;
        }

        return new CPUUsage(unit.name(), (long) Objects.requireNonNullElse(intervalLength, 10),
            (long) Objects.requireNonNullElse(cpuPercentage, 0));
    }
}
