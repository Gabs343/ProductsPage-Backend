package ar.edu.davinci.productspage.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@PrimaryKeyJoinColumn(name = "sle_id")
@DiscriminatorValue("CREDIT")
@Table(name="credit_sales")


@Data
@NoArgsConstructor
@SuperBuilder
public class CreditSale extends Sale implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5311376345294758357L;
	
	@Column(name = "cdt_fees_amount")
	private Integer feesAmount;
	
	@Column(name = "cdt_coefficient")
	private BigDecimal cardCoefficient;
	
	@Override
	public Double withSurcharge(Double basePrice) {
		// TODO Auto-generated method stub
		return basePrice + (basePrice * 0.01 + cardCoefficient.doubleValue() * feesAmount.doubleValue());
	}

}
