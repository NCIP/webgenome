/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-06-29 21:47:51 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.graphics.io.ClickBoxes;
import org.rti.webgenome.service.dao.ClickBoxesDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.ClickBoxesDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateClickBoxesDao extends HibernateDaoSupport implements
		ClickBoxesDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final ClickBoxes clickBoxes) {
		this.getHibernateTemplate().delete(clickBoxes);
	}

	/**
	 * {@inheritDoc}
	 */
	public ClickBoxes load(final Long id) {
		return (ClickBoxes)
			this.getHibernateTemplate().load(ClickBoxes.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final ClickBoxes clickBoxes) {
		this.getHibernateTemplate().save(clickBoxes);
	}

}
