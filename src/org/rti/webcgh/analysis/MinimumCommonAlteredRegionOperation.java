/*
$Revision: 1.4 $
$Date: 2006-10-29 17:16:12 $

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

package org.rti.webcgh.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.rti.webcgh.domain.AnnotatedGenomeFeature;
import org.rti.webcgh.domain.AnnotationType;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.util.ValidationUtils;

/**
 * Find minimum common altered genome regions.  If data are
 * LOH, then these are the minimum common LOH segments.
 * If the data are copy number, then the minimum common
 * amplified and deleted regions are identified.
 * regions.  Each of these regions defines a genome interval
 * of maximum size that is altered
 * in >= <code>minPercent</code>% bioassays.
 * @author dhall
 *
 */
public final class MinimumCommonAlteredRegionOperation
implements MultiExperimentToNonArrayDataAnalyticOperation {
	
	/**
	 * Default minimum percent of bioassays that must be altered
	 * for an interval to be included.
	 */
	private static final float DEF_MIN_PERCENT = (float) 0.5;
	
	/**
	 * Default threshold for determining if a datum is in an
	 * altered genome region.
	 */
	private static final float DEF_THRESHOLD = (float) 0.5;
	
	/**
	 * Minimum percent of bioassays that must be altered
	 * for an interval to be included.
	 */
	private float minPercent = DEF_MIN_PERCENT;
	
	/**
	 * Threshold for determining if a datum is in an
	 * altered genome region.
	 */
	private float threshold = DEF_THRESHOLD;
	
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
				(float) 1.0)) {
			gen.addInvalidParameterName("minPercent",
					String.valueOf(this.minPercent));
		}
		if (Float.isNaN(this.threshold)) {
			gen.addInvalidParameterName("threshold",
					String.valueOf(this.threshold));
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
							AnnotationType.LOH_SEGMENT);
				ChromosomeArrayData cad =
					new ChromosomeArrayData(chromosome);
				cad.setChromosomeAlterations(alts);
				output.add(cad);
			} else {
				List<AnnotatedGenomeFeature> alts =
					this.minimumCommonAlterations(input,
							AnnotationType.AMPLIFIED_SEGMENT);
				ChromosomeArrayData cad =
					new ChromosomeArrayData(chromosome);
				cad.setChromosomeAlterations(alts);
				output.add(cad);
				alts = this.minimumCommonAlterations(input,
							AnnotationType.DELETED_SEGMENT);
				cad = new ChromosomeArrayData(chromosome);
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
	 * @return Minimum common alterations
	 */
	private List<AnnotatedGenomeFeature> minimumCommonAlterations(
			final Collection<ChromosomeArrayData> cads,
			final AnnotationType altType) {
		List<AnnotatedGenomeFeature> alts =
			this.accumulateAlterations(cads, altType);
		List<AnnotatedGenomeFeature> isecs =
			this.findAllIntersections(alts);
		int min = (int) Math.ceil((double) cads.size() * this.minPercent);
		this.weed(isecs, alts, min);
		return this.unionAll(isecs);
	}
	
	
	/**
	 * Accumulate all alterations in given chromosome array data.
	 * @param cads Chromosome array data
	 * @param altType Alteration type
	 * @return All alterations
	 */
	private List<AnnotatedGenomeFeature> accumulateAlterations(
			final Collection<ChromosomeArrayData> cads,
			final AnnotationType altType) {
		List<AnnotatedGenomeFeature> alts =
			new ArrayList<AnnotatedGenomeFeature>();
		for (ChromosomeArrayData cad : cads) {
			Iterator<AnnotatedGenomeFeature>it =
				cad.alteredSegmentIterator(this.threshold,
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
						f1.merge(f2);
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
     * Get user configurable properties.
     * @return User configurable properties
     */
	public List<UserConfigurableProperty> getUserConfigurableProperties() {
		List<UserConfigurableProperty> props =
			new ArrayList<UserConfigurableProperty>();
		props.add(new SimpleUserConfigurableProperty("minPercent",
				"Minimum percent of altered bioassays",
				String.valueOf(this.minPercent)));
		props.add(new SimpleUserConfigurableProperty("threshold",
				"Threshold", String.valueOf(this.threshold)));
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
		return props;
	}

	
    /**
     * Set some property of the operation.  The name of this
     * property should correspond to one of user configurable
     * property names.
     * @param name Name of property to set.
     * @param value Value of property.
     */
	public void setProperty(final String name,
			final String value) {
		if ("minPercent".equals(name)) {
			this.minPercent = Float.parseFloat(value);
		} else if ("threshold".equals(name)) {
			this.threshold = Float.parseFloat(value);
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
    	List<AnnotatedGenomeFeature> feats = cad.getChromosomeAlterations();
    	if (feats != null && feats.size() > 0) {
    		AnnotatedGenomeFeature feat = feats.get(0);
    		AnnotationType type = feat.getAnnotationType();
    		if (type == AnnotationType.AMPLIFIED_SEGMENT) {
    			name = "MCAR";
    		} else if (type == AnnotationType.DELETED_SEGMENT) {
    			name = "MCDR";
    		} else if (type == AnnotationType.LOH_SEGMENT) {
    			name = "MCLOHR";
    		}
    	}
    	return name;
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
