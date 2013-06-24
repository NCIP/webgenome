/*
$Revision: 1.4 $
$Date: 2008-10-23 16:17:06 $


*/

package org.rti.webgenome.domain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

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
	
	/** VB added support for range **/
	private String startPositionColumnName = null;
	private String endPositionColumnName = null;
	private String numMarkersColumnName = null;
	
	/**
	 * Units of chromosome position in the
	 * column given by {@code positionColumnName}.
	 */
	private BpUnits positionUnits = null;
	
	/** Name of new experiment that will result from upload. */
	private String experimentName = null;
	
	/** Organism that was subject of experiment. */
	private Organism organism = null;
	
	/** Quantitation type of all data. */
	private QuantitationType quantitationType = null;
	
	
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
	 * Get quantitation type of all data.
	 * @return Quantitation type
	 */
	public QuantitationType getQuantitationType() {
		return quantitationType;
	}

	/**
	 * Set quantitation type of all data.
	 * @param quantitationType Quantitation type
	 * of all data
	 */
	public void setQuantitationType(
			final QuantitationType quantitationType) {
		this.quantitationType = quantitationType;
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
	
	/**
	 * Constructor performing deep copy of given object.
	 * @param props Object to deep copy
	 */
	public UploadDataSourceProperties(
			final UploadDataSourceProperties props) {
		this.chromosomeColumnName = props.chromosomeColumnName;
		this.dataFileMetaData = new HashSet<DataFileMetaData>();
		for (DataFileMetaData meta : props.dataFileMetaData) {
			this.dataFileMetaData.add(new DataFileMetaData(meta));
		}
		this.experimentName = props.experimentName;
		this.organism = props.organism;
		this.positionColumnName = props.positionColumnName;
		this.positionUnits = props.positionUnits;
		this.reporterFileFormat = props.reporterFileFormat;
		this.reporterFileReporterNameColumnName =
			props.reporterFileReporterNameColumnName;
		this.reporterLocalFileName = props.reporterLocalFileName;
		this.reporterRemoteFileName = props.reporterRemoteFileName;
		this.quantitationType = props.quantitationType;
	}
	
	//
	//  B U S I N E S S   M E T H O D S
	//
	
	/**
	 * Get name of reporter file format to be used for persistence.
	 * @return Reporter file format
	 */
	public String getReporterFileFormatName() {
		String name = null;
		if (this.reporterFileFormat != null) {
			name = this.reporterFileFormat.name();
		} else {
			name = "null";
		}
		return name;
	}
	
	/**
	 * Set reporter file format using name.  This is used
	 * for persistence.
	 * @param name Name of reporter file format
	 */
	public void setReporterFileFormatName(final String name) {
		if ("null".equals(name)) {
			this.reporterFileFormat = null;
		} else {
			this.reporterFileFormat =
				RectangularTextFileFormat.valueOf(name);
		}
	}
	
	/**
	 * Get name of position units for persistence of enumerated type.
	 * @return Name of position units
	 */
	public String getPositionUnitsName() {
		String name = null;
		if (this.positionUnits != null) {
			name = this.positionUnits.getName();
		} else {
			name = "null";
		}
		return name;
	}
	
	/**
	 * Set position units using units name.  This is used for
	 * persistence of the enumerated type.
	 * @param name Name of position units
	 */
	public void setPositionUnitsName(final String name) {
		if ("null".equals(name)) {
			this.positionUnits = null;
		} else {
			this.positionUnits = BpUnits.getUnits(name);
		}
	}
	
	/**
	 * Get ID of quantitation type for persisting
	 * this enumerated type.
	 * @return ID of quantitation type.
	 */
	public String getQuantitationTypeId() {
		String name = null;
		if (this.quantitationType != null) {
			name = this.quantitationType.getId();
		} else {
			name = "null";
		}
		return name;
	}
	
	/**
	 * Set quantitation type by specifying ID.  This
	 * is used for persistence.
	 * @param id ID of quantitation type
	 */
	public void setQuantitationTypeId(final String id) {
		if ("null".equals(id)) {
			this.quantitationType = null;
		} else {
			this.quantitationType =
				QuantitationType.getQuantitationType(id);
		}
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
	
	public String print2Buff(){
		StringBuffer buff = new StringBuffer();
		buff.append("********Printing UploadProperties attributes*******");
		buff.append("chromosomeColumnName = " + this.chromosomeColumnName + "\n");
		
		if (!dataFileMetaData.isEmpty()){
			for(DataFileMetaData entry:dataFileMetaData){			
				buff.append(entry.print2Buff());
			}
		}else
			buff.append("dataFileMetaData is empty");
		
		
		buff.append("experimentName = " + this.experimentName + "\n");	
		if (organism != null)
			buff.append("organism = " + this.organism.print2Buff() + "\n");
		else
			buff.append("organism is empty");
		
		buff.append("positionColumnName = " + this.positionColumnName + "\n");
		if (this.quantitationType != null)
			buff.append("quantitationType.name = " + this.quantitationType.getName() + "\n");
		else
			buff.append("quantitationType is empty");
		
		if (this.reporterFileFormat != null)
			buff.append("reporterFileFormat = " + this.reporterFileFormat.print2Buff() + "\n");
		else
			buff.append("reporterFileFormat is empty");
		
		buff.append("reporterFileReporterNameColumnName = " + this.reporterFileReporterNameColumnName + "\n");	
		buff.append("reporterLocalFileName = " + this.reporterLocalFileName + "\n");	
		buff.append("reporterRemoteFileName = " + this.reporterRemoteFileName + "\n");	
		
	
			
		return buff.toString();
	}

	public String getStartPositionColumnName() {
		return startPositionColumnName;
	}

	public void setStartPositionColumnName(String startPositionColumnName) {
		this.startPositionColumnName = startPositionColumnName;
	}

	public String getEndPositionColumnName() {
		return endPositionColumnName;
	}

	public void setEndPositionColumnName(String endPositionColumnName) {
		this.endPositionColumnName = endPositionColumnName;
	}

	public String getNumMarkersColumnName() {
		return numMarkersColumnName;
	}

	public void setNumMarkersColumnName(String numMarkersColumnName) {
		this.numMarkersColumnName = numMarkersColumnName;
	}
}
