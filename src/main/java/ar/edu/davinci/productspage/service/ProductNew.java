package ar.edu.davinci.productspage.service;

import java.math.BigDecimal;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductState;

public class ProductNew implements ProductStateStrategy{

	@Override
	public BigDecimal getSellPrice(BigDecimal basePrice) {
		// TODO Auto-generated method stub
		return basePrice;
	}

}
