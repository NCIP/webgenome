/*
$Revision: 1.3 $
$Date: 2008-10-23 16:17:06 $


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
	
	public String print2Buff(){
		StringBuffer buff = new StringBuffer();
		buff.append("***Printing DataColumnMetaData*****\n");
		buff.append("BioAssayName = " + this.getBioAssayName() + "\n");
		buff.append("ColumnName = " + this.getColumnName() + "\n");
		buff.append("Id = " + this.getId() + "\n");
		return buff.toString();
	}
}
