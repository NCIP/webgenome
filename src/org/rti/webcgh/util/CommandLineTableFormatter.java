/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/CommandLineTableFormatter.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

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
