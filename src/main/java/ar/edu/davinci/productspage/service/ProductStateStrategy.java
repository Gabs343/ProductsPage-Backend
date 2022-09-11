package ar.edu.davinci.productspage.service;

import ar.edu.davinci.productspage.domain.Product;

public interface ProductStateStrategy {
	public void setSellPrice(Product product);
}
