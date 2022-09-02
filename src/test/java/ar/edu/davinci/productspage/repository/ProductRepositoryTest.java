package ar.edu.davinci.productspage.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductType;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductRepositoryTest.class); 
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	void testFindAllByIdIterableOfID() {
		assertNotNull(productRepository, "The repository is null");
		List<Product> products = productRepository.findAll();
		
		LOGGER.info("Products found: " + products.size());
		
		assertNotNull(products, "The Products list is null");
		assertTrue(products.size() > 0, "No existing products");
	}
	
	@Test
	void testFindById() {
		Long id = 3L;
		Product product = null;
		
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent()) {
			product = optionalProduct.get();
			
			LOGGER.info("Product Found: " + product);
			assertEquals(ProductType.HEADPHONE, product.getType());
		}else {
			LOGGER.info("Product not found for the id: " + id);
			assertNull(product);
		}
	}

}
