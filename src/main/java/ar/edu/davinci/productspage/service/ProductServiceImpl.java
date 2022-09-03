package ar.edu.davinci.productspage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.exception.BusinessException;

public class ProductServiceImpl implements ProductService{

	@Override
	public Product save(Product product) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product update(Product product) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> list(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
