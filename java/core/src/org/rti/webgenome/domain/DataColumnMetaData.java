/*
$Revision: 1.2 $
$Date: 2007-08-28 17:24:13 $

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

/**
 * Contains metadata for a data-containing column in an uploaded
 * 'rectangular' file.
 * @author dhall
 *
 */
public class DataColumnMetaData {
	
	//
	//  A T T R I B U T E S
	//
	
	/** Primary key value used for persistence. */
	private Long id = null;

	/** Name of the column, i.e. the column heading. */
	private String columnName = null;
	
	/**
	 * When data files are uploaded, data-containing
	 * columns represent a single bioassay.  This field
	 * represents the user-specified bioassay name.
	 */
	private String bioAssayName = null;
	
	//
	//  G E T T E R S / S E T T E R S
	//
	
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
	 * Get bioassay name.
	 * When data files are uploaded, data-containing
	 * columns represent a single bioassay.  This field
	 * represents the user-specified bioassay name.
	 * @return Bioassay name
	 */
	public String getBioAssayName() {
		return bioAssayName;
	}

	/**
	 * Set bioassay name.
	 * When data files are uploaded, data-containing
	 * columns represent a single bioassay.  This field
	 * represents the user-specified bioassay name.
	 * @param bioAssayName Biassay name
	 */
	public void setBioAssayName(final String bioAssayName) {
		this.bioAssayName = bioAssayName;
	}

	/**
	 * Get name of the column, i.e. the column heading.
	 * @return Column name.
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Set name of the column, i.e. the column heading.
	 * @param columnName Column name
	 */
	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	//
	//  C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 */
	public DataColumnMetaData() {
		super();
	}

	/**
	 * Constructor.
	 * @param columnName Name of the column, i.e. the column heading
	 * @param bioAssayName Bioassay name.
	 * When data files are uploaded, data-containing
	 * columns represent a single bioassay.  This field
	 * represents the user-specified bioassay name.
	 */
	public DataColumnMetaData(final String columnName,
			final String bioAssayName) {
		super();
		this.columnName = columnName;
		this.bioAssayName = bioAssayName;
	}
	
	/**
	 * Constructor that performs deep copy.
	 * @param meta Data column meta data
	 */
	public DataColumnMetaData(final DataColumnMetaData meta) {
		this(meta.columnName, meta.bioAssayName);
	}
}
