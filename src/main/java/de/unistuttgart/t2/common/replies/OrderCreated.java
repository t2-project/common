package de.unistuttgart.t2.common.replies;

public class OrderCreated {
	private String id;
	
	public OrderCreated() {
		super();
	}
	
	public OrderCreated(String id) {
		super();
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}	
}
