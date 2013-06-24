/*
$Revision: 1.1 $
$Date: 2007-07-09 22:29:43 $


*/

package org.rti.webgenome.service.dao.hibernate;

import org.rti.webgenome.domain.Experiment.BioAssayDataConstraintsWrapper;
import org.rti.webgenome.service.dao.BioAssayDataConstraintsWrapperDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.BioAssayDataConstraintsWrapperDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateBioAssayDataConstraintsWrapperDao extends
		HibernateDaoSupport implements BioAssayDataConstraintsWrapperDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final BioAssayDataConstraintsWrapper wrapper) {
		this.getHibernateTemplate().delete(wrapper);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final BioAssayDataConstraintsWrapper wrapper) {
		this.getHibernateTemplate().save(wrapper);
	}

}
