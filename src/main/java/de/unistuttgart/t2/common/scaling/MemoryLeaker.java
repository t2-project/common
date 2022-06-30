package de.unistuttgart.t2.common.scaling;

import java.time.Instant;
import java.util.HashSet;

import javax.servlet.http.*;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Allows to create a memory leak of arbitrary size.
 * <p>
 * This component has to ignore the principles of OOP programming as Spring does not seem to be able
 * to autowire interceptors.<br>
 * Also, in this case setting this globally makes sense as there is no reason to have a
 * deterministic memory leak that only affects part of the application (however that should be
 * possible in the first place).
 *
 * @author Leon Hofmeister
 */
public final class MemoryLeaker implements HandlerInterceptor {

	static final HashSet<Instant>	memoryLeak	= new HashSet<>();
	static volatile double			expectedMemoryPercentage;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
		Object handler) throws Exception {
		adaptMemory();
		return true;
	}

	/**
	 * Clears the memory leak if the is expected memory percentage is {@code < 0}.<br>
	 * If the expected memory percentage is {@code > 0}, the memory will be filled until it reaches
	 * the expected memory percentage.<br>
	 * Nothing will be done if the value is {@code = 0}.
	 */
	private static void adaptMemory() {
		Runtime runtime = Runtime.getRuntime();
		if (expectedMemoryPercentage < 0) {
			memoryLeak.clear();
			System.gc();
		} else if (expectedMemoryPercentage > 0)
			do {
				memoryLeak.add(Instant.now());
				// i.e. 1 - 0.95 <= (20/100) -> allocate more memory
			} while (1 - expectedMemoryPercentage <= (double) runtime.freeMemory()
				/ runtime.totalMemory());
	}

	/**
	 * Changes the size of the memory leak: values {@code <= 0} mean no memory leak, values
	 * {@code 0 < x < 1} are the numeric representation of the size of the memory leak (0.5 for
	 * example means "use half of all available memory"), values {@code 1 <= x < 100} are regarded
	 * as human percentages (so 50.7 will have the same effect as passing 0.507).<br>
	 * {@code 0} disables the memory leak without clearing the potentially already allocated memory.
	 *
	 * @param newPercentage the minimal percentage of memory to use
	 * @throws IllegalArgumentException if the value is {@code >= 100}
	 */
	public static void changeExpectedMemoryPercentage(double newPercentage)
		throws IllegalArgumentException {
		if (newPercentage >= 100)
			throw new IllegalArgumentException(String
				.format("Cannot request memory above 100%. You requested %.2f%%.", newPercentage));
		if (newPercentage >= 1)
			newPercentage /= 100; // Has been passed as actual percentage, i.e. 56.5
		expectedMemoryPercentage = newPercentage;
		adaptMemory();
	}

	/**
	 * Clears the memory leak, if present.
	 */
	public static void clearMemoryLeak() {
		changeExpectedMemoryPercentage(-1);
	}
}