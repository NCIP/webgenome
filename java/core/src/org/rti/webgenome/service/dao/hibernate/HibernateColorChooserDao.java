/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-07-16 16:25:14 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.graphics.util.ColorChooser;
import org.rti.webgenome.service.dao.ColorChooserDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.ColorChooserDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateColorChooserDao extends HibernateDaoSupport implements
		ColorChooserDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final ColorChooser colorChooser) {
		this.getHibernateTemplate().delete(colorChooser);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final ColorChooser colorChooser) {
		this.getHibernateTemplate().saveOrUpdate(colorChooser);
	}
}
