/*
$Revision: 1.2 $
$Date: 2007-08-17 19:02:17 $

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.service.dao.ExperimentDao;
import org.rti.webgenome.util.DbUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of
 * {@link org.rti.webgenome.service.dao.ExperimentDao}
 * using Hibernate.
 * @author dhall
 *
 */
public class HibernateExperimentDao extends HibernateDaoSupport implements
		ExperimentDao {
	
	/** Data source used for JDBC query. */
	private DataSource dataSource = null;
	
	/**
	 * Set data source that is used for a JDBC query.
	 * @param dataSource Data source
	 */
	public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(final Experiment experiment) {
		this.getHibernateTemplate().delete(experiment);
	}

	/**
	 * {@inheritDoc}
	 */
	public void save(final Experiment experiment) {
		this.getHibernateTemplate().save(experiment);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public Experiment load(final Long id) {
		return (Experiment) this.getHibernateTemplate().load(
				Experiment.class, id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isReferenced(final Long experimentId) {
		boolean referenced = false;
		
		// Check for reference to experiment
		referenced =
			DbUtils.recordWithFieldValueExists(this.dataSource,
					"job_out_experiment_names", "experiment_id",
					experimentId)
			|| DbUtils.recordWithFieldValueExists(this.dataSource,
					"job_experiments", "experiment_id",
					experimentId)
			|| DbUtils.recordWithFieldValueExists(this.dataSource,
					"data_src_props", "input_experiment_id",
					experimentId)
			|| DbUtils.recordWithFieldValueExists(this.dataSource,
					"dsp_experiments", "experiment_id",
					experimentId)
			|| DbUtils.recordWithFieldValueExists(this.dataSource,
					"plot_exp_ids", "exp_id",
					experimentId);
		
		// Next, check for reference to nested biospecimens
		if (!referenced) {
			Collection<Long> bioAssayIds = this.getBioAssayIds(experimentId);
			for (Long bioAssayId : bioAssayIds) {
				referenced =
					DbUtils.recordWithFieldValueExists(this.dataSource,
							"job_out_bioassay_names", "bioassay_id",
							bioAssayId)
					|| DbUtils.recordWithFieldValueExists(
							this.dataSource,
							"bioassay", "parent_bioassay_id",
							bioAssayId);
				if (referenced) {
					break;
				}
			}
		}
		
		return referenced;
	}
	
	
	/**
	 * Get bioassay IDs associated with given experiment ID.
	 * @param experimentId Primary key of an experiment record
	 * @return Bioassay IDs
	 */
	private Collection<Long> getBioAssayIds(final Long experimentId) {
		Collection<Long> ids = new ArrayList<Long>();
		PreparedStatement stmt = null;
		ResultSet rset = null;
		String sql =
			"SELECT bioassay_id "
			+ "FROM experiment_bioassay "
			+ "WHERE experiment_id = ?";
		try {
			stmt = this.dataSource.getConnection().prepareStatement(sql);
			stmt.setLong(1, experimentId);
			rset = stmt.executeQuery();
			while (rset.next()) {
				ids.add(rset.getLong(1));
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error getting bioassays associated with experiment", e);
		} finally {
			DbUtils.close(rset);
			DbUtils.close(stmt);
		}
		return ids;
	}
}
