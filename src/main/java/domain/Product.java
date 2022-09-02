package domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="products")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
	
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
	
	@Column(name = "pdt_type_product")
	@Enumerated(EnumType.STRING)
	private ProductType type;
}
