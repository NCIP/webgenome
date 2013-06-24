/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/util/CommandLineTableFormatterTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/

package org.rti.webgenome.util;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.CommandLineTableFormatter;

import junit.framework.TestCase;

/**
 * Test case for class <code>CommandLineTableFormatter</code>
 */
public class CommandLineTableFormatterTester extends TestCase {
	
	
	private static final Logger LOGGER = 
		Logger.getLogger(CommandLineTableFormatterTester.class);
	
	
	/**
	 * 
	 *
	 */
	public void test1() {
		CommandLineTableFormatter formatter = 
			new CommandLineTableFormatter(3);
		formatter.addRow(new String[]{"1", "22", "333"});
		formatter.addRow(new String[]{"22", "1", "1"});
		formatter.addRow(new String[]{"1", "1", "22"});
		formatter.setPadding(5);
		String table = formatter.getTable();
		LOGGER.info("\n" + table);
		assertEquals(table.length(), 53);
	}

}
