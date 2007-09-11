/*
$Revision: 1.1 $
$Date: 2007-09-11 22:52:24 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
