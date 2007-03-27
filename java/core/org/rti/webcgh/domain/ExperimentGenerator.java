/*
$Revision: 1.1 $
$Date: 2007-03-27 19:42:10 $

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

package org.rti.webcgh.domain;

import java.util.List;

import org.rti.webcgh.service.io.DataFileManager;

/**
 * Class that generates <code>Experiment</code>
 * objects for unit testing.
 * @author dhall
 *
 */
public final class ExperimentGenerator {
	
	// =======================
	//    Attributes
	// =======================
	
	/** Array datum generator. */
	private ArrayDatumGenerator arrayDatumGenerator = null;
	
	
	// =========================
	//     Constructors
	// =========================
	
	/**
	 * Constructor.
	 */
	public ExperimentGenerator() {
		this.arrayDatumGenerator = new ArrayDatumGenerator();
	}
	
	
	/**
	 * Constructor.
	 * @param gap Gap between generated reporters
	 * in base pairs
	 */
	public ExperimentGenerator(final long gap) {
		this.arrayDatumGenerator = new ArrayDatumGenerator(gap);
	}
	
	
	// =======================
	//    Business methods
	// =======================
	
	/**
	 * Create new experiment object where all data are
	 * in memory.
	 * @param numBioAssays Number of bioassays to create
	 * for experiment.
	 * @param numChromosomes Number of chromosomes
	 * @param numDatumPerChromosome Number of
	 * <code>ArrayDatum</code> objects to create
	 * per chromosome
	 * @return An experiment where all data are in
	 * memory
	 */
	public Experiment newInMemoryExperiment(
			final int numBioAssays,
			final int numChromosomes,
			final int numDatumPerChromosome) {
		Experiment exp = new Experiment();
		exp.setName("Experiment");
		Array array = new Array();
		for (int i = 0; i < numBioAssays; i++) {
			DataContainingBioAssay ba = new DataContainingBioAssay();
			exp.add(ba);
			ba.setArray(array);
			ba.setName("Bioassay " + i);
			for (short j = 1; j <= numChromosomes; j++) {
				this.arrayDatumGenerator.setChromosome(j);
				for (int k = 0; k < numDatumPerChromosome; k++) {
					ArrayDatum ad = this.arrayDatumGenerator.newArrayDatum();
					ba.add(ad);
				}
			}
			this.arrayDatumGenerator.reset();
		}
		return exp;
	}
	
	
	/**
	 * Create new experiment object where chromosome
	 * array data have been serialized to disk.
	 * @param numBioAssays Number of bioassays to create
	 * for experiment.
	 * @param numChromosomes Number of chromosomes
	 * @param numDatumPerChromosome Number of
	 * <code>ArrayDatum</code> objects to create
	 * per chromosome
	 * @param dataFileManager A data file manager to
	 * mediate serialization/deserialization
	 * @return An experiment where chromosome array
	 * data have been serialized
	 */
	public Experiment newDataSerializedExperiment(
			final int numBioAssays,
			final int numChromosomes,
			final int numDatumPerChromosome,
			final DataFileManager dataFileManager) {
		Experiment exp = new Experiment();
		exp.setName("Experiment");
		Array array = new Array();
		for (int i = 0; i < numBioAssays; i++) {
			DataSerializedBioAssay ba = new DataSerializedBioAssay();
			exp.add(ba);
			ba.setArray(array);
			ba.setName("Bioassay " + i);
			for (short j = 1; j <= numChromosomes; j++) {
				ChromosomeArrayData cad = new ChromosomeArrayData(j);
				this.arrayDatumGenerator.setChromosome(j);
				for (int k = 0; k < numDatumPerChromosome; k++) {
					ArrayDatum ad = this.arrayDatumGenerator.newArrayDatum();
					cad.add(ad);
				}
				dataFileManager.saveChromosomeArrayData(ba, cad);
			}
			this.arrayDatumGenerator.reset();
		}
		return exp;
	}
	
	
	/**
	 * Get all reporters generated thus far.
	 * @return Reporters generated thus far
	 */
	public List<Reporter> getReporters() {
		return this.arrayDatumGenerator.getReporters();
	}
	
	
	/**
	 * Set gap between generated reporters.
	 * @param gap Gap between generated reporters
	 * in base pairs.
	 */
	public void setGap(final long gap) {
		this.arrayDatumGenerator.setGap(gap);
	}
}
