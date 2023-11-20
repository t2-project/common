package de.unistuttgart.t2.common.scaling.cpu;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Tests that allocating extra memory works as intended.
 *
 * @author Leon Hofmeister
 */
public final class CPUUsageTest {

    private final CPUManager manager = new CPUManager();

    @Test
    void testCPUUsageHasCurrentlyAvailableProcessors() {
        assertEquals(Runtime.getRuntime().availableProcessors(), manager.status.availableCores);
    }

    @Test
    void testConvertDTOToCPUUsage() {
        CPUUsageRequest test = new CPUUsageRequest();

        // Firstly, test that the default interval length is set when nothing has been given
        checkDefaultUsage(test.convertFromRatio());

        // Secondly, test that an invalid time unit behaves the same as a not given time unit
        test.timeUnit = "abcdefghijklmnopqrstuvwxyz";
        checkDefaultUsage(test.convertFromRatio());

        // Thirdly, test that a completely valid object is parsed as intended
        test.timeUnit = ChronoUnit.HOURS.name();
        test.intervalLength = 1L;
        test.cpuPercentage = 100 * 0.25 * Runtime.getRuntime().availableProcessors();
        CPUUsage parsed = test.convertFromRatio();
        assertEquals(parsed.interval, Duration.of(1, ChronoUnit.HOURS));
        assertEquals(parsed.minCPUUsageTotal, test.cpuPercentage);
    }

    /**
     * Unlike the memory test, there is no general API to query the CPU usage, so the test has to restrict itself to
     * testing that nothing throws an error, and everything works <em>theoretically</em> as intended.
     */
    @Test
    void testCPUUsage25Percent() {
        assertFalse(manager.status.limitsPresent());
        checkDefaultUsage(CPUUsage.newUsageWithoutLimits());

        final double percentage = 0.25;

        // Normal case: Require 25% CPU for all cores combined with an interval of 1 minute
        manager.requireCPU(new CPUUsage(ChronoUnit.MINUTES.name(), 1, percentage));
        assertTrue(manager.status.limitsPresent());

        final int cores = Runtime.getRuntime().availableProcessors();
        final long limitInNanosecondsPerCore = manager.status.limitInNanosecondsPerCore();

        // Test that it really blocks for 15 seconds for all cores combined
        assertEquals(manager.status.interval.toNanos() * percentage / cores, limitInNanosecondsPerCore);
        assertEquals(Duration.ofSeconds(15).toNanos() / cores, limitInNanosecondsPerCore);

        // Stop the CPU leak again
        manager.stop();
        assertTrue(manager.taskExecutor.isEmpty());
        assertFalse(manager.status.limitsPresent());
    }

    @Test
    void testCPUMultiCoreUsage() {
        final int cores = Runtime.getRuntime().availableProcessors();

        assumeTrue(cores > 1, "Cannot execute multi core test with only 1 core");
        final double maxPercentage = cores * 100;

        // Normal case 2: Require ({number of cores}-constant)% CPU for all cores combined (i.e. 800%-50%)
        // (at least two cores must be present) for an interval of 30 seconds
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, cores - 0.5));
        assertTrue(manager.status.limitsPresent());
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage - 50, d -> {}, true));
        assertTrue(manager.status.limitsPresent());

        // Normal case 3: exactly ({100*number of cores}-1)% CPU has been requested - valid
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, cores - Double.MIN_NORMAL, d -> {}, true));
        assertTrue(manager.status.limitsPresent());
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage - 1, d -> {}, true));
        assertTrue(manager.status.limitsPresent());

        // Edge case: exactly {100*number of cores}% CPU has been requested - fail silently
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, cores));
        assertFalse(manager.status.limitsPresent());
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage, d -> {}, true));
        assertFalse(manager.status.limitsPresent());

        // Error case: more memory requested than possible - fail silently
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, cores + 1));
        assertFalse(manager.status.limitsPresent());
        manager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage + 1, d -> {}, true));
        assertFalse(manager.status.limitsPresent());

        // Stop the CPU leak again
        manager.stop();
        assertTrue(manager.taskExecutor.isEmpty());
        assertFalse(manager.status.limitsPresent());
    }

    private void checkDefaultUsage(CPUUsage usage) {
        assertEquals(Duration.of(CPUUsage.DEFAULT_INTERVAL_LENGTH, CPUUsage.DEFAULT_TIME_UNIT), usage.interval);
        assertEquals(CPUUsage.DEFAULT_REQUESTED_CPU_PERCENTAGE, usage.minCPUUsageTotal);
        assertFalse(usage.limitsPresent());
    }
}
