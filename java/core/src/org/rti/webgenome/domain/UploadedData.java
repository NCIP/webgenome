/*
$Revision: 1.2 $
$Date: 2007-09-14 22:14:11 $


*/

package org.rti.webgenome.domain;

import java.io.File;

/**
 * Data that were uploaded in a 'rectangular' file.
 * @author dhall
 *
 */
public class UploadedData {

	/** File where the data reside on the server. */
	private File file = null;
	
	/** File format. */
	private RectangularTextFileFormat fileFormat = null;
	
	/** Name (not full path) of file on users system. */
	private String remoteFileName = null;
	

	/**
	 * Get file where the data reside on the server.
	 * @return A file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set file where the data reside on the server.
	 * @param file A rectangular format file
	 */
	public void setFile(final File file) {
		this.file = file;
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
	 * Get name (not full path) of file on users system.
	 * @return File name
	 */
	public String getRemoteFileName() {
		return remoteFileName;
	}

	/**
	 * Set name (not full path) of file on users system.
	 * @param remoteFileName File name
	 */
	public void setRemoteFileName(final String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	/**
	 * Constructor.
	 */
	public UploadedData() {
		
	}

	/**
	 * Constructor.
	 * @param file File where the data reside on the server
	 * @param fileFormat File format
	 * @param remoteFileName Name (not path) of file on users system
	 */
	public UploadedData(final File file,
			final RectangularTextFileFormat fileFormat,
			final String remoteFileName) {
		super();
		this.file = file;
		this.fileFormat = fileFormat;
		this.remoteFileName = remoteFileName;
	}
}
