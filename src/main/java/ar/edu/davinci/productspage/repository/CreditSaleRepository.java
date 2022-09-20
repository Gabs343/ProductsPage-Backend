package ar.edu.davinci.productspage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.productspage.domain.CreditSale;

@Repository
public interface CreditSaleRepository extends JpaRepository<CreditSale, Long>{

}
