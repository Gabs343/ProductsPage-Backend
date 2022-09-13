package ar.edu.davinci.productspage.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.edu.davinci.productspage.service.ProductStateStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="products")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4709040225837229370L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "pdt_id")
	private Long id;
	
	@Column(name = "pdt_name", nullable = false)
	private String name;
	
	@Column(name = "pdt_description", nullable = false)
	private String description;
	
	@Column(name = "pdt_base_price")
	private BigDecimal basePrice;
	
	@Column(name = "pdt_final_price")
	private BigDecimal finalPrice;
	
	@Column(name = "pdt_type_product")
	@Enumerated(EnumType.STRING)
	private ProductType type;
	
	@Column(name = "pdt_state_product")
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@JsonIgnore
	@Transient
	private ProductStateStrategy stateStrategy;
	
	@JsonIgnore
	@OneToOne(targetEntity = Stock.class, cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="pdt_stock_id", referencedColumnName="stk_id", nullable=false)
	private Stock stock;
	
	public void addQuantity(Integer addQuantity) {
		stock.addQuantity(addQuantity);;
	}

	public void removeQuantity(Integer removeQuantity) {
		stock.removeQuantity(removeQuantity);;
	}
	
	public Integer getQuantity() {
		return stock.getQuantity();
	}
}
