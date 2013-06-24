/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-05-19 20:11:02 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.util.List;

import org.rti.webgenome.domain.Principal;
import org.rti.webgenome.service.dao.PrincipalDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>PrincipalDao</code> using
 * Hibernate.
 * @author dhall
 *
 */
public final class HibernatePrincipalDao extends HibernateDaoSupport
	implements PrincipalDao {

	/**
	 * Persist given principal.
	 * @param principal Principal to persist.
	 */
	public void save(final Principal principal) {
		this.getHibernateTemplate().save(principal);
	}
	
	/**
	 * Updated persisted data on given principal.
	 * @param principal Principal whose persistent
	 * data should be updated.
	 */
	public void update(final Principal principal) {
		this.getHibernateTemplate().update(principal);
	}

	/**
	 * Load principal with given email (email is retrieved case-insensitively, i.e. regardless of case).
	 * @param email Email of principal.
	 * @return Principal with given email, or null
	 * if no such principal exists.
	 */
	public Principal load(final String email) {
		String query = "from Principal p where lower(p.email) = ?";
		Object[] args = new Object[] { toLowerCase ( email ) };
		Principal p = null;
		List principals = this.getHibernateTemplate().find(query, args);
		if (principals != null && principals.size() > 0) {
			p = (Principal) principals.get(0);
		}
		return p;
	}
	
	/**
	 * Load principal with given name and password.
	 * @param email Email Address of principal.
	 * @param password Principal's password.
	 * @param domain Authentication domain
	 * @return Principal with given name and password
	 * or null if no such principal exists.
	 */
	public Principal load( final String email,
						   final String password,
						   final String domain) {
		String query = "from Principal p where lower(p.email) = ? "
			+ "and p.password = ? and p.domain=?";
		Object[] args = new Object[] { toLowerCase(email), password, domain};
		Principal p = null;
		List principals = this.getHibernateTemplate().find(query, args);
		if (principals != null && principals.size() > 0) {
			p = (Principal) principals.get(0);
		}
		return p;
	}
	
	
	/**
	 * Delete data from given principal from persistent
	 * storage.
	 * @param principal Principal whose information
	 * should be removed from persistent storage.
	 */
	public void delete(final Principal principal) {
		this.getHibernateTemplate().delete(principal);
	}
	
	/**
	 * Convert email to case-insensitive.
	 * @param value String to convert to lower-case
	 * @return lower-case value of value parameter. 
	 * */
	private String toLowerCase ( String value ) {
		return value == null ? value : value.toLowerCase() ;
	}
}
