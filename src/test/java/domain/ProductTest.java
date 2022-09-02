package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductType;

class ProductTest {

	@Test
	void testBuilder() {
		Long id = 1L;
		
		String name = "test-1";
		String description = "description-1";
		ProductType type = ProductType.HEADPHONE;
		BigDecimal basePrice = new BigDecimal(50);
		
		Product product = Product.builder()
							.id(id)
							.name(name)
							.description(description)
							.type(type)
							.basePrice(basePrice)
							.build();
		
		assertNotNull(product);
		assertEquals(id, product.getId());
		assertEquals(description, product.getDescription());
		assertEquals(type, product.getType());
		assertEquals(basePrice, product.getBasePrice());
	}

}
