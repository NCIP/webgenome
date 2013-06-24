/*
$Revision: 1.1 $
$Date: 2007-06-28 22:12:17 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.service.dao.PlotParametersDao;
import org.rti.webgenome.service.plot.PlotParameters;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.PlotParametersDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernatePlotParametersDao extends HibernateDaoSupport implements
		PlotParametersDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final PlotParameters params) {
		this.getHibernateTemplate().delete(params);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final PlotParameters params) {
		this.getHibernateTemplate().save(params);
	}

}
