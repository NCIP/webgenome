/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/util/PathTokenizerTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/

package org.rti.webgenome.util;

import org.rti.webgenome.util.PathTokenizer;

import junit.framework.TestCase;

/**
 * Tester for <code>PathTokenizer</code>.
 * @author dhall
 *
 */
public final class PathTokenizerTester extends TestCase {
    
    
    /**
     * Test on null string.
     *
     */
    public void testNull() {
        String path = null;
        try {
            PathTokenizer pt = new PathTokenizer(path);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
    
    
    /**
     * Test on empty string.
     *
     */
    public void testEmpty() {
        String path = "";
        PathTokenizer pt = new PathTokenizer(path);
        assertFalse(pt.hasNext());
    }
    
    
    /**
     * Test on path with mixed path separators.
     *
     */
    public void testMixed() {
        String path = "C:\\temp/test";
        PathTokenizer pt = new PathTokenizer(path);
        assertTrue(pt.hasNext());
        assertEquals("C:", pt.next());
        assertTrue(pt.hasNext());
        assertEquals("temp", pt.next());
        assertTrue(pt.hasNext());
        assertEquals("test", pt.next());
        assertFalse(pt.hasNext());
    }
    
    
//    /**
//     * Test on abolute path.
//     *
//     */
//    public void testAbsolutePath() {
//        String path = "/webgenome/temp/test";
//        PathTokenizer pt = new PathTokenizer(path);
//        assertTrue(pt.hasNext());
//        assertEquals("webgenome", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("temp", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("test", pt.next());
//        assertFalse(pt.hasNext());
//    }
    
    
    /**
     * Test on path that begins with empty field.
     *
     */
//    public void testBeginsEmpty() {
//        String path = "//temp/test";
//        PathTokenizer pt = new PathTokenizer(path);
//        assertTrue(pt.hasNext());
//        assertEquals("", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("temp", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("test", pt.next());
//        assertFalse(pt.hasNext());
//    }
    
    
//    /**
//     * Test on path with an empty field in the center.
//     *
//     */
//    public void testMiddleEmpty() {
//        String path = "/temp//test";
//        PathTokenizer pt = new PathTokenizer(path);
//        assertTrue(pt.hasNext());
//        assertEquals("temp", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("", pt.next());
//        assertTrue(pt.hasNext());
//        assertEquals("test", pt.next());
//        assertFalse(pt.hasNext());
//    }

}
