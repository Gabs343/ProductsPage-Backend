package ar.edu.davinci.productspage.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.productspage.domain.Item;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.repository.ItemRepository;

@Service
public class ItemServiceImpl implements ItemService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	private ItemRepository repository;
	
	@Autowired
	public ItemServiceImpl(final ItemRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Item save(Item item) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Saving the user: " + item.toString());
		if(item.getId() == null) return repository.save(item);
		throw new BusinessException("Cannot save the item with an especifict id...");
	}

	@Override
	public Item update(Item item) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Updating the item: " + item.toString());
		if(item.getId() != null) return repository.save(item);
		throw new BusinessException("Cannot update a item that was not created...");
	}

	@Override
	public void delete(Item item) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting Item with the id: " + item.getId());
		repository.delete(item);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting Item with the id: " + id);
		repository.deleteById(id);
	}

	@Override
	public Item findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Finding User with the id: " + id);
		
		Optional<Item> itemOptional = repository.findById(id);
		if(itemOptional.isPresent()) return itemOptional.get();
		throw new BusinessException("Cannot find the user with the id: " + id);
	}

	@Override
	public List<Item> listAll() {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all the Items");
		return repository.findAll();
	}

	@Override
	public Page<Item> list(Pageable pageable) {
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

}
