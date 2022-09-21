package ar.edu.davinci.productspage.controller.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditSaleRequest {
	
	private Long userId;
	
	private Integer feesAmount;
}
