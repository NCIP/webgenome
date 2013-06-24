/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

package org.rti.webgenome.client;


/**
 * Generates <code>BioAssayDTO</code> objects
 * for testing.
 * @author dhall
 *
 */
public class BioAssayDTOGenerator {
	
	/** Bioassay datum DTO generator. */
	private final BioAssayDatumDTOGenerator bioAssayDatumDtoGenerator;
	
	/** Count of objects generated since instantiation. */
	private int bioAssayDTOCount = 0;
	
	
	/**
	 * Constructor.
	 * @param gap Gap between generated reporters in base pairs.
	 */
	public BioAssayDTOGenerator(final long gap) {
		this.bioAssayDatumDtoGenerator = new BioAssayDatumDTOGenerator(gap);
	}

	
	/**
	 * Generate a new bioassay data transfer object.
	 * @param constraints Constraints.
	 * @return A new bioassay data transfer object.
	 */
	public final BioAssayDTO newBioAssayDTO(
			final BioAssayDataConstraints[] constraints) {
		int bioAssayNum = ++this.bioAssayDTOCount;
		String bioAssayName = "Bioassay " + bioAssayNum;
		String bioAssayId = String.valueOf(bioAssayNum);
		DefBioAssayDTOImpl dto = new DefBioAssayDTOImpl(bioAssayId,
				bioAssayName,
				this.bioAssayDatumDtoGenerator.newBioAssayDatumDTOs(
						constraints));
		if (constraints != null && constraints.length > 0) {
			for (int i = 0; i < constraints.length; i++) {
				if (i == 0) {
					dto.setQuantitationType(
							constraints[i].getQuantitationType());
				} else {
					if (!dto.getQuantitationType().equals(
							constraints[i].getQuantitationType())) {
						throw new IllegalArgumentException(
								"Cannot have mixed quantitation types within a "
								+ "bioassay");
					}
				}
			}
		}
		return dto;
	}
	
	
	/**
	 * Generate a new bioassay data transfer object.
	 * @param name Bioassay name
	 * @param constraints Constraints.
	 * @return A new bioassay data transfer object.
	 */
	public final BioAssayDTO newBioAssayDTO(
			final String name,
			final BioAssayDataConstraints[] constraints) {
		DefBioAssayDTOImpl dto = (DefBioAssayDTOImpl)
			this.newBioAssayDTO(constraints);
		dto.setName(name);
		return dto;
	}
}
