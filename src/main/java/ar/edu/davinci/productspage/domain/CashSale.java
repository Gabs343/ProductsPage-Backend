package ar.edu.davinci.productspage.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "sle_id")
@DiscriminatorValue("CASH")
@Table(name="cash_sales")

@Data
@NoArgsConstructor(force = true)
@SuperBuilder
public class CashSale extends Sale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5732946603177000131L;

	@Override
	public Double withSurcharge(Double basePrice) {
		// TODO Auto-generated method stub
		return basePrice;
	}

}
