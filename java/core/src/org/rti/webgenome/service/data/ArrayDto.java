/*
$Revision: 1.1 $
$Date: 2008-03-12 22:23:17 $


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
