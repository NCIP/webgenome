/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/RegressionVariables.java,v $
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

package org.rti.regression;

import org.rti.regression.matrix.BioinfoMatrix;

/**
 * Input variables for regression
 */
public class RegressionVariables {
	
	private BioinfoMatrix values = null;
	private BioinfoMatrix groupVariables = null;
	private BioinfoMatrix arrayVariables = null;
	
	
	/**
	 * @return Returns the arrayVariables.
	 */
	public BioinfoMatrix getArrayVariables() {
		return arrayVariables;
	}
	
	
	/**
	 * @param arrayVariables The arrayVariables to set.
	 */
	public void setArrayVariables(BioinfoMatrix arrayVariables) {
		this.arrayVariables = arrayVariables;
	}
	
	
	/**
	 * @return Returns the groupVariables.
	 */
	public BioinfoMatrix getGroupVariables() {
		return groupVariables;
	}
	
	
	/**
	 * @param groupVariables The groupVariables to set.
	 */
	public void setGroupVariables(BioinfoMatrix groupVariables) {
		this.groupVariables = groupVariables;
	}
	
	
	/**
	 * @return Returns the values.
	 */
	public BioinfoMatrix getValues() {
		return values;
	}
	
	
	/**
	 * @param values The values to set.
	 */
	public void setValues(BioinfoMatrix values) {
		this.values = values;
	}
	
	
	/**
	 * Constructor
	 *
	 */
	public RegressionVariables() {}
	
	
	/**
	 * Constructor
	 * @param values Data values
	 * @param groupVariables Group-level variables
	 * @param arrayVariables Array-level variables
	 */
	public RegressionVariables
	(
		BioinfoMatrix values, BioinfoMatrix groupVariables, 
		BioinfoMatrix arrayVariables)
	{
		this.values = values;
		this.groupVariables = groupVariables;
		this.arrayVariables = arrayVariables;
	}

}
