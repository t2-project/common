package de.unistuttgart.t2.common.scaling;

import java.util.function.DoubleConsumer;

/**
 * Utility class to handle mathematical percentages correctly.
 * <p>
 * Only located inside this package because adding its own package only for this file would be overkill in this case.
 *
 * @author Leon Hofmeister
 * @since 1.2.0
 */
final class Percentage {

    /**
     * Converts percentages in range {@code 0% <= toConvert < 100%} to their common mathematical form
     * ({@code 0.0 <= toConvert < 1.0}).<br>
     * No-op when {@code 0.0 <= toConvert < 1.0} already.<br>
     * Behaves exactly as {@code toConvert/100} when {@code 1.0 <= toConvert < 100.0}.
     *
     * @param toConvert      the double to convert
     * @param onInvalidValue action to perform when an invalid value has been given. The parameter will be given in
     *                       human readable form (i.e. {@code -0.5 -> -50.0[%]} and {@code 200.0 -> 200[%]})
     * @return the converted percentage, or {@link Double#NaN} if the value could not be converted
     * @since 1.2.0
     */
    static double fromRealPercentage(double toConvert, DoubleConsumer onInvalidValue) {
        if (toConvert >= 100 || toConvert < 0) {
            if (toConvert > -1) // -0.5 -> -50[%]
                toConvert *= 100;
            onInvalidValue.accept(toConvert);
            return Double.NaN;
        }
        if (toConvert >= 1) {
            toConvert /= 100; // 56.5[%] -> 0.565
        }
        return toConvert;
    }
}
