/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2007-12-17 17:42:59 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.rti.webgenome.util.SystemUtils;

/**
 * An annotated genome feature.
 * @author dhall
 *
 */
public class AnnotatedGenomeFeature extends GenomeInterval
implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	
	// ===================================
	//        Attributes
	// ===================================
	
	/** Display name of feature. */
	private String name = null;
	
	/** Annotation type. */
	private AnnotationType annotationType;
	
	/**
	 * Some sort of quantitation.  The meaning of this
	 * field will vary with the annotation type.
	 * Clients should know how to interpret this.
	 */
	private float quantitation = Float.NaN;
	
	/** Child annotation features. */
	private SortedSet<AnnotatedGenomeFeature> childFeatures =
		new TreeSet<AnnotatedGenomeFeature>();
	
	/** Organism. */
	private Organism organism;
	
	
	// ===================================
	//         Getters/setters
	// ===================================
	
	/**
	 * Get quantitation.  The meaning of this
	 * field will vary with the annotation type.
	 * Clients should know how to interpret this.
	 * @return Quantitation
	 */
	public final float getQuantitation() {
		return quantitation;
	}


	/**
	 * Set quantitation.  The meaning of this
	 * field will vary with the annotation type.
	 * Clients should know how to interpret this.
	 * @param quantitation Quantitation
	 */
	public final void setQuantitation(final float quantitation) {
		this.quantitation = quantitation;
	}


	/**
	 * Get display name of feature.
	 * @return Display name
	 */
	public final String getName() {
		return name;
	}


	/**
	 * Set display name of feature.
	 * @param name Display name
	 */
	public final void setName(final String name) {
		this.name = name;
	}


	/**
	 * Get organism.
	 * @return Organism
	 */
	public final Organism getOrganism() {
		return organism;
	}


	/**
	 * Set organism.
	 * @param organism Organism
	 */
	public final void setOrganism(final Organism organism) {
		this.organism = organism;
	}


	/**
	 * Get child annotation features.
	 * @return Child annotation features
	 */
	public final SortedSet<AnnotatedGenomeFeature> getChildFeatures() {
		return childFeatures;
	}


	/**
	 * Set child annotation features.
	 * @param childFeatures Child annotation features
	 */
	public final void setChildFeatures(
			final SortedSet<AnnotatedGenomeFeature> childFeatures) {
		this.childFeatures = childFeatures;
	}


	/**
	 * Set annotation tpye.
	 * @param annotationType Annotation type
	 */
	public final void setAnnotationType(
			final AnnotationType annotationType) {
		this.annotationType = annotationType;
	}


	/**
	 * Get annotation type.
	 * @return Annotation type
	 */
	public final AnnotationType getAnnotationType() {
		return annotationType;
	}
	
	
	// ========================================
	//       Constructors
	// ========================================
	
	
	/**
	 * Constructor.
	 */
	public AnnotatedGenomeFeature() {
		
	}
	
	/**
	 * Constructor.
	 * @param annotationType Annotation type
	 */
	public AnnotatedGenomeFeature(final AnnotationType annotationType) {
		this.annotationType = annotationType;
	}
	
	
	/**
	 * Constructor.
	 * @param chromosome Chromosome number
	 * @param startLocation Chromosomal start location of interval
	 * in base pairs
	 * @param endLocation Chromosomal end location of interval
	 * in base pairs
	 * @param annotationType Annotation type
	 */
	public AnnotatedGenomeFeature(final short chromosome,
			final long startLocation, final long endLocation,
			final AnnotationType annotationType) {
		super(chromosome, startLocation, endLocation);
		this.annotationType = annotationType;
	}


	/**
	 * Constructor.
	 * @param chromosome Chromosome number
	 * @param startLocation Chromosomal start location of interval
	 * in base pairs
	 * @param endLocation Chromosomal end location of interval
	 * in base pairs
	 * @param annotationType Annotation type
	 * @param featureName Name of feature
	 */
	public AnnotatedGenomeFeature(final short chromosome,
			final long startLocation, final long endLocation,
			final AnnotationType annotationType,
			final String featureName) {
		this(chromosome, startLocation, endLocation, annotationType);
		this.name = featureName;
	}

	
	/**
	 * Constructor.
	 * @param chromosome Chromosome number
	 * @param startLocation Chromosomal start location of interval
	 * in base pairs
	 * @param endLocation Chromosomal end location of interval
	 * in base pairs
	 * @param annotationType Annotation type
	 * @param quantitation Quantitation.  The meaning of this
	 * field will vary with the annotation type.
	 * Clients should know how to interpret this.
	 */
	public AnnotatedGenomeFeature(final short chromosome,
			final long startLocation, final long endLocation,
			final AnnotationType annotationType,
			final float quantitation) {
		this(chromosome, startLocation, endLocation, annotationType);
		this.quantitation = quantitation;
	}
	
	
	// ========================================
	//      Business methods
	// ========================================
	
	/**
	 * Weighted average quantitation.  Weights are based on length.
	 * @param f1 First feature
	 * @param f2 Second feature
	 * @return Weighted average
	 */
	public static float weightedAverage(final AnnotatedGenomeFeature f1,
			final AnnotatedGenomeFeature f2) {
		long totalLength = f1.length() + f2.length();
		double w1 = (double) f1.length() / (double) totalLength;
		double w2 = (double) f2.length() / (double) totalLength;
		return f1.quantitation * (float) w1 + f2.quantitation * (float) w2;
	}
	
	
	/**
	 * Add child feature.
	 * @param child Child feature
	 */
	public final void addChild(final AnnotatedGenomeFeature child) {
		this.childFeatures.add(child);
	}
	
	
	/**
	 * Add child features.
	 * @param children Child features
	 */
	public final void addChildren(
			final List<AnnotatedGenomeFeature> children) {
		this.childFeatures.addAll(children);
	}
		
	
	// ================================
	//      Overrides
	// ================================
	
	/**
	 * Find intersection of given features.
	 * @param feat1 First feature.
	 * @param feat2 Second feature.
	 * @return Intersection or null if the features do
	 * not overlap.  The <code>quantitation</code>
	 * value will be the average of the two
	 * input features.
	 */
	public static AnnotatedGenomeFeature intersection(
			final AnnotatedGenomeFeature feat1,
			final AnnotatedGenomeFeature feat2) {
		if (feat1.annotationType != feat2.annotationType) {
			throw new IllegalArgumentException(
					"Features have different alteration types");
		}
		AnnotatedGenomeFeature newIval = null;
		GenomeInterval ival = GenomeInterval.intersection(feat1, feat2);
		if (ival != null) {
			float mean = Float.NaN;
			if (!Float.isNaN(feat1.quantitation)
					&& !Float.isNaN(feat2.quantitation)) {
				mean = (feat1.quantitation + feat2.quantitation) / (float) 2.0;
			}
			newIval = new AnnotatedGenomeFeature(ival.getChromosome(),
					ival.getStartLocation(), ival.getEndLocation(),
					feat1.annotationType, mean);
		}
		return newIval;
	}
}
