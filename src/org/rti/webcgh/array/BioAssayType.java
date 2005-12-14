/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/BioAssayType.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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

package org.rti.webcgh.array;

import org.rti.webgenome.client.QuantitationTypes;

/**
 * Bioassay type
 */
public class BioAssayType {
	
	/**
	 * CGH
	 */
	public static final BioAssayType CGH = new BioAssayType("cgh", "arrayCGH");
	
	/**
	 * Gene expression
	 */
	public static final BioAssayType GENE_EXPRESSION = 
		new BioAssayType("expression", "gene expression");
	
	/**
	 * LOH
	 */
	public static final BioAssayType LOH =
		new BioAssayType("loh", "LOH");
	
	private String name = null;
	private String id = null;
	
	private BioAssayType(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	/**
	 * Get experiment type with given id
	 * @param id ID
	 * @return Experiment type
	 */
	public static BioAssayType getBioAssayType(String id) {
		BioAssayType type = CGH;
		if ("cgh".equals(id))
			type = CGH;
		else if ("expression".equals(id))
			type = GENE_EXPRESSION;
		else if (id == QuantitationTypes.COPY_NUMBER)
			type = CGH;
		else if (id == QuantitationTypes.COPY_NUMBER_LOG2_RATION)
			type = CGH;
		else if (id == QuantitationTypes.FOLD_CHANGE)
			type = GENE_EXPRESSION;
		else if (id == QuantitationTypes.FOLD_CHANGE_LOG2_RATIO)
			type = GENE_EXPRESSION;
		else if (id == QuantitationTypes.LOH)
			type = LOH;
		return type;
	}
	
	
	/**
	 * Get all experiment types
	 * @return All experiment types
	 */
	public static BioAssayType[] getAllBioAssayTypes() {
		return new BioAssayType[] {CGH, GENE_EXPRESSION, LOH};
	}
	
	
	/**
	 * Get name
	 * @return Name
	 */
	public String getName() {
	    return name;
	}
	
	
	/**
	 * Get ID
	 * @return ID
	 */
	public String getId() {
	    return id;
	}

}
