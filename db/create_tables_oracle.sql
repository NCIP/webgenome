--
-- Reporter
--
CREATE TABLE reporter_2 (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	chromosome INT,
	location LONG,
	PRIMARY KEY (id)
);
CREATE SEQUENCE reporter_seq;

--
-- BioAssayData
--
CREATE TABLE bio_assay_data_2 (
	id NUMBER(38) NOT NULL,
	PRIMARY KEY (id)
);
CREATE SEQUENCE bio_assay_data_seq;

--
-- ChromosomeArrayData
-- FOREIGN KEY (bio_assay_data_id) REFERENCES bio_assay_data_2 (id)
--
CREATE TABLE chromosome_array_data_2 (
	id NUMBER(38) NOT NULL,
	chromosome INT,
	bio_assay_data_id NUMBER(38),
	PRIMARY KEY (id)
);
CREATE SEQUENCE chromosome_array_data_seq;

--
-- ArrayDatum
--
CREATE TABLE array_datum_2 (
	id NUMBER(38) NOT NULL,
	value FLOAT,
	error FLOAT,
	reporter_id NUMBER(38),
	chromosome_array_data_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (reporter_id) REFERENCES reporter_2 (id),
	FOREIGN KEY (chromosome_array_data_id) REFERENCES chromosome_array_data_2 (id)
);
CREATE SEQUENCE array_datum_seq;

--
-- Organism
--
CREATE TABLE organism_2 (
	id NUMBER(38) NOT NULL,
	genus VARCHAR(64),
	species VARCHAR(64),
	PRIMARY KEY (id)
);
INSERT INTO organism_2 (id, genus, species) VALUES (1, 'Homo', 'sapiens');
CREATE SEQUENCE organism_seq START WITH 2;

--
-- ShoppingCart
--
CREATE TABLE shopping_cart_2 (
	id NUMBER(38) NOT NULL,
	user_name VARCHAR(128),
	PRIMARY KEY (id)
);
CREATE SEQUENCE shopping_cart_seq;

--
-- Experiment
--
CREATE TABLE experiment_2 (
	id NUMBER(38) NOT NULL,
	name VARCHAR(256),
	shopping_cart_id NUMBER(38),
	PRIMARY KEY(id)
);
CREATE SEQUENCE experiment_seq;

--
-- BioAssay
--
CREATE TABLE bio_assay_2 (
	id NUMBER(38) NOT NULL,
	bio_assay_data_id NUMBER(38),
	name VARCHAR(256),
	organism_id NUMBER(38),
	experiment_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism_2 (id),
	FOREIGN KEY (experiment_id) REFERENCES experiment_2 (id)
);
CREATE SEQUENCE bio_assay_seq;
