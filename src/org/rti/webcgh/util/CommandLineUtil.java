/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/util/CommandLineUtil.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:02 $

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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Class for working with command line arguments
 */
public class CommandLineUtil {
	
	// ================================================
	//                Constants
	// ================================================
	
	private static final int STATE_INIT = 0;
	private static final int STATE_OPT_NAME_NO_VALUE = 1;
	private static final int STATE_OPT_NAME_WITH_VALUE = 2;
	private static final int STATE_OPT_VALUE = 3;
	private static final int STATE_END_OF_OPTS = 4;
	private static final int STATE_INVALID = 5;
	
	
	// =================================================
	//     Attributes with accessors/mutators
	// =================================================
	
	private Set optionsWithoutValues = new HashSet();
	
	
	/**
	 * Set list of command line options that do not take associated values.
	 * An example of such an option is the JAR '-x' option.  All
	 * options must begin with the '-' character.
	 * @param options Command line options that do not take values
	 */
	public void setOptionsWithoutValues(String[] options) {
		this.optionsWithoutValues = new HashSet();
		for (int i = 0; i < options.length; i++)
			this.optionsWithoutValues.add(options[i]);
	}
	
	/**
	 * Set list of command line options that do not take associated values.
	 * An example of such an option is the JAR '-x' option.  All
	 * options must begin with the '-' character.
	 * @return options Command line options that do not take values
	 */
	public String[] getOptionsWithoutValues() {
		String[] opts = new String[0];
		opts = (String[])this.optionsWithoutValues.toArray(opts);
		return opts;
	}
	
	
	// ================================================
	//               Constructors
	// ================================================
	
	/**
	 * Constructor
	 */
	public CommandLineUtil() {}
	
	
	/**
	 * Constructor
	 * @param optionsWithoutValues List of command line options 
	 * that do not take associated values.
	 * An example of such an option is the JAR '-x' option.  All
	 * options must begin with the '-' character.
	 */
	public CommandLineUtil(String[] optionsWithoutValues) {
		this();
		this.setOptionsWithoutValues(optionsWithoutValues);
	}
	
	
	// =======================================================
	//                Public methods
	// =======================================================
	

    /**
     * <p>
     * Converts command line options into properties.  A well-formed
     * command line will look like:
     * </p>
     * 
     * <p>
     * java class_name [options] [arguments]
     * </p>
     * 
     * <p>
     * Options are of the form '-option' or '-option value'
     * A given option may or may not be included.  Options can
     * be in any order.  However, all options must precede all
     * arguments.  All arguments (if any) are
     * mandatory and must be supplied in a given order.
     * </p>
     * 
     * <p>
     * This method creates a set of properties as follows:
     * <ul>
     * <li>For each option name there will be a property name.
     * <li>For each option value there will be a property value
     * associated with the option name that immediately precedes the value
     * <li>The values of properties associated with options that lack
     * values will be equal to the property name
     * </ul>
     * </p>
     * 
     * @param args Command line arguments
     * @return Properties
     * @throws IllegalArgumentException if command line is
     * not well-formed, as described above
     */
    public Properties commandLineOptionsToProperties
	(
		String[] args
	) {
    	Properties props = new Properties();
    	int state = STATE_INIT;
    	String currPropName = null;
    	for (int i = 0; i < args.length; i++) {
    		String token = args[i];
    		if (! this.validToken(state, token))
    			throw new IllegalArgumentException("Unexpected token '" + token + "'");
    		state = newState(state, token);
    		switch (state) {
    			case STATE_OPT_NAME_NO_VALUE :
    				props.setProperty(token, token);
    				break;
    			case STATE_OPT_NAME_WITH_VALUE :
    				currPropName = token;
    				break;
    			case STATE_OPT_VALUE :
    				props.setProperty(currPropName, token);
    				break;
    		}
    	}
    	if (state == STATE_OPT_NAME_WITH_VALUE)
    	    throw new IllegalArgumentException("Expecting value after option '" + args[args.length - 1] + "'");
    	return props;
    }
    
    
    /**
     * <p>
     * Parse arguments out of raw command line tokens.  A well-formed
     * command line will look like:
     * </p>
     * 
     * <p>
     * java class_name [options] [arguments]
     * </p>
     * 
     * <p>
     * Options are of the form '-option' or '-option value'
     * A given option may or may not be included.  Options can
     * be in any order.  However, all options must precede all
     * arguments.  All arguments (if any) are
     * mandatory and must be supplied in a given order.
     * </p>
     * @param rawArgs Raw command line tokens
     * @return Arguments from command line
     */
    public String[] parseArguments(String[] rawArgs) {
    	List args = new ArrayList();
    	int state = STATE_INIT;
    	for (int i = 0; i < rawArgs.length; i++) {
    		String token = rawArgs[i];
    		if (! this.validToken(state, token))
    			throw new IllegalArgumentException("Unexpected token '" + token + "'");
    		state = this.newState(state, token);
    		if (state == STATE_END_OF_OPTS)
    			args.add(token);
    	}
    	String[] argsArray = new String[0];
    	argsArray = (String[])args.toArray(argsArray);
    	return argsArray;
    }
	
	
	// =======================================================
	//                   Private methods
	// =======================================================
    
    
    private boolean isOption(String token) {
        return token.indexOf("-") == 0;
    }
    
    
    private boolean validToken(int state, String token) {
    	if ("-".equals(token))
    		return false;
    	boolean valid = true;
    	
    	// Token is an option
    	if (this.isOption(token)) {
    		if (state == STATE_OPT_NAME_WITH_VALUE)
    			valid = false;
    		else if (state == STATE_END_OF_OPTS)
    		    valid = false;
    	}
    	
    	return valid;
    }
    
    
    private int newState(int state, String token) {
    	int newState = STATE_INVALID;
    	
    	// Token is an option
    	if (this.isOption(token)) {
    	    if (this.optionsWithoutValues.contains(token))
    	        newState = STATE_OPT_NAME_NO_VALUE;
    	    else
    	        newState = STATE_OPT_NAME_WITH_VALUE;
    	}
    	
    	// Token is not an option
    	else {
    	    
    	    // Option value
    	    if (state == STATE_OPT_NAME_WITH_VALUE)
    	        newState = STATE_OPT_VALUE;
    	    
    	    // End of options
    	    else
    	        newState = STATE_END_OF_OPTS;
    	}
    	
    	// Token not an option
    	return newState;
    }
}
