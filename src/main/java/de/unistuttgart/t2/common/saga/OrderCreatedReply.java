package de.unistuttgart.t2.common.saga;

/**
 *
 * Reply that tells the orchestrator, under which id the order service created a
 * new order.
 * 
 * @author maumau
 *
 */
public class OrderCreatedReply {
    private String id;

    public OrderCreatedReply() {
        super();
    }

    public OrderCreatedReply(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
