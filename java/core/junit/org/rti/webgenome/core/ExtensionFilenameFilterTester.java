/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:38 $


*/


package org.rti.webgenome.core;

import java.io.File;

import org.rti.webgenome.core.ExtensionFilenameFilter;

import junit.framework.TestCase;

/**
 * Tester for <code>ExtensionFileFilter</code>.
 */
public final class ExtensionFilenameFilterTester extends TestCase {
    
    
    /**
     * Test all methods.
     */
    public void test1() {
        ExtensionFilenameFilter sqlFilter1 = new ExtensionFilenameFilter("sql");
        ExtensionFilenameFilter sqlFilter2 =
        	new ExtensionFilenameFilter(".sql");
        String sqlFile = "dataFile.sql";
        String txtFile = "dataFile.txt";
        File dummyDir = new File("dummyDir");
        assertTrue(sqlFilter1.accept(dummyDir, sqlFile));
        assertFalse(sqlFilter1.accept(dummyDir, txtFile));
        assertTrue(sqlFilter2.accept(dummyDir, sqlFile));
        assertFalse(sqlFilter2.accept(dummyDir, txtFile));
    }

}
