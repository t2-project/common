package de.unistuttgart.t2.common.saga.commands;

import de.unistuttgart.t2.common.saga.SagaData;

/**
 * A command that makes a saga participant perform a compensation.
 *
 * @author maumau
 */
public final class CompensationCommand extends SagaCommand {

    public CompensationCommand(SagaData data) {
        super(data);
    }

    public CompensationCommand() {}
}
