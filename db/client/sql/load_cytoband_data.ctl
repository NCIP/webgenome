LOAD DATA
	INFILE '..\data\cytoband.csv'
	INTO TABLE cytoband
	FIELDS TERMINATED BY WHITESPACE
	( ID, NAME,START_LOC,END_LOC,STAIN,CYTOLOGICAL_MAP_ID )
