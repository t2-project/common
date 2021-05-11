package de.unistuttgart.t2.common.saga.commands;

import de.unistuttgart.t2.common.saga.SagaData;

/**
 * A command that makes a saga participant perfomr a compensation.
 * 
 * @author maumau
 *
 */
public class CompensationCommand extends SagaCommand {
    public CompensationCommand(SagaData data) {
        super(data);
    }

    public CompensationCommand() {
        super();
    }
}
