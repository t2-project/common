package de.unistuttgart.t2.common.commands.order;

import java.util.Date;

public class OrderAction extends OrderCommand {

	private String sessionId;
	private Date timestamp;
	
	public OrderAction() {}
	
	public OrderAction(String sessionId, Date timestamp) {
		super();
		this.sessionId = sessionId;
		this.timestamp = timestamp;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
	
}
