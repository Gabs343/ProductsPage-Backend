package ar.edu.davinci.productspage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.productspage.domain.CashSale;
import ar.edu.davinci.productspage.domain.CreditSale;
import ar.edu.davinci.productspage.domain.Item;
import ar.edu.davinci.productspage.domain.Sale;
import ar.edu.davinci.productspage.exception.BusinessException;

public interface SaleService {
	
	CashSale save(CashSale sale) throws BusinessException;
	CashSale save(CashSale sale, Item item) throws BusinessException;
	
	CreditSale save(CreditSale sale) throws BusinessException;
	CreditSale save(CreditSale sale, Item item) throws BusinessException;
	
	void delete(Sale sale);
	void delete(Long id);
	
	Sale findById(Long id) throws BusinessException;
	
	List<Sale> list();
	Page<Sale> list(Pageable pageable);
	
	long count();
	
	public Sale addItem(Long saleId, Item item) throws BusinessException;
	
	public Sale updateItem(Long saleId, Long itemId, Item item) throws BusinessException;
	
	public Sale deleteItem(Long saleId, Long itemId) throws BusinessException;
}
