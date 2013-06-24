/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.4 $
$Date: 2007-12-05 21:02:29 $


*/

package org.rti.webgenome.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.AnnotatedGenomeFeature;
import org.rti.webgenome.domain.AnnotationType;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.QuantitationType;
import org.rti.webgenome.util.ValidationUtils;

/**
 * Find minimum common altered genome regions.  If data are
 * LOH, then these are the minimum common LOH segments.
 * If the data are copy number, then the minimum common
 * amplified and deleted regions are identified.
 * Each of these regions defines a genome interval
 * of maximum size that is altered
 * in >= <code>minPercent</code>% bioassays.
 * @author dhall
 *
 */
public final class MinimumCommonAlteredRegionOperation
implements MultiExperimentStatelessOperation {
	
	/**
	 * Default minimum percent of bioassays that must be altered
	 * for an interval to be included.
	 */
	private static final float DEF_MIN_PERCENT = (float) 50;
	
	/**
	 * Default threshold for determining if a datum is in a
	 * region of LOH.
	 */
	private static final float DEF_LOH_THRESHOLD = (float) 0.5;
	
	/**
	 * Default threshold for determining if a datum is in a
	 * region of amplification.
	 */
	private static final float DEF_AMPLIFICATION_THRESHOLD = (float) 2.0;
	
	/**
	 * Default threshold for determining if a datum is in a
	 * region of deletion.
	 */
	private static final float DEF_DELETION_THREHSOLD = (float) 0.5;
	
	/**
	 * Minimum percent of bioassays that must be altered
	 * for an interval to be included.
	 */
	private float minPercent = DEF_MIN_PERCENT;
	
	/**
	 * Threshold for determining if a datum is in a
	 * region of LOH.
	 */
	private float lohThreshold = DEF_LOH_THRESHOLD;
	
	/**
	 * Threshold for determining if a datum is in a
	 * region of amplification.
	 */
	private float amplificationThreshold = DEF_AMPLIFICATION_THRESHOLD;
	
	
	/**
	 * Threshold for determining if a datum is in a
	 * region of deletion.
	 */
	private float deletionThreshold = DEF_DELETION_THREHSOLD;
	
	/**
	 * If <code>false</code>, then
     * segment endpoints are set to be the outermost reporter locations
     * within a segment.  If <code>true</code>, then endpoints
     * are interpolated distally to the points where linearly descending
     * lines drawn to the next data point cross the threshold.
	 */
	private boolean interpolate = false;
	
	/** Quantitation type. */
	private QuantitationType quantitationType;
	
	
	/**
	 * Set quantitation type.
	 * @param quantitationType Quantitation type
	 */
    public void setQuantitationType(final QuantitationType quantitationType) {
		this.quantitationType = quantitationType;
	}


	/**
     * Perform operation.
     * @param input Input data
     * @return Output data
     * @throws AnalyticException if an error occurs
     * during this operation
     */
	public List<ChromosomeArrayData> perform(
			final List<ChromosomeArrayData> input) throws AnalyticException {
		
		// Make sure user configurable parameters okay
		ParameterErrorMessageGenerator gen =
			new ParameterErrorMessageGenerator();
		if (!ValidationUtils.inRange(this.minPercent, (float) 0.0,
				(float) 100.0)) {
			gen.addInvalidParameterName("minPercent",
					String.valueOf(this.minPercent));
		}
		if (Float.isNaN(this.lohThreshold)) {
			gen.addInvalidParameterName("lohThreshold",
					String.valueOf(this.lohThreshold));
		}
		if (Float.isNaN(this.amplificationThreshold)) {
			gen.addInvalidParameterName("amplificationThreshold",
					String.valueOf(this.amplificationThreshold));
		}
		if (Float.isNaN(this.deletionThreshold)) {
			gen.addInvalidParameterName("deletionThreshold",
					String.valueOf(this.deletionThreshold));
		}
		if (gen.invalidParameters()) {
			throw new AnalyticException(gen.getMessage());
		}
		
		// Find minimum common alterations
		List<ChromosomeArrayData> output =
			new ArrayList<ChromosomeArrayData>();
		if (input.size() > 0) {
			short chromosome = input.get(0).getChromosome();
			if (this.quantitationType == QuantitationType.LOH) {
				List<AnnotatedGenomeFeature> alts =
					this.minimumCommonAlterations(input,
							AnnotationType.LOH_SEGMENT,
							this.lohThreshold);
				ChromosomeArrayData cad =
					new ChromosomeArrayData(chromosome);
				cad.setChromosomeAlterations(alts);
				cad.setAlterationType(AnnotationType.LOH_SEGMENT);
				output.add(cad);
			} else {
				List<AnnotatedGenomeFeature> alts =
					this.minimumCommonAlterations(input,
							AnnotationType.AMPLIFIED_SEGMENT,
							this.amplificationThreshold);
				ChromosomeArrayData cad =
					new ChromosomeArrayData(chromosome);
				cad.setChromosomeAlterations(alts);
				cad.setAlterationType(AnnotationType.AMPLIFIED_SEGMENT);
				output.add(cad);
				alts = this.minimumCommonAlterations(input,
							AnnotationType.DELETED_SEGMENT,
							this.deletionThreshold);
				cad = new ChromosomeArrayData(chromosome);
				cad.setAlterationType(AnnotationType.DELETED_SEGMENT);
				cad.setChromosomeAlterations(alts);
				output.add(cad);
			}
		}
		return output;
	}
	
	
	/**
	 * Find minimum common alteration set.
	 * @param cads Chromosome array data
	 * @param altType Alteration type
	 * @param threshold Alteration threshold value
	 * @return Minimum common alterations
	 */
	private List<AnnotatedGenomeFeature> minimumCommonAlterations(
			final Collection<ChromosomeArrayData> cads,
			final AnnotationType altType,
			final float threshold) {
		List<AnnotatedGenomeFeature> alts =
			this.accumulateAlterations(cads, altType, threshold);
		List<AnnotatedGenomeFeature> isecs =
			this.findAllIntersections(alts);
		int min = (int) Math.ceil((double) cads.size()
				* this.minPercent / 100.0);
		this.weed(isecs, alts, min);
		return this.unionAll(isecs);
	}
	
	
	/**
	 * Accumulate all alterations in given chromosome array data.
	 * @param cads Chromosome array data
	 * @param altType Alteration type
	 * @param threshold Alteration threshold
	 * @return All alterations
	 */
	private List<AnnotatedGenomeFeature> accumulateAlterations(
			final Collection<ChromosomeArrayData> cads,
			final AnnotationType altType, final float threshold) {
		List<AnnotatedGenomeFeature> alts =
			new ArrayList<AnnotatedGenomeFeature>();
		for (ChromosomeArrayData cad : cads) {
			Iterator<AnnotatedGenomeFeature>it =
				cad.alteredSegmentIterator(threshold,
						this.interpolate, altType);
			while (it.hasNext()) {
				AnnotatedGenomeFeature f = it.next();
				alts.add(f);
			}
		}
		Collections.sort(alts, new GenomeIntervalComparator());
		return alts;
	}
	
	
	/**
	 * Find all pairwise intersections of given altered segments.
	 * @param alts Altered segments.
	 * @return All pairwise intersections.
	 */
	private List<AnnotatedGenomeFeature> findAllIntersections(
			final List<AnnotatedGenomeFeature> alts) {
		List<AnnotatedGenomeFeature> intersections =
			new ArrayList<AnnotatedGenomeFeature>();
		for (AnnotatedGenomeFeature f1 : alts) {
			for (AnnotatedGenomeFeature f2 : alts) {
				if (f1 != f2) {
					if (f1.leftOf(f2)) {
						break;
					}
					AnnotatedGenomeFeature intersection =
						AnnotatedGenomeFeature.intersection(f1, f2);
					if (intersection != null) {
						intersections.add(intersection);
					}
				}
			}
		}
		Collections.sort(intersections, new GenomeIntervalComparator());
		return intersections;
	}
	
	
	/**
	 * Weed out intersections that are not contained in
	 * <code>min</code> number of intervals.
	 * @param intersections Intersections
	 * @param intervals Full altered intervals
	 * @param min Minimum number of intervals an intersection
	 * has be be contained in to remain in set.
	 */
	private void weed(final List<AnnotatedGenomeFeature> intersections,
			final List<AnnotatedGenomeFeature> intervals,
			final int min) {
		Iterator<AnnotatedGenomeFeature> it = intersections.iterator();
		while (it.hasNext()) {
			int count = 0;
			AnnotatedGenomeFeature isect = it.next();
			for (AnnotatedGenomeFeature feat : intervals) {
				if (isect.leftOf(feat)) {
					break;
				}
				if (feat.contains(isect)) {
					count++;
				}
			}
			if (count < min) {
				it.remove();
			}
		}
	}
	
	
	/**
	 * Union all overlapping intersections.
	 * @param feats Features
	 * @return Unioned features
	 */
	private List<AnnotatedGenomeFeature> unionAll(
			final List<AnnotatedGenomeFeature> feats) {
		List<AnnotatedGenomeFeature> newSet =
			new ArrayList<AnnotatedGenomeFeature>();
		while (feats.size() > 0) {
			Iterator<AnnotatedGenomeFeature> it =
				feats.iterator();
			if (it.hasNext()) {
				AnnotatedGenomeFeature f1 = it.next();
				newSet.add(f1);
				it.remove();
				while (it.hasNext()) {
					AnnotatedGenomeFeature f2 = it.next();
					if (f1.overlaps(f2)) {
						float avg = AnnotatedGenomeFeature.
							weightedAverage(f1, f2);
						f1.merge(f2);
						f1.setQuantitation(avg);
						it.remove();
					}
				}
			}
		}
		return newSet;
	}

	
    /**
     * Get name of operation.
     * @return Name of operation
     */
	public String getName() {
		return "Minimimum common alterations";
	}

	
	/**
     * {@inheritDoc}
     */
    public List<UserConfigurableProperty> getUserConfigurableProperties(
    		final Collection<QuantitationType> qTypes) {
    	
    	// Properties common to all quantitation types
		List<UserConfigurableProperty> props =
			new ArrayList<UserConfigurableProperty>();
		props.add(new SimpleUserConfigurableProperty("minPercent",
				"Minimum percent of altered bioassays (0% - 100%)",
				String.valueOf(this.minPercent)));
		String interpolateStr = "NO";
		if (this.interpolate) {
			interpolateStr = "YES";
		}
		UserConfigurablePropertyWithOptions opts =
			new UserConfigurablePropertyWithOptions("interpolate",
					"Interpolate alteration endpoints", interpolateStr);
		opts.addOption("0", "NO");
		opts.addOption("1", "YES");
		props.add(opts);
		
		// LOH properties
		for (QuantitationType qType : qTypes) {
			if (qType == QuantitationType.LOH) {
				props.add(new SimpleUserConfigurableProperty("lohThreshold",
						"LOH threshold", String.valueOf(this.lohThreshold)));
			} else {
				props.add(new SimpleUserConfigurableProperty(
						"amplificationThreshold", "Amplification threshold",
						String.valueOf(this.amplificationThreshold)));
				props.add(new SimpleUserConfigurableProperty(
						"deletionThreshold",
						"Deletion threshold", String.valueOf(
								this.deletionThreshold)));
			}
		}
		return props;
	}

	
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     * @throws BadUserConfigurablePropertyException if value is invalid.
     */
	public void setProperty(final String name,
			final String value) throws BadUserConfigurablePropertyException {
		if ("minPercent".equals(name)) {
			try {
				this.minPercent = Float.parseFloat(value);
				if (this.minPercent < (float) 0.0
						|| this.minPercent > (float) 100.0) {
					throw new BadUserConfigurablePropertyException(
						"Minimum percent must be between 0 and 100, inclusive");
				}
			} catch (NumberFormatException e) {
				throw new BadUserConfigurablePropertyException(
						"Minimum percent not valid number");
			}
		} else if ("threshold".equals(name)) {
			try {
				this.lohThreshold = Float.parseFloat(value);
			} catch (NumberFormatException e) {
				throw new BadUserConfigurablePropertyException(
						"LOH threshold not valid number");
			}
		} else if ("amplificationThreshold".equals(name)) {
			try {
				this.amplificationThreshold = Float.parseFloat(value);
			} catch (NumberFormatException e) {
				throw new BadUserConfigurablePropertyException(
						"Amplification threshold not valid number");
			}
		} else if ("deletionThreshold".equals(name)) {
			try {
				this.deletionThreshold = Float.parseFloat(value);
			} catch (NumberFormatException e) {
				throw new BadUserConfigurablePropertyException(
						"Deletion threshold not valid number");
			}
		} else if ("interpolate".equals(name)) {
			if ("YES".equals(value)) {
				this.interpolate = true;
			} else if ("NO".equals(value)) {
				this.interpolate = false;
			}
		}
	}
	
	
	/**
     * Generate a name from given chromosome array data.
     * @param cad Chromosome array data
     * @return Name
     */
    public String getName(final ChromosomeArrayData cad) {
    	String name = "";
		AnnotationType type = cad.getAlterationType();
		if (type == AnnotationType.AMPLIFIED_SEGMENT) {
			name = "MCAR";
		} else if (type == AnnotationType.DELETED_SEGMENT) {
			name = "MCDR";
		} else if (type == AnnotationType.LOH_SEGMENT) {
			name = "MCLOHR";
		}
    	return name;
    }
	
	
    /**
     * Determine the number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     * @param experiments Some experiments
     * @return The number of bioassays that would result
     * from a proper running of the given experiments through
     * this operation.
     */
    public int numResultingBioAssays(
    		final Collection<Experiment> experiments) {
    	int num = -1;
    	QuantitationType qtype = Experiment.getCopyNumberQuantitationType(
    			experiments);
    	if (qtype == QuantitationType.COPY_NUMBER
    			|| qtype == QuantitationType.LOG_2_RATIO_COPY_NUMBER
    			|| qtype == QuantitationType.FOLD_CHANGE
    			|| qtype == QuantitationType.LOG_2_RATIO_FOLD_CHANGE) {
    		num = 2;
    	} else if (qtype == QuantitationType.LOH) {
    		num = 1;
    	} else {
    		throw new WebGenomeSystemException(
    				"minimum common alteration operation "
    				+ "not configured for quantitation type '"
    				+ qtype + "'");
    	}
    	return num;
    }

	// =================================
	//     Helper classes
	// =================================
	
	/**
	 * Comparator used to order genome intervals
	 * during identification of common altered regions.
	 */
	static class GenomeIntervalComparator
	implements Comparator<GenomeInterval> {

		/**
		 * Comparason method.
		 * @param gi1 First genome interval
		 * @param gi2 Second genome interval
		 * @return -1 if <code>gi1</code> to left of
		 * <code>gi2</code>, 0 if equivalent, and 1 if
		 * to right.  If the left endpoint of an interval
		 * is the the left of the other, or if the left
		 * endpoints are equal and the right endpoint is
		 * to the left, then the first interval is considered
		 * to the left.  If both endpoints are the same,
		 * the intervals are equivalent.  Being to the right
		 * is symmetrical with being to the left.
		 */
		public int compare(final GenomeInterval gi1,
				final GenomeInterval gi2) {
			int rval = 0;
			long s1 = gi1.getStartLocation();
			long e1 = gi1.getEndLocation();
			long s2 = gi2.getStartLocation();
			long e2 = gi2.getEndLocation();
			if (s1 < s2) {
				rval = -1;
			} else if (s1 == s2) {
				if (e1 < e2) {
					rval = -1;
				} else if (e1 == e2) {
					rval = 0;
				} else if (e1 > e2) {
					rval = 1;
				}
			} else if (s1 > s2) {
				rval = 1;
			}
			return rval;
		}
		
	}
}
