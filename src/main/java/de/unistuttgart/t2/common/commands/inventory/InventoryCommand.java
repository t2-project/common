package de.unistuttgart.t2.common.commands.inventory;

import io.eventuate.tram.commands.common.Command;

/**
 * generic Command to the inventory
 * 
 * @author maumau
 *
 */
public abstract class InventoryCommand implements Command{
	public static final String channel = "inventory";
	
	public InventoryCommand() {}
}
