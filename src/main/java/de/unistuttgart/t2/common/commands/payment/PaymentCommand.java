package de.unistuttgart.t2.common.commands.payment;

import io.eventuate.tram.commands.common.Command;

/**
 * generic command to payment service
 * 
 * @author maumau
 *
 */
public abstract class PaymentCommand implements Command{
	public static final String channel = "payment";

	public PaymentCommand() {}
}
