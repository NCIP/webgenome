/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:35 $

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

package org.rti.webcgh.service.io;

import java.io.File;
import java.util.List;

import org.rti.webcgh.service.io.RectangularFileReader;
import org.rti.webcgh.util.FileUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>RectangularFileReader</code>.
 * @author dhall
 *
 */
public final class RectangularFileReaderTester extends TestCase {
    
    // =================================
    //      Constants
    // =================================
    
    /**
     * Path (relative to classpath) to directory containing
     * test files.
     */
    private static final String TEST_DIRECTORY =
        "org/rti/webcgh/io/unit_test/rectangular_file_reader_test_files";
    
    /**
     * Test method getHeadings() on a CSV file.
     *
     */
    public void testGetHeadingsCsv() {
        File file = FileUtils.getFile(TEST_DIRECTORY, "normal.csv");
        RectangularFileReader reader = new RectangularFileReader(file);
        List<String> headings = reader.getColumnHeadings();
        assertEquals(3, headings.size());
        assertEquals("b", headings.get(1));
    }
    
    /**
     * Test method getHeadings() on a tab-delimited file.
     *
     */
    public void testGetHeadingsTxt() {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "normal.txt");
        RectangularFileReader reader = new RectangularFileReader(file);
        reader.setDelimiter('\t');
        List<String> headings = reader.getColumnHeadings();
        assertEquals(3, headings.size());
        assertEquals("b", headings.get(1));
    }
    
    
    /**
     * Test method getColumn(int) on a CSV file
     * with some empty fields.
     *
     */
    public void testGetColumnCsvEmptyFields() {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "empty_fields.csv");
        RectangularFileReader reader = new RectangularFileReader(file);
        
        // First column
        List<String> col = reader.getColumn(0);
        assertEquals(4, col.size());
        assertEquals("a1", col.get(0));
        assertEquals("a2", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("a4", col.get(3));
        
        // Second column
        col = reader.getColumn(1);
        assertEquals(4, col.size());
        assertEquals("", col.get(0));
        assertEquals("b2", col.get(1));
        assertEquals("b3", col.get(2));
        assertEquals("", col.get(3));
        
        // Third column
        col = reader.getColumn(2);
        assertEquals(4, col.size());
        assertEquals("c1", col.get(0));
        assertEquals("", col.get(1));
        assertEquals("c3", col.get(2));
        assertEquals("", col.get(3));
    }
    
    
    /**
     * Test method getColumn(int) on a tab-delimited file
     * with some empty fields.
     *
     */
    public void testGetColumnTxtEmptyFields() {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "empty_fields.txt");
        RectangularFileReader reader = new RectangularFileReader(file);
        reader.setDelimiter('\t');
        
        // First column
        List<String> col = reader.getColumn(0);
        assertEquals(4, col.size());
        assertEquals("a1", col.get(0));
        assertEquals("a2", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("a4", col.get(3));
        
        // Second column
        col = reader.getColumn(1);
        assertEquals(4, col.size());
        assertEquals("", col.get(0));
        assertEquals("b2", col.get(1));
        assertEquals("b3", col.get(2));
        assertEquals("", col.get(3));
        
        // Third column
        col = reader.getColumn(2);
        assertEquals(4, col.size());
        assertEquals("c1", col.get(0));
        assertEquals("", col.get(1));
        assertEquals("c3", col.get(2));
        assertEquals("", col.get(3));
    }
    
    
    /**
     * Test method getColumn(int) on a CSV file
     * with truncated rows (lines).
     *
     */
    public void testGetColumnCsvTruncated() {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "truncated.csv");
        RectangularFileReader reader = new RectangularFileReader(file);
        
        // First column
        List<String> col = reader.getColumn(0);
        assertEquals(4, col.size());
        assertEquals("a1", col.get(0));
        assertEquals("a2", col.get(1));
        assertEquals("a3", col.get(2));
        assertEquals("a4", col.get(3));
        
        // Second column
        col = reader.getColumn(1);
        assertEquals(4, col.size());
        assertEquals("b1", col.get(0));
        assertEquals("b2", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("b4", col.get(3));
        
        // Third column
        col = reader.getColumn(2);
        assertEquals(4, col.size());
        assertEquals("c1", col.get(0));
        assertEquals("", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("c4", col.get(3));
    }
    
    
    /**
     * Test method getColumn(int) on a tab-delimited file
     * with truncated rows (lines).
     *
     */
    public void testGetColumnTxtTruncated() {
    	File file = FileUtils.getFile(TEST_DIRECTORY, "truncated.txt");
        RectangularFileReader reader = new RectangularFileReader(file);
        reader.setDelimiter('\t');
        
        // First column
        List<String> col = reader.getColumn(0);
        assertEquals(4, col.size());
        assertEquals("a1", col.get(0));
        assertEquals("a2", col.get(1));
        assertEquals("a3", col.get(2));
        assertEquals("a4", col.get(3));
        
        // Second column
        col = reader.getColumn(1);
        assertEquals(4, col.size());
        assertEquals("b1", col.get(0));
        assertEquals("b2", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("b4", col.get(3));
        
        // Third column
        col = reader.getColumn(2);
        assertEquals(4, col.size());
        assertEquals("c1", col.get(0));
        assertEquals("", col.get(1));
        assertEquals("", col.get(2));
        assertEquals("c4", col.get(3));
    }
}
