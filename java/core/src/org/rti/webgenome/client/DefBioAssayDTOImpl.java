/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

package org.rti.webgenome.client;


import java.util.ArrayList;
import java.util.Collection;

import org.rti.webgenome.util.SystemUtils;

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
