/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/ArrayDatumDelimitedFileReader.java,v $
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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

package org.rti.webcgh.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.rti.webcgh.array.ArrayDatum;
import org.rti.webcgh.array.Chromosome;
import org.rti.webcgh.array.GenomeAssembly;
import org.rti.webcgh.array.GenomeLocation;
import org.rti.webcgh.array.Quantitation;
import org.rti.webcgh.array.QuantitationType;
import org.rti.webcgh.array.Reporter;
import org.rti.webcgh.array.ReporterMapping;
import org.rti.webcgh.util.DelimitedFileReader;

/**
 * Reads array data from delimited files
 */
public class ArrayDatumDelimitedFileReader implements ArrayDatumFileReader {
	
	
	// ==============================
	//     Attributes
	// ==============================
	
	private char delimiter = ',';
	private Map colMap = null;
	private DelimitedFileReader delimitedFileReader = null;
	private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
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
	public ArrayDatumDelimitedFileReader() {}
	
	
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
	 * @param row Spreadsheet row
	 * @return A datum object
	 */
	private ArrayDatum newArrayDatum(String[] row) {
		ArrayDatum datum = null;
		try {
			String probeName = getStringProperty(row, "Clone");
			double chromDbl = getNumericProperty(row, "Chromosome");
			double ratio = getNumericProperty(row, "Log2Rat");
			String cytPos = getStringProperty(row, "FISH");
			double physPosDbl = 
				getNumericProperty(row, "KB_POSITION") * 1000.0;
			if ((probeName != null) && 
				(! Double.isNaN(chromDbl)) &&
				(! Double.isNaN(ratio)) &&
				(cytPos != null) &&
				(! Double.isNaN(physPosDbl))) {
				Reporter reporter = this.domainObjectFactory.getReporter(probeName);
				if (! reporter.isMapped(this.genomeAssembly)) {
					Chromosome chromosome = this.domainObjectFactory.getChromosome(
							this.genomeAssembly, (short)chromDbl);
					GenomeLocation genomeLocation = 
						new GenomeLocation(chromosome, (long)physPosDbl);
					ReporterMapping reporterMapping =
						new ReporterMapping(reporter, genomeLocation);
					reporter.setReporterMapping(reporterMapping);
				}
				Quantitation quantitation = 
					new Quantitation((float)ratio, QuantitationType.LOG_2_RATIO);
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