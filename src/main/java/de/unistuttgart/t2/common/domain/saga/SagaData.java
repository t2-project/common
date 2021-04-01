package de.unistuttgart.t2.common.domain.saga;

/**
 * all the Data, that any saga participant might possibly need.
 * 
 * the other option would be, that each saga participant collects its data from
 * other services personally (e.g. payment again asks cart for which and what
 * quantity of items were bought), but that is a lot of communication effort,
 * thus biiig message.
 * 
 * Payment wants:
 * 	- payment info (creditcard stuff)
 *  - total money
 *  
 * Inventory wants:
 *  - sessionid (to identify which reservations to delete)
 *  - (maybe) total for verification
 *  
 * Order wants: 
 *  - (nothing really)
 *  - but takes total, ordered units and products and, session id anyway. 
 * 
 * 
 */
public class SagaData {
	
	// for payment
	private CreditCardInfo creditCardInfo;
	
	// identify user (at inventory)
	private String sessionId;
	// identify order (for order compensation)
	private String orderId;
	
	// costs
	private double total;
	
	// cart content (things that were bought and how many of them) 
		// actually... no. because no one cares. 

	public SagaData(CreditCardInfo creditCardInfo, String sessionId, String orderId, double total) {
		super();
		this.creditCardInfo = creditCardInfo;
		this.sessionId = sessionId;
		this.orderId = orderId;
		this.total = total;
	}
	
	public SagaData(CreditCardInfo creditCardInfo, String sessionId, double total) {
		super();
		this.creditCardInfo = creditCardInfo;
		this.sessionId = sessionId;
		this.total = total;
	}
	
	

	public CreditCardInfo getCreditCardInfo() {
		return creditCardInfo;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public double getTotal() {
		return total;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}

