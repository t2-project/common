package de.unistuttgart.t2.common;

import java.util.*;

import com.fasterxml.jackson.annotation.*;

/**
 * Request to update the content of a users cart.
 * <p>
 * An update may increase or decrease the number of items in the cart, depending on whether the the values in the
 * difference/content map are positive or negative. A negative number implies a decrease of the units of the given
 * product in the cart and a positive number implies an increase thereof.
 * <p>
 * Used to communicate from UI to UIBackend.
 */
public class UpdateCartRequest {

    @JsonProperty("content")
    private Map<String, Integer> difference;

    @JsonCreator
    public UpdateCartRequest(Map<String, Integer> content) {
        difference = content;
    }

    public void setContent(Map<String, Integer> content) {
        difference = content;
    }

    public Map<String, Integer> getContent() {
        return difference;
    }

    /**
     * Get the productIds of the products in this cart.
     *
     * @return ids of all products in the cart
     */
    @JsonIgnore
    public Collection<String> getProductIds() {
        return difference.keySet();
    }

    /**
     * Get the number of units of a product with the given id or zero if the product is not in the cart.
     *
     * @param productId to identify the product
     * @return number of units if the product is in the cart, zero otherwise
     */
    @JsonIgnore
    public Integer getUnits(String productId) {
        if (!difference.containsKey(productId)) {
            return 0;
        }
        return difference.get(productId);
    }
}
