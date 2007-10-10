--
-- This file contains definitions for three tables
-- that were not created during the schema creation
-- for the WebGenome 3.0 release at NCICB.  These
-- tables originally contained comments within their
-- SQL create statements.
--

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
	
	an_op_class_name VARCHAR2(256),
	
	input_experiment_id NUMBER(38),
	
	PRIMARY KEY (id)
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
	
	loh_threshold NUMBER(38),
	intplt_loh_eps VARCHAR2(8),
	draw_raw_loh_probs VARCHAR2(8),
	intplt_type VARCHAR2(256),
	show_annotation VARCHAR2(8),
	show_genes VARCHAR2(8),
	show_reporter_names VARCHAR2(8),
	
	cn_max_saturation NUMBER(38),
	cn_min_saturation NUMBER(38),
	expr_max_saturation NUMBER(38),
	expr_min_saturation NUMBER(38),
	min_mask NUMBER(38),
	max_mask NUMBER(38),
	
	draw_feature_labels VARCHAR2(8),
	
	row_height INT,
	bar_width INT,
	
	ideogram_size VARCHAR2(256),
	track_width INT,
	ideogram_thickness INT,
	
	cn_min_y NUMBER(38),
	cn_max_y NUMBER(38),
	expr_min_y NUMBER(38),
	expr_max_y NUMBER(38),
	draw_points VARCHAR2(8),
	draw_error_bars VARCHAR2(8),
	draw_stems VARCHAR2(8),
	
	width INT,
	
	height INT,
	draw_horiz_grid_lines VARCHAR2(8),
	draw_vert_grid_lines VARCHAR2(8),
	
	min_y NUMBER(38),
	max_y NUMBER(38),
	
	PRIMARY KEY (id)
);

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

ALTER TABLE experiment
ADD CONSTRAINT fk_experiment_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props(id);

ALTER TABLE data_src_props
ADD CONSTRAINT fk_dsp_iei
FOREIGN KEY (input_experiment_id) REFERENCES experiment (id);

ALTER TABLE dsp_experiments
ADD CONSTRAINT fk_de_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE user_conf_prop
ADD CONSTRAINT fk_ucp_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE job
ADD CONSTRAINT fk_job_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props (id);

ALTER TABLE data_file_meta_data
ADD CONSTRAINT dfmd_dspi
FOREIGN KEY (data_src_props_id) REFERENCES data_src_props(id);

ALTER TABLE data_src_props
ADD CONSTRAINT dsp_orgid
FOREIGN KEY (organism_id) REFERENCES organism(id);

ALTER TABLE plot_params_ivals
ADD CONSTRAINT fk_ppi_plot_params_id
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

ALTER TABLE plot
ADD CONSTRAINT fk_plot_plot_params_id
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

ALTER TABLE ann_plot_params_types
ADD CONSTRAINT fk_appt_ppi
FOREIGN KEY (plot_params_id) REFERENCES plot_params(id);

ALTER TABLE job
ADD CONSTRAINT fk_job_ppi
FOREIGN KEY (plot_params_id) REFERENCES plot_params (id);
