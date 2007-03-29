/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:31 $

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

import java.util.Set;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.hibernate.HibernateAnnotatedGenomeFeatureDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * Tester for <code>HibernateAnnotatedGenomeFeatureDao</code>.
 * @author dhall
 *
 */
public final class HibernateAnnotatedGenomeFeatureDaoTester
extends TestCase {

	
	/**
	 * Test all methods.
	 */
	public void testAllMethods() {
		
		// Get DAO bean
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
        "org/rti/webcgh/service/dao/hibernate/unit_test/beans.xml");
		HibernateAnnotatedGenomeFeatureDao dao =
			(HibernateAnnotatedGenomeFeatureDao)
			ctx.getBean("annotatedGenomeFeatureDao");
		
		// Create test object
		Organism org = new Organism("genus", "species");
		AnnotatedGenomeFeature feat = new AnnotatedGenomeFeature();
		feat.setAnnotationType(AnnotationType.GENE);
		feat.setChromosome((short) 1);
		feat.setStartLocation(1);
		feat.setEndLocation(100);
		feat.setQuantitation((float) 1.0);
		feat.setOrganism(org);
		
		AnnotatedGenomeFeature childFeat = new AnnotatedGenomeFeature();
		childFeat.setAnnotationType(AnnotationType.EXON);
		childFeat.setChromosome((short) 1);
		childFeat.setStartLocation(1);
		childFeat.setEndLocation(100);
		childFeat.setQuantitation((float) 1.0);
		childFeat.setOrganism(org);
		feat.addChild(childFeat);
		
		// Save
		dao.save(feat);
		
		// Load
		SortedSet<AnnotatedGenomeFeature> feats =
			dao.load((short) 1, (long) 1, (long) 100,
					AnnotationType.GENE, org);
		assertNotNull(feats);
		assertEquals(1, feats.size());
		AnnotatedGenomeFeature feat2 = feats.first();
		assertEquals((short) 1, feat2.getChromosome());
		assertEquals(1, feat2.getChildFeatures().size());
		feats = dao.load((short) 1, (long) 101, (long) 1000,
				AnnotationType.GENE, org);
		assertNotNull(feats);
		assertEquals(0, feats.size());
		
		// Query organisms
//		Set<Organism> orgs = dao.organismsWithLoadedGenes();
//		assertNotNull(orgs);
//		assertEquals(1, orgs.size());
//		assertEquals("genus", orgs.iterator().next().getGenus());
		
		// Query feature types
		Set<AnnotationType> types = dao.availableAnnotationTypes(org);
		assertNotNull(types);
		assertEquals(2, types.size());
		
		// Delete
		dao.deleteAll(AnnotationType.GENE, org);
		feats =
			dao.load((short) 1, (long) 1, (long) 100,
					AnnotationType.GENE, org);
		assertNotNull(feats);
		assertEquals(0, feats.size());
	}
}
