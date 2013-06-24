/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-06-25 18:41:54 $


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
