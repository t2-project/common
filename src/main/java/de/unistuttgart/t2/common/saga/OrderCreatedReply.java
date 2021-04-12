package de.unistuttgart.t2.common.saga;

/**
 * reply from order service to orchestrator, such that the orchestrator now
 * knows the id of the created order
 * 
 * @author maumau
 *
 */
public class OrderCreatedReply {
	private String id;

	public OrderCreatedReply() {
		super();
	}

	public OrderCreatedReply(String id) {
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
