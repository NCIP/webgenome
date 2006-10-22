--
-- User
--
CREATE TABLE principal (
    id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	password VARCHAR(48),
	PRIMARY KEY (id)
);

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

--
-- BioAssayData
--
CREATE TABLE bio_assay_data_2 (
	id NUMBER(38) NOT NULL,
	PRIMARY KEY (id)
);

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

--
-- ShoppingCart
--
CREATE TABLE shopping_cart_2 (
	id NUMBER(38) NOT NULL,
	user_name VARCHAR(128),
	PRIMARY KEY (id)
);

--
-- Image
--
CREATE TABLE plot_2 (
	id NUMBER(38) NOT NULL,
	name VARCHAR(128),
	shopping_cart_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart_2(id)
);

--
-- Experiment
--
CREATE TABLE experiment_2 (
	id NUMBER(38) NOT NULL,
	name VARCHAR(256),
	shopping_cart_id NUMBER(38),
	PRIMARY KEY(id)
);

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

--
-- CytologicalMap
--
CREATE TABLE cytological_map_2 (
	id NUMBER(38) NOT NULL,
	chromosome INT,
	centromere_start NUMBER(38),
	centromere_end NUMBER(38),
	organism_id number(38),
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism_2(id)
);

--
-- Cytoband
--
CREATE TABLE cytoband_2 (
	id NUMBER(38) NOT NULL,
	name VARCHAR(16),
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	stain VARCHAR(16),
	cytological_map_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (cytological_map_id) REFERENCES cytological_map_2(id)
);

--
-- Processing Job
--
CREATE TABLE processing_job (
    job_id           NUMBER(38) NOT NULL,
    request_id       VARCHAR2(50) NULL,
    user_id          VARCHAR2(100) NOT NULL,   
    type             VARCHAR2(128) NULL,
    percent_complete NUMBER(4) NULL,
    created_dt       DATE NULL,
    modified_dt      DATE NULL,
    job_properties   VARCHAR2(500) NULL

    PRIMARY KEY (job_id)
);

--
-- Processing Job Status
--
CREATE TABLE processing_job_status (
    status_id     NUMBER(38) NOT NULL,
    job_id        NUMBER(38) NOT NULL,
    status        VARCHAR2(50) NOT NULL,
    datetime      TIMESTAMP NULL,
    created_dt    DATE NULL,
    modified_dt   DATE NULL,

    PRIMARY KEY   (status_id),
    FOREIGN KEY   (job_id) REFERENCES processing_job (job_id)
);
