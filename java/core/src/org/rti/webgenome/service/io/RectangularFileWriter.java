/*
$Revision: 1.3 $
$Date: 2008-05-23 21:08:56 $

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

package org.rti.webgenome.service.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.util.IOUtils;
import org.rti.webgenome.units.BpUnits;

/**
 * Write files of rectangular data providing access to
 * individual columns.  Rectangular data files are
 * delimited (i.e., CSV or tab-delimited) text files that
 * are conceptually matrices.  Each line corresponds to
 * a row in the matrix.  Columns are separated by the
 * delimiting character.  The first line (i.e., row)
 * must contain column headings (i.e., names).
 * 
 * @author vbakalov
 *
 */
public final class RectangularFileWriter {
    
    // ================================
    //        Attributes
    // ================================
	
	/** Heading indexes */
	static public final short REPORTER_NAME_HEADING_IDX = 0;
	static public final short CHROMOSOME_HEADING_IDX = 1;
	static public final short POSITION_HEADING_IDX = 2;
	static public final short REPORTER_VALUE_HEADING_IDX = 3;
	
	/* Default reporter value heading name */
	static public final String DEFAULT_REPORTER_VALUE_HEADING = "Reporter Value";
	
	/** Default column headings */
	static protected  String headings[] = {"Resporeter Name", "Chromosome", "Position", "Reporter Value"};
	
    
    
    
    /** Delimiting character separating columns. */
    private char delimiter = ',';
    
    /** List of arrDatums to write in the file. */
    private List<ArrayDatum> arrDatums = null;
    
    private File file = null;
    
    
    /**
     * Get delimiting character that separates columns.
     * @return Delimiting character
     */
    public char getDelimiter() {
        return delimiter;
    }

    /**
     * Set delimiting character that separates columns.
     * @param delimiter Delimiting character
     */
    public void setDelimiter(final char delimiter) {
        this.delimiter = delimiter;
    }
    
    // =================================
    //        Constructors
    // =================================

    /**
     * Constructor.
     * @param arrDatums ArrayDatum to write
     */
    public RectangularFileWriter(List<ArrayDatum> arrDatums) {               
        this.arrDatums = arrDatums;
    }
    
    /**
     * Constructor.
     * @param arrDatums ArrayDatum to write
     * @param headings array of column headings
     */
    public RectangularFileWriter(List<ArrayDatum> arrDatums, String headings[]) {               
        this.arrDatums = arrDatums;
        if (headings != null)
        	this.headings = headings;
        
    }
    
   
    
    
    // =================================
    //     Public methods
    // =================================
    
    /**
     * Writes column heading and data.
     * Data are reporter name, chromosome number, chromosome position and
     * reporter value;
     * 
     * @return Column headings
     */
    public void writeData2File() throws Exception{
        
        BufferedReader in = null;
        try {
        	
        	FileOutputStream fos = new FileOutputStream(this.file, true);
    		PrintStream ps = new PrintStream(fos);
    		
    		// write column headings
        	for (int i = 0; i <  headings.length; i++){
        		ps.print((String)headings[i]);
        		ps.print(delimiter);
        	}
        	ps.print("\n");
        	
        	// print reporter values and chromosome number and position
        	for (ArrayDatum ad : arrDatums){        		
            		Reporter r = ad.getReporter();
            		ps.print(r.getName() + delimiter);
            		ps.print(r.getChromosome() + delimiter);    		
            		ps.print(r.getLocation() + delimiter);
            		ps.println(ad.getValue());            		    			        		
    		}
           
        	ps.close();
    		fos.close();
    		
        } catch (Exception e) {
            throw new WebGenomeSystemException("Error reading file '"
                    + this.file.getAbsolutePath()
                    + "'", e);
        } finally {
        	IOUtils.close(in);
        }
        
    }
    
    /**
     * Writes data to StringBuffer.
     * 
     * @param sbuff
     * @return total bytes written
     * @throws Exception
     */
    public void writeData2StringBuffer(StringBuffer sbuff) throws Exception{
    	
    	
    	// write column headings
    	for (int i = 0; i <  headings.length; i++){
    		sbuff.append((String)headings[i]);
    		sbuff.append(delimiter);
    	}
    	sbuff.append("\n");
    	
    	// print reporter values and chromosome number and position
    	for (ArrayDatum ad : arrDatums){
    		Reporter r = ad.getReporter();
    		sbuff.append(r.getName() + delimiter);
    		sbuff.append(new Short(r.getChromosome()).toString() + delimiter);    		
    		sbuff.append(new Long(r.getLocation()).toString() + delimiter);
    		sbuff.append(new Float(ad.getValue()).toString());
    		sbuff.append("\n");    			
		}
    
    			   
    }

	public static String[] getHeadings() {
		return headings;
	}

	public static void setHeadings(String[] headings) {
		RectangularFileWriter.headings = headings;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	
    
}
