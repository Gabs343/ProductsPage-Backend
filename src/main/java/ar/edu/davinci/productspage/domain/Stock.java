package ar.edu.davinci.productspage.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ar.edu.davinci.productspage.service.ProductStateStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="stocks")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "stk_id")
	private Long id;
	
	@Column(name = "stk_quantity")
	private Integer quantity;
	
	public Integer getQuantity() {
		return quantity;
	}
	
	public void addQuantity(Integer addQuantity) {
		quantity += addQuantity;
	}
	
	public void removeQuantity(Integer removeQuantity) {
		quantity -= removeQuantity;
	}
}
