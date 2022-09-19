package ar.edu.davinci.productspage.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="sales_type")
@Table(name="sales")

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public abstract class Sale implements Serializable{

	private static final long serialVersionUID = 855869872685414050L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "sle_id")
	private Long id;
	
	@ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "sle_usr_id", referencedColumnName = "usr_id", nullable = false)
	private User user;
	
	@Column(name = "sle_date")
	@Temporal(TemporalType.DATE)
	private Date saleDate;
	
	@OneToMany(mappedBy="sale", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonManagedReference
	private List<Item> items;
	
	public void addItem(Item item) {
		if(items == null) {
			items = new ArrayList<Item>();
		}
		items.add(item);
	}
	
	public BigDecimal getGrossAmount() {
		Double grossAmount = items.stream().collect(
				Collectors.summingDouble(item -> item.getPrice().doubleValue()));
		return new BigDecimal(grossAmount).setScale(2, RoundingMode.UP);
	}
	
	public BigDecimal getFinalAmount() {
		Double finalPrice = items.stream().collect(
				Collectors.summingDouble(item -> withSurcharge(item.getPrice().doubleValue())));
		return new BigDecimal(finalPrice).setScale(2, RoundingMode.UP);
	}
	
	public abstract Double withSurcharge(Double basePrice);
	
}
