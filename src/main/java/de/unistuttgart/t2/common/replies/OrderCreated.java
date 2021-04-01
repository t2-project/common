package de.unistuttgart.t2.common.replies;

/**
 * reply from order service to orchestrator, such that the orchestrator now
 * knows the id of the created order
 * 
 * @author maumau
 *
 */
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
