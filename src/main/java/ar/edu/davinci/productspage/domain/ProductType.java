package ar.edu.davinci.productspage.domain;

import java.util.LinkedList;
import java.util.List;

public enum ProductType {
	HEADPHONE("Headphone"),
	MOUSE("Mouse"),
	KEYBOARD("Keyboard");
	
	private String description;
	
	private ProductType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static List<ProductType> getProductTypes(){
		List<ProductType> types = new LinkedList<ProductType>();
		types.add(ProductType.HEADPHONE);
		types.add(ProductType.MOUSE);
		types.add(ProductType.KEYBOARD);
		
		return types;
	}
}
