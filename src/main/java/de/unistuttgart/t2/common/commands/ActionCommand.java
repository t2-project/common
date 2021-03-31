package de.unistuttgart.t2.common.commands;

import de.unistuttgart.t2.common.domain.saga.SagaData;


/** 
 * signals a saga participant to take an action. 
 * 
 * @author maumau
 *
 */
public class ActionCommand extends SagaCommand{
	public ActionCommand(SagaData data) {
		super(data);
	}
	

}
