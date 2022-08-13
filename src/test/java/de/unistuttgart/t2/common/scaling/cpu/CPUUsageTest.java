package de.unistuttgart.t2.common.scaling.cpu;

import static org.junit.jupiter.api.Assertions.*;

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
    void testCPUUsage() {
        assertFalse(CPUUsageManager.status.limitsPresent());
        checkDefaultUsage(CPUUsage.newUsageWithoutLimits());

        // Normal case: Require 25% CPU for all cores combined with an interval of 1 minute
        final double percentage = 0.25;
        CPUUsageManager.requireCPU(new CPUUsage(ChronoUnit.MINUTES.name(), 1, percentage));
        assertTrue(CPUUsageManager.status.limitsPresent());

        // Test that it really should block for 15 seconds
        assertEquals(
            CPUUsageManager.status.interval.get(ChronoUnit.NANOS) * percentage
                / Runtime.getRuntime().availableProcessors(),
            CPUUsageManager.status.limitInNanosecondsPerCore());
        assertEquals(15 * 1_000_000_000, CPUUsageManager.status.limitInNanosecondsPerCore());

        // Stopping the CPU leak again
        CPUUsageManager.stop();
        assertTrue(CPUUsageManager.taskExecutor.isEmpty());
        assertFalse(CPUUsageManager.status.limitsPresent());
    }

    private void checkDefaultUsage(CPUUsage usage) {
        assertEquals(Duration.of(CPUUsage.DEFAULT_INTERVAL_LENGTH, CPUUsage.DEFAULT_TIME_UNIT), usage.interval);
        assertEquals(CPUUsage.DEFAULT_REQUESTED_CPU_PERCENTAGE, usage.minCPUUsage);
        assertFalse(usage.limitsPresent());
        assertThrows(IllegalStateException.class, usage::limitInNanosecondsPerCore);
    }
}
