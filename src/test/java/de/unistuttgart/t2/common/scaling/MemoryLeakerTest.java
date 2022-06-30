package de.unistuttgart.t2.common.scaling;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests that allocating extra memory works as intended.
 *
 * @author Leon Hofmeister
 */
public final class MemoryLeakerTest {

	private final long	initialTotalMemory	= Runtime.getRuntime().totalMemory();
	private final long	initialFreeMemory	= Runtime.getRuntime().freeMemory();

	@Test
	void testMemoryLeak() {

		// More than half of the memory is still available
		assertEquals(MemoryLeaker.expectedMemoryPercentage, 0.0);
		assertTrue((double) initialFreeMemory / initialTotalMemory > 0.5);
		final double expectedPercentage = 0.6;

		// Normal case
		MemoryLeaker.changeExpectedMemoryPercentage(expectedPercentage);
		assertEquals(MemoryLeaker.expectedMemoryPercentage, expectedPercentage);

		// The percentage of used memory is at least {expected}
		final long	total	= Runtime.getRuntime().totalMemory();
		final long	free	= Runtime.getRuntime().freeMemory();
		final long	used	= total - free;
		assertTrue((double) used / total >= expectedPercentage);

		// Nothing changes memory wise for 0.0
		MemoryLeaker.changeExpectedMemoryPercentage(0.0);
		assertEquals(total, Runtime.getRuntime().totalMemory());
		assertEquals(free, Runtime.getRuntime().freeMemory());

		// The memory gets freed for a negative value
		MemoryLeaker.clearMemoryLeak();
		assertTrue(total >= Runtime.getRuntime().totalMemory());
		assertTrue(used >= Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
	}
}
