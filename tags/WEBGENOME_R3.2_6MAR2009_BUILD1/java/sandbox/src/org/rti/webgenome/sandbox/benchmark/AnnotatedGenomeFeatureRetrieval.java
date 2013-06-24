/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.sandbox.benchmark;

import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.AnnotatedGenomeFeatureDao;
import org.rti.webgenome.service.dao.OrganismDao;
import org.rti.webgenome.util.StopWatch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class for benchmarking the retrieval of annotated genome features
 * from the database.
 * @author dhall
 *
 */
public final class AnnotatedGenomeFeatureRetrieval {
	
	//
	//     STATICS
	//
	
	/** Chromosome number. */
	private static final short CHROMOSOME = (short) 1;
	
	/** Chromosome start position. */
	private static final long START_POS = 50000000;
	
	/** Chromosome end position. */
	private static final long END_POS = 60000000;
	
	/** Annotation type that is queried for in benchmark. */
	private static final AnnotationType ANNOTATION_TYPE =
		AnnotationType.GENE;
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 *
	 */
	private AnnotatedGenomeFeatureRetrieval() {
		
	}

	//
	//     MAIN METHOD
	//
	
	/**
	 * Main methods.
	 * @param args Command line arguments
	 */
	public static void main(final String[] args) {
		
		// Get DAO beans
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"test/benchmark/applicationContext.xml");
		OrganismDao oDao = (OrganismDao) ctx.getBean("organismDao");
		AnnotatedGenomeFeatureDao hDao = (AnnotatedGenomeFeatureDao)
			ctx.getBean("hibernateAnnotatedGenomeFeatureDao");
		
		// Get default organism
		Organism organism = oDao.loadDefault();
		
		StopWatch stopWatch = new StopWatch();
		SortedSet<AnnotatedGenomeFeature> feats = null;
		
		// Perform benchmark query using hibernate
		System.out.println("Querying via Hibernate for features of type '"
				+ ANNOTATION_TYPE.toString()
				+ "' over genome interval '" + CHROMOSOME + ":"
				+ START_POS + "-" + END_POS + "'");
		stopWatch.start();
		feats =
			hDao.load(CHROMOSOME, START_POS, END_POS, ANNOTATION_TYPE,
					organism);
		stopWatch.stop();
		System.out.println("Finished.  Elapsed time: "
				+ stopWatch.getFormattedElapsedTime());
		System.out.println(feats.size() + " features retrieved");
		if (feats.size() > 0) {
			AnnotatedGenomeFeature feat = feats.first();
			System.out.println("Feature '" + feat.getId() + "' has "
					+ feat.getChildFeatures().size() + " child features");
		}
	}
}
