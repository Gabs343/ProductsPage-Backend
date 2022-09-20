package ar.edu.davinci.productspage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.productspage.domain.CashSale;
import ar.edu.davinci.productspage.domain.CreditSale;
import ar.edu.davinci.productspage.domain.Item;
import ar.edu.davinci.productspage.domain.Product;
import ar.edu.davinci.productspage.domain.Sale;
import ar.edu.davinci.productspage.domain.User;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.repository.CashSaleRepository;
import ar.edu.davinci.productspage.repository.CreditSaleRepository;
import ar.edu.davinci.productspage.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(SaleServiceImpl.class);
	
	private final SaleRepository saleRepository;
	private final CashSaleRepository cashSaleRepository;
	private final CreditSaleRepository creditSaleRepository;
	
	private final UserService userService;
	private final ProductService productService;
	private final ItemService itemService;
	
	@Autowired
	public SaleServiceImpl(final SaleRepository saleRepository,
							final CashSaleRepository cashSaleRepository,
							final CreditSaleRepository creditSaleRepository,
							final UserService userService,
							final ProductService productService,
							final ItemService itemService) {
		
		this.saleRepository = saleRepository;
		this.cashSaleRepository = cashSaleRepository;
		this.creditSaleRepository = creditSaleRepository;
		this.userService = userService;
		this.productService = productService;
		this.itemService = itemService;
	}

	@Override
	public CashSale save(CashSale sale) throws BusinessException {
		// TODO Auto-generated method stub
		User user = null;
		if(sale.getUser().getId() != null) {
			user = getUser(sale.getUser().getId());
		}else {
			throw new BusinessException("The user is mandatory for this operation");
		}
		
		List<Item> items = new ArrayList<Item>();
		if(sale.getItems() != null) {
			items = getItems(sale.getItems());
		}
		
		sale = CashSale.builder()
				.user(user)
				.items(items)
				.saleDate(Calendar.getInstance().getTime())
				.build();
		
		return cashSaleRepository.save(sale);
	}

	@Override
	public CashSale save(CashSale sale, Item item) throws BusinessException {
		// TODO Auto-generated method stub
		sale.addItem(item);
		return cashSaleRepository.save(sale);
	}

	@Override
	public CreditSale save(CreditSale sale) throws BusinessException {
		// TODO Auto-generated method stub
		User user = null;
		if(sale.getUser().getId() != null) {
			user = getUser(sale.getUser().getId());
		}else {
			throw new BusinessException("The User is mandatory for this operation");
		}
		
		List<Item> items = new ArrayList<Item>();
		if(sale.getItems() != null) {
			items = getItems(sale.getItems());
		}
		
		sale = CreditSale.builder()
				.user(user)
				.items(items)
				.cardCoefficient(sale.getCardCoefficient())
				.feesAmount(sale.getFeesAmount())
				.saleDate(Calendar.getInstance().getTime())
				.build();
		
		return creditSaleRepository.save(sale);
	}

	@Override
	public CreditSale save(CreditSale sale, Item item) throws BusinessException {
		// TODO Auto-generated method stub
		sale.addItem(item);
		return creditSaleRepository.save(sale);
	}

	@Override
	public void delete(Sale sale) {
		// TODO Auto-generated method stub
		saleRepository.delete(sale);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		saleRepository.deleteById(id);
	}

	@Override
	public Sale findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Finding the sale for the id: " + id);
		Optional<Sale> saleOptional = saleRepository.findById(id);
		if(saleOptional.isPresent()) {
			return saleOptional.get();
		}
		throw new BusinessException("Canoot find the sale with the id: " + id);
	}

	@Override
	public List<Sale> list() {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all sales");
		return saleRepository.findAll();
	}

	@Override
	public Page<Sale> list(Pageable pageable) {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all sales for pages");
		LOGGER.debug("Pageable: offset: " + pageable.getOffset() +
					", pageSize: " + pageable.getPageSize() + 
					" and pageNumber: " + pageable.getPageNumber());
		return saleRepository.findAll(pageable);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return saleRepository.count();
	}

	@Override
	public Sale addItem(Long saleId, Item item) throws BusinessException {
		// TODO Auto-generated method stub
		Sale sale = findById(saleId);
		Product product = getProduct(item.getProduct().getId());
		
		if(item.getQuantity() <= product.getQuantity()) {
			product.removeQuantity(item.getQuantity());
			
			product = productService.update(product);
			
			Item newItem = Item.builder()
					.product(product)
					.sale(sale)
					.quantity(item.getQuantity())
					.build();
			
			newItem = itemService.save(newItem);
			
			sale.addItem(newItem);
			return sale;
		}
		
		throw new BusinessException("The requested quantity is bigger than the stock");
		
	}

	@Override
	public Sale updateItem(Long saleId, Long itemId, Item item) throws BusinessException {
		// TODO Auto-generated method stub
		Sale sale = findById(saleId);
		Item actualItem = getItem(itemId);
		
		if(item.getQuantity() <= actualItem.getProduct().getQuantity()) {
			actualItem.getProduct().addQuantity(actualItem.getQuantity());
			actualItem.setQuantity(item.getQuantity());
			actualItem.getProduct().removeQuantity(item.getQuantity());
			
			productService.update(actualItem.getProduct());
			
			actualItem.setQuantity(item.getQuantity());
			
			actualItem = itemService.update(actualItem);
			return sale;
		}
		
		throw new BusinessException("The requested quantity is bigger than the stock");
	}

	@Override
	public Sale deleteItem(Long saleId, Long itemId) throws BusinessException {
		// TODO Auto-generated method stub
		Sale sale = findById(saleId);
		Item item = getItem(itemId);
		
		item.getProduct().addQuantity(item.getQuantity());
		productService.update(item.getProduct());
		
		itemService.delete(itemId);
		
		sale.getItems().remove(item);
		saleRepository.save(sale);
		
		return sale;
	}
	
	private User getUser(Long userId) throws BusinessException{
		return userService.findById(userId);
	}
	
	private List<Item> getItems(List<Item> requestItems) throws BusinessException{
		List<Item> items = new ArrayList<Item>();
		for(Item requestItem: requestItems) {
			Product product = getProduct(requestItem.getProduct().getId());
			
			Item item = Item.builder()
						.quantity(requestItem.getQuantity())
						.product(product)
						.build();
			
			items.add(item);
		}
		
		
		return items;
	}
	
	private Item getItem(Long itemId) throws BusinessException{
		if(itemId != null) {
			return itemService.findById(itemId);
		}
		throw new BusinessException("The item is mandatory for this operation");
	}
	
	private Product getProduct(Long productId) throws BusinessException{
		if(productId != null) {
			return productService.findById(productId);
		}
		throw new BusinessException("The product is mandatory for this operation");
	}

}
