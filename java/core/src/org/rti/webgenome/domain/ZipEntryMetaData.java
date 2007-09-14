/*
$Revision: 1.2 $
$Date: 2007-09-14 22:14:11 $

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
