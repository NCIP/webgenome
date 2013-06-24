/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2007-07-27 22:21:19 $


*/

package org.rti.webgenome.domain;

/**
 * Properties of a data source.  It is necessary for
 * <code>Experiment</code>s to retain information that can be used
 * by data services to obtain additional data.  Classes that implement
 * this interface should override <code>equals</code> and <code>hashCode</code>
 * methods.
 * @author dhall
 *
 */
public interface DataSourceProperties {
	
	
	/**
	 * Set primary key value used for persistence.
	 * @param id Primary key value
	 */
	void setId(Long id);
	
	/**
	 * Get primary key value used for persistence.
	 * @return Primary key value
	 */
	Long getId();
	
	
	/**
	 * Base class for <code>DataSourceProperties</code>-implementing
	 * classes.
	 * @author dhall
	 *
	 */
	public class BaseDataSourceProperties implements DataSourceProperties {
		
		/** Primary key for persistence. */
		private Long id = null;
		
		
		/**
		 * Get primary key value for persistence.
		 * @return Primary key value
		 */
		public final Long getId() {
			return id;
		}


		/**
		 * Set primary key value for persistence.
		 * @param id Primary key value
		 */
		public final void setId(final Long id) {
			this.id = id;
		}
	}
}
