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

import ar.edu.davinci.productspage.controller.request.UserInsertRequest;
import ar.edu.davinci.productspage.controller.request.UserUpdateRequest;
import ar.edu.davinci.productspage.controller.response.UserResponse;
import ar.edu.davinci.productspage.domain.User;
import ar.edu.davinci.productspage.exception.BusinessException;
import ar.edu.davinci.productspage.service.UserService;
import ma.glasnost.orika.MapperFacade;

@RestController
public class UserControllerRest extends ShopApp{
	
	private final Logger LOGGER = LoggerFactory.getLogger(UserControllerRest.class);

	@Autowired
	private UserService service;
	
	@Autowired
	private MapperFacade mapper;
	
	@GetMapping(path = "/users/all")
	public List<User> getList(){
		LOGGER.info("User list");
		return service.list();
	}
	
	@GetMapping(path = "/users")
	public ResponseEntity<Page<UserResponse>> getList(Pageable pageable){
		LOGGER.info("List of paginated users");
		LOGGER.info("Pageable: " + pageable);
		
		Page<UserResponse> usersResponse = null;
		Page<User> users = null;
		
		try {
			users = service.list(pageable);
		}catch(Exception e) {
			LOGGER.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		try {
			usersResponse = users.map(user -> mapper.map(user, UserResponse.class));
		}catch(Exception e) {
			LOGGER.error("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(usersResponse, HttpStatus.OK);
	}
	
	@GetMapping(path = "/users/{id}")
	public ResponseEntity<Object> getUser(@PathVariable Long id){
		LOGGER.info("Returning the required user");
		
		UserResponse userResponse = null;
		User user = null;
		
		try {
			user = service.findById(id);
		}catch(BusinessException e) {
			LOGGER.error("Error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			userResponse = mapper.map(user, UserResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	@PostMapping(path = "/users")
	public ResponseEntity<UserResponse> createUser(@RequestBody UserInsertRequest userData){
		
		User user = null;
		UserResponse userResponse = null;
	
		try {
			user = mapper.map(userData, User.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			user = service.save(user);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
		
		try {
			userResponse = mapper.map(user, UserResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	@PutMapping(path = "/users/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userData){
		
		User userToModify = null;
		User newUser = null;
		UserResponse userResponse = null;
		
		try {
			newUser = mapper.map(userData, User.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		try {
			userToModify = service.findById(id);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		if(Objects.nonNull(userResponse)) {
			userToModify.setName(newUser.getName());
			userToModify.setLastname(newUser.getLastname());
			userToModify.setMail(newUser.getMail());
			userToModify.setType(newUser.getType());
			
			try {
				userToModify = service.update(userToModify);
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
			LOGGER.error("The User to modify is null");
			
			return new ResponseEntity<>(null, HttpStatus.CREATED);
		}
		
		try {
			userResponse = mapper.map(userToModify, UserResponse.class);
		}catch(Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
		try {
			service.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
}
