/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.dao;

import java.util.Set;
import java.util.SortedSet;

import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.Organism;

/**
 * Data access class for <code>AnnotatedGenomeFeature</code>.
 * @author dhall
 *
 */
public interface AnnotatedGenomeFeatureDao {

	/**
	 * Persist given feature.
	 * @param feature Feature to persist
	 */
	void save(AnnotatedGenomeFeature feature);
	
	/**
	 * Un-persist all features of given type and organism.
	 * @param annotationType Feature type
	 * @param organism Organism
	 */
	void deleteAll(AnnotationType annotationType, Organism organism);
	
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
	SortedSet<AnnotatedGenomeFeature> load(
			short chromosome, long startPos, long endPos,
			AnnotationType annotationType, Organism organism);
	
	/**
	 * Return all organisms who have gene data loaded in
	 * database.
	 * @return All organisms who have gene data loaded in
	 * database
	 */
	Set<Organism> organismsWithLoadedGenes();
	
	/**
	 * Get all annotation types with data for given organism.
	 * @param org An organism
	 * @return Annotation types
	 */
	Set<AnnotationType> availableAnnotationTypes(Organism org);
}
