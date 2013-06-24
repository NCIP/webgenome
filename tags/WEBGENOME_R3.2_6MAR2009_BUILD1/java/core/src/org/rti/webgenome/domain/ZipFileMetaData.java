/*
$Revision: 1.3 $
$Date: 2007-09-18 00:09:59 $


*/

package org.rti.webgenome.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents metadata concerning an uploaded
 * ZIP file containing individual data files.
 * @author dhall
 *
 */
public class ZipFileMetaData {

	//
	//  A T T R I B U T E S
	//
	
	/** Name (not full path) of ZIP file on remote system. */
	private String remoteFileName = null;
	
	/** Metadata on data files in ZIP file. */
	private Collection<ZipEntryMetaData> zipEntryMetaData =
		new ArrayList<ZipEntryMetaData>();
	
	/** File format or enclosed data files. */
	private RectangularTextFileFormat fileFormat = null;

	/** Wrong file format **/
	private String errorFileName = "";
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
	public String getErrorFileName() {
		return errorFileName;
	}

	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	/**
	 * Get metadata on data files in ZIP file.
	 * @return Metadata on data files in ZIP file.
	 */
	public Collection<ZipEntryMetaData> getZipEntryMetaData() {
		return zipEntryMetaData;
	}

	/**
	 * Set metadata on data files in ZIP file.
	 * @param zipEntryMetaData Metadata on data files in ZIP file.
	 */
	public void setZipEntryMetaData(
			final Collection<ZipEntryMetaData> zipEntryMetaData) {
		this.zipEntryMetaData = zipEntryMetaData;
	}
	
	/**
	 * Get format of file.
	 * @return File format
	 */
	public RectangularTextFileFormat getFileFormat() {
		return fileFormat;
	}

	/**
	 * Set format of file.
	 * @param fileFormat File format
	 */
	public void setFileFormat(
			final RectangularTextFileFormat fileFormat) {
		this.fileFormat = fileFormat;
	}

	/**
	 * Get name (not full path) of ZIP file on remote system.
	 * @return Name (not full path) of ZIP file on remote system.
	 */
	public String getRemoteFileName() {
		return remoteFileName;
	}

	/**
	 * Set name (not full path) of ZIP file on remote system.
	 * @param remoteFileName Name (not full path) of ZIP file
	 * on remote system.
	 */
	public void setRemoteFileName(final String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public ZipFileMetaData() {
		
	}

	/**
	 * Constructor.
	 * @param remoteFileName Name (not full path) of ZIP file
	 * on remote system.
	 */
	public ZipFileMetaData(final String remoteFileName) {
		super();
		this.remoteFileName = remoteFileName;
	}
	
	
	//
	//  B U S I N E S S  M E T H O D S
	//
	
	/**
	 * Add given 'metadatum'.
	 * @param zipEntryMetaDatum 'Metadatum' to add
	 */
	public void add(final ZipEntryMetaData zipEntryMetaDatum) {
		this.zipEntryMetaData.add(zipEntryMetaDatum);
	}
	
	
	/**
	 * Get enclosed zip entry metadata by specifying the
	 * corresponding local file name.
	 * @param localFileName Name (not full path) of local file
	 * associated with zip entry (i.e. local file containing
	 * zip entry file contents).
	 * @return Zip entry meta data
	 */
	public ZipEntryMetaData getZipEntryMetaDataByLocalFileName(
			final String localFileName) {
		ZipEntryMetaData meta = null;
		for (ZipEntryMetaData m : this.zipEntryMetaData) {
			if (localFileName.equals(m.getLocalFile().getName())) {
				meta = m;
				break;
			}
		}
		return meta;
	}
}
