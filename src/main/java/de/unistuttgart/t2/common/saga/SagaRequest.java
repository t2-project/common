package de.unistuttgart.t2.common.saga;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * all the Data, that any saga participant might possibly need. for passing
 * from uibackend to orchestrator
 * 
 * this is a separate class even though the content is literally the same as in
 * {@linkplain SagaData} such that i can change the communication backend - orchestrator and
 * orchestrator - participants independently of each other. 
 * 
 * also i dont want to clutter my saga data with those json annotations.
 */
public class SagaRequest {

	// for payment
	@JsonProperty("cardNumber")
	private String cardNumber;
	@JsonProperty("cardOwner")
	private String cardOwner;
	@JsonProperty("checksum")
	private String checksum;

	// identify user (at inventory)
	@JsonProperty("sessionId")
	private String sessionId;

	// costs
	@JsonProperty("total")
	private double total;

	public SagaRequest() {
		super();
	}

	@JsonCreator
	public SagaRequest(String sessionId, String cardNumber, String cardOwner, String checksum, double total) {
		super();
		this.cardNumber = cardNumber;
		this.cardOwner = cardOwner;
		this.checksum = checksum;
		this.sessionId = sessionId;
		this.total = total;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
