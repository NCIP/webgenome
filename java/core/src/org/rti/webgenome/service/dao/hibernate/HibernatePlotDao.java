/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-08-17 20:05:05 $


*/

package org.rti.webgenome.service.dao.hibernate;

import javax.sql.DataSource;

import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.service.dao.PlotDao;
import org.rti.webgenome.util.DbUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.PlotDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernatePlotDao extends HibernateDaoSupport implements PlotDao {
	
	/** Data source for JDBC queries. */
	private DataSource dataSource = null;
	
	
	/**
	 * Set data source for JDBC queries.
	 * @param dataSource Data source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final Plot plot) {
		this.getHibernateTemplate().delete(plot);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final Plot plot) {
		this.getHibernateTemplate().save(plot);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReferenced(final Long plotId) {
		return DbUtils.recordWithFieldValueExists(this.dataSource,
				"job", "plot_id", plotId);
	}
}
