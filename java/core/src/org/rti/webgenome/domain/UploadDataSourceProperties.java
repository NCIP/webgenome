/*
$Revision: 1.1 $
$Date: 2007-08-24 21:51:57 $

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

package org.rti.webgenome.domain;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.units.BpUnits;

/**
 * This class represents an upload of data.
 * @author dhall
 *
 */
public class UploadDataSourceProperties
extends DataSourceProperties.BaseDataSourceProperties {
	
	//
	//  A T T R I B U T E S
	//

	/** Metadata on data files to upload. */
	private Set<DataFileMetaData> dataFileMetaData =
		new HashSet<DataFileMetaData>();
	
	/**
	 * Local name of file containign reporter data as
	 * it resides on the server.  Note, this is not an
	 * absolute path.
	 */
	private String reporterLocalFileName = null;
	
	/**
	 * Remote name of file containing reporter data
	 * as it resides on the users system.  Note, this
	 * is not an absolute path.
	 */
	private String reporterRemoteFileName = null;
	
	/** Format of file containing reporter data. */
	private RectangularTextFileFormat reporterFileFormat = null;
	
	/** Heading of column in reporter file containing reporter names. */
	private String reporterFileReporterNameColumnName = null;
	
	/**
	 * Name of column containing reporter chromosome numbers.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 */
	private String chromosomeColumnName = null;
	
	/**
	 * Name of column containing reporter chromosome positions.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 */
	private String positionColumnName = null;
	
	/**
	 * Units of chromosome position in the
	 * column given by {@code positionColumnName}.
	 */
	private BpUnits positionUnits = null;
	
	/** Name of new experiment that will result from upload. */
	private String experimentName = null;
	
	/** Organism that was subject of experiment. */
	private Organism organism = null;
	
	
	//
	//  G E T T E R S / S E T T E R S
	//

	/**
	 * Get name of column containing reporter chromosome numbers.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 * @return Column heading
	 */
	public String getChromosomeColumnName() {
		return chromosomeColumnName;
	}

	/**
	 * Set name of column containing reporter chromosome numbers.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 * @param chromosomeColumnName Column heading
	 */
	public void setChromosomeColumnName(final String chromosomeColumnName) {
		this.chromosomeColumnName = chromosomeColumnName;
	}

	/**
	 * Get heading of column in reporter file containing reporter names.
	 * @return Column heading
	 */
	public String getReporterFileReporterNameColumnName() {
		return reporterFileReporterNameColumnName;
	}

	/**
	 * Set heading of column in reporter file containing reporter names.
	 * @param reporterFileReporterNameColumnName Column name
	 */
	public void setReporterFileReporterNameColumnName(
			final String reporterFileReporterNameColumnName) {
		this.reporterFileReporterNameColumnName =
			reporterFileReporterNameColumnName;
	}

	/**
	 * Get metadata on files to upload.
	 * @return File metadata
	 */
	public Set<DataFileMetaData> getDataFileMetaData() {
		return dataFileMetaData;
	}

	/**
	 * Set metadata on files to upload.
	 * @param dataFileMetaData File metadata
	 */
	public void setDataFileMetaData(
			final Set<DataFileMetaData> dataFileMetaData) {
		this.dataFileMetaData = dataFileMetaData;
	}

	/**
	 * Get name of new experiment that results from upload.
	 * @return Experiment name
	 */
	public String getExperimentName() {
		return experimentName;
	}

	/**
	 * Set name of new experiment that results from upload.
	 * @param experimentName Experiment name
	 */
	public void setExperimentName(final String experimentName) {
		this.experimentName = experimentName;
	}

	/**
	 * Get organism that was subject of experiment.
	 * @return Organism
	 */
	public Organism getOrganism() {
		return organism;
	}

	/**
	 * Set organism that was subject of experiment.
	 * @param organism Organism
	 */
	public void setOrganism(final Organism organism) {
		this.organism = organism;
	}

	/**
	 * Get name of column containing reporter chromosome positions.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 * @return Column heading
	 */
	public String getPositionColumnName() {
		return positionColumnName;
	}

	/**
	 * Set Name of column containing reporter chromosome positions.
	 * If {@code reporterLocalFileName} is not null, then
	 * this name should reference a column in the corresponding
	 * file.  Otherwise, it should reference a column in
	 * ALL data files.
	 * @param positionColumnName Column heading
	 */
	public void setPositionColumnName(final String positionColumnName) {
		this.positionColumnName = positionColumnName;
	}

	/**
	 * Get units of chromosome position in the
	 * column given by {@code positionColumnName}.
	 * @return Chromosome units
	 */
	public BpUnits getPositionUnits() {
		return positionUnits;
	}

	/**
	 * Set units of chromosome position in the
	 * column given by {@code positionColumnName}.
	 * @param positionUnits Chromosome units
	 */
	public void setPositionUnits(final BpUnits positionUnits) {
		this.positionUnits = positionUnits;
	}

	/**
	 * Get format of file containing reporter data.
	 * @return File format
	 */
	public RectangularTextFileFormat getReporterFileFormat() {
		return reporterFileFormat;
	}

	/**
	 * Set format of file containing reporter data.
	 * @param reporterFileFormat File format
	 */
	public void setReporterFileFormat(
			final RectangularTextFileFormat reporterFileFormat) {
		this.reporterFileFormat = reporterFileFormat;
	}

	/**
	 * Get local name of file containign reporter data as
	 * it resides on the server.  Note, this is not an
	 * absolute path.
	 * @return Local file name
	 */
	public String getReporterLocalFileName() {
		return reporterLocalFileName;
	}

	/**
	 * Set local name of file containign reporter data as
	 * it resides on the server.  Note, this is not an
	 * absolute path.
	 * @param reporterLocalFileName Local file name
	 */
	public void setReporterLocalFileName(final String reporterLocalFileName) {
		this.reporterLocalFileName = reporterLocalFileName;
	}

	/**
	 * Get remote name of file containing reporter data
	 * as it resides on the users system.  Note, this
	 * is not an absolute path.
	 * @return Remote file name
	 */
	public String getReporterRemoteFileName() {
		return reporterRemoteFileName;
	}

	/**
	 * Set remote name of file containing reporter data
	 * as it resides on the users system.  Note, this
	 * is not an absolute path.
	 * @param reporterRemoteFileName Remote file name
	 */
	public void setReporterRemoteFileName(
			final String reporterRemoteFileName) {
		this.reporterRemoteFileName = reporterRemoteFileName;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public UploadDataSourceProperties() {
		
	}
	
	//
	//  B U S I N E S S   M E T H O D S
	//
	
	/**
	 * Get name of reporter file format to be used for persistence.
	 * @return Reporter file format
	 */
	public String getReporterFileFormatName() {
		return this.reporterFileFormat.name();
	}
	
	/**
	 * Set reporter file format using name.  This is used
	 * for persistence.
	 * @param name Name of reporter file format
	 */
	public void setReporterFileFormatName(final String name) {
		this.reporterFileFormat = RectangularTextFileFormat.valueOf(name);
	}
	
	/**
	 * Get name of position units for persistence of enumerated type.
	 * @return Name of position units
	 */
	public String getPositionUnitsName() {
		return this.positionUnits.getName();
	}
	
	/**
	 * Set position units using units name.  This is used for
	 * persistence of the enumerated type.
	 * @param name Name of position units
	 */
	public void setPositionUnitsName(final String name) {
		this.positionUnits = BpUnits.getUnits(name);
	}
	
	/**
	 * Add data file meta data.
	 * @param metaData Data file meta data
	 */
	public void add(final DataFileMetaData metaData) {
		this.dataFileMetaData.add(metaData);
	}
	
	
	/**
	 * Remove data file metadata with given local file name.
	 * @param localFileName Local file name (not absolute path)
	 */
	public void removeDataFileMetaData(final String localFileName) {
		for (DataFileMetaData meta : this.dataFileMetaData) {
			if (localFileName.equals(meta.getLocalFileName())) {
				this.dataFileMetaData.remove(meta);
				break;
			}
		}
	}
	
	
	/**
	 * Remove reporter file metadata.
	 */
	public void removeRepoterFile() {
		this.reporterFileFormat = null;
		this.reporterLocalFileName = null;
		this.reporterRemoteFileName = null;
		this.reporterFileReporterNameColumnName = null;
	}
	
	
	/**
	 * Set metadata about reporter file.
	 * @param format File format
	 * @param localFileName Local file name on server (not absolute path)
	 * @param remoteFileName Remote file name of user's system
	 * @param reporterFileReporterNameColumnName
	 * Heading of column in reporter file containing reporter names.
	 * (not absolute path)
	 */
	public void setReporterFile(
			final RectangularTextFileFormat format,
			final String localFileName,
			final String remoteFileName,
			final String reporterFileReporterNameColumnName) {
		this.reporterFileFormat = format;
		this.reporterLocalFileName = localFileName;
		this.reporterRemoteFileName = remoteFileName;
		this.reporterFileReporterNameColumnName =
			reporterFileReporterNameColumnName;
	}
}
