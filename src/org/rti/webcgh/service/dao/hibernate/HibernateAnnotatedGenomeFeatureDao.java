/*
$Revision: 1.5 $
$Date: 2007-02-16 23:29:38 $

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

package org.rti.webcgh.service.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.AnnotatedGenomeFeatureDao;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>AnnotatedGenomeFeatureDao</code> using
 * Hibernate.
 * @author dhall
 *
 */
public class HibernateAnnotatedGenomeFeatureDao
extends HibernateDaoSupport implements AnnotatedGenomeFeatureDao {
	
	//
	//     ATTRIBUTES
	//
	
	/**
	 * Data source used for a single SQL query that I could
	 * not figure out now to do using HQL and Spring.
	 */
	private DataSource dataSource = null;
	
	//
	//     GETTERS/SETTERS
	//
	
	
	/**
	 * Set data source which is used for a single SQL query that I could
	 * not figure out now to do using HQL and Spring.
	 * @param dataSource Data source
	 */
	public final void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	//
	//     INTERFACE: AnnotatedGenomeFeatureDao
	//

	/**
	 * Persist given feature.
	 * @param feature Feature to persist
	 */
	public final void save(final AnnotatedGenomeFeature feature) {
		this.getHibernateTemplate().save(feature);
	}
	
	/**
	 * Un-persist all features of given type and organism.
	 * @param annotationType Feature type
	 * @param organism Organism
	 */
	@SuppressWarnings("unchecked")
	public final void deleteAll(final AnnotationType annotationType,
			final Organism organism) {
		HibernateTemplate template = this.getHibernateTemplate();
		
		// Get features
		String query =
			"from AnnotatedGenomeFeature f "
			+ "where f.annotationType = ? and f.organism = ?";
		Object[] args = new Object[] {annotationType.toString(),
				organism};
		List<AnnotatedGenomeFeature> feats =
			template.find(query, args);
		
		// Delete features
		template.deleteAll(feats);
	}
	
	
	/**
	 * Load all annotated features of given type
	 * and organism in given genomic
	 * range.
	 * @param chromosome Chromosome number
	 * @param startPos Starting position of range
	 * @param endPos Ending position of range
	 * @param annotationType Annotation feature type
	 * @param organism Organism
	 * @return Annotated features
	 */
	@SuppressWarnings("unchecked")
	public final SortedSet<AnnotatedGenomeFeature> load(
			final short chromosome, final long startPos,
			final long endPos,
			final AnnotationType annotationType,
			final Organism organism) {
		String query =
			"from AnnotatedGenomeFeature f "
			+ "where f.annotationType = ? and f.organism = ? "
			+ "and f.chromosome = ? and f.startLocation <= ? "
			+ "and f.endLocation >= ?";
		Object[] args = new Object[] {annotationType.toString(),
				organism,
				chromosome, endPos, startPos};
		List<AnnotatedGenomeFeature> feats =
			this.getHibernateTemplate().find(query, args);
		SortedSet<AnnotatedGenomeFeature> ss =
			new TreeSet<AnnotatedGenomeFeature>();
		ss.addAll(feats);
		return ss;
	}
	
	/**
	 * Return all organisms who have gene data loaded in
	 * database.
	 * @return All organisms who have gene data loaded in
	 * database
	 */
	@SuppressWarnings("unchecked")
	public final Set<Organism> organismsWithLoadedGenes() {
		Set<Organism> orgs = new HashSet<Organism>();
		String query =
			"select org "
			+ "from Organism org, AnnotatedGenomeFeature feat "
			+ "where feat.organism = org";
		List<Organism> results = this.getHibernateTemplate().find(query);
		orgs.addAll(results);
		return orgs;
	}
	
	// TODO (or not to do!): Convert SQL code below to HQL
	/**
	 * Get all annotation types with data for given organism.
	 * @param org An organism
	 * @return Annotation types
	 */
	@SuppressWarnings("unchecked")
	public final Set<AnnotationType> availableAnnotationTypes(
			final Organism org) {
		Set<AnnotationType> types = new HashSet<AnnotationType>();
		
		// Unable to get the following query to work with HQL,
		// so I fell back on SQL
		try {
			Connection conn = this.dataSource.getConnection();
			String query =
				"select distinct(annotation_type) "
				+ "from annotated_genome_feature "
				+ "where organism_id = ?";
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, org.getId());
			ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				String name = rset.getString(1);
				AnnotationType type = AnnotationType.valueOf(name);
				types.add(type);
			}
		} catch (SQLException e) {
			throw new WebcghSystemException(
					"Error retrieving available annotation types", e);
		}
		return types;
	}
}
