/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/CommandLineTableFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Formats text into columns of fixed width for
 * output to the command line.  Alignment is
 * left justified.
 */
public class CommandLineTableFormatter {
	
	
	// ========================================
	//            State variables
	// ========================================
	
	private final List rows = new ArrayList();
	
	
	// ========================================
	//    Attributes with accessors/mutators
	// ========================================
	
	private final int numColumns;
	private int padding = 5;
	
	/**
	 * Get padding (i.e. number of blank characters between columns)
	 * @return Number of blank characters between columns
	 */
	public int getPadding() {
		return this.padding;
	}
	
	
	/**
	 * Set padding (i.e. number of blank characters between columns)
	 * @param padding Number of blank characters between columns
	 */
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	
	// ================================================
	//                Constructors
	// ================================================
	
	/**
	 * Constructor
	 * @param numColumns Number of columns in table
	 */
	public CommandLineTableFormatter(int numColumns) {
		this.numColumns = numColumns;
	}
	
	
	// ===============================================
	//               Public methods
	// ===============================================
	
	
	/**
	 * Add a column to the table
	 * @param row A row in the table
	 * @throws IllegalArgumentException if the number of columns
	 * in the given row does not match the number of columns
	 * specified in the constructor
	 */
	public void addRow(String[] row) {
		if (row.length != this.numColumns)
			throw new IllegalArgumentException("Wrong number of columns");
		this.rows.add(row);
	}
	
	
	/**
	 * Add column headings
	 * @param headings Column headings
	 */
	public void addColumnHeadings(String[] headings) {
	    this.addRow(headings);
	    String[] lines = new String[headings.length];
	    for (int i = 0; i < headings.length; i++) {
	        StringBuffer buff = new StringBuffer();
	        for (int j = 0; j < headings[i].length(); j++)
	            buff.append("-");
	        lines[i] = buff.toString();
	    }
	    this.addRow(lines);
	}
	
	
	/**
	 * Get formatted table
	 * @return Formatted table
	 */
	public String getTable() {
		StringBuffer buff = new StringBuffer();
		int[] columnWidths = this.computeColumnWidths();
		for (Iterator it = this.rows.iterator(); it.hasNext();) {
			String[] row = (String[])it.next();
			for (int i = 0; i < row.length; i++) {
				String cell = row[i];
				int columnWidth = columnWidths[i];
				buff.append(this.formatCell(cell, columnWidth));
				if (i < row.length - 1)
					buff.append(this.emptySpace(this.padding));
			}
			if (it.hasNext())
				buff.append("\n");
		}
		return buff.toString();
	}
	
	
	// =========================================
	//             Private methods
	// =========================================
	
	private int[] computeColumnWidths() {
		int[] widths = new int[this.numColumns];
		for (int i = 0; i < this.numColumns; i++)
			widths[i] = this.longestStringInColumn(i);
		return widths;
	}
	
	
	private int longestStringInColumn(int colNum) {
		int longest = 0;
		for (Iterator it = this.rows.iterator(); it.hasNext();) {
			String[] row = (String[])it.next();
			String cell = row[colNum];
			if (cell != null) {
				int length = cell.length();
				if (length > longest)
					longest = length;
			}
		}
		return longest;
	}
	
	
	private String formatCell(String cell, int columnWidth) {
		if (cell == null)
			return this.emptySpace(columnWidth);
		StringBuffer buff = new StringBuffer(cell);
		for (int i = cell.length(); i < columnWidth; i++)
			buff.append(" ");
		return buff.toString();
	}
	
	
	private String emptySpace(int size) {
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < size; i++)
			buff.append(" ");
		return buff.toString();
	}

}
