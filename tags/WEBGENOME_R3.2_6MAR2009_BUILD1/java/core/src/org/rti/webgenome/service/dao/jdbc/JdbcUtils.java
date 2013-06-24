/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.service.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Utility methods for JDBC.
 * @author dhall
 *
 */
public final class JdbcUtils {

	/**
	 * Constructor.
	 */
	private JdbcUtils() {
		
	}
	
	/**
	 * Wrapper around ResultSet.close() and Statement.close()
	 * methods that rethrows SQLExceptions as unchecked
	 * exceptions.
	 * @param rset A result set to close
	 * @param stmt A statement to close
	 */
	public static void close(final ResultSet rset, final Statement stmt) {
		try {
			if (rset != null) {
				rset.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error closing JDBC result set and statement", e);
		}
	}
}
