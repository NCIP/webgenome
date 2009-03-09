/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:17 $

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

package org.rti.webgenome.service.data;

import java.util.List;

/**
 * A data transfer object for transferring properties of
 * bioassays from a remote data source.
 * @author dhall
 *
 */
public class BioAssayDto {
	
	//
	//  A T T R I B U T E S
	//
	
	/** Identifier of bioassay in remote data source. */
	private String remoteId = null;
	
	/** Bioassay name. */
	private String name = null;
	
	/** ID of array in remote data source. */
	private String remoteArrayId = null;
	
	/** Actual data values from array. */
	private List<Float> values = null;
	
	/** Names of corresponding reporters. */
	private List<String> reporterNames = null;
	
	
	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Getter for name property.
	 * @return Bioassay name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name property.
	 * @param name Bioassay name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter for remoteId property.
	 * @return Identifier of bioassay in remote data source
	 */
	public String getRemoteId() {
		return remoteId;
	}

	/**
	 * Setter for remoteIdProperty.
	 * @param remoteId Identifier of bioassay in remote data source
	 */
	public void setRemoteId(final String remoteId) {
		this.remoteId = remoteId;
	}

	/**
	 * Getter for remoteArrayId property.
	 * @return ID of array in remote data source
	 */
	public String getRemoteArrayId() {
		return remoteArrayId;
	}

	/**
	 * Setter for remoteArrayId property.
	 * @param remoteArrayId ID of array in remote data source
	 */
	public void setRemoteArrayId(final String remoteArrayId) {
		this.remoteArrayId = remoteArrayId;
	}

	/**
	 * Getter for values property.
	 * @return Actual data values from array
	 */
	public List<Float> getValues() {
		return values;
	}

	/**
	 * Setter for values property.
	 * @param values Actual data values from array
	 */
	public void setValues(final List<Float> values) {
		this.values = values;
	}

	/**
	 * Getter for reporterNames property.
	 * @return Names of corresponding reporters
	 */
	public List<String> getReporterNames() {
		return reporterNames;
	}

	/**
	 * Setter for reporterNames property.
	 * @param reporterNames Names of corresponding reporters
	 */
	public void setReporterNames(final List<String> reporterNames) {
		this.reporterNames = reporterNames;
	}
	
	
}
