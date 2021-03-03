package de.unistuttgart.t2.common.commands;

public class CreateOrderCommand extends OrderCommand {
	private double total;
	private String productId;
	private int amount;
	
	public CreateOrderCommand() {}
	
	public CreateOrderCommand(double total, String productId, int amount) {
		super();
		this.total = total;
		this.productId = productId;
		this.amount = amount;
	}
	
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
