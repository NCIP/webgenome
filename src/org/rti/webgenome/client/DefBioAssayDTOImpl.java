/*
$Revision: 1.3 $
$Date: 2006-10-16 20:06:57 $

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
    
    /**
     * Constructor.
     * @param name Name
     * @param id ID
     * @param bioAssayDatumDTOArray Initial array of BioAssayDatumDTO
     */
    public DefBioAssayDTOImpl(final String name, final String id,
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

}
