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

import java.util.HashSet;
import java.util.Set;

/**
 * A data transfer object for transferring properties
 * of experiments from remote data source.
 * @author dhall
 *
 */
public class ExperimentDto {

	//
	//  A T T R I B U T E S
	//
	
	/** Identifier of experiment in remote data source. */
	private String remoteId = null;
	
	/** Experiment name. */
	private String name = null;
	
	/** Identifiers of enclosed bioassays in remote data source. */
	private Set<String> remoteBioAssayIds = new HashSet<String>();
	
	/** Name of organism from remote data source. */
	private String organismName = null;

	//
	//  G E T T E R S  /  S E T T E R S
	//
	
	/**
	 * Getter for name property.
	 * @return Experiment name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name property.
	 * @param name Experiment name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter for organismName property.
	 * @return Name of organism from remote data source
	 */
	public String getOrganismName() {
		return organismName;
	}

	/**
	 * Setter for organismName property.
	 * @param organismName Name of organism from remote data source
	 */
	public void setOrganismName(final String organismName) {
		this.organismName = organismName;
	}

	
	/**
	 * Getter for remoteBioAssayIds property.
	 * @return Identifiers of enclosed bioassays in remote data source
	 */
	public Set<String> getRemoteBioAssayIds() {
		return remoteBioAssayIds;
	}

	/**
	 * Setter for remoteBioAssayIds property.
	 * @param remoteBioAssayIds Identifiers of enclosed bioassays
	 * in remote data source
	 */
	public void setRemoteBioAssayIds(final Set<String> remoteBioAssayIds) {
		this.remoteBioAssayIds = remoteBioAssayIds;
	}

	/**
	 * Getter for remoteId property.
	 * @return Identifier of experiment in remote data source
	 */
	public String getRemoteId() {
		return remoteId;
	}

	/**
	 * Setter for remoteIdProperty.
	 * @param remoteId Identifier of experiment in remote data source
	 */
	public void setRemoteId(final String remoteId) {
		this.remoteId = remoteId;
	}
	
	//
	//  B U S I N E S S    M E T H O D S
	//
	
	/**
	 * Add a remote bioassay identifier.
	 * @param id Identifier of a bioassay in a remote data source.
	 */
	public void addRemoteBioAssayId(final String id) {
		this.remoteBioAssayIds.add(id);
	}
}
