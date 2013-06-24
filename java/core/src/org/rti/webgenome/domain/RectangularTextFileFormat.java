/*
$Revision: 1.2 $
$Date: 2008-10-23 16:17:06 $


*/

package org.rti.webgenome.domain;

/**
 * Format of 'rectangular' text files.
 * @author dhall
 *
 */
public enum RectangularTextFileFormat {

	/** Comma-separated values. */
	CSV(','),
	
	/** Tab-delimited. */
	TAB_DELIMITED('\t');
	
	/** Character that delimits columns. */
	private final char delimiter;
	
	/**
	 * Constructor.
	 * @param delimiter Character that delimits columns.
	 */
	private RectangularTextFileFormat(final char delimiter) {
		this.delimiter = delimiter;
	}
	
	/**
	 * Get character that delimits columns.
	 * @return Delimiting character
	 */
	public char getDelimiter() {
		return this.delimiter;
	}
	
	public String print2Buff(){
		StringBuffer buff = new StringBuffer();
		buff.append("******Printing RectangilarTextFileFormat START*****\n");
		buff.append("Delimiter is " + this.delimiter + "\n");
		buff.append("******Printing RectangilarTextFileFormat END*****\n");
		return buff.toString();
	}
}
