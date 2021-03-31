package de.unistuttgart.t2.common.domain.saga;

/**
 * All the informations usually found on a creditcard.
 * 
 * Supposed to be part of the SagaData
 * 
 * @author maumau
 *
 */
public class CreditCardInfo {
	private String cardNumber;
	private String cardOwner;
	private String checksum;
	
	public CreditCardInfo() {
		super();
	}
	public CreditCardInfo(String cardNumber, String cardOwner, String checksum) {
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
