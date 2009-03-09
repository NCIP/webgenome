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
 * Data transfer object for transferring array data
 * from remote data source.
 * @author dhall
 *
 */
public class ArrayDto {

	//
	//  A T T R I B U T E S
	//
	
	/** Identifier of array in remote data source. */
	private String remoteId = null;
	
	/** Array name. */
	private String name = null;
	
	/** Names of reporters on array. */
	private List<String> reporterNames = null;
	
	/** Chromosome numbers of corresponding reporters. */
	private List<Short> chromosomeNumbers = null;
	
	/** Chromosome locations of corresponding reporters. */
	private List<Long> chromosomeLocations = null;
	
	
	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Getter for name property.
	 * @return Array name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name property.
	 * @param name Array name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter for remoteId property.
	 * @return Identifier of array in remote data source
	 */
	public String getRemoteId() {
		return remoteId;
	}

	/**
	 * Setter for remoteIdProperty.
	 * @param remoteId Identifier of array in remote data source
	 */
	public void setRemoteId(final String remoteId) {
		this.remoteId = remoteId;
	}

	/**
	 * Getter for chromosomeLocations property.
	 * @return Chromosome locations of corresponding reporters.
	 */
	public List<Long> getChromosomeLocations() {
		return chromosomeLocations;
	}

	/**
	 * Setter for chromosomeLocations property.
	 * @param chromosomeLocation Chromosome locations of corresponding reporters
	 */
	public void setChromosomeLocations(final List<Long> chromosomeLocation) {
		this.chromosomeLocations = chromosomeLocation;
	}

	/**
	 * Getter for chromosomeNumbers property.
	 * @return Chromosome numbers of corresponding reporters.
	 */
	public List<Short> getChromosomeNumbers() {
		return chromosomeNumbers;
	}

	/**
	 * Setter for chromosomeNumbers property.
	 * @param chromosomeNumbers Chromosome numbers of corresponding reporters.
	 */
	public void setChromosomeNumbers(final List<Short> chromosomeNumbers) {
		this.chromosomeNumbers = chromosomeNumbers;
	}

	/**
	 * Getter for reporterNames property.
	 * @return Names of reporters on array
	 */
	public List<String> getReporterNames() {
		return reporterNames;
	}

	/**
	 * Setter for reporterNames property.
	 * @param reporterNames Names of reporters on array
	 */
	public void setReporterNames(final List<String> reporterNames) {
		this.reporterNames = reporterNames;
	}
}
