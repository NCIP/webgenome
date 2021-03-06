/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

---
-- Principal
--
CREATE TABLE principal (
    id NUMBER(38) NOT NULL,
	name VARCHAR2(48),
	password VARCHAR2(48),
	domain VARCHAR2(128),
	admin CHAR(1),
	PRIMARY KEY (id)
);
INSERT INTO principal (id, name, password, admin, domain) values (1, 'admin', 'cureforcancer', 'T', 'webgenome');
INSERT INTO principal (id, name, password, admin, domain) values (2, 'tippie', 'toe', 'F', 'webgenome');

--
-- Organism
--
CREATE TABLE organism (
	id NUMBER(38) NOT NULL,
	genus VARCHAR2(64),
	species VARCHAR2(64),
	PRIMARY KEY (id)
);
INSERT INTO organism (id, genus, species) VALUES (1, 'Homo', 'sapiens');

--
-- Array
--
CREATE TABLE array (
	id NUMBER(38) NOT NULL,
	name VARCHAR2(128),
	disposable VARCHAR2(8),
	PRIMARY KEY (id)
);

--
-- Array.chromosomeReportersFileNames map
--
CREATE TABLE reporters_file_names (
	array_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	file_name VARCHAR2(512),
	PRIMARY KEY (array_id, chromosome)
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
	PRIMARY KEY (id)
);

--
-- Cytoband
--
CREATE TABLE cytoband (
	id NUMBER(38) NOT NULL,
	name VARCHAR2(16),
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	stain VARCHAR2(16),
	cytological_map_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- AnnotatedGenomeFeature
--
CREATE TABLE annotated_genome_feature (
	id NUMBER(38) NOT NULL,
	name VARCHAR2(64),
	annotation_type VARCHAR2(16),
	quantitation NUMBER(16),
	chromosome INT,
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	parent_id NUMBER(38),
	organism_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- Job
--
-- (1) First block are common fields
-- (2) Second block are fields specific to
--     AnalysisJob, ReRunAnalysisJob, and DataImportJob
-- (3) Third block are fields specific to
--     PlotJob and ReRunAnalysisOnPlotExperimentsJob
--
CREATE TABLE job (
	id NUMBER(38) NOT NULL,
	type VARCHAR2(16),
	user_id VARCHAR2(64),
	user_domain VARCHAR2(128),
	instantiation_date TIMESTAMP,
	start_date TIMESTAMP,
	end_date TIMESTAMP,
	termination_message VARCHAR2(1024),
	description VARCHAR2(256),
	user_notified_complete VARCHAR2(8),
	user_notified_start VARCHAR2(8),
	data_src_props_id NUMBER(38),
	plot_params_id NUMBER(38),
	plot_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- AnalysisJob.outputBioAssayNames property
--
CREATE TABLE job_out_bioassay_names (
	job_id NUMBER(38) NOT NULL,
	bioassay_id NUMBER(38) NOT NULL,
	name VARCHAR2(128),
	PRIMARY KEY (job_id, bioassay_id)
);

--
-- AnalysisJob.outputExperimentNames property
--
CREATE TABLE job_out_experiment_names (
	job_id NUMBER(38) NOT NULL,
	experiment_id NUMBER(38) NOT NULL,
	name VARCHAR2(128),
	PRIMARY KEY (job_id, experiment_id)
);

--
-- ReRunAnalysisOnPlotExperimentsJob.experiments
--
CREATE TABLE job_experiments (
	job_id NUMBER(38) NOT NULL,
	experiment_id NUMBER(38) NOT NULL,
	PRIMARY KEY (job_id, experiment_id)
);

--
-- DataSourceProperties
--
-- (1) First block are common properties
-- (2) Second block is the subclass discriminator
-- (3) Third block are properties specific to EjbDataSourceProperties
-- (4) Fourth block are properties specific to UploadDataSourceProperties
-- (5) Fifth block are properties specific to AnalysisDataSourceProperties
-- (6) Sixth block are properties specific to SingleAnalysisDataSourceProperties
--
CREATE TABLE data_src_props (
	id NUMBER(38) NOT NULL,
	type VARCHAR2(16),
	jndi_name VARCHAR2(256),
	jndi_provider_url VARCHAR2(1024),
	client_id VARCHAR2(128),
	rep_loc_file_name VARCHAR2(256),
	rep_rem_file_name VARCHAR2(256),
	rep_file_format VARCHAR2(256),
	rep_col_name VARCHAR2(256),
	chrom_col_name VARCHAR2(256),
	pos_col_name VARCHAR2(256),
	pos_units VARCHAR2(256),
	exp_name VARCHAR2(128),
	organism_id NUMBER(38),
	quant_type VARCHAR2(256),
	quant_type_label VARCHAR(256),
	an_op_class_name VARCHAR2(256),
	input_experiment_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- DataFileMetaData
--
CREATE TABLE data_file_meta_data (
	id NUMBER(38) NOT NULL,
	data_src_props_id NUMBER(38),
	remote_file_name VARCHAR(256),
	local_file_name VARCHAR(256),
	format VARCHAR2(256),
	reporter_col_name VARCHAR2(128),
	PRIMARY KEY (id)
);

--
-- DataColumnMetaData
--
CREATE TABLE data_col_meta_data (
	id NUMBER(38) NOT NULL,
	data_file_meta_data_id NUMBER(38),
	column_name VARCHAR2(128),
	bioassay_name VARCHAR2(128),
	PRIMARY KEY (id)
);

--
-- MultiAnalysisDataSourceProperties.inputExperiments property
--
CREATE TABLE dsp_experiments (
	data_src_props_id NUMBER(38) NOT NULL,
	experiment_id NUMBER(38) NOT NULL,
	PRIMARY KEY (data_src_props_id, experiment_id)
);

--
-- AnalysisDataSourceProperties.userConfigurableProperties
-- property
--
CREATE TABLE user_conf_prop (
	id NUMBER(38) NOT NULL,
	type VARCHAR2(16),
	current_value VARCHAR2(128),
	display_name VARCHAR2(128),
	name VARCHAR2(128),
	data_src_props_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- Specific options for a UserConfigurablePropertyWithOptions
-- (i.e, the 'options' attribute).
--
CREATE TABLE prop_options (
	code VARCHAR2(128),
	display_name VARCHAR2(128),
	user_conf_prop_id NUMBER(38) NOT NULL,
	PRIMARY KEY (user_conf_prop_id, code)
);

--
-- PlotParameters
--
-- This table holds all properties in PlotParameters and subclasses.
--
-- (1) First block contains properties for PlotParameters base class
-- (2) Second block contains properties for BaseGenomicPlotParameters
-- (3) Third block contains properties for HeatMapPlotParameters
-- (4) Fourth block contains properties for AnnotationPlotParameters
-- (5) Fifth block contains properties for BarPlotParameters
-- (6) Sixth block contains properties for IdeogramPlotParameters
-- (7) Seventh block contains properties for ScatterPlotParameters
-- (8) Eigth block contains properties found only in
--     AnnotationPlotParameters, ScatterPlotParameters,
--     and GenomeSnapshotPlotParameters
-- (9) Ninth block contains properties found only in
--     ScatterPlotParameters and GenomeSnapshotPlotParameters
-- (10) Tenth block contains properties found only in
--      GenomicSnapshotPlotParameters
--
CREATE TABLE plot_params (
	id NUMBER(38) NOT NULL,
	plot_name VARCHAR2(256),
	num_plots_per_row INT,
	units VARCHAR2(256),
	type VARCHAR2(16),
	loh_threshold NUMBER,
	intplt_loh_eps VARCHAR2(8),
	draw_raw_loh_probs VARCHAR2(8),
	intplt_type VARCHAR2(256),
	show_annotation VARCHAR2(8),
	show_genes VARCHAR2(8),
	show_reporter_names VARCHAR2(8),
	cn_max_saturation NUMBER,
	cn_min_saturation NUMBER,
	expr_max_saturation NUMBER,
	expr_min_saturation NUMBER,
	min_mask NUMBER,
	max_mask NUMBER,
	draw_feature_labels VARCHAR2(8),
	row_height INT,
	bar_width INT,
	ideogram_size VARCHAR2(256),
	track_width INT,
	ideogram_thickness INT,
	cn_min_y NUMBER,
	cn_max_y NUMBER,
	expr_min_y NUMBER,
	expr_max_y NUMBER,
	draw_points VARCHAR2(8),
	draw_error_bars VARCHAR2(8),
	draw_stems VARCHAR2(8),
	width INT,
	height INT,
	draw_horiz_grid_lines VARCHAR2(8),
	draw_vert_grid_lines VARCHAR2(8),
	min_y NUMBER,
	max_y NUMBER,
	PRIMARY KEY (id)
);

--
-- GenomeInterval objects associated with a
-- PlotParameters object (i.e. genomeIntervals property).
--
CREATE TABLE plot_params_ivals (
	id NUMBER(38) NOT NULL,
	plot_params_id NUMBER(38),
	chromosome NUMBER(2),
	start_location NUMBER(38),
	end_location NUMBER(38),
	PRIMARY KEY (id)
);

--
-- Annotation type objects associated with an
-- AnnotationPlotParameters object (i.e. annotationTypes property).
--
CREATE TABLE ann_plot_params_types (
	plot_params_id NUMBER(38),
	name VARCHAR2(256),
	PRIMARY KEY (plot_params_id, name)
);

--
-- Next four tables commented out because these
-- data are now being persisted via object
-- serialization.

--
-- ClickBoxes
--
--CREATE TABLE click_boxes (
--	id NUMBER(38) NOT NULL,
--	origin_x INT,
--	origin_y INT,
--	box_width INT,
--	box_height INT,
--	width INT,
--	height INT,
--	num_rows INT,
--	num_cols INT,
--	plot_id NUMBER(38),
--	PRIMARY KEY (id)
--);

--
-- ClickBoxes.clickBox property
--
--CREATE TABLE click_boxes_text (
--	click_boxes_id NUMBER(38) NOT NULL,
--	list_index INT,
--	text_value VARCHAR2(128),
--	PRIMARY KEY (click_boxes_id, list_index)
--);

--
-- MouseOverStripes
--
--CREATE TABLE mouse_over_stripes (
--	id NUMBER(38) NOT NULL,
--	orientation VARCHAR2(16),
--	width INT,
--	height INT,
--	origin_x INT,
--	origin_y INT,
--	plot_id NUMBER(38),
--	PRIMARY KEY (id)
--);

--
-- MouseOverStripe
--
--CREATE TABLE mouse_over_stripe (
--	id NUMBER(38) NOT NULL,
--	mouse_over_stripes_id NUMBER(38),
--	start_pix INT,
--	end_pix INT,
--	text VARCHAR2(1024),
--	list_index INT,
--	PRIMARY KEY (id)
--);

--
-- Plot
--
CREATE TABLE plot (
	id NUMBER(38) NOT NULL,
	def_img_file_name VARCHAR2(256),
	width INT,
	height INT,
	plot_params_id NUMBER(38),
	shopping_cart_id NUMBER(38),
	list_index INT,
	cb_file_name VARCHAR2(256),
	mos_file_name VARCHAR2(256),
	PRIMARY KEY (id)
);

--
-- Property Plot.imageFileMap
--
CREATE TABLE img_file_map (
	plot_id NUMBER(38) NOT NULL,
	img_name VARCHAR2(256),
	file_name VARCHAR2(256),
	PRIMARY KEY (plot_id, img_name, file_name)
);

--
-- Property Plot.experiments
--
CREATE TABLE plot_exp_ids (
	exp_id NUMBER(38) NOT NULL,
	plot_id NUMBER(38) NOT NULL,
	PRIMARY KEY (exp_id, plot_id)
);

--
-- BioAssay.  Actually only the DataSerializedBioAssay
-- subclass of BioAssay is currently persisted.
--
CREATE TABLE bioassay (
	id NUMBER(38) NOT NULL,
	type VARCHAR2(16),
	name VARCHAR2(128),
	color_int INT,
	selected VARCHAR2(8),
	parent_bioassay_id NUMBER(38),
	organism_id NUMBER(38),
	array_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- Property DataSerializedBioAssay.chromosomeArrayDataFileIndex.
--
CREATE TABLE array_data_file_index (
	bioassay_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	file_name VARCHAR2(512),
	PRIMARY KEY (bioassay_id, chromosome)
);

--
-- Property DataSerializedBioAssay.chromosomeSizes.
--
CREATE TABLE chrom_sizes (
	bioassay_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	chrom_size NUMBER(38),
	PRIMARY KEY (bioassay_id, chromosome)
);

--
-- Property DataSerializedBioAssay.minValues
--
CREATE TABLE min_values (
	bioassay_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	min_value NUMBER(38,6),
	PRIMARY KEY (bioassay_id, chromosome)
);

--
-- Property DataSerializedBioAssay.maxValues
--
CREATE TABLE max_values (
	bioassay_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	max_value NUMBER(38,6),
	PRIMARY KEY (bioassay_id, chromosome)
);

--
-- Property DataSerializedBioAssay.numDatum
--
CREATE TABLE num_datum (
	bioassay_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	num_datum INT,
	PRIMARY KEY (bioassay_id, chromosome)
);

--
-- BioAssayDataConstraints
--
CREATE TABLE bioassay_data_constraints (
	id NUMBER(38) NOT NULL,
	chromosome VARCHAR2(64),
	start_point NUMBER(38),
	end_point NUMBER(38),
	quant_type varchar2(64),
	PRIMARY KEY (id)
);

-- ################################################

--
-- Experiment
--
CREATE TABLE experiment (
	id NUMBER(38) NOT NULL,
	name VARCHAR2(128),
	source_db_id VARCHAR2(256),
	quant_type VARCHAR2(128),
    quant_type_label VARCHAR2(256),
	terminal VARCHAR2(8),
	organism_id NUMBER(38),
	data_src_props_id NUMBER(38),
	shopping_cart_id NUMBER(38),
	PRIMARY KEY (id)
);

--
-- Experiment.bioAssays property
--
CREATE TABLE experiment_bioassay (
	experiment_id NUMBER(38) NOT NULL,
	bioassay_id NUMBER(38) NOT NULL,
	PRIMARY KEY (experiment_id, bioassay_id)
);

--
-- Experiment.bioAssayDataConstraints property
--
CREATE TABLE exp_bioassay_data_constr (
	experiment_id NUMBER(38) NOT NULL,
	bioassay_data_constraints_id NUMBER(38) NOT NULL,
	PRIMARY KEY (experiment_id, bioassay_data_constraints_id)
);

-- #########################################################3

--
-- ColorChooser
--
CREATE TABLE color_chooser (
	id NUMBER(38) NOT NULL,
	PRIMARY KEY (id)
);

--
-- ColorChooser.colors propery
-- 
CREATE TABLE color_chooser_color (
	color_chooser_id NUMBER(38) NOT NULL,
	list_index INT NOT NULL,
	color INT,
	PRIMARY KEY (color_chooser_id, list_index)
);

--
-- ColorChooser.colorCounts property
--
CREATE TABLE color_chooser_color_count (
	color_chooser_id NUMBER(38) NOT NULL,
	color INT NOT NULL,
	usage_count INT,
	PRIMARY KEY (color_chooser_id, color)
);

--
-- ShoppingCart
--
CREATE TABLE shopping_cart (
	id NUMBER(38) NOT NULL,
	user_name VARCHAR2(128),
	user_domain VARCHAR2(128),
	color_chooser_id NUMBER(38) NOT NULL,
	PRIMARY KEY (id)
);

ALTER TABLE reporters_file_names
ADD CONSTRAINT fk_rpf_array_id
FOREIGN KEY (array_id) REFERENCES array(id);

ALTER TABLE cytological_map
ADD CONSTRAINT fk_cm_organism_id
FOREIGN KEY (organism_id) REFERENCES organism(id);

ALTER TABLE cytoband
ADD CONSTRAINT fk_cb_cyt_map_id
FOREIGN KEY (cytological_map_id) REFERENCES cytological_map(id);

ALTER TABLE annotated_genome_feature
ADD CONSTRAINT fk_agf_org_id
FOREIGN KEY (organism_id) REFERENCES organism(id);

ALTER TABLE prop_options
ADD CONSTRAINT fk_po_ucpi
FOREIGN KEY (user_conf_prop_id) REFERENCES user_conf_prop(id);

ALTER TABLE plot_params_ivals
ADD CONSTRAINT fk_ppi_plot_params_id
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

--
-- Constraint commented out because these
-- data are now being persisted via object
-- serialization.
--

--ALTER TABLE mouse_over_stripe
--ADD CONSTRAINT fk_mos_mosid
--FOREIGN KEY (mouse_over_stripes_id) REFERENCES mouse_over_stripes (id);

ALTER TABLE plot
ADD CONSTRAINT fk_plot_plot_params_id
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

ALTER TABLE img_file_map
ADD CONSTRAINT fk_ifm_plot_id
FOREIGN KEY (plot_id) REFERENCES plot(id);

ALTER TABLE plot_exp_ids
ADD CONSTRAINT fk_pei_plot_id
FOREIGN KEY (plot_id) REFERENCES plot(id);

ALTER TABLE plot_exp_ids
ADD CONSTRAINT fk_pei_exp_id
FOREIGN KEY (exp_id) REFERENCES experiment(id);

ALTER TABLE bioassay
ADD CONSTRAINT fk_bioassay_pbi
FOREIGN KEY (parent_bioassay_id) REFERENCES bioassay(id);

ALTER TABLE bioassay
ADD CONSTRAINT fk_bioassay_org_id
FOREIGN KEY (organism_id) REFERENCES organism(id);

ALTER TABLE bioassay
ADD CONSTRAINT fk_bioassay_array_id
FOREIGN KEY (array_id) REFERENCES array(id);

ALTER TABLE array_data_file_index
ADD CONSTRAINT fk_adfi_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE chrom_sizes
ADD CONSTRAINT fk_cs_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE min_values
ADD CONSTRAINT fk_min_values_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE max_values
ADD CONSTRAINT fk_max_values_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE num_datum
ADD CONSTRAINT fk_nd_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE experiment
ADD CONSTRAINT fk_experiment_org_id
FOREIGN KEY (organism_id) REFERENCES organism(id);

ALTER TABLE experiment
ADD CONSTRAINT fk_experiment_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props(id);

ALTER TABLE experiment_bioassay
ADD CONSTRAINT fk_eb_ei
FOREIGN KEY (experiment_id) REFERENCES experiment(id);

ALTER TABLE experiment_bioassay
ADD CONSTRAINT fk_eb_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay(id);

ALTER TABLE exp_bioassay_data_constr
ADD CONSTRAINT fk_ebdc_ei
FOREIGN KEY (experiment_id) REFERENCES experiment(id);

ALTER TABLE exp_bioassay_data_constr
ADD CONSTRAINT fk_ebdc_bdci
FOREIGN KEY (bioassay_data_constraints_id) REFERENCES bioassay_data_constraints(id);

ALTER TABLE color_chooser_color
ADD CONSTRAINT fk_ccc_cci
FOREIGN KEY (color_chooser_id) REFERENCES color_chooser(id);

ALTER TABLE color_chooser_color_count
ADD CONSTRAINT fk_cccc_cci
FOREIGN KEY (color_chooser_id) REFERENCES color_chooser(id);

ALTER TABLE shopping_cart
ADD CONSTRAINT fk_sc_cci
FOREIGN KEY (color_chooser_id) REFERENCES color_chooser(id);

ALTER TABLE ann_plot_params_types
ADD CONSTRAINT fk_appt_ppi
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

ALTER TABLE plot
ADD CONSTRAINT fk_plot_sci
FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);

ALTER TABLE experiment
ADD CONSTRAINT fk_experiment_sci
FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart (id);

--
-- Constraint commented out because these
-- data are now being persisted via object
-- serialization.
--

--ALTER TABLE click_boxes_text
--ADD CONSTRAINT fk_cbt_cbi
--FOREIGN KEY (click_boxes_id) REFERENCES click_boxes (id);

--
-- Constraint commented out because these
-- data are now being persisted via object
-- serialization.
--

--ALTER TABLE mouse_over_stripes
--ADD CONSTRAINT fk_mos_pi
--FOREIGN KEY (plot_id) REFERENCES plot (id);

--
-- Constraint commented out because these
-- data are now being persisted via object
-- serialization.
--

--ALTER TABLE click_boxes
--ADD CONSTRAINT fk_cb_pi
--FOREIGN KEY (plot_id) REFERENCES plot (id);

ALTER TABLE data_src_props
ADD CONSTRAINT fk_dsp_iei
FOREIGN KEY (input_experiment_id) REFERENCES experiment (id);

ALTER TABLE dsp_experiments
ADD CONSTRAINT fk_de_ei
FOREIGN KEY (experiment_id) REFERENCES experiment (id);

ALTER TABLE dsp_experiments
ADD CONSTRAINT fk_de_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE user_conf_prop
ADD CONSTRAINT fk_ucp_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE job
ADD CONSTRAINT fk_job_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE job_out_bioassay_names
ADD CONSTRAINT fk_jobn_ji
FOREIGN KEY (job_id) REFERENCES job (id);

ALTER TABLE job_out_bioassay_names
ADD CONSTRAINT fk_jobn_bi
FOREIGN KEY (bioassay_id) REFERENCES bioassay (id);

ALTER TABLE job_out_experiment_names
ADD CONSTRAINT fk_joen_ji
FOREIGN KEY (job_id) REFERENCES job (id);

ALTER TABLE job_out_experiment_names
ADD CONSTRAINT fk_joen_bi
FOREIGN KEY (experiment_id) REFERENCES experiment (id);

ALTER TABLE job_experiments
ADD CONSTRAINT fk_je_ji
FOREIGN KEY (job_id) REFERENCES job (id);

ALTER TABLE job_experiments
ADD CONSTRAINT fk_je_ei
FOREIGN KEY (experiment_id) REFERENCES experiment (id);

ALTER TABLE job
ADD CONSTRAINT fk_job_ppi
FOREIGN KEY (plot_params_id) REFERENCES plot_params (id);

ALTER TABLE job
ADD CONSTRAINT fk_job_pi
FOREIGN KEY (plot_id) REFERENCES plot (id);

ALTER TABLE data_col_meta_data
ADD CONSTRAINT dcmd_dfmdi
FOREIGN KEY (data_file_meta_data_id) REFERENCES data_file_meta_data (id);

ALTER TABLE data_file_meta_data
ADD CONSTRAINT dfmd_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props(id);

ALTER TABLE data_src_props
ADD CONSTRAINT dsp_orgid
FOREIGN KEY (organism_id) REFERENCES organism(id);