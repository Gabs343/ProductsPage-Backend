package ar.edu.davinci.productspage.service;

import java.math.BigDecimal;

public class ProductPromotion implements ProductStateStrategy{
	
	private Double discount = 20.0;
	
	@Override
	public BigDecimal getSellPrice(BigDecimal basePrice) {
		// TODO Auto-generated method stub
		BigDecimal amountToSubstract = basePrice.multiply(new BigDecimal(discount))
									.divide(new BigDecimal(100));
		
		return basePrice.subtract(amountToSubstract);
	}

}
