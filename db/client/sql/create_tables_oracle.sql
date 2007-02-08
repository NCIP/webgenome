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
