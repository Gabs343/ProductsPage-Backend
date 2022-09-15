package ar.edu.davinci.productspage.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInsertRequest {
	
	private String name;
	
	private String lastname;
	
	private String mail;
	
	private String type;
}
