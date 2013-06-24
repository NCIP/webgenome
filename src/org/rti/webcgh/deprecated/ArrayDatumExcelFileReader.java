/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/deprecated/ArrayDatumExcelFileReader.java,v $
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.rti.webcgh.deprecated.array.ArrayDatum;
import org.rti.webcgh.deprecated.array.Chromosome;
import org.rti.webcgh.deprecated.array.GenomeAssembly;
import org.rti.webcgh.deprecated.array.GenomeLocation;
import org.rti.webcgh.deprecated.array.Quantitation;
import org.rti.webcgh.deprecated.array.QuantitationType;
import org.rti.webcgh.deprecated.array.Reporter;
import org.rti.webcgh.deprecated.array.ReporterMapping;

/**
 * Reads array data from Excel files
 */
public class ArrayDatumExcelFileReader implements ArrayDatumFileReader {
	
	// ==============================
	//     Attributes
	// ==============================
	
	private HSSFSheet sheet = null;
	private int lastRowNum = -1;
	private int currentRow = 1;
	private Map colMap = null;
	private InputStream in = null;
	private GenomeAssembly genomeAssembly = null;
	private DomainObjectFactory domainObjectFactory = new DomainObjectFactory();
	
	
	// =================================
	//     Constructor
	// =================================
	
	
	/**
	 * Constructor
	 */
	public ArrayDatumExcelFileReader() {}
	
	
	// ===========================================
	//  Methods in ArayDatumFileReader interface
	// ===========================================
	
	/**
	 * Open file for reading
	 * @param file A file
	 * @param genomeAssembly Genome assembly
	 * @throws FileNotFoundException if file not found
	 * @throws IOException if there is a file error
	 */
	public void open(File file, GenomeAssembly genomeAssembly) throws FileNotFoundException, IOException {
		this.currentRow = 1;
		this.in = new FileInputStream(file);
		HSSFWorkbook workbook = new HSSFWorkbook(this.in);
		this.sheet = workbook.getSheetAt(0);
		this.lastRowNum = sheet.getLastRowNum();
		this.colMap = this.createColumnMappings(sheet);
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
		while (this.currentRow <= this.lastRowNum && datum == null) {
			HSSFRow row = this.sheet.getRow(this.currentRow++);
			datum = this.newArrayDatum(row);
		}
		return datum;
	}
	
	
	/**
	 * Close file for reading
	 * @throws IOException If a file error occurs
	 */
	public void close() throws IOException {
		this.in.close();
	}
	
	
	// ========================================
	//      Private methods
	// ========================================
	
	
	/**
	 * Create a mapping between column names and numbers
	 * @param sheet Spreadsheet object
	 * @return Mapping of column names to ordinal numbers
	 */
	private Map createColumnMappings(HSSFSheet sheet) {
		Map map = new HashMap();
		HSSFRow row = sheet.getRow(0);
		if (row != null) {
			for (short i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
				Short idx = new Short(i);
				HSSFCell cell = row.getCell(i);
				if (cell != null) {
					String colName = cell.getStringCellValue();
					map.put(colName, idx);
				}
			}
		}
		return map;
	}
	
	/**
	 * Instantiate a datum object
	 * @param row Spreadsheet row
	 * @return A datum object
	 */
	private ArrayDatum newArrayDatum(HSSFRow row) {
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
	private String getStringProperty(HSSFRow row, String propName) {
		String prop = null;
		HSSFCell cell = getPropertyCell(row, propName);
		if (cell != null)
			prop = cell.getStringCellValue();
		return prop;
	}
	
	
	/**
	 * Get numeric value of a cell
	 * @param row Spreadsheet row
	 * @param propName Column name
	 * @return Numeric value of a spreadsheet cell
	 */
	private double getNumericProperty(HSSFRow row, String propName) {
		double prop = Double.NaN;
		HSSFCell cell = getPropertyCell(row, propName);
		if (cell != null)
			prop = cell.getNumericCellValue();
		return prop;
	}	
	
	
	/**
	 * Get cell with property (column name)
	 * @param row Spreadsheet row
	 * @param propName Column name
	 * @return Cell with column name
	 */
	private HSSFCell getPropertyCell(HSSFRow row, String propName) {
		HSSFCell cell = null;
		Short idx = (Short)this.colMap.get(propName);
		if (idx != null) {
			short p = idx.shortValue();
			cell = row.getCell(p);
		}
		return cell;
	}

}
