package de.unistuttgart.t2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The content of a users shopping cart.
 * <p>
 * Holds the productIds of the products in the cart and how many units of each product are in the cart.
 * <p>
 * Used to communicated with the cart service.
 *
 * @author maumau
 */
public final class CartContent {

    @JsonProperty("content")
    private Map<String, Integer> content;

    public CartContent() {
        this(new HashMap<>());
    }

    @JsonCreator
    public CartContent(Map<String, Integer> content) {
        this.content = content;
    }

    public void setContent(Map<String, Integer> content) {
        this.content = content;
    }

    public Map<String, Integer> getContent() {
        return content;
    }

    /**
     * Get the productIds of the products in this cart.
     *
     * @return ids of all products in the cart
     */
    @JsonIgnore
    public Collection<String> getProductIds() {
        return content.keySet();
    }

    /**
     * Get the number of units of a product with the given id or zero if the product is not in the cart.
     *
     * @param productId to identify the product
     * @return number of units if the product is in the cart, zero otherwise
     */
    @JsonIgnore
    public Integer getUnits(String productId) {
        return content.getOrDefault(productId, 0);
    }
}
