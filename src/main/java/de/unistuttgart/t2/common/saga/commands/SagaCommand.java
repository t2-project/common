package de.unistuttgart.t2.common.saga.commands;

import de.unistuttgart.t2.common.saga.SagaData;
import io.eventuate.tram.commands.common.Command;

/**
 * generic command to saga participants.
 * 
 * storing the names of the channels here is not intended, but i do it anyway
 * because i want them at one central place.
 * 
 * @author maumau
 *
 */
public abstract class SagaCommand implements Command {
    public static final String inventory = "inventory";
    public static final String payment = "payment";
    public static final String order = "order";

    private SagaData data;

    public SagaCommand(SagaData data) {
        this.data = data;
    }

    public SagaCommand() {
    }

    public SagaData getData() {
        return data;
    }

    public void setData(SagaData data) {
        this.data = data;
    }
}
