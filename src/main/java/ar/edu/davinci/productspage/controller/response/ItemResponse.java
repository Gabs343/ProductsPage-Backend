package ar.edu.davinci.productspage.controller.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
	
	private Long id;
	
	private Integer quantity;
	
	private ProductResponse product;
	
	private BigDecimal price;
}
