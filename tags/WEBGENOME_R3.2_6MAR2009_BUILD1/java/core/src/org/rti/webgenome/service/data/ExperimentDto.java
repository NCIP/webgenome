/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:17 $


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
