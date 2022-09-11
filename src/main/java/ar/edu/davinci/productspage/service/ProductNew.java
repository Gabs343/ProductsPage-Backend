package ar.edu.davinci.productspage.service;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductState;

public class ProductNew implements ProductStateStrategy{

	@Override
	public void setSellPrice(Product product) {
		// TODO Auto-generated method stub
		product.setState(ProductState.NEW);
		product.setFinalPrice(product.getBasePrice());
	}

}
