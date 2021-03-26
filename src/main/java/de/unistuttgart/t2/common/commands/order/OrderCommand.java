package de.unistuttgart.t2.common.commands.order;

import io.eventuate.tram.commands.common.Command;

/**
 * generic command to order service
 * 
 * @author maumau
 *
 */
public abstract class OrderCommand implements Command {

	public static final String channel = "order";
	
	public OrderCommand() {};
}
