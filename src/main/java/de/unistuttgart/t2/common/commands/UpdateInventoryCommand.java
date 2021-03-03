package de.unistuttgart.t2.common.commands;

import io.eventuate.tram.commands.common.Command;

/**
 * generic Command to updated the inventory
 * 
 * @author maumau
 *
 */
public abstract class UpdateInventoryCommand implements Command{

	public static final String channel = "inventory";
	
	private int amount;
	private String productId;
	
	public UpdateInventoryCommand() {}
	
	public UpdateInventoryCommand(int amount, String productId) {
		this.amount = amount;
		this.productId = productId;
	}
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
}
