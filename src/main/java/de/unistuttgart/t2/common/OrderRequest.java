package de.unistuttgart.t2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * to be passed from ui to the ui backend.
 * 
 * @author maumau
 *
 */
public class OrderRequest {
	
	@JsonProperty("cardNumber")
	private String cardNumber;
	@JsonProperty("cardOwner")
	private String cardOwner;
	@JsonProperty("checksum")
	private String checksum;
	
	public OrderRequest() {
		super();
	}
	@JsonCreator
	public OrderRequest(String cardNumber, String cardOwner, String checksum) {
		super();
		this.cardNumber = cardNumber;
		this.cardOwner = cardOwner;
		this.checksum = checksum;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public String getCardOwner() {
		return cardOwner;
	}
	public String getChecksum() {
		return checksum;
	}
}
