package ar.edu.davinci.productspage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.davinci.productspage.controller.request.ProductInsertRequest;
import ar.edu.davinci.productspage.controller.request.ProductUpdateRequest;
import ar.edu.davinci.productspage.controller.response.ProductResponse;
import ar.edu.davinci.productspage.domain.Product;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class OrikaConfiguration {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrikaConfiguration.class);
	
	private ObjectMapper objectMapper;
	
	public OrikaConfiguration() {
		objectMapper = new ObjectMapper();
	}
	
	@Bean
	public MapperFacade mapper() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		
		//PRODUCT
		mapperFactory.classMap(Product.class, ProductInsertRequest.class).byDefault().register();
		mapperFactory.classMap(Product.class, ProductUpdateRequest.class).byDefault().register();
		
		mapperFactory.classMap(Product.class, ProductResponse.class)
						.customize(new CustomMapper<Product, ProductResponse>() {
			public void mapAToB(final Product product, final ProductResponse response, final MappingContext context) {
				LOGGER.info("### Custom mapping for Product --> ProductResponse ###");
				
				response.setId(product.getId());
				response.setName(product.getName());
				response.setDescription(product.getDescription());
				response.setType(product.getType().getDescription());
				response.setBasePrice(product.getBasePrice());
			}
		}).register();
		
		return mapperFactory.getMapperFacade();
	}
}
