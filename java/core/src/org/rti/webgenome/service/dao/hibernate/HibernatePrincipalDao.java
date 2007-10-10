/*
$Revision: 1.2 $
$Date: 2007-10-10 17:47:01 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the
National Cancer Institute, and so to the extent government employees are
co-authors, any rights in such works shall be subject to Title 17 of the
United States Code, section 105.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this 
list of conditions and the disclaimer of Article 3, below. Redistributions in 
binary form must reproduce the above copyright notice, this list of conditions 
and the following disclaimer in the documentation and/or other materials 
provided with the distribution.

2. The end-user documentation included with the redistribution, if any, must 
include the following acknowledgment:

"This product includes software developed by the RTI and the National Cancer 
Institute."

If no such end-user documentation is to be included, this acknowledgment shall 
appear in the software itself, wherever such third-party acknowledgments 
normally appear.

3. The names "The National Cancer Institute", "NCI", 
“Research Triangle Institute”, and "RTI" must not be used to endorse or promote 
products derived from this software.

4. This license does not authorize the incorporation of this software into any 
proprietary programs. This license does not authorize the recipient to use any 
trademarks owned by either NCI or RTI.

5. THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
(INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE
NATIONAL CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
	 * Load principal with given name.
	 * @param name Name of principal.
	 * @return Principal with given name, or null
	 * if no such principal exists.
	 */
	public Principal load(final String name) {
		String query = "from Principal p where p.name = ?";
		Object[] args = new Object[] {name};
		Principal p = null;
		List principals = this.getHibernateTemplate().find(query, args);
		if (principals != null && principals.size() > 0) {
			p = (Principal) principals.get(0);
		}
		return p;
	}
	
	
	/**
	 * Load principal with given name and password.
	 * @param name Name of principal.
	 * @param password Principal's password.
	 * @param domain Authentication domain
	 * @return Principal with given name and password
	 * or null if no such principal exists.
	 */
	public Principal load(final String name, final String password,
			final String domain) {
		String query = "from Principal p where p.name = ? "
			+ "and p.password = ? and p.domain=?";
		Object[] args = new Object[] {name, password, domain};
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
}
