/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/domain/ExperimentGenerator.java,v $
$Revision: 1.1 $
$Date: 2006-08-28 20:16:28 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.domain;

import org.rti.webcgh.io.DataFileManager;

/**
 * Class that generates <code>Experiment</code>
 * objects for unit testing.
 * @author dhall
 *
 */
public final class ExperimentGenerator {
	
	/**
	 * Private constructor to make sure
	 * other classes do not create concrete
	 * instances of this utility class.
	 */
	private ExperimentGenerator() {
		
	}
	
	
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
	public static Experiment newInMemoryExperiment(
			final int numBioAssays,
			final int numChromosomes,
			final int numDatumPerChromosome) {
		Experiment exp = new Experiment();
		ArrayDatumGenerator adg = new ArrayDatumGenerator();
		for (int i = 0; i < numBioAssays; i++) {
			DataContainingBioAssay ba = new DataContainingBioAssay();
			exp.add(ba);
			for (short j = 0; j < numChromosomes; j++) {
				for (int k = 0; k < numDatumPerChromosome; k++) {
					ArrayDatum ad = adg.newArrayDatum();
					ad.getReporter().setChromosome(j);
					ba.add(ad);
				}
			}
			adg.reset();
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
	public static Experiment newDataSerializedExperiment(
			final int numBioAssays,
			final int numChromosomes,
			final int numDatumPerChromosome,
			final DataFileManager dataFileManager) {
		Experiment exp = new Experiment();
		ArrayDatumGenerator adg = new ArrayDatumGenerator();
		for (int i = 0; i < numBioAssays; i++) {
			DataSerializedBioAssay ba = new DataSerializedBioAssay();
			exp.add(ba);
			for (short j = 0; j < numChromosomes; j++) {
				ChromosomeArrayData cad = new ChromosomeArrayData(j);
				for (int k = 0; k < numDatumPerChromosome; k++) {
					ArrayDatum ad = adg.newArrayDatum();
					ad.getReporter().setChromosome(j);
					cad.add(ad);
				}
				dataFileManager.saveChromosomeArrayData(ba, cad);
			}
			adg.reset();
		}
		return exp;
	}

}
