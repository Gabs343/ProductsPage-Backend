package ar.edu.davinci.productspage.controller.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class SaleResponse {
	
	private Long id;
	
	private UserResponse user;
	
	private String saleDate;
	
	private List<ItemResponse> items;
	
	private BigDecimal finalAmount;
}
