package ar.edu.davinci.productspage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.davinci.productspage.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

}
