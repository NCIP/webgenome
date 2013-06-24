/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $


*/

package org.rti.webgenome.domain;

/**
 * Type of genome annotation.
 * @author dhall
 *
 */
public enum AnnotationType {
	
	/** Genome interval that exhibits loss of heterozygosity. */
	LOH_SEGMENT,
	
	/** Genome interval that is amplified. */
	AMPLIFIED_SEGMENT,
	
	/** Genome interval that is deleted. */
	DELETED_SEGMENT,
	
	/** Gene. */
	GENE,
	
	/** Exon. */
	EXON;
}
