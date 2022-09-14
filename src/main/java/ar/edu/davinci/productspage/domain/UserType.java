package ar.edu.davinci.productspage.domain;

import java.util.LinkedList;
import java.util.List;

public enum UserType {
	ADMIN("Admin");
	
	private String description;
	
	private UserType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static List<UserType> getTypes(){
		List<UserType> types = new LinkedList<UserType>();
		types.add(ADMIN);
		
		return types;
	}
}
