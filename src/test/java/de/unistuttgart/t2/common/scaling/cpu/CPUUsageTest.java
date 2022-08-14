package de.unistuttgart.t2.common.scaling.cpu;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests that allocating extra memory works as intended.
 *
 * @author Leon Hofmeister
 */
public final class CPUUsageTest {

    void testCPUUsageHasCurrentlyAvailableProcessors() {
        assertEquals(Runtime.getRuntime().availableProcessors(), CPUUsageManager.status.availableCores);
    }

    @Test
    void testConvertDTOToCPUUsage() {
        CPUUsageRequest test = new CPUUsageRequest();

        // Firstly, test that the default interval length is set when nothing has been given
        checkDefaultUsage(test.convert());

        // Secondly, test that an invalid time unit behaves the same as a not given time unit
        test.timeUnit = "abcdefghijklmnopqrstuvwxyz";
        checkDefaultUsage(test.convert());

        // Thirdly, test that a completely valid object is parsed as intended
        test.timeUnit = ChronoUnit.HOURS.name();
        test.intervalLength = 1L;
        test.cpuPercentage = 100 * 0.25 * Runtime.getRuntime().availableProcessors();
        CPUUsage parsed = test.convert();
        assertEquals(parsed.interval, Duration.of(1, ChronoUnit.HOURS));
        assertEquals(parsed.minCPUUsage, test.cpuPercentage);
    }

    /**
     * Unlike the memory test, there is no general API to query the CPU usage, so the test has to restrict itself to
     * testing that nothing throws an error, and everything works <em>theoretically</em> as intended.
     */
    @Test
    void testCPUUsage25Percent() {
        assertFalse(CPUUsageManager.status.limitsPresent());
        checkDefaultUsage(CPUUsage.newUsageWithoutLimits());

        final double percentage = 0.25;

        // Normal case: Require 25% CPU for all cores combined with an interval of 1 minute
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.MINUTES.name(), 1, percentage));
        assertTrue(CPUUsageManager.status.limitsPresent());

        final int cores = Runtime.getRuntime().availableProcessors();
        final long limitInNanosecondsPerCore = CPUUsageManager.status.limitInNanosecondsPerCore();

        // Test that it really blocks for 15 seconds for all cores combined
        assertEquals(CPUUsageManager.status.interval.toNanos() * percentage / cores, limitInNanosecondsPerCore);
        assertEquals(Duration.ofSeconds(15).toNanos() / cores, limitInNanosecondsPerCore);

        // Stop the CPU leak again
        CPUUsageManager.stop();
        assertTrue(CPUUsageManager.taskExecutor.isEmpty());
    }

    @Test
    void testCPUMultiCoreUsage() {
        final int cores = Runtime.getRuntime().availableProcessors();

        assumeTrue(cores > 1, "Cannot execute multi core test with only 1 core");
        final double maxPercentage = cores * 100;

        // Normal case 2: Require ({100*number of cores}-constant)% CPU for all cores combined (i.e. 800%-50%)
        // (at least two cores must be present) for an interval of 30 seconds
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage - 50));
        assertTrue(CPUUsageManager.status.limitsPresent());

        // Normal case 3: exactly ({100*number of cores}-1)% CPU has been requested - valid
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage - 1));
        assertTrue(CPUUsageManager.status.limitsPresent());

        // Edge case: exactly {100*number of cores}% CPU has been requested - fail silently
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage));
        assertFalse(CPUUsageManager.status.limitsPresent());

        // Error case: more memory requested than possible - fail silently
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.SECONDS.name(), 30, maxPercentage + 1));
        assertFalse(CPUUsageManager.status.limitsPresent());

        // Stop the CPU leak again
        CPUUsageManager.stop();
        assertTrue(CPUUsageManager.taskExecutor.isEmpty());
    }

    private void checkDefaultUsage(CPUUsage usage) {
        assertEquals(Duration.of(CPUUsage.DEFAULT_INTERVAL_LENGTH, CPUUsage.DEFAULT_TIME_UNIT), usage.interval);
        assertEquals(CPUUsage.DEFAULT_REQUESTED_CPU_PERCENTAGE, usage.minCPUUsage);
        assertFalse(usage.limitsPresent());
        assertThrows(IllegalStateException.class, usage::limitInNanosecondsPerCore);
    }
}
