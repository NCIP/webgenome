/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/ArrayDatumDelimitedTextFileReader.java,v $
$Revision: 1.1 $
$Date: 2006-10-21 21:04:54 $

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

package org.rti.webcgh.deprecated;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.deprecated.array.ArrayDatum;
import org.rti.webcgh.deprecated.array.Chromosome;
import org.rti.webcgh.deprecated.array.GenomeAssembly;
import org.rti.webcgh.deprecated.array.GenomeLocation;
import org.rti.webcgh.deprecated.array.Quantitation;
import org.rti.webcgh.deprecated.array.QuantitationType;
import org.rti.webcgh.deprecated.array.Reporter;
import org.rti.webcgh.deprecated.array.ReporterMapping;

/**
 * Reads array data from delimited text files files
 */
public class ArrayDatumDelimitedTextFileReader implements ArrayDatumFileReader {
	
	// =====================================
	//   Column headings expected in file
	// =====================================
	
	// Reporter name
	private static final String REPORTER_NAME_COL_HEADING = "Clone";
	
	// Chromosome number
	private static final String CHROMOSOME_COL_HEADING = "Chromosome";
	
	// Quantitation (currently always log2 ratio)
	private static final String QUANTITATION_COL_HEADING = "Log2Rat";
	
	
	// ==============================
	//     Attributes
	// ==============================
	
	// Delimiting character
	private char delimiter = ',';
	
	// Mapping of column headings to column numbers
	private Map colMap = null;
	
	// Delimite file reader
	private DelimitedFileReader delimitedFileReader = null;
	
	// Factory for creating and caching domain objects
	private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
	
	// Genome assembly associated with data
	private GenomeAssembly genomeAssembly = null;
	
	
	/**
	 * @return Returns the delimiter.
	 */
	public char getDelimiter() {
		return delimiter;
	}
	
	
	/**
	 * @param delimiter The delimiter to set.
	 */
	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}
		
	
	/**
	 * @param domainObjectCache The domainObjectCache to set.
	 */
	public void setDomainObjectFactory(DomainObjectFactory domainObjectCache) {
		this.domainObjectFactory = domainObjectCache;
	}
	
	
	
	// =================================
	//     Constructor
	// =================================
	
	
	/**
	 * Constructor
	 */
	public ArrayDatumDelimitedTextFileReader() {}
	
	
	// ===========================================
	//  Methods in ArayDatumFileReader interface
	// ===========================================
	
	/**
	 * Open file for reading
	 * @param file A file
	 * @param genomeAssembly
	 * @throws FileNotFoundException if file not found
	 * @throws IOException if there is a file error
	 */
	public void open(File file, GenomeAssembly genomeAssembly) throws FileNotFoundException, IOException {
		this.delimitedFileReader = new DelimitedFileReader(file, this.delimiter);
		String[] colHeadings = this.delimitedFileReader.nextRow();
		if (colHeadings != null)
			this.colMap = this.createColumnMappings(colHeadings);
		this.genomeAssembly = genomeAssembly;
	}
	
	/**
	 * Get next datum.
	 * @return An array datum or null if there are no
	 * more data in file.
	 * @throws IOException if a file error occurs
	 */
	public ArrayDatum nextDatum() throws IOException {
		ArrayDatum datum = null;
		while (datum == null) {
			String[] row = this.delimitedFileReader.nextRow();
			if (row == null)
				break;
			datum = this.newArrayDatum(row);
		}
		return datum;
	}
	
	
	/**
	 * Close file for reading
	 * @throws IOException If a file error occurs
	 */
	public void close() throws IOException {
		this.delimitedFileReader.close();
	}
	
	
	// ========================================
	//      Private methods
	// ========================================
	
	
	/**
	 * Create a mapping between column names and numbers
	 * @param row Row
	 * @return Mapping of column names to ordinal numbers
	 */
	private Map createColumnMappings(String[] row) {
		Map map = new HashMap();
		for (int i = 0; i < row.length; i++)
			map.put(row[i], new Integer(i));
		return map;
	}
	
	/**
	 * Instantiate a datum object
	 * @param row Row in data table
	 * @return A datum object
	 */
	private ArrayDatum newArrayDatum(String[] row) {
		ArrayDatum datum = null;
		try {
			
			// Get properties of array datum
			String reporterName = getStringProperty(row, REPORTER_NAME_COL_HEADING);
			double chromDbl = getNumericProperty(row, CHROMOSOME_COL_HEADING);
			double quantitationDbl = getNumericProperty(row, QUANTITATION_COL_HEADING);
			double physPosDbl = 
				getNumericProperty(row, "KB_POSITION") * 1000.0;
			
			// If all properties okay, instantiate array datum object
			if ((reporterName != null) && 
				(! Double.isNaN(chromDbl)) &&
				(! Double.isNaN(quantitationDbl)) &&
				(! Double.isNaN(physPosDbl))) {
				
				// Instantiate reporter.  (Reporter may be cached if seen
				// before.)
				Reporter reporter = this.domainObjectFactory.getReporter(reporterName);
				
				// If this is first time seeing reporter (i.e., not cached), we
				// need to add some mapping properties
				if (! reporter.isMapped(this.genomeAssembly)) {
					Chromosome chromosome = this.domainObjectFactory.getChromosome(
							this.genomeAssembly, (short)chromDbl);
					GenomeLocation genomeLocation = 
						new GenomeLocation(chromosome, (long)physPosDbl);
					ReporterMapping reporterMapping =
						new ReporterMapping(reporter, genomeLocation);
					reporter.setReporterMapping(reporterMapping);
				}
				
				// For now, quantitation will always be log2 ratio
				Quantitation quantitation = 
					new Quantitation((float)quantitationDbl, QuantitationType.LOG_2_RATIO);
				datum = new ArrayDatum(reporter, quantitation);
			}
		} catch (Exception e) {}
		return datum;
	}
	
	
	/**
	 * Get string value of a cell
	 * @param row Spreadsheet row
	 * @param propName Column name
	 * @return String value of a spreadsheet cell
	 */
	private String getStringProperty(String[] row, String propName) {
		return getPropertyCell(row, propName);
	}
	
	
	/**
	 * Get numeric value of a cell
	 * @param row Spreadsheet row
	 * @param propName Column name
	 * @return Numeric value of a spreadsheet cell
	 */
	private double getNumericProperty(String[] row, String propName) {
		double prop = Double.NaN;
		String cell = getPropertyCell(row, propName);
		if (cell != null)
			prop = Double.parseDouble(cell);
		return prop;
	}	
	
	
	/**
	 * Get cell with property (column name)
	 * @param row Spreadsheet row
	 * @param propName Column name
	 * @return Cell with column name
	 */
	private String getPropertyCell(String[] row, String propName) {
		String cell = null;
		Integer idx = (Integer)this.colMap.get(propName);
		if (idx != null) {
			int p = idx.intValue();
			cell = row[p];
		}
		return cell;
	}

}
