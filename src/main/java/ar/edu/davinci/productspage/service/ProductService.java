package ar.edu.davinci.productspage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.exception.BusinessException;

public interface ProductService {
	
	Product save(Product product) throws BusinessException;
	
	Product update(Product product) throws BusinessException;
	
	Product findById(Long id) throws BusinessException;
	
	void delete(Product product);
	void delete(Long id);
	
	List<Product> list();
	
	Page<Product> list(Pageable pageable);
	
	long count();
}
