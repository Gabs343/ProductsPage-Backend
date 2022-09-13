--
--	DROP OF TABLES
--

DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS stocks;

--
-- CREATE OF TABLES
--

CREATE TABLE STOCKS(
	stk_id bigint NOT NULL AUTO_INCREMENT,
	stk_quantity int DEFAULT NULL,
	PRIMARY KEY(stk_id)
);

CREATE TABLE PRODUCTS(
	pdt_id bigint NOT NULL AUTO_INCREMENT,
	pdt_name varchar(255) DEFAULT NULL,
	pdt_description varchar(255) DEFAULT NULL,
	pdt_base_price decimal(19, 2) DEFAULT NULL,
	pdt_final_price decimal(19, 2) DEFAULT NULL,
	pdt_type_product varchar(255) DEFAULT NULL,
	pdt_state_product varchar(255) DEFAULT NULL,
	pdt_stock_id bigint NOT NULL,
	CONSTRAINT pdt_stock_fk FOREIGN KEY (pdt_stock_id) REFERENCES stocks(stk_id),
	PRIMARY KEY(pdt_id)	
);