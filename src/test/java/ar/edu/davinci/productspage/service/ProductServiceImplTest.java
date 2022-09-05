package ar.edu.davinci.productspage.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductType;
import ar.edu.davinci.productspage.exception.BusinessException;

class ProductServiceImplTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImplTest.class);
	
	@Autowired
	ProductService productService;

	@Test
	void testListAll() {
		List<Product> products = productService.list();
		
		LOGGER.info("Product list size: " + products.size());
		
		assertNotNull(products, "Products list is null");
		assertTrue(products.size() > 0, "Products list is empty");
	}
	
	@Test
	void testList() {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Product> products = productService.list(pageable);
		
		LOGGER.info("Products size: " + products.getSize());
		LOGGER.info("Products total pages: " +products.getTotalPages());
		
		Pageable pageable1 = PageRequest.of(1, 2);
		Page<Product> products1 = productService.list(pageable1);
		
		LOGGER.info("Products size: " + products1.getSize());
		LOGGER.info("Products total pages: " + products1.getTotalPages());
		
		assertNotNull(products, "Products list is null");
		assertTrue(products.getSize() > 0, "Products list is empty");
	}
	
	@Test
	void testFindById() {
		try {
			Product product = productService.findById(1L);
			
			assertNotNull(product);
		}catch(BusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testFindById_withError() {
		Exception exception = assertThrows(BusinessException.class, () -> {
			productService.findById(0L);
		});
		
		String expectedMessage = "Product with the id 0 not found";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testSave() {
		LOGGER.info("Count before insert: " + productService.count());
		Product newProduct = Product.builder()
				.name("test-name")
				.description("test-description")
				.type(ProductType.MOUSE)
				.basePrice(BigDecimal.valueOf(343.99))
				.build();
		
		Product savedProduct;
		
		try{
			savedProduct = productService.save(newProduct);
			
			assertNotNull(savedProduct);
			assertEquals(newProduct.getName(), savedProduct.getName());
		}catch(BusinessException e) {
			e.printStackTrace();
		}
		LOGGER.info("Count after insert: " + productService.count());
	}
	
	@Test
	void testDelete() {
		LOGGER.info("Count before delete: " + productService.count());
		Product product = null;
		try {
			product = productService.findById(2L);
			LOGGER.info("Prodcut to delete: " + product.toString());
			productService.delete(product);
		}catch(BusinessException e){
			e.printStackTrace();
		}
		LOGGER.info("Count after delete: " + productService.count());
	}

}
