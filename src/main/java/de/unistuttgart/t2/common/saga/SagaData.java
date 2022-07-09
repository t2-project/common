package de.unistuttgart.t2.common.saga;

import java.util.*;

/**
 * All the Information that any saga participant might possibly need.
 * <p>
 * Payment wants:
 * <ul>
 * <li>the information usually found on a credit card
 * <li>the total money to pay
 * </ul>
 * <p>
 * Inventory wants:
 * <ul>
 * <li>the sessionId to identify the reservations to commit / delete.
 * </ul>
 * <p>
 * Order wants:
 * <ul>
 * <li>nothing really
 * <li>but takes total and sessionId anyway.
 * </ul>
 * <p>
 * Another option would be, that each saga participant collects its data from other services personally (e.g. payment
 * again asks cart for which and what quantity of items were bought), but that is a lot of communication effort. To
 * avoid this there is just this one big messages, that contains all information and every one just picks what they
 * need.
 */
public final class SagaData {

    // for payment
    private final String cardNumber;
    private final String cardOwner;
    private final String checksum;

    // identify user (at inventory)
    private final String sessionId;
    // identify order (for order compensation)
    private String orderId;

    // costs
    private final double total;

    // for tracing only
    private Map<String, String> traceContext;

    // cart content (things that were bought and how many of them)
    // actually... no. because no one cares.

    public SagaData(String cardNumber, String cardOwner, String checksum, String sessionId, String orderId,
        double total, Map<String, String> traceContext) {
        this.cardNumber = cardNumber;
        this.cardOwner = cardOwner;
        this.checksum = checksum;
        this.sessionId = sessionId;
        this.orderId = orderId;
        this.total = total;
        this.traceContext = traceContext;
    }

    public SagaData(String cardNumber, String cardOwner, String checksum, String sessionId, double total) {
        this(cardNumber, cardOwner, checksum, sessionId, null, total, new HashMap<>());
    }

    public SagaData() {
        this(null, null, null, null, 0);
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

    public String getSessionId() {
        return sessionId;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotal() {
        return total;
    }

    public Map<String, String> getTraceContext() {
        return traceContext;
    }

    public void setTraceContext(Map<String, String> traceContext) {
        this.traceContext = traceContext;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
