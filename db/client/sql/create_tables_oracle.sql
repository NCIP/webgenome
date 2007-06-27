--
-- Principal
--
CREATE TABLE principal (
    id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	password VARCHAR(48),
	admin CHAR(1),
	PRIMARY KEY (id)
);
INSERT INTO principal (id, name, password, admin) values (1, 'admin', 'cureforcancer', 'T');

--
-- Organism
--
CREATE TABLE organism (
	id NUMBER(38) NOT NULL,
	genus VARCHAR(64),
	species VARCHAR(64),
	PRIMARY KEY (id)
);
INSERT INTO organism (id, genus, species) VALUES (1, 'Homo', 'sapiens');

--
-- Array
--
CREATE TABLE array (
	id NUMBER(38) NOT NULL,
	name VARCHAR(128),
	PRIMARY KEY (id)
);

--
-- Array.chromosomeReportersFileNames map
--
CREATE TABLE reporters_file_names (
	array_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	file_name VARCHAR(512),
	PRIMARY KEY (array_id, chromosome),
	FOREIGN KEY (array_id) REFERENCES array(id)
);

--
-- CytologicalMap
--
CREATE TABLE cytological_map (
	id NUMBER(38) NOT NULL,
	chromosome INT,
	centromere_start NUMBER(38),
	centromere_end NUMBER(38),
	organism_id number(38),
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism(id)
);

--
-- Cytoband
--
CREATE TABLE cytoband (
	id NUMBER(38) NOT NULL,
	name VARCHAR(16),
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	stain VARCHAR(16),
	cytological_map_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (cytological_map_id) REFERENCES cytological_map(id)
);

--
-- AnnotatedGenomeFeature
--
CREATE TABLE annotated_genome_feature (
	id NUMBER(38) NOT NULL,
	name VARCHAR(64),
	annotation_type VARCHAR(16),
	quantitation NUMBER(16),
	chromosome INT,
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	parent_id NUMBER(38),
	organism_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism(id)
);

--
-- Job
--
CREATE TABLE job (
	id NUMBER(38) NOT NULL,
	type VARCHAR(16),
	user_id VARCHAR(64),
	instantiation_date TIMESTAMP,
	start_date TIMESTAMP,
	end_date TIMESTAMP,
	termination_message VARCHAR(256),
	PRIMARY KEY (id)
);

--
-- Sequence used to assign unique IDs to all
-- subclasses of UserConfigurableProperty
--
CREATE SEQUENCE seq_prop_id START WITH 1 INCREMENT BY 1;

--
-- SimpleUserConfigurableProperty
--
CREATE TABLE simp_user_conf_prop (
	id NUMBER(38) NOT NULL,
	current_value VARCHAR(128),
	display_name VARCHAR(128),
	name VARCHAR(128),
	PRIMARY KEY (id)
);

--
-- UserConfigurablePropertyWithOptions
--
CREATE TABLE user_conf_prop_opt (
	id NUMBER(38) NOT NULL,
	current_value VARCHAR(128),
	display_name VARCHAR(128),
	name VARCHAR(128),
	PRIMARY KEY (id)
);

--
-- Specific options for a UserConfigurablePropertyWithOptions
-- (i.e, the 'options' attribute.
--
CREATE TABLE prop_options (
	code VARCHAR(128),
	display_name VARCHAR(128),
	user_conf_prop_opt_id NUMBER(38) NOT NULL,
	PRIMARY KEY (user_conf_prop_opt_id, code),
	FOREIGN KEY (user_conf_prop_opt_id) REFERENCES user_conf_prop_opt(id)
);

CREATE SEQUENCE seq_ds_props_id START WITH 1 INCREMENT BY 1;

--
-- SimulatedDataSourceProperties
--
CREATE TABLE sim_data_src_props (
	id NUMBER(38) NOT NULL,
	client_id VARCHAR(128),
	PRIMARY KEY (id)
);

--
-- EjbDataSourceProperties
--
CREATE TABLE ejb_data_src_props (
	id NUMBER(38) NOT NULL,
	client_id VARCHAR(128),
	jndi_name VARCHAR(256),
	jndi_provider_url VARCHAR(1024),
	PRIMARY KEY (id)
);

--
-- BioAssay
--
CREATE TABLE bio_assay (
	id NUMBER(38) NOT NULL,
	name VARCHAR(256),
	PRIMARY KEY (id)
);

--
-- Experiment
--
CREATE TABLE experiment (
	id NUMBER(38) NOT NULL,
	name VARCHAR(256),
	PRIMARY KEY (id)
);
