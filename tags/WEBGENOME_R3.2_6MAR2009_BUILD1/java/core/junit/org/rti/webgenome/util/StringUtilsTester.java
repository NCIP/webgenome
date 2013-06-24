/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/util/StringUtilsTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/

package org.rti.webgenome.util;

import org.rti.webgenome.util.StringUtils;

import junit.framework.TestCase;

/**
 * Tester for StringUtils
 */
public class StringUtilsTester extends TestCase {
	
	
    /**
     * 
     *
     */
	public void testCompare() {
		assertEquals(StringUtils.compare(null, null), 0);
		assertEquals(StringUtils.compare("a", null), 1);
		assertEquals(StringUtils.compare(null, "a"), -1);
		assertEquals(StringUtils.compare("hello", "goodbye"), 1);
		assertEquals(StringUtils.compare("goodbye", "hello"), -1);
		assertEquals(StringUtils.compare("hell", "hello"), -1);
		assertEquals(StringUtils.compare("hello", "hell"), 1);
		assertEquals(StringUtils.compare("hello", "hello"), 0);
	}
	
	
	/**
	 * 
	 *
	 */
	public void testSplit() {
	    String str1 = "Hello\tworld\t hello ";
	    String[] words = StringUtils.split(str1, "\t");
	    assertEquals(words.length, 3);
	    assertEquals(words[0], "Hello");
	    assertEquals(words[1], "world");
	    assertEquals(words[2], "hello");
	}

}
