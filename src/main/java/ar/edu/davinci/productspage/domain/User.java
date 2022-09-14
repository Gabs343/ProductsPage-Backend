package ar.edu.davinci.productspage.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="users")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3794496840063014991L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "usr_id")
	private Long id;
	
	@Column(name = "usr_name")
	private String name;
	
	@Column(name = "usr_lastname")
	private String lastname;
	
	@Column(name = "usr_mail")
	private String mail;
	
	@Column(name = "usr_type_user")
	private UserType type;

}
