package de.unistuttgart.t2.common.domain;

/**
 * A product as sold by the store.
 * 
 * @author maumau
 *
 */
public class Product {
	
	private String id;
	private String name;
	private int units;
	private double price;
	
	public Product() {	}

	public Product(String id, String name, int units, double price) {
		super();
		this.id = id;
		this.name = name;
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
