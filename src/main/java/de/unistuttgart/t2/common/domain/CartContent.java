package de.unistuttgart.t2.common.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	
	public Collection<String> getProductIds(){
		return content.keySet();
	}
	
	public Integer getUnits(String productId) {
		if (!content.containsKey(productId)) {
			return 0;
		} 
		return content.get(productId);
	}
}
