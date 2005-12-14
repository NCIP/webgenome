-- MySQL dump 9.10
--
-- Host: localhost    Database: hg17
-- ------------------------------------------------------
-- Server version	4.0.20

--
-- Table structure for table `cytoBand`
--

CREATE TABLE cytoBand (
  chrom varchar(255) NOT NULL default '',
  chromStart int(10) unsigned NOT NULL default '0',
  chromEnd int(10) unsigned NOT NULL default '0',
  name varchar(255) NOT NULL default '',
  gieStain varchar(255) NOT NULL default '',
  PRIMARY KEY  (chrom(12),chromStart),
  UNIQUE KEY chrom (chrom(12),chromEnd)
) TYPE=MyISAM;
