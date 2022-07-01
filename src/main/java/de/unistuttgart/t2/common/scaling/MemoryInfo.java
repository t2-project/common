package de.unistuttgart.t2.common.scaling;

/**
 * Data class to query the current memory statistics.
 * <p>
 * It offers no methods because it is only intended to be serialized as a JSON object.
 *
 * @author Leon Hofmeister
 * @since 1.1
 */
@SuppressWarnings("unused")
public final class MemoryInfo {

    private final long used, free, total, max;
    private final double usedRatio;

    public MemoryInfo() {
        Runtime runtime = Runtime.getRuntime();
        max = runtime.maxMemory();
        total = runtime.totalMemory();
        free = runtime.freeMemory();
        used = total - free;
        usedRatio = (double) used / total;
    }
}
