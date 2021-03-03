package de.unistuttgart.t2.common.commands;

import io.eventuate.tram.commands.common.Command;

/**
 * Command send to payment service to do the payment.
 * @author maumau
 *
 */
public class CheckCreditCommand implements Command {

	public static final String channel = "payment";
	
	private double total;
	private String creditCardNumber;

	public CheckCreditCommand() { }

	public CheckCreditCommand(double total, String creditCardNumber) {
		this.total = total;
		this.creditCardNumber = creditCardNumber;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}	
}
