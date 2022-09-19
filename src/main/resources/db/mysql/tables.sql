--
--	DROP OF TABLES
--

DROP TABLE IF EXISTS credit_sales;

DROP TABLE IF EXISTS cash_sales;

DROP TABLE IF EXISTS items_sale;

DROP TABLE IF EXISTS sales;

DROP TABLE IF EXISTS products;

DROP TABLE IF EXISTS stocks;

DROP TABLE IF EXISTS users;


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

CREATE TABLE USERS(
	usr_id bigint NOT NULL AUTO_INCREMENT,
	usr_name varchar(25) DEFAULT NULL,
	usr_lastname varchar(255) DEFAULT NULL,
	usr_mail varchar(255) DEFAULT NULL,
	usr_type_user varchar(255) DEFAULT NULL,
	PRIMARY KEY(usr_id)
);

CREATE TABLE SALES(
	sle_id bigint NOT NULL AUTO_INCREMENT,
	sle_types_sales varchar(30) NOT NULL,
	sle_date datetime(6) DEFAULT NULL,
	sle_usr_id bigint NOT NULL,
	CONSTRAINT sle_usr_fk FOREIGN KEY (sle_usr_id) REFERENCES users (usr_id),
	PRIMARY KEY (sle_id)
);

CREATE TABLE ITEMS_SALE(
	itm_id bigint NOT NULL AUTO_INCREMENT,
	itm_quantity INT DEFAULT NULL,
	itm_pdt_id bigint NOT NULL,
	itm_sle_id bigint NOT NULL,
	CONSTRAINT itm_pdt_fk FOREIGN KEY (itm_pdt_id) REFERENCES products(pdt_id),
	CONSTRAINT itm_sle_fk FOREIGN KEY (itm_sle_id) REFERENCES sales (sle_id),
	PRIMARY KEY(itm_id)
);

CREATE TABLE CASH_SALES(
	sle_id bigint NOT NULL,
	CONSTRAINT csl_sle_fk FOREIGN KEY (sle_id) REFERENCES sales (sle_id),
	PRIMARY KEY (sle_id)
);

CREATE TABLE CREDIT_SALES(
	sle_id bigint NOT NULL,
	cdt_fees_amount INT DEFAULT NULL,
	cdt_coefficient INT DEFAULT NULL,
	CONSTRAINT cdt_sle_fk FOREIGN KEY (sle_id) REFERENCES sales (sle_id),
	PRIMARY KEY (sle_id)
);