package ar.edu.davinci.productspage.controller.rest;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.davinci.productspage.controller.request.ProductInsertRequest;
import ar.edu.davinci.productspage.controller.request.ProductUpdateRequest;
import ar.edu.davinci.productspage.controller.request.StockAddRequest;
import ar.edu.davinci.productspage.controller.request.StockRemoveRequest;
import ar.edu.davinci.productspage.controller.response.ProductResponse;
import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.Stock;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.service.ProductService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class ProductControllerRest extends ShopApp{
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductControllerRest.class);
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private MapperFacade mapper;
	
	@GetMapping(path = "/products/all")
	public List<Product> getList(){
		LOGGER.info("Products list");
		return service.list();
	}
	
	@GetMapping(path = "/products")
	public ResponseEntity<Page<ProductResponse>> getList(Pageable pageable){
		LOGGER.info("List of paginated products");
		LOGGER.info("Pageable: " + pageable);
		
		Page<ProductResponse> productResponse = null;
		Page<Product> products = null;
		
		try {
			products = service.list(pageable);
		}catch(Exception e) {
			LOGGER.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			productResponse = products.map(product -> mapper.map(product, ProductResponse.class));
		}catch(Exception e) {
			LOGGER.error("Error: " +  e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
	
	@GetMapping(path = "/products/{id}")
	public ResponseEntity<Object> getProduct(@PathVariable Long id){
		LOGGER.info("Returning the required product");
		
		ProductResponse productResponse = null;
		Product product = null;
		
		try {
			product = service.findById(id);
		}catch(BusinessException e) {
			LOGGER.error("Error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			productResponse = mapper.map(product, ProductResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(productResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = "/products")
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductInsertRequest productData){
		
		Product product = null;
		ProductResponse productResponse = null;
		
		try {
			product = mapper.map(productData, Product.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			product = service.save(product);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		
		try {
			productResponse = mapper.map(product, ProductResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody ProductUpdateRequest productData){
		
		Product productToModify = null;
		Product newProduct = null;
		ProductResponse productResponse = null;
		
		try {
			newProduct = mapper.map(productData, Product.class);
		}catch(Exception e){
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			productToModify = service.findById(id);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		if(Objects.nonNull(productToModify)) {
			productToModify.setName(newProduct.getName());
			productToModify.setDescription(newProduct.getDescription());
			productToModify.setType(newProduct.getType());
			productToModify.setState(newProduct.getState());
			productToModify.setBasePrice(newProduct.getBasePrice());
			productToModify.setFinalPrice(newProduct.getFinalPrice());
			
			try {
				productToModify = service.update(productToModify);
			}catch(BusinessException e) {
				LOGGER.error(e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
			}catch(Exception e) {
				LOGGER.error(e.getMessage());
				
				e.printStackTrace();
				
				return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
			}
		}else {
			LOGGER.error("The Product to modify is null");
			
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		}
		
		try {
			productResponse = mapper.map(productToModify, ProductResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	
	}
	
	@PutMapping("/products/{id}/stocks/add")
	public ResponseEntity<Object> addingQuantityToStock(@PathVariable("id") Long id, @RequestBody StockAddRequest stockData){
		
		Product productToModify = null;
		Stock newStock = null;
		ProductResponse productResponse = null;
		
		try {
			newStock = mapper.map(stockData, Stock.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			productToModify = service.findById(id);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		if(Objects.nonNull(newStock)) {
			productToModify.addQuantity(newStock.getQuantity());
			
			try {
				productToModify = service.update(productToModify);
			}catch(BusinessException e){			
				LOGGER.error(e.getMessage());
				e.printStackTrace();
				return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
			}catch(Exception e) {
				LOGGER.error(e.getMessage());
				
				e.printStackTrace();
				
				return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
			}
			
		}else {
			LOGGER.error("The Stock to modify is null");
			
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		}
		
		try {
			productResponse = mapper.map(productToModify, ProductResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}
	
	@PutMapping("/products/{id}/stocks/remove")
	public ResponseEntity<Object> removingQuantityToStock(@PathVariable("id") Long id, @RequestBody StockRemoveRequest stockData){
		
		Product productToModify = null;
		Stock newStock = null;
		ProductResponse productResponse = null;
		
		try {
			newStock = mapper.map(stockData, Stock.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			productToModify = service.findById(id);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		if(Objects.nonNull(newStock)) {
			if(newStock.getQuantity() <= productToModify.getQuantity()) {
				productToModify.removeQuantity(newStock.getQuantity());
				
				try {
					productToModify = service.update(productToModify);
				}catch(BusinessException e){			
					LOGGER.error(e.getMessage());
					e.printStackTrace();
					return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
				}catch(Exception e) {
					LOGGER.error(e.getMessage());
					
					e.printStackTrace();
					
					return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
				}
			}else {
				LOGGER.error("The Stock dont have that quantity of products...");
				return new ResponseEntity<>(null, HttpStatus.CREATED);
			}
			
			
		}else {
			LOGGER.error("The Stock to modify is null");
			
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		}
		
		try {
			productResponse = mapper.map(productToModify, ProductResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") Long id){
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
