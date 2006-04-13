## Conversion from Cloudscape to Oracle
## 1. BIGINT -> NUMBER(38)
##    SMALLINT -> NUMBER(5)
## 2. number -> num  (Table:chromosome)

--
-- Organism
--
CREATE TABLE organism (
	id NUMBER(38) NOT NULL,
	genus VARCHAR(48),
	species VARCHAR(48),
	PRIMARY KEY (id)
);
INSERT INTO organism(id, genus, species) VALUES (1, 'Homo', 'sapiens');
INSERT INTO organism(id, genus, species) VALUES (2, 'Mus', 'musculus');

--
-- GenomeAssembly
--
CREATE TABLE genome_assembly (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	organism_id NUMBER(38),
	PRIMARY KEY(id),
	FOREIGN KEY (organism_id) REFERENCES organism(id)
);
INSERT INTO genome_assembly (id, name, organism_id) VALUES (1, 'Unspecified', 1);
INSERT INTO genome_assembly (id, name, organism_id) VALUES (2, 'Unspecified', 2);

--
-- Chromosome
--
CREATE TABLE chromosome (
	id NUMBER(38) NOT NULL,
	num NUMBER(5) NOT NULL,
	length NUMBER(38),
	genome_assembly_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id)
);

--
-- GenomeLocation
--
CREATE TABLE genome_location (
	id NUMBER(38) NOT NULL,
	location NUMBER(38),
	chromosome_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (chromosome_id) REFERENCES chromosome(id)
);

--
-- QuantitationType
--
CREATE TABLE quantitation_type (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	PRIMARY KEY (id)
);

--
-- Array
--
CREATE TABLE array (
	id NUMBER(38) NOT NULL,
	vendor VARCHAR(48),
	name VARCHAR(48),
	organism_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism(id)
);
INSERT INTO array (id, vendor, name, organism_id) VALUES (1, '', 'Unspecified', 1);
INSERT INTO array (id, vendor, name, organism_id) VALUES (2, '', 'Unspecified', 2);

--
-- ArrayMapping
--
CREATE TABLE array_mapping (
	id NUMBER(38) NOT NULL,
	array_id NUMBER(38),
	genome_assembly_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (array_id) REFERENCES array(id),
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id)
);
INSERT INTO array_mapping (id, array_id, genome_assembly_id) VALUES (1, 1, 1);
INSERT INTO array_mapping (id, array_id, genome_assembly_id) VALUES (2, 2, 2);

--
-- Reporter
--
CREATE TABLE reporter (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	array_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (array_id) REFERENCES array(id)
);

--
-- ReporterMapping
--
CREATE TABLE reporter_mapping (
	id NUMBER(38) NOT NULL,
	genome_location_id NUMBER(38),
	reporter_id NUMBER(38),
	array_mapping_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (genome_location_id) REFERENCES genome_location(id),
	FOREIGN KEY (reporter_id) REFERENCES reporter(id),
	FOREIGN KEY (array_mapping_id) REFERENCES array_mapping(id)
);

--
-- Quantitation
--
CREATE TABLE quantitation (
	id NUMBER(38) NOT NULL,
	value FLOAT,
	quantitation_type_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (quantitation_type_id) REFERENCES quantitation_type (id)
);

--
-- Experiment
--
CREATE TABLE experiment (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	description VARCHAR(1024),
	virtual CHAR(1),
	database_name VARCHAR(48),
	user_name VARCHAR(48),
	PRIMARY KEY (id)
);

--
-- BioAssayData
--
CREATE TABLE bio_assay_data (
	id NUMBER(38) NOT NULL,
	PRIMARY KEY (id)
);


--
-- BinnedData
--
CREATE TABLE binned_data (
	id NUMBER(38) NOT NULL,
	quantitation_type_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (quantitation_type_id) REFERENCES quantitation_type(id)
);

--
-- ChromosomeBin
--
CREATE TABLE chromosome_bin (
	id NUMBER(38) NOT NULL,
	bin INT,
	value FLOAT,
	chromosome_num INT,
	binned_data_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (binned_data_id) REFERENCES binned_data(id)
);

--
-- BioAssay
--
CREATE TABLE bio_assay (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	description VARCHAR(1024),
	experiment_id NUMBER(38),
	bio_assay_data_id VARCHAR(256),
	binned_data_id NUMBER(38),
	database_name VARCHAR(48),
	PRIMARY KEY (id),
	FOREIGN KEY (experiment_id) REFERENCES experiment(id),
	FOREIGN KEY (binned_data_id) REFERENCES binned_data(id)
);

--
-- ArrayDatum
--
CREATE TABLE array_datum (
	id NUMBER(38) NOT NULL,
	reporter_id NUMBER(38),
	quantitation_id NUMBER(38),
	bio_assay_data_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (reporter_id) REFERENCES reporter(id),
	FOREIGN KEY (quantitation_id) REFERENCES quantitation(id),
	FOREIGN KEY (bio_assay_data_id) REFERENCES bio_assay_data(id)
);

--
-- CytologicalMapSet
--
CREATE TABLE cytological_map_set (
	id NUMBER(38) NOT NULL,
	upload_date DATE,
	genome_assembly_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id)
);

--
-- CytologicalMap
--
CREATE TABLE cytological_map (
	id NUMBER(38) NOT NULL,
	chromosome_id NUMBER(38),
	centromere_start NUMBER(38),
	centromere_end NUMBER(38),
	cytological_map_set_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (chromosome_id) REFERENCES chromosome(id),
	FOREIGN KEY (cytological_map_set_id) REFERENCES cytological_map_set(id)
);

--
-- GenomeFeatureType
--
CREATE TABLE genome_feature_type (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	represents_gene CHAR(1),
	PRIMARY KEY (id)
);

--
-- GenomeFeatureDataSet
--
CREATE TABLE genome_feature_data_set (
	id NUMBER(38) NOT NULL,
	upload_date DATE,
	genome_feature_type_id NUMBER(38),
	genome_assembly_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (genome_feature_type_id) REFERENCES genome_feature_type (id),
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id)
);


--
-- GenomeFeature
--
CREATE TABLE genome_feature (
	id NUMBER(38) NOT NULL,
	name VARCHAR(128),
	chromosome_id NUMBER(38),
	chrom_start NUMBER(38),
	chrom_end NUMBER(38),
	genome_feature_data_set_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (chromosome_id) REFERENCES chromosome(id),
	FOREIGN KEY (genome_feature_data_set_id) REFERENCES genome_feature_data_set(id)
);

--
-- Exon
--
CREATE TABLE exon (
	id NUMBER(38) NOT NULL,
	chrom_start NUMBER(38),
	chrom_end NUMBER(38),
	genome_feature_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (genome_feature_id) REFERENCES genome_feature(id)
);

--
-- Cytoband
--
CREATE TABLE cytoband (
	id NUMBER(38) NOT NULL,
	name VARCHAR(48),
	chrom_start NUMBER(38),
	chrom_end NUMBER(38),
	stain VARCHAR(16),
	cytological_map_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (cytological_map_id) REFERENCES cytological_map(id)
);

--
-- Pipeline
--
CREATE TABLE pipeline (
	id NUMBER(38) NOT NULL,
	name VARCHAR(128),
	user_name VARCHAR(48),
	PRIMARY KEY (id)
);

--
-- PipelineStep
--
CREATE TABLE pipeline_step (
	id NUMBER(38) NOT NULL,
	pipeline_id NUMBER(38),
	step_num INT,
	class_name VARCHAR(256),
	PRIMARY KEY (id),
	FOREIGN KEY (pipeline_id) REFERENCES pipeline(id)
);

--
-- PipelineStepParameter
--
CREATE TABLE pipeline_step_parameter (
	id NUMBER(38) NOT NULL,
	pipeline_step_id NUMBER(38),
	param_name VARCHAR(128),
	value VARCHAR(256),
	PRIMARY KEY (id),
	FOREIGN KEY (pipeline_step_id) REFERENCES pipeline_step(id)
);
	