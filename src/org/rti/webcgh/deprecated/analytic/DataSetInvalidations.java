/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/analytic/DataSetInvalidations.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 05:33:47 $

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


package org.rti.webcgh.deprecated.analytic;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Container for set of invalidations
 */
public class DataSetInvalidations {
	
	private Collection invalidations = new ArrayList();
	
	
	/**
	 * Constructor
	 *
	 */
	public DataSetInvalidations() {}
	
	
	/**
	 * Setter for property invalidations
	 * @param invalidations Invalidations
	 */
	public void setInvalidations(Collection invalidations) {
		this.invalidations = invalidations;
	}
	
	
	/**
	 * Getter for property invalidations
	 * @return Invalidations
	 */
	public Collection getInvalidations() {
		return invalidations;
	}
	
	
	/**
	 * Add an invalidation
	 * @param invalidation An invalidation
	 */
	public void addInvalidation(DataSetInvalidation invalidation) {
		invalidations.add(invalidation);
	}
	
	
	/**
	 * Add invalidations
	 * @param dsi Data set invalidations
	 */
	public void addInvalidations(DataSetInvalidations dsi) {
		if (dsi != null)
			invalidations.addAll(dsi.getInvalidations());
	}
	
	
	public String getMessages() {
		StringBuffer buff = new StringBuffer();
		int count = 0;
		for (Iterator it = this.invalidations.iterator(); it.hasNext();) {
			DataSetInvalidation dsi = (DataSetInvalidation)it.next();
			if (count++ > 0)
				buff.append("***");
			buff.append(dsi.getMessage());
		}
		return buff.toString();
	}

}
