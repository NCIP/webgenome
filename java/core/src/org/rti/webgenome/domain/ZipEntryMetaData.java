/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-09-14 22:14:11 $


*/

package org.rti.webgenome.domain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Metadata about data file that originated from
 * an uploaded ZIP file.
 * @author dhall
 *
 */
public class ZipEntryMetaData {

	//
	//  A T T R I B U T E S
	//
	
	/**
	 * Local copy of a file
	 * extracted from an uploaded ZIP file.
	 * 
	 */
	private File localFile = null;
	
	/**
	 * Name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 */
	private String remoteFileName = null;
	
	/** Column headings. */
	private List<String> columnHeadings = new ArrayList<String>();
	
	//
	//  G E T T E R S / S E T T E R S
	//



	/**
	 * Get local copy of a file
	 * extracted from an uploaded ZIP file.
	 * @return Local copy of a file
	 * extracted from an uploaded ZIP file.
	 */
	public File getLocalFile() {
		return localFile;
	}

	/**
	 * Set local copy of a file
	 * extracted from an uploaded ZIP file.
	 * @param localFile Local copy of a file
	 * extracted from an uploaded ZIP file.
	 */
	public void setLocalFile(final File localFile) {
		this.localFile = localFile;
	}

	
	/**
	 * Get column headings.
	 * @return Column headings
	 */
	public List<String> getColumnHeadings() {
		return columnHeadings;
	}

	/**
	 * Set column headings.
	 * @param columnHeadings Column headings
	 */
	public void setColumnHeadings(final List<String> columnHeadings) {
		this.columnHeadings = columnHeadings;
	}

	/**
	 * Get name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 * @return Name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 */
	public String getRemoteFileName() {
		return remoteFileName;
	}

	/**
	 * Set name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 * @param remoteFileName Name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 */
	public void setRemoteFileName(final String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}
	
	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param localFile Local copy of a file
	 * extracted from an uploaded ZIP file.
	 * @param remoteFileName Name (not full path) on remote file system
	 * of file extracted from an uploaded ZIP file.
	 */
	public ZipEntryMetaData(final File localFile,
			final String remoteFileName) {
		super();
		this.localFile = localFile;
		this.remoteFileName = remoteFileName;
	}
	
	//
	//  B U S I N E S S  M E T H O D S
	//
	
	/**
	 * Add column heading.
	 * @param columnHeading Column heading (i.e. name)
	 */
	public void addColumnHeadings(final String columnHeading) {
		this.columnHeadings.add(columnHeading);
	}
}
