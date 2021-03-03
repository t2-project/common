package de.unistuttgart.t2.common.commands;

public class RejectOrderCommand extends OrderCommand {
	private String orderId;
	
	public RejectOrderCommand() {}

	public RejectOrderCommand(String orderId) {
		super();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
}
