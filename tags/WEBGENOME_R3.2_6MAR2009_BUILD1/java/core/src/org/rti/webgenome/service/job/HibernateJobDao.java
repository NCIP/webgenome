/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2008-10-23 16:17:07 $


*/

package org.rti.webgenome.service.job;

import java.util.Collection;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of {@link JobDao} using Hibernate.
 * @author dhall
 *
 */
public class HibernateJobDao extends HibernateDaoSupport
implements JobDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final Job job) {
		 this.getHibernateTemplate().delete(job);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Collection<Job> loadAll() {
		return this.getHibernateTemplate().loadAll(org.rti.webgenome.service.job.AbstractJob.class);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final Job job) {
		this.getHibernateTemplate().saveOrUpdate(job);
	}
}
