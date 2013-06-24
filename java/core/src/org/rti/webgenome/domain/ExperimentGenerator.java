/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:42 $


*/

package org.rti.webgenome.domain;

import java.util.List;

import org.rti.webgenome.service.io.DataFileManager;

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
	
	/** Next bioassay ID. */
	private long nextBioAssayId = 1;
	
	
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
			ba.setId(this.nextBioAssayId++);
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
