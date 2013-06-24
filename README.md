Welcome to the WebGenome Project!
=====================================
WebGenome is a web-based application for creating genomics plots of array-based data and genomic annotation. Version 2.3 provides a client plotting service to external applications, such as Rembrandt. Users must enter webGenome through redirection from another application; the only way to load data is by selecting it in the referring client application.

Data types supported in WebGenome include copy number (i.e., aCGH), loss of heterozygosity (LOH), and gene expression. Two basic types of plots can be generated:
 * Scatter Plots - Plots DNA copy number measurements across the genome, chromosome, or chromosomal interval.
 * Ideogram Plot - Shows chromosomal amplifications and deletions in relation to cytogenetic chromosome ideograms.

The underlying data model used by webGenome is a very simplied version of the MAGE-OM. The most important concept for the use of the system is that individual array data sets, called bioassays are aggregated into experiments. 
     
The WebGenome is an Open Source project and it is written in Java using Apache Batik, Apache Commons, caCORE CSM, caBIO, caArray client, JBoss, Spring Framework, Struts.

The WebGenome is distributed under the BSD 3-Clause License.
Please see the NOTICE and LICENSE files for details.

You will find more details about the WebGenome in the following links:
 * [Code Repository] (https://github.com/NCIP/webgenome)
 * [Binary distribution] (http://ncicb.nci.nih.gov/download/downloadwebgenome.jsp)

Please join us in further developing and improving WebGenome.
