-- MySQL dump 9.11
--
-- Host: localhost    Database: hg17
-- ------------------------------------------------------
-- Server version	4.0.21

--
-- Table structure for table `knownGene`
--

CREATE TABLE knownGene (
  name varchar(255) NOT NULL default '',
  chrom varchar(255) NOT NULL default '',
  strand char(1) NOT NULL default '',
  txStart int(10) unsigned NOT NULL default '0',
  txEnd int(10) unsigned NOT NULL default '0',
  cdsStart int(10) unsigned NOT NULL default '0',
  cdsEnd int(10) unsigned NOT NULL default '0',
  exonCount int(10) unsigned NOT NULL default '0',
  exonStarts longblob NOT NULL,
  exonEnds longblob NOT NULL,
  proteinID varchar(40) NOT NULL default '',
  alignID varchar(8) NOT NULL default '',
  KEY name (name(10)),
  KEY chrom (chrom(12),txStart),
  KEY chrom_2 (chrom(12),txEnd),
  KEY protein (proteinID(12)),
  KEY align (alignID)
) TYPE=MyISAM;
