package de.unistuttgart.t2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request to order all Items in a users cart.
 * 
 * <p>
 * Holds the payment information only. The products to be ordered will be
 * retrieved from the cart service, and the sessionId is in that session object
 * that is always there.
 * 
 * <p>
 * Used to communicate from UI to UIBackend.
 * 
 * @author maumau
 *
 */
public class OrderRequest {

    @JsonProperty("cardNumber")
    private String cardNumber;
    @JsonProperty("cardOwner")
    private String cardOwner;
    @JsonProperty("checksum")
    private String checksum;

    public OrderRequest() {
        super();
    }

    @JsonCreator
    public OrderRequest(String cardNumber, String cardOwner, String checksum) {
        super();
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.checksum = checksum;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public String getChecksum() {
        return checksum;
    }
}
