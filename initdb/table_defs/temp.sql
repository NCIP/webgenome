--
-- Table organism
--
CREATE TABLE organism (
	id BIGINT NOT NULL IDENTITY,
	genus VARCHAR(48),
	species VARCHAR(48),
	PRIMARY KEY (id)
);

--
-- Table genome
--
CREATE TABLE genome_assembly (
	id BIGINT NOT NULL IDENTITY,
	name VARCHAR(48),
	organism_id BIGINT,
	PRIMARY KEY(id),
	FOREIGN KEY (organism_id) REFERENCES organism(id) ON DELETE CASCADE
);

--
-- Table feature_type
--
CREATE TABLE annotation_feature_type (
	id BIGINT NOT NULL IDENTITY,
	is_a_gene char(1),
	name VARCHAR(48),
	PRIMARY KEY (id)
);

--
-- Table annotation_feature
--
CREATE TABLE annotation_feature (
	id BIGINT NOT NULL IDENTITY,
	name VARCHAR(128),
	annotation_feature_type_id BIGINT,
	chromosome INT,
	chrom_start BIGINT,
	chrom_end BIGINT,
	genome_assembly_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (annotation_feature_type_id) REFERENCES annotation_feature_type(id) ON DELETE CASCADE,
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id) ON DELETE CASCADE
);

--
-- Table exon
--
CREATE TABLE exon (
	id BIGINT NOT NULL IDENTITY,
	annotation_feature_id BIGINT,
	chrom_start BIGINT,
	chrom_end BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (annotation_feature_id) REFERENCES annotation_feature(id) ON DELETE CASCADE
);

--
-- Table probe_set
--
CREATE TABLE probe_set (
	id BIGINT NOT NULL IDENTITY,
	vendor VARCHAR(48),
	name VARCHAR(48),
	organism_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism(id) ON DELETE CASCADE
);

--
-- Table probe
--
CREATE TABLE probe (
	id BIGINT NOT NULL IDENTITY,
	name VARCHAR(128),
	probe_set_id BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (probe_set_id) REFERENCES probe_set(id) ON DELETE CASCADE
);

--
-- Table probe_mapping
--
CREATE TABLE probe_mapping (
	id BIGINT NOT NULL IDENTITY,
	probe_id BIGINT,
	genome_assembly_id BIGINT,
	chromosome INT,
	chrom_start BIGINT,
	chrom_end BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (probe_id) REFERENCES probe(id) ON DELETE CASCADE,
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id) ON DELETE CASCADE
);

--
-- Table cytoband
--
CREATE TABLE cytoband (
	id BIGINT NOT NULL IDENTITY,
	name VARCHAR(48),
	genome_assembly_id BIGINT,
	stain VARCHAR(16),
	chromosome INT,
	chrom_start BIGINT,
	chrom_end BIGINT,
	PRIMARY KEY (id),
	FOREIGN KEY (genome_assembly_id) REFERENCES genome_assembly(id) ON DELETE CASCADE
);