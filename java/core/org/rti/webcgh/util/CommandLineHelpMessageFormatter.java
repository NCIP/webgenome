/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/org/rti/webcgh/util/CommandLineHelpMessageFormatter.java,v $
$Revision: 1.1 $
$Date: 2007-03-27 19:42:08 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package org.rti.webcgh.util;

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
