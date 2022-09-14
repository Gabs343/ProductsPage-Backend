package ar.edu.davinci.productspage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ar.edu.davinci.productspage.domain.User;
import ar.edu.davinci.productspage.domain.UserType;
import ar.edu.davinci.productspage.exception.BusinessException;

public interface UserService {
	
	User save(User user) throws BusinessException;
	
	User update(User user) throws BusinessException;
	
	User findById(Long id) throws BusinessException;
	
	void delete(User user);
	void delete(Long id);
	
	List<User> list();
	
	Page<User> list(Pageable pageable);
	
	long count();
	
	List<UserType> getUserTypes();
}
