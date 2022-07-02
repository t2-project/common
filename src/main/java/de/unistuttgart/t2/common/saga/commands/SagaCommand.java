package de.unistuttgart.t2.common.saga.commands;

import de.unistuttgart.t2.common.saga.SagaData;
import io.eventuate.tram.commands.common.Command;

/**
 * Generic command to the saga participants.
 * <p>
 * Holds the {@link de.unistuttgart.t2.common.saga.SagaData SagaData} that the saga participants need to perform actions
 * or compensations.
 * <p>
 * Storing the names of the queues here is not intended, but i do it anyway because i want them at one central place.
 *
 * @author maumau
 */
public abstract class SagaCommand implements Command {

    public static final String inventory = "inventory";
    public static final String payment = "payment";
    public static final String order = "order";

    private SagaData data;

    public SagaCommand(SagaData data) {
        this.data = data;
    }

    public SagaCommand() {}

    public SagaData getData() {
        return data;
    }

    public void setData(SagaData data) {
        this.data = data;
    }
}
