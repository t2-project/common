package de.unistuttgart.t2.common.scaling;

public class MemoryInfo {

	final long		used, free, total, max;
	final double	usedRatio;

	public MemoryInfo() {
		Runtime runtime = Runtime.getRuntime();
		max			= runtime.maxMemory();
		total		= runtime.totalMemory();
		free		= runtime.freeMemory();
		used		= total - free;
		usedRatio	= (double) used / total;
	}
}
