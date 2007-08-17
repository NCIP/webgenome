/*
$Revision: 1.2 $
$Date: 2007-08-17 20:05:05 $

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

import javax.sql.DataSource;

import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.service.dao.PlotDao;
import org.rti.webgenome.util.DbUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.PlotDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernatePlotDao extends HibernateDaoSupport implements PlotDao {
	
	/** Data source for JDBC queries. */
	private DataSource dataSource = null;
	
	
	/**
	 * Set data source for JDBC queries.
	 * @param dataSource Data source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final Plot plot) {
		this.getHibernateTemplate().delete(plot);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final Plot plot) {
		this.getHibernateTemplate().save(plot);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReferenced(final Long plotId) {
		return DbUtils.recordWithFieldValueExists(this.dataSource,
				"job", "plot_id", plotId);
	}
}
