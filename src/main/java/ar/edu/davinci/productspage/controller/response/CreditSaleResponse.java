package ar.edu.davinci.productspage.controller.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditSaleResponse extends SaleResponse{
	private BigDecimal cardCoefficient;
	private Integer feesAmount;
}
