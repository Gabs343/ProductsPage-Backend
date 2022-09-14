package ar.edu.davinci.productspage.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ar.edu.davinci.productspage.domain.User;
import ar.edu.davinci.productspage.domain.UserType;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	private UserRepository repository;
	
	@Autowired
	public UserServiceImpl(final UserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public User save(User user) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Saving the user: " + user.toString());
		if(user.getId() == null) return repository.save(user);
		throw new BusinessException("Cannot save the user with an especifict id...");
	}

	@Override
	public User update(User user) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Updating User: " + user.toString());
		if(user.getId() != null) return repository.save(user);
		throw new BusinessException("Cannot update a user that was not created... ");
	}

	@Override
	public User findById(Long id) throws BusinessException {
		// TODO Auto-generated method stub
		LOGGER.debug("Finding User with the id: " + id);
		Optional<User> userOptional = repository.findById(id);
		if(userOptional.isPresent()) {
			return userOptional.get();
		}
		
		throw new BusinessException("Cannot find the user with the id: " + id);
	}

	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting User: " + user.toString());
		repository.delete(user);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting User with the id: " + id);
		repository.deleteById(id);
	}

	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all the Users");
		return repository.findAll();
	}

	@Override
	public Page<User> list(Pageable pageable) {
		// TODO Auto-generated method stub
		LOGGER.debug("Listing all the Users in pages");
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
	public List<UserType> getUserTypes() {
		// TODO Auto-generated method stub
		return UserType.getTypes();
	}

}
