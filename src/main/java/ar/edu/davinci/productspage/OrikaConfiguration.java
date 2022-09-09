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
import ar.edu.davinci.productspage.domain.ProductType;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class OrikaConfiguration {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrikaConfiguration.class);
	
	private final ObjectMapper objectMapper;
	
	public OrikaConfiguration() {
		objectMapper = new ObjectMapper();
	}
	
	@Bean
	public MapperFacade mapper() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		
		//PRODUCT
		mapperFactory.classMap(Product.class, ProductResponse.class)
						.customize(new CustomMapper<Product, ProductResponse>() {
			public void mapAtoB(final Product product, final ProductResponse response, final MappingContext context) {
				LOGGER.info("### Custom mapping for Product --> ProductResponse ###");
				
				response.setId(product.getId());
				response.setName(product.getName());
				response.setDescription(product.getDescription());
				response.setType(product.getType().getDescription());
				response.setBasePrice(product.getBasePrice());
			}
		}).register();
		
		mapperFactory.classMap(ProductInsertRequest.class, Product.class)
						.customize(new CustomMapper<ProductInsertRequest, Product>(){
			public void mapAtoB(final ProductInsertRequest insertRequest, final Product product, final MappingContext context) {
				LOGGER.info("### Custom mapping for ProductIntertRequest --> Product ###");
				
				product.setName(insertRequest.getName());
				product.setDescription(insertRequest.getDescription());
				
				ProductType type = ProductType.valueOf(insertRequest.getType());
				product.setType(type);
				
				product.setBasePrice(insertRequest.getBasePrice());
			}				
		}).register();
		
		mapperFactory.classMap(Product.class, ProductUpdateRequest.class).byDefault().register();
		
		return mapperFactory.getMapperFacade();
	}
}
