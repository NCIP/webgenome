/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:17 $


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
