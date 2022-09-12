package ar.edu.davinci.productspage.service;

import java.math.BigDecimal;

import ar.edu.davinci.productspage.domain.Product;

public interface ProductStateStrategy {
	public BigDecimal getSellPrice(BigDecimal basePrice);
}
