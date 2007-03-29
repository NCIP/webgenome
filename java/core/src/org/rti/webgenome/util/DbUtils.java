/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/util/DbUtils.java,v $
$Revision: 1.2 $
$Date: 2007-03-29 18:02:01 $

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
package org.rti.webgenome.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.rti.webgenome.core.WebGenomeRuntimeException;

/**
 * Database utilities
 */
public class DbUtils {
    
    
	/**
	 * Close a statement
	 * @param stmt A statement
	 */
	public static void close(Statement stmt) {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new WebGenomeRuntimeException("Error closing database connection", e);
			}
	}
	
	
	/**
	 * Close a result set
	 * @param rset A result set
	 */
	public static void close(ResultSet rset) {
		if (rset != null)
			try {
				rset.close();
			} catch (SQLException e) {
				throw new WebGenomeRuntimeException("Error closing result set", e);
			}
	}
	
	
	/**
	 * Close a database connections
	 * @param con A connection
	 */
	public static void close(Connection con) {
	    if (con != null)
            try {
            	if (! con.isClosed())
            		con.close();
            } catch (SQLException e) {
                throw new WebGenomeRuntimeException("Error closing database connection");
            }
	}
	
	
	/**
	 * Encode a boolean value as an integer
	 * @param value Boolean value
	 * @return Integer equivalent
	 */
	public static int encodeBoolean(boolean value) {
	    return value? 1 : 0;
	}
	
	
	/**
	 * Decode an integer as a boolean value
	 * @param value An integer encoded boolean value
	 * @return Boolean equivalent
	 */
	public static boolean decodeBoolean(int value) {
	    return (value == 0)? false : true;
	}

}
