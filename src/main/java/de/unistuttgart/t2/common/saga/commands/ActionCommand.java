package de.unistuttgart.t2.common.saga.commands;

import de.unistuttgart.t2.common.saga.SagaData;

/**
 * A command that makes a saga participant perform an action.
 * 
 * @author maumau
 *
 */
public class ActionCommand extends SagaCommand {
    public ActionCommand(SagaData data) {
        super(data);
    }

    public ActionCommand() {
        super();
    }
}