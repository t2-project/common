package de.unistuttgart.t2.common.commands;

import de.unistuttgart.t2.common.domain.saga.SagaData;

/**
 * signals saga participant to execute a compensation
 * @author maumau
 *
 */
public class CompensationCommand extends SagaCommand{
	public CompensationCommand(SagaData data) {
		super(data);
	}

}
