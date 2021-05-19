package de.unistuttgart.t2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Request to start a saga.
 * 
 * <p>
 * Holds all the information that are necessary for the saga. That is
 * information about the payment method and the costs, and the sessionId to
 * identify the users. all the Data, that any saga participant might possibly
 * 
 * <p>
 * Used to communicate with the orchestrator service.
 * 
 */
public class SagaRequest {

    // for payment
    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardOwner")
    private String cardOwner;
    @JsonProperty("checksum")
    private String checksum;

    // identify user
    @JsonProperty("sessionId")
    private String sessionId;

    // costs
    @JsonProperty("total")
    private double total;

    public SagaRequest() {
        super();
    }

    @JsonCreator
    public SagaRequest(String sessionId, String cardNumber, String cardOwner, String checksum, double total) {
        super();
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.checksum = checksum;
        this.sessionId = sessionId;
        this.total = total;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
    
    public String getChecksum() {
        return checksum;
    }

    public String getSessionId() {
        return sessionId;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return String.format("SessionID : %s, Card : %s, %s, %s , Total : %f", sessionId, cardOwner, cardNumber,
                checksum, total);
    }

}
