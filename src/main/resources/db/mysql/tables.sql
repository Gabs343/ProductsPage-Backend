--
--	DROP OF TABLES
--

DROP TABLE IF EXISTS products;

--
-- CREATE OF TABLES
--

CREATE TABLE PRODUCTS(
	pdt_id bigint NOT NULL AUTO_INCREMENT,
	pdt_name varchar(255) DEFAULT NULL,
	pdt_description varchar(255) DEFAULT NULL,
	pdt_base_price decimal(19, 2) DEFAULT NULL,
	pdt_type_product varchar(255) DEFAULT NULL,
	pdt_state_product varchar(255) DEFAULT NULL,
	PRIMARY KEY(pdt_id)
	
);