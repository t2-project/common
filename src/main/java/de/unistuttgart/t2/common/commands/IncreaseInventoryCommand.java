package de.unistuttgart.t2.common.commands;

public class IncreaseInventoryCommand extends UpdateInventoryCommand {
	
	public IncreaseInventoryCommand() {}
	
	public IncreaseInventoryCommand(int amount, String productId) {
		super(amount, productId);
	}
}
