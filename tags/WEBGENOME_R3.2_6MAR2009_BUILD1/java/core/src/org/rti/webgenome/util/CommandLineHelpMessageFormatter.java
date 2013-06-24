/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/CommandLineHelpMessageFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $



*/

package org.rti.webgenome.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Formats help messages on invoking a main-method containing
 * class from the command line.
 */
public class CommandLineHelpMessageFormatter {
	
	
	private final String usageDescription;
	private final String command;
	private final List args = new ArrayList();
	private final List options = new ArrayList();
	
	
	// ===========================================
	//               Constructors
	// ===========================================
	
	/**
	 * Constructor
	 * @param command Command
	 * @param usageDescription Description of usage of class
	 */
	public CommandLineHelpMessageFormatter
	(
		String command, String usageDescription
	) {
		this.command = command;
		this.usageDescription = usageDescription;
	}
	
	
	// ============================================
	//             Public methods
	// ============================================
	
	
	/**
	 * Add a command line argument
	 * @param name Argument name
	 * @param description Argument description
	 */
	public void addArgument(String name, String description) {
		Option opt = new Option(name, null, description);
		this.args.add(opt);
	}
	
	
	/**
	 * Add a command line option
	 * @param name Option name
	 * @param value Option template value
	 * @param description Option description
	 */
	public void addOption(String name, String value, String description) {
		Option opt = new Option(name, value, description);
		this.options.add(opt);
	}
	
	
	/**
	 * Return formatted message for display on command line
	 * describing how to use class main method
	 * @return Formatted message
	 */
	public String getMessage() {
		StringBuffer buff = new StringBuffer("Usage: " + this.command);
		if (this.options.size() > 0)
			buff.append(" [options]");
		if (this.args.size() > 0)
			buff.append(" " + this.formatArgs());
		if (this.args.size() > 0)
			buff.append("\n\nArguments:\n\n" + this.formatArgDescriptions());
		if (this.options.size() > 0)
			buff.append("\n\nOptions:\n\n" + this.formatOptionDescriptions());
		buff.append("\n");
		return buff.toString();
	}
	
	
	// ==============================================
	//               Private methods
	// ==============================================
	
	private String formatArgs() {
		StringBuffer buff = new StringBuffer();
		int count = 0;
		for (Iterator it = this.args.iterator(); it.hasNext();) {
			Option opt = (Option)it.next();
			if (count++ > 0)
				buff.append(" ");
			buff.append(opt.getName());
		}
		return buff.toString();
	}
	
	
	private String formatArgDescriptions() {
		CommandLineTableFormatter formatter = 
			new CommandLineTableFormatter(2);
		for (Iterator it = this.args.iterator(); it.hasNext();) {
			Option opt = (Option)it.next();
			String[] row = new String[2];
			row[0] = opt.getName();
			row[1] = opt.getDescription();
			formatter.addRow(row);
		}
		return formatter.getTable();
	}
	
	
	private String formatOptionDescriptions() {
		CommandLineTableFormatter formatter =
			new CommandLineTableFormatter(2);
		for (Iterator it = this.options.iterator(); it.hasNext();) {
			Option opt = (Option)it.next();
			String[] row = new String[2];
			row[0] = opt.getName() + " " + opt.getValue();
			row[1] = opt.getDescription();
			formatter.addRow(row);
		}
		return formatter.getTable();
	}
	
	// =============================================
	//      Embedded class for storing options
	// =============================================
	
	class Option {
		
		private final String name;
		private final String value;
		private final String description;
		
		
		private Option(String name, String value, String description) {
			this.name = name;
			this.value = value;
			this.description = description;
		}
		
		
		private String getName() {
			return this.name;
		}
		
		
		private String getValue() {
			return this.value;
		}
		
		
		private String getDescription() {
			return this.description;
		}
	}

}
