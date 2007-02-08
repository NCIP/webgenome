/*
$Revision: 1.2 $
$Date: 2007-02-08 22:41:47 $

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

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.AnnotatedGenomeFeatureDao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>AnnotatedGenomeFeatureDao</code> using
 * Hibernate.
 * @author dhall
 *
 */
public class HibernateAnnotatedGenomeFeatureDao
extends HibernateDaoSupport implements AnnotatedGenomeFeatureDao {

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
	public final void deleteAll(final AnnotationType annotationType,
			final Organism organism) {
		
		// Get features
		String query =
			"from AnnotatedGenomeFeature f "
			+ "where f.annotationType = ? and f.organism = ?";
		Object[] args = new Object[] {annotationType.toString(),
				organism};
		List feats = this.getHibernateTemplate().find(query, args);
		
		// Delete features
		this.getHibernateTemplate().deleteAll(feats);
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
}
