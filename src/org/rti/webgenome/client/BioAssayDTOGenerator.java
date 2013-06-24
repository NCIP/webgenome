/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2006-11-29 03:14:07 $

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
