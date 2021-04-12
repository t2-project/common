package de.unistuttgart.t2.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * a product as sold by the store.
 * 
 * to be passed from inventory to ui backend.
 * 
 * @author maumau
 *
 */
public class Product {
	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String desription;
	@JsonProperty("units")
	private int units;
	@JsonProperty("price")
	private double price;
	
	public Product() {	}

	@JsonCreator
	public Product(@JsonProperty("id") String id, @JsonProperty("name") String name, @JsonProperty("description") String desription, @JsonProperty("units") int units, @JsonProperty("price") double price) {
		super();
		this.id = id;
		this.name = name;
		this.desription = desription;
		this.units = units;
		this.price = price;
	}
	
	
	public Product(String name, String desription, int units, double price) {
		super();
		this.name = name;
		this.desription = desription;
		this.units = units;
		this.price = price;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return desription;
	}
	public void setDescription(String desription) {
		this.desription = desription;
	}
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}


}
