/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2007-09-08 17:17:17 $


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
	public void update(final Experiment experiment) {
		this.getHibernateTemplate().update(experiment);
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
