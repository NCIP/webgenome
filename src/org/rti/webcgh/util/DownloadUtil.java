/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/DownloadUtil.java,v $
$Revision: 1.2 $
$Date: 2006-10-21 05:34:56 $

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

import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.rti.webcgh.core.WebcghApplicationException;
import org.rti.webcgh.deprecated.array.GenomeFeature;
import org.rti.webcgh.deprecated.array.GenomeFeatureIterator;
import org.rti.webcgh.deprecated.array.GenomeFeatureSearchResults;


/**
 * Util class to generate excel from a collection
 */
public class DownloadUtil {
	static private Logger _logger = Logger.getLogger(DownloadUtil.class.getName());
	private static final int MIN_ROWS = 70;
	 
	 /**
	   * This method will iterate through the list of Feature objects and will
	   * create an excel file. 
	   * @param maps A collection of TypeSpecific Map
	   * @param os an OutputStream where it writes the excel data
	   * @param type Annotation type, e.g. knownGene
	   * @return The size of the collection
	   * @throws WebcghApplicationException
	   */
	 public static int annotation2Excel(GenomeFeatureSearchResults[] maps, String type, OutputStream os) 
		throws WebcghApplicationException{
	   try{  
	   		// find the right TypeSpecificAnnotationMap
			GenomeFeatureSearchResults tsfMap = null;
			for (int i = 0; i < maps.length; i++) {
				tsfMap = maps[i];
				if (tsfMap.equivalentType(type))
					break;
			}

		   HSSFWorkbook wb = new HSSFWorkbook();
		   HSSFSheet sheet = wb.createSheet("Annotation Report");
		   sheet.setDefaultColumnWidth((short)25);
		   sheet.createFreezePane(0, 1);
      
			// Create header row
			HSSFRow row = sheet.createRow((short)0);  

			// Needs to be bold  
			HSSFFont font = wb.createFont();     
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);    

			// Fonts are set into a style so create a new one to use.
			HSSFCellStyle styleHeader = wb.createCellStyle();
			styleHeader.setFont(font);
			styleHeader.setWrapText(true);
			styleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
			HSSFCell cell = null;

			// create header row
			GenomeFeature.addHeadings(row, styleHeader);

			HSSFCellStyle styleData = wb.createCellStyle();
			styleData.setWrapText(true);
			styleData.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			styleData.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
      
			int i = 1;
			// Iterate through the list and insert data in the xls sheet.
			for (GenomeFeatureIterator it = tsfMap.iterator(); it.hasNext();){
			  GenomeFeature feature = it.next();
			  row = sheet.createRow((short)i++);	  
			  feature.addFieldValues(row, styleData);
			}

			// For some reason if rows are < than MIN_ROWS the Excel file gives an error when
			// opened in Excel. To work around this we need to insert empty rows until it reaches
			// MIN_ROWS number.
			insertRows(sheet, i, 5);
        
			  wb.write(os);
			  byte[] b = wb.getBytes();
			  return b.length;     
        
		  }catch(Exception e){
			  throw new WebcghApplicationException("Could not create an excel sheet!", e); 
		  }
		}

	/**
	  * For some reason if rows are < than MIN_ROWS the Excel file gives an error when
	  * opened in Excel. To work around this this method will insert empty rows until it reaches
	  * the MIN_ROWS number.
	  *
	  * @param  sheet the Excel sheet
	  * @param  lastRowNum  the last row number
	  * @param  numCols a number of columns in the row
	  * @throws Exception
	  */
	   static private void insertRows(HSSFSheet sheet, int lastRowNum, int numCols) 
	   throws Exception{
		while ( lastRowNum < MIN_ROWS ){
			  HSSFRow row = sheet.createRow((short)lastRowNum++);     

			  for ( int i = 0; i < numCols; i++){
				HSSFCell cell = row.createCell((short)i);
				cell.setCellValue("");                               
			  }  
		}
	  } 

}
