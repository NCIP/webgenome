/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $


*/

package org.rti.webgenome.graphics.util;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.rti.webgenome.domain.Experiment;

/**
 * This is a helper class that maps chromosome positions
 * to genomic positions.  The latter are positions in
 * a concatenated set of chromosomes ordered on chromosome
 * number.  The coordinates on this concatenated set
 * go from 0 (left hand side of first chromosome)
 * to SUM_CHROMOSOME_SIZES (right hand side of last chromosome).
 * @author dhall
 *
 */
public class GenomePositionMapper {
	
	/** Total number of base pairs in genome. */
	private long genomeLength = 0;
	
	/**
	 * Map of the starting location of each chromosome in
	 * the concatenation (values) to chromosome number (keys). */
	private SortedMap<Short, Long> chromosomeStartLocations =
		new TreeMap<Short, Long>();
	
	/**
	 * Constructor.
	 * @param experiments Experiments that will be plotted.
	 */
	public GenomePositionMapper(
			final Collection<Experiment> experiments) {
		
		// Determine inferred length of each chromosome
		SortedMap<Short, Long> chromLengths = new TreeMap<Short, Long>();
		for (Experiment exp : experiments) {
			for (Short chrom : exp.getChromosomes()) {
				Long incumbentLength = chromLengths.get(chrom);
				Long contenderLength = exp.inferredChromosomeSize(chrom);
				if (incumbentLength == null) {
					chromLengths.put(chrom, contenderLength);
				} else {
					if (contenderLength > incumbentLength) {
						chromLengths.put(chrom, contenderLength);
					}
				}
			}
		}
		
		// Initialize object attributes
		long cumulativeLength = 0;
		for (Short chrom : chromLengths.keySet()) {
			this.chromosomeStartLocations.put(chrom, cumulativeLength);
			cumulativeLength += chromLengths.get(chrom);
		}
		this.genomeLength = cumulativeLength;
	}
	
	/**
	 * Map location on chromosome to location on chromosome
	 * concatenation.
	 * @param chromosome A chromosome number
	 * @param chromosomeLocation A location on given chromosome
	 * number in base pairs
	 * @return Position within chromosome concatenation
	 */
	public long chromToGenomicLocation(
			final short chromosome, final long chromosomeLocation) {
		return this.chromosomeStartLocations.get(chromosome)
			+ chromosomeLocation;
	}
	
	/**
	 * Get length of entire chromosome concatenation in base pairs.
	 * @return Genome length in base pairs
	 */
	public long getGenomeLength() {
		return this.genomeLength;
	}

	/**
	 * Get start locations for chromosomes in concatenation.
	 * @return Map giving chromosome start locations ordered on
	 * chromosome number (keys).
	 */
	public SortedMap<Short, Long> getChromosomeStartLocations() {
		return chromosomeStartLocations;
	}
}
