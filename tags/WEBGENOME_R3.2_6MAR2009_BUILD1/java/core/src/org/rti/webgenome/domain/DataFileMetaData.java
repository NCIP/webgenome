/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.6 $
$Date: 2008-10-23 16:17:06 $


*/

package org.rti.webgenome.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents metadata about a data file that is uploaded during
 * an import operation.
 * @author dhall
 *
 */
public class DataFileMetaData {

	//
	//  A T T R I B U T E S
	//
	
	/** Primary key value used for persistence. */
	private Long id = null;
	
	/**
	 * Name of remote file as it resides on the user's system.
	 * Note, this is not the full file path.
	 */
	private String remoteFileName = null;
	
	/**
	 * Name of local file as it resides on the server.
	 * Note, this is not the full file path.
	 */
	private String localFileName = null;
	
	/** File format. */
	private RectangularTextFileFormat format = null;
	
	/** Name (heading) of column containing reporter names. */
	private String reporterNameColumnName  = null;
	
	/** Column metadata. */
	private Set<DataColumnMetaData> dataColumnMetaData =
		new HashSet<DataColumnMetaData>();
	

	//
	//  G E T T E R S / S E T T E R S
	//
	
	/**
	 * Get metadata on data columns.
	 * @return Metadata
	 */
	public Set<DataColumnMetaData> getDataColumnMetaData() {
		return dataColumnMetaData;
	}

	/**
	 * Set metadata on data columns.
	 * @param dataColumnMetaData Metadata
	 */
	public void setDataColumnMetaData(
			final Set<DataColumnMetaData> dataColumnMetaData) {
		this.dataColumnMetaData = dataColumnMetaData;
	}

	/**
	 * Get primary key value used for persistence.
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set primary key value used for persistence.
	 * @param id ID
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Get file format.
	 * @return File format
	 */
	public RectangularTextFileFormat getFormat() {
		return format;
	}

	/**
	 * Set file format.
	 * @param format File format
	 */
	public void setFormat(final RectangularTextFileFormat format) {
		this.format = format;
	}

	/**
	 * Get name of local file as it resides on the server.
	 * Note, this is not the full file path.
	 * @return Local file name
	 */
	public String getLocalFileName() {
		return localFileName;
	}

	/**
	 * Set name of local file as it resides on the server.
	 * Note, this is not the full file path.
	 * @param localFileName Local file name
	 */
	public void setLocalFileName(final String localFileName) {
		this.localFileName = localFileName;
	}

	/**
	 * Get name of remote file as it resides on the user's system.
	 * Note, this is not the full file path.
	 * @return Remote file name
	 */
	public String getRemoteFileName() {
		return remoteFileName;
	}

	/**
	 * Set name of local file as it resides on the user's system.
	 * Note, this is not the full file path.
	 * @param remoteFileName Remote file name
	 */
	public void setRemoteFileName(final String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	/**
	 * Get name (heading) of column containing reporter name.
	 * @return Reporter column name
	 */
	public String getReporterNameColumnName() {
		return reporterNameColumnName;
	}

	/**
	 * Set name (heading) of column containing reporter name.
	 * @param reporterNameColumnName Reporter column name
	 */
	public void setReporterNameColumnName(
			final String reporterNameColumnName) {
		this.reporterNameColumnName = reporterNameColumnName;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public DataFileMetaData() {
		
	}
	
	/**
	 * Constructor performing deep copy of given object.
	 * @param meta Object to deep copy
	 */
	public DataFileMetaData(final DataFileMetaData meta) {
		this.dataColumnMetaData = new HashSet<DataColumnMetaData>();
		for (DataColumnMetaData colMeta : meta.dataColumnMetaData) {
			this.dataColumnMetaData.add(new DataColumnMetaData(colMeta));
		}
		this.format = meta.format;
		this.localFileName = meta.localFileName;
		this.remoteFileName = meta.remoteFileName;
		this.reporterNameColumnName = meta.reporterNameColumnName;
	}
	
	//
	//  B U S I N E S S   M E T H O D S
	//
	
	/**
	 * Add metadata on a column.
	 * @param meta Metadata on a column
	 */
	public void add(final DataColumnMetaData meta) {
		this.dataColumnMetaData.add(meta);
	}
	
	/**
	 * Get name of format.  This is to be used in
	 * persistence of the enumerated type.
	 * @return Name of format.
	 */
	public String getFormatName() {
		String name = null;
		if (this.format == null) {
			name = "null";
		} else {
			name = this.format.name();
		}
		return name;
	}
	
	/**
	 * Set format using name.  This is to be used
	 * in persistence of the enumerated type.
	 * @param formatName Name of format.
	 */
	public void setFormatName(final String formatName) {
		if ("null".equals(formatName)) {
			this.format = null;
		} else {
			this.format = RectangularTextFileFormat.valueOf(formatName);
		}
	}
	
	public String print2Buff(){
		StringBuffer buff = new StringBuffer();
		buff.append("*****Printing DataFileMetaData START ******\n");
		
	    for(DataColumnMetaData colMetaDataEntry : dataColumnMetaData){
	    	buff.append(colMetaDataEntry.print2Buff());
	    }
	    			
		buff.append(this.getFormat().print2Buff());
		buff.append("FormatName = " + this.getFormatName() + "\n");
		buff.append("Id = " + this.getId()  + "\n");
		buff.append("LocalFileName = " + this.getLocalFileName() + "\n");
		buff.append("RemoteFileName = " + this.getRemoteFileName() + "\n");
		buff.append("ReporterNameColumnName = " + this.getReporterNameColumnName() + "\n");
		return buff.toString();
	}
}
