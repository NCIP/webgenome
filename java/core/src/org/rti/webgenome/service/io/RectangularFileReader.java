/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $

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
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.rti.webgenome.core.WebGenomeSystemException;

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
        }
        return col;
    }
    
    
    /**
     * Get data field (i.e., column) from line of text 
     * from file (i.e., matrix row).
     * @param line Line of text from file 
     * @param index Field (i.e., column) index
     * @return Data field
     */
    private String getField(final String line, final int index) {
        String field = "";
        
        // Find beginning of field
        int p = 0;
        for (int i = 0; i < index && p >= 0; i++) {
            if (i > 0 && line.charAt(p) == this.delimiter) {
                p++;
            }
            p = line.indexOf(this.delimiter, p);
        }

        // If we have not run off the end of line, parse field
        if (p >= 0) {
            int q = p;
            if (index > 0 && line.charAt(q) == this.delimiter) {
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
        
        return field;
    }
}
