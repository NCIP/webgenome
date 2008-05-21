/*
$Revision: 1.1 $
$Date: 2008-05-21 20:17:38 $

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

/**
 * Write files of rectangular data providing access to
 * individual columns.  Rectangular data files are
 * delimited (i.e., CSV or tab-delimited) text files that
 * are conceptually matrices.  Each line corresponds to
 * a row in the matrix.  Columns are separated by the
 * delimiting character.  The first line (i.e., row)
 * must contain column headings (i.e., names).
 * @author dhall
 *
 */
public final class RectangularFileWriter {
    
    // ================================
    //        Attributes
    // ================================
	
	/** Column headings */
	static protected final String headings[] = {"Resporeter Name", "Chromosome", "Chromosome Position", "Reporter Values"};
	
    
    
    
    /** Delimiting character separating columns. */
    private char delimiter = ',';
    
    /** List of arrDatums to write in the file. */
    List<ArrayDatum> arrDatums = null;
    
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
     * @param file A file
     */
    public RectangularFileWriter(List<ArrayDatum> arrDatums) {               
        this.arrDatums = arrDatums;
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
    public void writeData2File(final File f) throws Exception{
        
        BufferedReader in = null;
        try {
        	
        	FileOutputStream fos = new FileOutputStream(f, true);
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
    			ps.print(ad.getValue());
    			ps.print("\n");    			
    		}
           
        	ps.close();
    		fos.close();
    		
        } catch (Exception e) {
            throw new WebGenomeSystemException("Error reading file '"
                    + f.getAbsolutePath()
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
    		sbuff.append(r.getChromosome() + delimiter);
    		sbuff.append(r.getLocation() + delimiter);
    		sbuff.append(ad.getValue());
    		sbuff.append("\n");    			
		}
    
    			   
    }
    
}
