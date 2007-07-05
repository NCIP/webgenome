--
-- Principal
--
CREATE TABLE principal (
    id NUMBER(38) NOT NULL,
	name VARCHAR2(48),
	password VARCHAR2(48),
	admin CHAR(1),
	PRIMARY KEY (id)
);
INSERT INTO principal (id, name, password, admin) values (1, 'admin', 'cureforcancer', 'T');

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
	PRIMARY KEY (id)
);

--
-- Array.chromosomeReportersFileNames map
--
CREATE TABLE reporters_file_names (
	array_id NUMBER(38) NOT NULL,
	chromosome INT NOT NULL,
	file_name VARCHAR2(512),
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
	name VARCHAR2(16),
	start_loc NUMBER(38),
	end_loc NUMBER(38),
	stain VARCHAR2(16),
	cytological_map_id NUMBER(38),
	PRIMARY KEY (id),
	FOREIGN KEY (cytological_map_id) REFERENCES cytological_map(id)
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
	PRIMARY KEY (id),
	FOREIGN KEY (organism_id) REFERENCES organism(id)
);

--
-- Job
--
CREATE TABLE job (
	id NUMBER(38) NOT NULL,
	type VARCHAR2(16),
	user_id VARCHAR2(64),
	instantiation_date TIMESTAMP,
	start_date TIMESTAMP,
	end_date TIMESTAMP,
	termination_message VARCHAR2(256),
	PRIMARY KEY (id)
);

--
-- SimpleUserConfigurableProperty
--
CREATE TABLE simp_user_conf_prop (
	id NUMBER(38) NOT NULL,
	current_value VARCHAR2(128),
	display_name VARCHAR2(128),
	name VARCHAR2(128),
	PRIMARY KEY (id)
);

--
-- UserConfigurablePropertyWithOptions
--
CREATE TABLE user_conf_prop_opt (
	id NUMBER(38) NOT NULL,
	current_value VARCHAR2(128),
	display_name VARCHAR2(128),
	name VARCHAR2(128),
	PRIMARY KEY (id)
);

--
-- Specific options for a UserConfigurablePropertyWithOptions
-- (i.e, the 'options' attribute.
--
CREATE TABLE prop_options (
	code VARCHAR2(128),
	display_name VARCHAR2(128),
	user_conf_prop_opt_id NUMBER(38) NOT NULL,
	PRIMARY KEY (user_conf_prop_opt_id, code),
	FOREIGN KEY (user_conf_prop_opt_id) REFERENCES user_conf_prop_opt(id)
);

--
-- SimulatedDataSourceProperties
--
CREATE TABLE sim_data_src_props (
	id NUMBER(38) NOT NULL,
	client_id VARCHAR2(128),
	PRIMARY KEY (id)
);

--
-- EjbDataSourceProperties
--
CREATE TABLE ejb_data_src_props (
	id NUMBER(38) NOT NULL,
	client_id VARCHAR2(128),
	jndi_name VARCHAR2(256),
	jndi_provider_url VARCHAR2(1024),
	PRIMARY KEY (id)
);

--
-- PlotParameters
--
-- This table holds all properties in PlotParameters and subclasses.
--
CREATE TABLE plot_params (

	-- PlotParameters --
	id NUMBER(38) NOT NULL,
	plot_name VARCHAR2(256),
	num_plots_per_row INT,
	units VARCHAR2(256),
	type VARCHAR2(16),
	
	-- BaseGenomicPlotParameters --
	loh_threshold NUMBER(38),
	intplt_loh_eps VARCHAR2(8),
	draw_raw_loh_probs VARCHAR2(8),
	intplt_type VARCHAR2(256),
	show_annotation VARCHAR2(8),
	show_genes VARCHAR2(8),
	show_reporter_names VARCHAR2(8),
	
	-- HeatMapPlotParameters --
	max_saturation NUMBER(38),
	min_saturation NUMBER(38),
	min_mask NUMBER(38),
	max_mask NUMBER(38),
	
	-- AnnotationPlotParameters --
	draw_feature_labels VARCHAR2(8),
	
	-- BarPlotParameters --
	row_height INT,
	bar_width INT,
	
	-- IdeogramPlotParameters --
	ideogram_size VARCHAR2(256),
	track_width INT,
	ideogram_thickness INT,
	
	-- ScatterPlotParameters --
	min_y NUMBER(38),
	max_y NUMBER(38),
	height INT,
	draw_horiz_grid_lines VARCHAR2(8),
	draw_vert_grid_lines VARCHAR2(8),
	draw_points VARCHAR2(8),
	draw_error_bars VARCHAR2(8),
	
	-- AnnotationPlotParameters and ScatterPlotParameters --
	width INT,
	
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
	PRIMARY KEY (id),
	FOREIGN KEY (plot_params_id) REFERENCES plot_params(id)
);

--
-- Annotation type objects associated with an
-- AnnotationPlotParameters object (i.e. annotationTypes property).
--
CREATE TABLE ann_plot_params_types (
	plot_params_id NUMBER(38),
	name VARCHAR2(256),
	PRIMARY KEY (plot_params_id, name),
	FOREIGN KEY (plot_params_id) REFERENCES plot_params(id)
);

--
-- ClickBoxes
--
CREATE TABLE click_boxes (
	id NUMBER(38) NOT NULL,
	origin_x INT,
	origin_y INT,
	box_width INT,
	box_height INT,
	click_box CLOB,
	PRIMARY KEY (id)
);

--
-- MouseOverStripes
--
CREATE TABLE mouse_over_stripes (
	id NUMBER(38) NOT NULL,
	orientation VARCHAR2(16),
	width INT,
	height INT,
	origin_x INT,
	origin_y INT,
	PRIMARY KEY (id)
);

--
-- MouseOverStripes
--
CREATE TABLE mouse_over_stripe (
	id NUMBER(38) NOT NULL,
	mouse_over_stripes_id NUMBER(38),
	start_pix INT,
	end_pix INT,
	text VARCHAR2(1024),
	list_index INT,
	PRIMARY KEY (id),
	FOREIGN KEY (mouse_over_stripes_id) REFERENCES mouse_over_stripes (id)
);

--
-- Plot
--
CREATE TABLE plot (
	id NUMBER(38) NOT NULL,
	def_img_file_name VARCHAR2(256),
	width INT,
	height INT,
	PRIMARY KEY (id)
);

--
-- Property Plot.imageFileMap
--
CREATE TABLE img_file_map (
	plot_id NUMBER(38) NOT NULL,
	img_name VARCHAR2(256),
	file_name VARCHAR2(256),
	PRIMARY KEY (plot_id, img_name, file_name),
	FOREIGN KEY (plot_id) REFERENCES plot(id)
);

--
-- Property Plot.clickBoxes
--
CREATE TABLE plot_click_boxes (
	plot_id NUMBER(38) NOT NULL,
	click_boxes_id NUMBER(38) NOT NULL,
	PRIMARY KEY (plot_id, click_boxes_id),
	FOREIGN KEY (plot_id) REFERENCES plot(id),
	FOREIGN KEY (click_boxes_id) REFERENCES click_boxes(id)
);

--
-- Property Plot.mouseOverStripes
--
CREATE TABLE plot_mouse_over_stripes (
	plot_id NUMBER(38) NOT NULL,
	mouse_over_stripes_id NUMBER(38) NOT NULL,
	PRIMARY KEY (plot_id, mouse_over_stripes_id),
	FOREIGN KEY (plot_id) REFERENCES plot(id),
	FOREIGN KEY (mouse_over_stripes_id) REFERENCES mouse_over_stripes(id)
);

--
-- Property Plot.plotParameters
--
CREATE TABLE plot_plot_params (
	plot_id NUMBER(38) NOT NULL,
	plot_params_id NUMBER(38) NOT NULL,
	PRIMARY KEY (plot_id, plot_params_id),
	FOREIGN KEY (plot_id) REFERENCES plot(id),
	FOREIGN KEY (plot_params_id) REFERENCES plot_params(id)
);

--
-- Property Plot.experimentIds
--
CREATE TABLE plot_exp_ids (
	exp_id NUMBER(38) NOT NULL,
	plot_id NUMBER(38) NOT NULL,
	PRIMARY KEY (exp_id, plot_id),
	FOREIGN KEY (plot_id) REFERENCES plot(id)
);
