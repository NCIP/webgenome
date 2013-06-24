/*
$Revision: 1.1 $
$Date: 2007-06-27 15:47:15 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.analysis.UserConfigurableProperty;
import org.rti.webgenome.service.dao.UserConfigurablePropertyDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.UserConfigurablePropertyDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateUserConfigurablePropertyDao
extends HibernateDaoSupport implements UserConfigurablePropertyDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final UserConfigurableProperty prop) {
		this.getHibernateTemplate().delete(prop);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final UserConfigurableProperty prop) {
		this.getHibernateTemplate().save(prop);
	}

}
