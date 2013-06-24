/*
$Revision: 1.2 $
$Date: 2007-04-10 22:32:41 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.util.List;

import org.rti.webgenome.util.FileUtils;

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
        "org/rti/webgenome/service/io/rectangular_file_reader_test_files";
    
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
