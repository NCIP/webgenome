/*
$Revision: 1.2 $
$Date: 2007-12-04 23:06:40 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.DataSourceProperties;
import org.rti.webgenome.service.dao.DataSourcePropertiesDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.DataSourcePropertiesDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateDataSourcePropertiesDao extends HibernateDaoSupport
		implements DataSourcePropertiesDao {
	
	/**
	 * {@inheritDoc}
	 */
	public void delete(final DataSourceProperties props) {
		this.getHibernateTemplate().delete(props);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final DataSourceProperties props) {
		this.getHibernateTemplate().save(props);
	}
}
