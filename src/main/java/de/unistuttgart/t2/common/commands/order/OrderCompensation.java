package de.unistuttgart.t2.common.commands.order;

public class OrderCompensation extends OrderCommand {
	private String orderId;
	
	public OrderCompensation() {}

	public OrderCompensation(String orderId) {
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
