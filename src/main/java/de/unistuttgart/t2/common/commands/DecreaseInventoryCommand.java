package de.unistuttgart.t2.common.commands;

public class DecreaseInventoryCommand extends UpdateInventoryCommand {
	
	public DecreaseInventoryCommand() {}
	
	public DecreaseInventoryCommand(int amount, String productId) {
		super(amount, productId);
	}
}
