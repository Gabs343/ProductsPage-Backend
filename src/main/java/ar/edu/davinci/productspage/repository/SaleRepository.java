package ar.edu.davinci.productspage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.davinci.productspage.domain.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{

}
