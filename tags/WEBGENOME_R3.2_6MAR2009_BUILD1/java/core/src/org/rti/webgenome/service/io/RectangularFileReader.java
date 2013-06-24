/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-07-26 16:45:33 $


*/

package org.rti.webgenome.service.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.IOUtils;

/**
 * Reads files of rectangular data providing access to
 * individual columns.  Rectangular data files are
 * delimited (i.e., CSV or tab-delimited) text files that
 * are conceptually matrices.  Each line corresponds to
 * a row in the matrix.  Columns are separated by the
 * delimiting character.  The first line (i.e., row)
 * must contain column headings (i.e., names).
 * @author dhall
 *
 */
public final class RectangularFileReader {
    
    // ================================
    //        Attributes
    // ================================
    
    /** File to be read. */
    private final File file;
    
    
    /** Delimiting character separating columns. */
    private char delimiter = ',';
    
    /** Below members were added to support files with data in quotes **/
    private boolean isOpenQuotes = false;
    private int quotesCommasToSkip = 0;
    private int openQuotesStartPos = 0;
    
    
    
    
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
    public RectangularFileReader(final File file) {
        
        // Make sure file exists and is readable
        if (!file.exists() || !file.isFile()) {
            throw new IllegalArgumentException("File '"
                    + file.getAbsolutePath()
                    + "' is not readable");
        }
        
        this.file = file;
    }
    
    /**
     * Constructor.
     * @param absolutePath Absolute path to rectangular file.
     */
    public RectangularFileReader(final String absolutePath) {
        this(new File(absolutePath));
    }
    
    // =================================
    //     Public methods
    // =================================
    
    /**
     * Return column headings.
     * @return Column headings
     */
    public List<String> getColumnHeadings() {
        List<String> headings = new ArrayList<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(this.file));
            String line = in.readLine();
            if (line != null && line.length() > 0) {
                StringTokenizer tok = 
                    new StringTokenizer(line, "" + this.delimiter);
                while (tok.hasMoreTokens()) {
                    headings.add(tok.nextToken());
                }
            }
        } catch (Exception e) {
            throw new WebGenomeSystemException("Error reading file '"
                    + this.file.getAbsolutePath()
                    + "'", e);
        } finally {
        	IOUtils.close(in);
        }
        return headings;
    }
    
    
    /**
     * Get column at index.
     * @param index Index of column
     * @return A column
     */
    public List<String> getColumn(final int index) {
        List<String> col = new ArrayList<String>();
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(this.file));
            String line = in.readLine();
            int count = 0;
            while (line != null) {
                if (count++ > 0) { // Skip first line; it contains headings
                    String field = this.getField(line, index);
                    col.add(field);
                }
                line = in.readLine();
            }
        } catch (Exception e) {
            throw new WebGenomeSystemException("Error reading file '"
                    + this.file.getAbsolutePath()
                    + "'", e);
        } finally {
        	IOUtils.close(in);
        }
        return col;
    }
    
    
    
    /**
     * Get data field (i.e., column) from line of text 
     * from file (i.e., matrix row).
     * 
     * V.B. Added support for fields in quotes such as "Human,123,406".
     * 
     * @param line Line of text from file 
     * @param index Field (i.e., column) index
     * @return Data field
     */
    public String getField(final String line, final int index) {
        String field = "";
        
        // to skip commas inside quotes if any
        int newIndex = index + quotesCommasToSkip;
        
        // Find beginning of field
        int p = 0;
        for (int i = 0; i < newIndex && p >= 0; i++) {
            if (i > 0 && line.charAt(p) == this.delimiter) {
                p++;
            }
            p = line.indexOf(this.delimiter, p);
        }
         
        if (p+1 < line.length()){
        	//need to check if "," is inside quotes and
        	String temp = line.substring(p + 1);
        	int endQuotes = temp.indexOf("\"" + this.delimiter);
                                
        	if (isOpenQuotes && line.charAt(p + 1) != '"' && endQuotes != -1 ){                           	
        		//beginning of the comma should be where the quotes end
        		p = p + endQuotes + 3;        
        		// need to find how many commas are inside the quotes string
        		String quotedString = line.substring(openQuotesStartPos, p - 2);
        		//System.out.println("***quotedString=" + quotedString);
        		for (int i = 0; i < quotedString.length(); i++){
        			if (quotedString.charAt(i) == this.delimiter)
        				quotesCommasToSkip++;
        		}
        	}
        }	
       
        
        // If we have not run off the end of line, parse field
        if (p >= 0) {
            int q = p;
            if (index > 0 && line.charAt(q) == this.delimiter) {
            	if (((q + 1)< line.length()) && line.charAt(q + 1) == '"'){
            		// if opening quotes need to figure out the closing quotes            		
            		p = q + 2;
            		openQuotesStartPos = p;
            		String temp = line.substring(p);
            		 
            		int endQuotes = temp.indexOf("\"" + this.delimiter);
            		field = temp.substring(0, endQuotes);
            		//System.out.println(field);
            		isOpenQuotes = true;
            		return field;
            	}else
            		q++;
            }
            q = line.indexOf(this.delimiter, q);
            if (q < 0) {
                q = line.length();
            }
            field = line.substring(p, q);
            if (field.length() > 0 && field.charAt(0) == this.delimiter) {
                field = field.substring(1);
            }
        }
        isOpenQuotes = false;
        return field;
    }
    
    
    /**
     * Validate if data are correct by checking that the count of delimiters
     * in every line is the one in the headings.
     * 
     * @param index Index of column
     * @return A column
     */
    public boolean validate() {
       
        BufferedReader in = null;
        int nbrDelimitersInHeading = 0;
        try {
            in = new BufferedReader(new FileReader(this.file));
            // read the heading and count number of delimiters
            String headingLine = in.readLine();
            
            StringTokenizer st = new StringTokenizer(headingLine, "" + this.delimiter);
            nbrDelimitersInHeading = st.countTokens();
    		
    		
    		String line = in.readLine();
    		
            while (line != null) {
            	int nbrDelimitersInLine = 0;
            	
        		
        		//if quotes are found clean commas inside quotes
        		int quotesIdx = line.indexOf("\"");
        		if (quotesIdx != 1)
        			line = removeCommasInQuotes( line, nbrDelimitersInHeading + 1);
        		
        		// replace delimiter with space + delimiter to be able to
        		// avoid empty string that will not result in tokens    		    		
        		line = line.replace("" + this.delimiter, " " + this.delimiter);
        		
            	st = new StringTokenizer(line, "" + this.delimiter);
        		
        		
        		nbrDelimitersInLine = st.countTokens();
        		
        		// for valid file the number of delimiters in heading should match
        		// the number of delimiters in all lines
        		if (nbrDelimitersInLine != nbrDelimitersInHeading)
        			return false;
        		
        		// continue to read lines
        		line = in.readLine();
        		
            }
        } catch (Exception e) {
            throw new WebGenomeSystemException("Error reading file '"
                    + this.file.getAbsolutePath()
                    + "'", e);
        } finally {
        	IOUtils.close(in);
        }
        return true;
    }
    
    private String removeCommasInQuotes(String line, int numberOfFields) throws Exception{
    	String resultLine = "";
    	for (int i = 0; i < numberOfFields; i++){    		
    		  String field = getField(line, i);
    		  //System.out.println("****Field is " + field);
    		  if (!field.equals("")){
    			  int commaIdx = field.indexOf(this.delimiter);    			  
    			  if (commaIdx != -1){
    				  // replace delimiter inside quotes string with empty string
    				  field = field.replace("" + this.delimiter, " " );
    			  }	  
    		  }	 
    		  resultLine += field;
    		  // don't add comma at the end
			  if (i != numberOfFields)
				  resultLine += ",";
    	}
    	//remove last comma
    	if (resultLine.endsWith("" + this.delimiter)){
    		resultLine = resultLine.substring(0, resultLine.length() -1);
    	}
    	
    	 //System.out.println("****Result line is " + resultLine);
    	return resultLine;
    	
    } 	
    
}
