package ar.edu.davinci.productspage.domain;

import java.util.LinkedList;
import java.util.List;

public enum ProductState {
	NEW("New"),
	PROMOTION("Promotion");
	
	
	private String description;
	
	private ProductState(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static List<ProductState> getProductStates(){
		List<ProductState> states = new LinkedList<ProductState>();
		states.add(ProductState.NEW);
		
		return states;
	}
}
