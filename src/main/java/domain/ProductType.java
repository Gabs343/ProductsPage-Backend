package domain;

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
}
