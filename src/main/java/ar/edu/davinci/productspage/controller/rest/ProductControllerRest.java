package ar.edu.davinci.productspage.controller.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.service.ProductService;

@RestController
public class ProductControllerRest extends ShopApp{
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductControllerRest.class);
	
	@Autowired
	private ProductService service;
	
	public List<Product> getList(){
		LOGGER.info("Products list");
		return service.list();
	}
}
