package de.unistuttgart.t2.common.commands;

import io.eventuate.tram.commands.common.Command;

public abstract class OrderCommand implements Command {

	public static final String channel = "order";
	
}
