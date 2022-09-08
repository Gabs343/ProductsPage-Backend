package ar.edu.davinci.productspage.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.ProductType;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private ProductRepository repository;
	
	@Autowired
	public ProductServiceImpl(final ProductRepository repository) {
		this.repository = repository;
	}

	@Override
	public Product save(Product product) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Saving the Product: " + product.toString());
		if(product.getId() == null) {
			return repository.save(product);
		}
		throw new BusinessException("Cannot save the product with an especifict id...");
	}

	@Override
	public Product update(Product product) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Updating Product: " + product.toString());
		if(product.getId() != null) {
			return repository.save(product);
		}
		throw new BusinessException("Cannot update a product that was not created...");
	}
	
	@Override
	public Product findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Finding Product with the id: " + id);
		Optional<Product> productOptional = repository.findById(id);
		if(productOptional.isPresent()) {
			return productOptional.get();
		}
		throw new BusinessException("Cannot find the product with the id: " + id);
	}

	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting Product: " + product.toString());
		repository.delete(product);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting Product with the id: " + id);
		repository.deleteById(id);
	}

	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all the Products");
		return repository.findAll();
	}

	@Override
	public Page<Product> list(Pageable pageable) {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all the Products in pages");
		LOGGER.debug("Pageable: offset: " + pageable.getOffset() + 
					", Page Size: " + pageable.getPageSize() + 
					" and Page Number: " + pageable.getPageNumber());
		return repository.findAll(pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return repository.count();
	}

	@Override
	public List<ProductType> getProductTypes() {
		// TODO Auto-generated method stub
		return ProductType.getProductTypes();
	}

}
