package ar.edu.davinci.productspage.controller.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
	
	private Long id;
	
	private String name;
	
	private String lastname;
	
	private String mail;
	
	private String type;

}
