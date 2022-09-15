package ar.edu.davinci.productspage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.davinci.productspage.controller.request.ProductInsertRequest;
import ar.edu.davinci.productspage.controller.request.ProductUpdateRequest;
import ar.edu.davinci.productspage.controller.request.StockAddRequest;
import ar.edu.davinci.productspage.controller.request.StockRemoveRequest;
import ar.edu.davinci.productspage.controller.request.UserInsertRequest;
import ar.edu.davinci.productspage.controller.request.UserUpdateRequest;
import ar.edu.davinci.productspage.controller.response.ProductResponse;
import ar.edu.davinci.productspage.controller.response.UserResponse;
import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductState;
import ar.edu.davinci.productspage.domain.ProductType;
import ar.edu.davinci.productspage.domain.Stock;
import ar.edu.davinci.productspage.domain.User;
import ar.edu.davinci.productspage.domain.UserType;
import ar.edu.davinci.productspage.service.ProductStateStrategy;
import ar.edu.davinci.productspage.service.StrategyFactory;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class OrikaConfiguration {
	
	private final Logger LOGGER = LoggerFactory.getLogger(OrikaConfiguration.class);
	
	private final ObjectMapper objectMapper;
	
	private final StrategyFactory factory;
	
	public OrikaConfiguration() {
		objectMapper = new ObjectMapper();
		factory = new StrategyFactory();
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
				response.setState(product.getState().getDescription());
				response.setBasePrice(product.getBasePrice());
				response.setFinalPrice(product.getFinalPrice());
				response.setQuantity(product.getQuantity());
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
				
				ProductState state = ProductState.valueOf(insertRequest.getState());
				product.setState(state);
				
				ProductStateStrategy strategy = factory.getStrategy(state);
				product.setStateStrategy(strategy);
				
				product.setBasePrice(insertRequest.getBasePrice());
				
				product.setFinalPrice(strategy.getSellPrice(product.getBasePrice()));
			
				Stock stock = Stock.builder().quantity(insertRequest.getQuantity()).build();
				
				product.setStock(stock);
			
			}				
		}).register();
		
		mapperFactory.classMap(ProductUpdateRequest.class, Product.class)
						.customize(new CustomMapper<ProductUpdateRequest, Product>(){
			public void mapAtoB(final ProductUpdateRequest updateRequest, final Product product, final MappingContext context) {
				LOGGER.info("### Custom mapping for ProductUpdateRequest --> Product ###");
				
				product.setName(updateRequest.getName());
				product.setDescription(updateRequest.getDescription());
				
				ProductType type = ProductType.valueOf(updateRequest.getType());
				product.setType(type);
				
				ProductState state = ProductState.valueOf(updateRequest.getState());
				product.setState(state);
				
				ProductStateStrategy strategy = factory.getStrategy(state);
				product.setStateStrategy(strategy);
				
				product.setBasePrice(updateRequest.getBasePrice());
				
				product.setFinalPrice(strategy.getSellPrice(product.getBasePrice()));
			
			}
		}).register();;
		
		mapperFactory.classMap(StockAddRequest.class, Stock.class)
						.customize(new CustomMapper<StockAddRequest, Stock>(){
			public void mapAtoB(final StockAddRequest addRequest, final Stock stock, final MappingContext context) {
				LOGGER.info("### Custom mapping for StockAddRequest --> Stock ###");
				
				stock.setQuantity(addRequest.getQuantity());
			}
		}).register();
		
		mapperFactory.classMap(StockRemoveRequest.class, Stock.class)
						.customize(new CustomMapper<StockRemoveRequest, Stock>(){
			public void mapAtoB(final StockRemoveRequest removeRequest, final Stock stock, final MappingContext context) {
				LOGGER.info("### Custom mapping for StocRemoveRequest --> Stock ###");

				stock.setQuantity(removeRequest.getQuantity());
			}
		}).register();
		
		//USER
		mapperFactory.classMap(User.class, UserResponse.class)
						.customize(new CustomMapper<User, UserResponse>(){
			public void mapAtoB(final User user, final UserResponse response, final MappingContext context) {
				LOGGER.info("### Custom mapping for User --> UserResponse ###");
				
				response.setId(user.getId());
				response.setName(user.getName());
				response.setLastname(user.getLastname());
				response.setMail(user.getMail());
				response.setType(user.getType().getDescription());
			}
		}).register();;
		
		mapperFactory.classMap(UserInsertRequest.class, User.class)
						.customize(new CustomMapper<UserInsertRequest, User>(){
			public void mapAtoB(final UserInsertRequest insertRequest, final User user, final MappingContext context) {
				LOGGER.info("### Custom mapping for UserInsertRequest --> User");
				
				user.setName(insertRequest.getName());
				user.setLastname(insertRequest.getLastname());
				user.setMail(insertRequest.getMail());
				
				UserType type = UserType.valueOf(insertRequest.getType());
				user.setType(type);
			}
		}).register();
		
		mapperFactory.classMap(UserUpdateRequest.class, User.class)
					.customize(new CustomMapper<UserUpdateRequest, User>(){
			public void mapAtoB(final UserUpdateRequest updateRequest, final User user, final MappingContext context) {
				LOGGER.info("### Custom mapping for UserUpdateRequest --> User");

				user.setName(updateRequest.getName());
				user.setLastname(updateRequest.getLastname());
				user.setMail(updateRequest.getMail());

				UserType type = UserType.valueOf(updateRequest.getType());
				user.setType(type);
			}
		}).register();
		
		return mapperFactory.getMapperFacade();
	}
}
