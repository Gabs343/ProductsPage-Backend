package ar.edu.davinci.productspage.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="items_sale")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Item implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -5538591139347342512L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "itm_id")
	private Long id;
	
	@Column(name = "itm_quantity")
	private Integer quantity;
	
	@ManyToOne(targetEntity = Product.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "itm_pdt_id", referencedColumnName = "pdt_id", nullable = false)
	private Product product;
}
