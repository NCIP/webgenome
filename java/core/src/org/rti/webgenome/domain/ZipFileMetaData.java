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

	
	//
	//  G E T T E R S / S E T T E R S
	//
	
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
}
