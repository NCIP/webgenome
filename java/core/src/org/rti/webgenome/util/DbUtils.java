/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.7 $
$Date: 2008-09-09 18:31:39 $


*/


package org.rti.webgenome.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.rti.webgenome.core.WebGenomeApplicationException;
import org.rti.webgenome.core.WebGenomeRuntimeException;
import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Database utilities.
 */
public final class DbUtils {
	
	/**
	 * Constructor.
	 */
	private DbUtils() {
		
	}
	
	/**
	 * A delimiting character for encoding/decoding
	 * rectangular data.
	 */
	private static final char DELIMITER = ',';
    
	/**
	 * An escape character for representing a delimiter
	 * character within a field.
	 */
	private static final char ESCAPE = '\\';
	
	/** String encoding of null. */
	private static final String NULL = "N*U*L*L";
    
	/**
	 * Close a statement.
	 * @param stmt A statement
	 */
	public static void close(final Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new WebGenomeRuntimeException(
						"Error closing database connection", e);
			}
		}
	}
	
	
	/**
	 * Close a result set.
	 * @param rset A result set
	 */
	public static void close(final ResultSet rset) {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e) {
				throw new WebGenomeRuntimeException(
						"Error closing result set", e);
			}
		}
	}
	
	
	/**
	 * Close a database connections.
	 * @param con A connection
	 */
	public static void close(final Connection con) {
	    if (con != null) {
            try {
            	if (!con.isClosed()) {
            		con.close();
            	}
            } catch (SQLException e) {
                throw new WebGenomeRuntimeException(
                		"Error closing database connection");
            }
	    }
	}
	
	
	/**
	 * Encode a boolean value as an integer.
	 * @param value Boolean value
	 * @return Integer equivalent
	 */
	public static int encodeBoolean(final boolean value) {
	    return value ? 1 : 0;
	}
	
	
	/**
	 * Decode an integer as a boolean value.
	 * @param value An integer encoded boolean value
	 * @return Boolean equivalent
	 */
	public static boolean decodeBoolean(final int value) {
	    return (value == 0) ? false : true;
	}

	
	/**
	 * Encode given matrix as a single string representation
	 * for storing in a CLOB field.
	 * @param matrix A matrix to store
	 * @return CLOB representation of given matrix
	 */
	public static String encodeClob(final String[][] matrix) {
		StringBuffer buff = new StringBuffer();
		
		// Special case: matrix with dimension 0, 0
		if (matrix == null || matrix.length < 1
				|| matrix[0].length < 1) {
			buff.append("0" + DELIMITER + "0");
			
		// General case
		} else {
		
			// Encode dimensions in first line
			buff.append(String.valueOf(matrix.length) + DELIMITER
					+ String.valueOf(matrix[0].length) + "\n");
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					String text = matrix[i][j];
					if (text != null) {
						for (int k = 0; k < text.length(); k++) {
							char c = text.charAt(k);
							if (c == DELIMITER || c == ESCAPE) {
								buff.append(ESCAPE);
							}
						buff.append(c);
						}
					} else {
						buff.append(NULL);
					}
					if (j < matrix[i].length - 1) {
						buff.append(DELIMITER);
					}
				}
				buff.append("\n");
			}
		}
		
		return buff.toString();
	}
	
	
	/**
	 * Decode CLOB-encoded matrix encoded using
	 * {@code encodeClob} method.
	 * @param clob CLOB-encoded matrix
	 * @return Decoded matrix
	 * @throws WebGenomeApplicationException If the format
	 * of the given clob cannot be parsed.
	 */
	public static String[][] decodeClob(final String clob)
	throws WebGenomeApplicationException {
		String[][] matrix = null;
		try {
			
			// Read dimensions of matrix from first line
			BufferedReader in = new BufferedReader(new StringReader(clob));
			String line = in.readLine();
			StringTokenizer tok = new StringTokenizer(line,
					String.valueOf(DELIMITER));
			int numRows = Integer.parseInt(tok.nextToken());
			int numCols = Integer.parseInt(tok.nextToken());
			
			// Instantiate and fill in matrix
			matrix = new String[numRows][];
			for (int i = 0; i < numRows; i++) {
				line = in.readLine();
				matrix[i] = new String[numCols];
				int j = 0, k = 0;
				boolean lastCharIsEscape = false;
				StringBuffer buff = new StringBuffer();
				while (k < line.length()) {
					char c = line.charAt(k++);
					if (lastCharIsEscape) {
						lastCharIsEscape = false;
						buff.append(c);
					} else {
						if (c == ESCAPE) {
							lastCharIsEscape = true;
						} else {
							if (c == DELIMITER || k == line.length()) {
								if (c != DELIMITER) {
									buff.append(c);
								}
								String value = buff.toString();
								if (NULL.equals(value)) {
									value = null;
								}
								matrix[i][j++] = value;
								buff = new StringBuffer();
							} else {
								buff.append(c);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new WebGenomeApplicationException(
					"Error decoding CLOB field value", e);
		}
		return matrix;
	}
	
	
	/**
	 * Is there a record in the database with a given field value?
	 * @param dataSource Data source
	 * @param tableName Name of table
	 * @param fieldName Name of field
	 * @param value Value to query
	 * @return T/F
	 */
	public static boolean recordWithFieldValueExists(
			final DataSource dataSource, final String tableName,
			final String fieldName, final Long value) {
		boolean exists = false;
		Statement stmt = null;
		ResultSet rset = null;
		try{
			Connection con = dataSource.getConnection();
			String sql =
			"SELECT * "
			+ " FROM " + tableName
			+ " WHERE " + fieldName + " = " + value;
			try {
			
				stmt = con.createStatement();
				rset = stmt.executeQuery(sql);
				exists = rset.next();
			
			} catch (SQLException e) {
				throw new WebGenomeSystemException(
					"Error querying database table '" + tableName
					+ "' field '" + fieldName + "'", e);
			} finally {
				DbUtils.close(rset);
				DbUtils.close(stmt);		
				close(con);
			}	
		}catch(SQLException e){
			throw new WebGenomeSystemException(
					"Could not close db connection ",  e);
		}
	
		return exists;
	}
	
	
	/**
	 * Get all values from string column of given table.
	 * @param dataSource Data source
	 * @param tableName Name of database table
	 * @param columnName Name of column in given table
	 * @return All string values from given column and
	 * table
	 */
	public static Collection<String> getStringValues(
			final DataSource dataSource,
			final String tableName, final String columnName) {
		
		Collection<String> data = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rset = null;
		try {
			
		Connection con = dataSource.getConnection();
		String sql =
			"SELECT " + columnName
			+ " FROM " + tableName;
		try{
			
			stmt = con.createStatement();
			rset = stmt.executeQuery(sql);
			while (rset.next()) {
				data.add(rset.getString(1));
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error querying database table '" + tableName
					+ "' column '" + columnName + "'", e);
		} finally {
			DbUtils.close(rset);
			DbUtils.close(stmt);
			close(con);
		}
	} catch (SQLException e) {
			throw new WebGenomeSystemException("Error when closing connection ", e);
	}		
		return data;
	}
}
