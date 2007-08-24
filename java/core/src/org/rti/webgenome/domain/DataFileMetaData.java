/*
$Revision: 1.4 $
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
		return this.format.name();
	}
	
	/**
	 * Set format using name.  This is to be used
	 * in persistence of the enumerated type.
	 * @param formatName Name of format.
	 */
	public void setFormatName(final String formatName) {
		this.format = RectangularTextFileFormat.valueOf(formatName);
	}
}
