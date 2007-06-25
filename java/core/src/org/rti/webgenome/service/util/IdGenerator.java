/*
$Revision: 1.2 $
$Date: 2007-06-25 18:41:54 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.rti.webgenome.service.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Utility class for generating unique identifiers
 * for objects that may or may not be persisted.  Each
 * instance of this class must correspond to a table in
 * the database.  This table must have a primary key
 * of a type compatable with {@link java.lang.Long}.
 * Upon initialization, the object finds
 * the greatest primary key in the given table.
 * @author dhall
 *
 */
public class IdGenerator {
	
	//
	//     A T T R I B U T E S
	//
	
	/** Next available ID value. */
	private long nextValue = 1;
	
	//
	//     C O N S T R U C T O R S
	//
	
	/**
	 * Constructor.
	 * @param tableName Name of database table corresponding to this
	 * id generator.
	 * @param keyColumnName Name of primary key column
	 * @param dataSource A data source containing given table and column
	 */
	public IdGenerator(final String tableName, final String keyColumnName,
			final DataSource dataSource) {
		this.nextValue = this.findNextKeyValue(tableName, keyColumnName,
				dataSource);
	}
	
	
	/**
	 * Find nxt primary key value for given table (i.e., one
	 * larger than current largest value.
	 * @param tableName Name of database table corresponding to this
	 * id generator.
	 * @param keyColumnName Name of primary key column
	 * @param dataSource A data source containing given table and column
	 * @return Next primary key value for given table (i.e., one
	 * larger than current largest value.
	 */
	private long findNextKeyValue(final String tableName,
			final String keyColumnName, final DataSource dataSource) {
		long max = 0;
		String sql = "SELECT MAX(" + keyColumnName + ") FROM " + tableName;
		Statement stmt = null;
		try {
			stmt = dataSource.getConnection().createStatement();
			ResultSet rset = null;
			rset = stmt.executeQuery(sql);
			if (rset.next()) {
				max = rset.getLong(1);
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error finding max primary key value in table '"
					+ tableName + "' column '" + keyColumnName + "'");
		}
		return max + 1;
	}
	
	//
	//     P U B L I C    M E T H O D S
	//
	
	/**
	 * Get next ID.
	 * @return Next id
	 */
	public final Long nextId() {
		return this.nextValue++;
	}
}
