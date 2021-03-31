package de.unistuttgart.t2.common.commands;

import de.unistuttgart.t2.common.domain.saga.SagaData;
import io.eventuate.tram.commands.common.Command;

/**
 * generic Command to the inventory
 * 
 * @author maumau
 *
 */
public abstract class SagaCommand implements Command{
	public static final String inventory = "inventory";
	public static final String payment = "payment";
	public static final String order = "order";
	
	private SagaData data;
	
	public SagaCommand(SagaData data) {
		this.data = data;
	}

	public SagaData getData() {
		return data;
	}
}
