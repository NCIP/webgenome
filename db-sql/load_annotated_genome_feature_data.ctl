LOAD DATA
	INSERT
	INTO TABLE annotated_genome_feature
	FIELDS TERMINATED BY "," OPTIONALLY ENCLOSED BY '"'
	( ID, NAME, ANNOTATION_TYPE, QUANTITATION, CHROMOSOME, START_LOC, END_LOC, PARENT_ID, ORGANISM_ID  )