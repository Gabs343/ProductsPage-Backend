package ar.edu.davinci.productspage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.productspage.domain.CashSale;

@Repository
public interface CashSaleRepository extends JpaRepository<CashSale, Long>{

}
