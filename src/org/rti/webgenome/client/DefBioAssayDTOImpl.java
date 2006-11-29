/*
$Revision: 1.9 $
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

import java.util.ArrayList;
import java.util.Collection;

import org.rti.webcgh.util.SystemUtils;

/**
 * Implementation of <code>BioAssayDTO</code> used primarily for
 * testing.
 * @author dhall
 *
 */
public class DefBioAssayDTOImpl implements BioAssayDTO {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	/** Bioassay name. */
    private String name = null;
    
    /** Bioassay identifier. */
    private String id = null;
    
    /** Bioassay datum. */
    private Collection<BioAssayDatumDTO> bioAssayData =
    	new ArrayList<BioAssayDatumDTO>();
    
    /** Quantitation type. */
    private String quantitationType = QuantitationTypes.COPY_NUMBER_LOG2_RATION;
    
    /**
     * Constructor.
     * @param id ID
     * @param name Name
     * @param bioAssayDatumDTOArray Initial array of BioAssayDatumDTO
     */
    public DefBioAssayDTOImpl(final String id, final String name, 
    		final BioAssayDatumDTO[] bioAssayDatumDTOArray) {
        this.name = name;
        this.id = id;
        for (int i = 0; i < bioAssayDatumDTOArray.length; i++) {
            this.bioAssayData.add(bioAssayDatumDTOArray[i]);
        }
    }
    
    /**
     * Get ID.
     * @return String ID
     */
    public final String getID() {
        return id;
    }

    /**
     * Get name.
     * @return String Name
     */
    public final String getName() {
        return name;
    }
    

    /**
     * Set ID.
     * @param id ID
     */
	public final void setId(final String id) {
		this.id = id;
	}

	/**
	 * Set name.
	 * @param name Name
	 */
	public final void setName(final String name) {
		this.name = name;
	}

	/**
     * Get BioAssayDatumDTO[] array.
     * @return BioAssayDatumDTO[] bioassay datum DTOs
     */
    public final BioAssayDatumDTO[] getBioAssayData() {
        BioAssayDatumDTO[] dtos = new BioAssayDatumDTO[0];
        dtos = (BioAssayDatumDTO[]) this.bioAssayData.toArray(dtos);
        return dtos;
    }
    
    /**
     * Add a BioAssayDatumDTO to this BioAssayDTO.
     * @param dto Bioassay datum data transfer object
     */
    public final void add(final BioAssayDatumDTO dto) {
        this.bioAssayData.add(dto);
    }

    /**
     * Get quantitation type.
     * @return Quantitation type
     */
	public final String getQuantitationType() {
		return this.quantitationType;
	}

	/**
	 * Set quantitation type.
	 * @param quantitationType Quantitation type
	 */
	public final void setQuantitationType(final String quantitationType) {
		this.quantitationType = quantitationType;
	}
}
