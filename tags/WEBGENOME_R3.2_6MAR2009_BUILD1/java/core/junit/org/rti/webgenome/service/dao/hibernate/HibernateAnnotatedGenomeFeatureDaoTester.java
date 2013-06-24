/*
$Revision: 1.2 $
$Date: 2007-04-13 02:52:12 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.util.Set;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Organism;
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
        "org/rti/webgenome/service/dao/hibernate/beans.xml");
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
