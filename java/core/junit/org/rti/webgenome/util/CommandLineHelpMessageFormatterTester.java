/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/junit/org/rti/webgenome/util/CommandLineHelpMessageFormatterTester.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $



*/

package org.rti.webgenome.util;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.CommandLineHelpMessageFormatter;

import junit.framework.TestCase;

/**
 * Tester for <code>CommandLineHelpMessageFormatter</code>
 */
public class CommandLineHelpMessageFormatterTester extends TestCase {
	
	private static final Logger LOGGER = 
		Logger.getLogger(CommandLineHelpMessageFormatterTester.class);
	
	
	/**
	 * 
	 *
	 */
	public void test1() {
		CommandLineHelpMessageFormatter f =
			new CommandLineHelpMessageFormatter(CommandLineHelpMessageFormatterTester.class.getName(),
				"This class does nothing");
		f.addArgument("file1", "A file that contains nothing");
		f.addArgument("additional_files", "More files that contain nothing");
		f.addOption("-d", "dir_path", "Path to a directory");
		f.addOption("-p", null, "Purge database before loading data");
		LOGGER.info("\n" + f.getMessage() + "\n");
		assertTrue(true);
	}

}
