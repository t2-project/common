package de.unistuttgart.t2.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * content of a users shopping cart
 * 
 * to be passed from cart to ui backend to ui and vice versa.
 * 
 * @author maumau
 *
 */
public class CartContent {
    @JsonProperty("content")
    private Map<String, Integer> content;

    public CartContent() {
        super();
        this.content = new HashMap<>();
    }

    @JsonCreator
    public CartContent(Map<String, Integer> content) {
        super();
        this.content = content;
    }

    public void setContent(Map<String, Integer> content) {
        this.content = content;
    }

    public Map<String, Integer> getContent() {
        return content;
    }

    public Collection<String> getProductIds() {
        return content.keySet();
    }

    /**
     * get units of product with given id or zero if product not in the cart
     * 
     * @param productId
     * @return units of product with given id
     */
    public Integer getUnits(String productId) {
        if (!content.containsKey(productId)) {
            return 0;
        }
        return content.get(productId);
    }
}
