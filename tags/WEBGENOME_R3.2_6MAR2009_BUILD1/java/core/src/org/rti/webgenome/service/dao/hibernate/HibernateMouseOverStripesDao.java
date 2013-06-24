/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-07-03 17:44:00 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.service.dao.MouseOverStripesDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.MouseOverStripesDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateMouseOverStripesDao extends HibernateDaoSupport implements
		MouseOverStripesDao {

	/**
	 * {@inheritDoc}
	 */
	public void delte(final MouseOverStripes stripes) {
		this.getHibernateTemplate().delete(stripes);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final MouseOverStripes stripes) {
		this.getHibernateTemplate().save(stripes);
	}

}
