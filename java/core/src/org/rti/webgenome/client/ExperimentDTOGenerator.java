/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

package org.rti.webgenome.client;


/**
 * Generates <code>ExperimentDTO</code> objects for testing.
 * @author dhall
 *
 */
public class ExperimentDTOGenerator {
	
	/** Bioassay data transfer object generator. */
	private final BioAssayDTOGenerator bioAssayDTOGenerator;
	
	/** Number of bioassays per experiment. */
	private final int numBioAssays;
	

	/**
	 * Constructor.
	 * @param gap Gap between generated reporters.
	 * @param numBioAssays Number of bioassays generated per experiment.
	 */
	public ExperimentDTOGenerator(final long gap, final int numBioAssays) {
		this.bioAssayDTOGenerator = new BioAssayDTOGenerator(gap);
		this.numBioAssays = numBioAssays;
	}
	
	
	/**
	 * Generate new experiment data transfer object.
	 * @param experimentId Experiment ID.
	 * @param constraints Constraints.
	 * @return Experiment data transfer object
	 */
	public final ExperimentDTO newExperimentDTO(
			final String experimentId,
			final BioAssayDataConstraints[] constraints) {
		BioAssayDTO[] bioAssayDtos = new BioAssayDTO[this.numBioAssays];
		for (int i = 0; i < this.numBioAssays; i++) {
			bioAssayDtos[i] =
				this.bioAssayDTOGenerator.newBioAssayDTO(constraints);
		}
		return new DefExperimentDTOImpl(experimentId, bioAssayDtos);
	}
}
