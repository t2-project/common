package de.unistuttgart.t2.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReservationRequest {
	
	@JsonProperty("productId")
	private String productId;
	@JsonProperty("sessionId")
	private String sessionId;
	@JsonProperty("units")
	private int units;
	
	public ReservationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@JsonCreator
	public ReservationRequest(String productId, String sessionId, int units) {
		super();
		this.productId = productId;
		this.sessionId = sessionId;
		this.units = units;
	}

	public String getProductId() {
		return productId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public int getUnits() {
		return units;
	}
	
	
}
