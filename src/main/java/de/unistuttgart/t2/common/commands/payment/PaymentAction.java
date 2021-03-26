package de.unistuttgart.t2.common.commands.payment;

/**
 * saga action for the payment service 
 * @author maumau
 *
 */
public class PaymentAction extends PaymentCommand {
	
	private String sessionId;

	public PaymentAction() { }

	public PaymentAction(String sessionId) {
		super();
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
