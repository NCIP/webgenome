/*
$Revision: 1.1 $
$Date: 2007-07-06 14:41:41 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.service.dao.BioAssayDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.BioAssayDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateBioAssayDao extends HibernateDaoSupport implements
		BioAssayDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final BioAssay bioAssay) {
		this.getHibernateTemplate().delete(bioAssay);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final BioAssay bioAssay) {
		this.getHibernateTemplate().save(bioAssay);
	}

}
