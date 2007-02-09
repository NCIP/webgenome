/*
$Revision: 1.7 $
$Date: 2007-02-09 20:43:51 $

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

package org.rti.webcgh.domain;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * An annotated genome feature.
 * @author dhall
 *
 */
public class AnnotatedGenomeFeature extends GenomeInterval {
	
	
	// ===================================
	//        Attributes
	// ===================================
	
	/** ID used as primary key for persistence. */
	private Long id = null;
	
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
	 * Get primary key ID used for persistence.
	 * @return ID
	 */
	public final Long getId() {
		return id;
	}


	/**
	 * Set primary key ID used for persistence.
	 * @param id ID
	 */
	public final void setId(final Long id) {
		this.id = id;
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
