/*
$Revision: 1.3 $
$Date: 2008-03-12 22:23:18 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.util.List;

import org.rti.webgenome.domain.Array;
import org.rti.webgenome.service.dao.ArrayDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of {@link org.rti.webgenome.service.dao.ArrayDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateArrayDao extends HibernateDaoSupport
implements ArrayDao {

	/**
	 * {@inheritDoc}
	 */
	public void delete(final Array array) {
		this.getHibernateTemplate().delete(array);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(final Array array) {
		this.getHibernateTemplate().saveOrUpdate(array);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReferenced(final Array array) {
		String query = "from BioAssay ba where ba.array = ?";
		Object[] args = new Object[]{array};
		List bioAssays =
			this.getHibernateTemplate().find(query, args);
		return bioAssays != null && bioAssays.size() > 0;
	}
}
