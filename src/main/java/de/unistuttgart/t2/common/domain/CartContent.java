package de.unistuttgart.t2.common.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CartContent {
	private Map<String, Integer> products;

	public CartContent() {
		super();
		this.products = new HashMap<>();
	}

	public CartContent(Map<String, Integer> products) {
		super();
		this.products = products;
	}

	public void setProducts(Map<String, Integer> products) {
		this.products = products;
	}
	
	public Collection<String> getProducts(){
		return products.keySet();
	}
	
	public Integer getUnits(String productId) {
		if (!products.containsKey(productId)) {
			return 0;
		} 
		return products.get(productId);
	}
}
