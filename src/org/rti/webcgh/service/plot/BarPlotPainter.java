/*
$Revision: 1.1 $
$Date: 2007-02-05 18:16:30 $

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

package org.rti.webcgh.service.plot;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.rti.webcgh.domain.ArrayDatum;
import org.rti.webcgh.domain.BioAssay;
import org.rti.webcgh.domain.ChromosomeArrayData;
import org.rti.webcgh.domain.Experiment;
import org.rti.webcgh.domain.Reporter;
import org.rti.webcgh.graphics.widget.Bar;
import org.rti.webcgh.graphics.widget.Caption;
import org.rti.webcgh.graphics.widget.PlotPanel;
import org.rti.webcgh.service.util.ChromosomeArrayDataGetter;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.units.VerticalAlignment;


/**
 * Facade class that has the ultimate responsibility
 * for creating a bar plot.
 * @author dhall
 *
 */
public class BarPlotPainter extends PlotPainter {
	
	//
	//     STATICS
	//
	
	/** Padding between graphical elements in pixels. */
	private static final int PADDING = 30;
	
	/**
	 * A padding multiplier used to calculate the padding in pixels
	 * between the top of the highest bar and the top of the plot.
	 */
	private static final float BIOASSAY_LABEL_PADDING_MULTIPLIER =
		(float) 0.1;
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Paint a bar plot on given plot panel.  Only selected
	 * reporters will be represented in the plot.
	 * @see org.rti.webcgh.domain.Reporter#isSelected()
	 * @param panel Panel on which to paint
	 * @param experiments Experiments
	 * @param params Plot parameters
	 */
	public final void paintPlot(final PlotPanel panel,
			final Collection<Experiment> experiments,
			final BarPlotParameters params) {
		
		// Make sure arguments okay
		if (panel == null) {
			throw new IllegalArgumentException("Plotting panel is null");
		}
		if (experiments == null) {
			throw new IllegalArgumentException("Experiments is null");
		}
		if (params == null) {
			throw new IllegalArgumentException("Plot parameters are null");
		}
		CommonArrayDatumGroupIterator it =
			new CommonArrayDatumGroupIterator(experiments);
		MultiPlotGridLayouter layouter = new MultiPlotGridLayouter(
				params.getMaxNumCols(), panel);
		layouter.setPadding(PADDING);
		while (it.hasNext()) {
			CommonArrayDatumGroup group = it.next();
			Collections.sort(group.tuples);
			PlotPanel column = panel.newChildPlotPanel();
			for (BioAssayNameArrayDatumTuple tuple : group.tuples) {
				ArrayDatum ad = tuple.arrayDatum;
				Bar bar = new Bar(ad.getValue(), ad.getError(),
						tuple.bioAssayName,
						this.withHeadroom(group.maxValue()),
						(float) 10.0,
						panel.getDrawingCanvas());
				column.add(bar, HorizontalAlignment.RIGHT_OF,
						VerticalAlignment.TOP_JUSTIFIED);
			}
			Caption caption = new Caption(group.reporterName,
					Orientation.HORIZONTAL, false,
					column.getDrawingCanvas());
			column.add(caption, HorizontalAlignment.CENTERED,
					VerticalAlignment.BELOW);
			layouter.add(column, HorizontalAlignment.RIGHT_OF,
					VerticalAlignment.TOP_JUSTIFIED);
		}
		layouter.flush();
	}
	
	
	/**
	 * Add "headroom" to value by return a value slightly
	 * larger.  This method is used to provide some padding
	 * between the highest bar and the bioassay labels.
	 * @param value A value
	 * @return A slightly larger value
	 */
	private float withHeadroom(final float value) {
		return value + value * BIOASSAY_LABEL_PADDING_MULTIPLIER;
	}
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 * @param chromosomeArrayDataGetter Chromosome
	 * array data getter
	 */
	public BarPlotPainter(final ChromosomeArrayDataGetter
			chromosomeArrayDataGetter) {
		super(chromosomeArrayDataGetter);
	}
	
	//
	//     HELPER CLASSES
	//
	
	
	/**
	 * Class which is a tuple of <code>BioAssayName.name</code>
	 * and an <code>ArrayDatum</code> object.
	 */
	private static final class BioAssayNameArrayDatumTuple
	implements Comparable<BioAssayNameArrayDatumTuple> {
		
		//
		//     ATTRIBUTES
		//
		
		/** Bioassay name. */
		private final String bioAssayName;
		
		/** Array datum. */
		private final ArrayDatum arrayDatum;
		
		//
		//     CONSTRUCTORS
		//
		
		/**
		 * Constructor.
		 * @param bioAssayName Name of a bioassay
		 * @param arrayDatum An array datum
		 */
		private BioAssayNameArrayDatumTuple(
				final String bioAssayName, final ArrayDatum arrayDatum) {
			this.bioAssayName = bioAssayName;
			this.arrayDatum = arrayDatum;
		}

		//
		//     Comparable INTERFACE
		//
		
		/**
		 * Comparison method.  Lexigraphical comparison is performed on
		 * <code>bioAssayName</code> property.
		 * @param o An object
		 * @return See java.lang.Comparable
		 * @see java.lang.Comparable
		 */
		public int compareTo(final BioAssayNameArrayDatumTuple o) {
			return this.bioAssayName.compareTo(
					((BioAssayNameArrayDatumTuple) o).bioAssayName);
		}
	}

	
	/**
	 * Class consisting of a group <code>ArrayDatum</code>
	 * objects that share the same reporter.  Each
	 * array datum represents a bioassay.
	 * @author dhall
	 *
	 */
	private static final class CommonArrayDatumGroup {
		
		//
		//     ATTRIBUTES
		//
		
		/** Reporter name. */
		private final String reporterName;
		
		/**
		 * Tuples consisting of an <code>ArrayDatum</code>
		 * object and name of associated bioassay.
		 */
		private final List<BioAssayNameArrayDatumTuple> tuples =
			new ArrayList<BioAssayNameArrayDatumTuple>();
		
		
		//
		//     CONSTRUCTORS
		//
		
		/**
		 * Constructor.
		 * @param reporterName Reporter name
		 */
		private CommonArrayDatumGroup(final String reporterName) {
			this.reporterName = reporterName;
		}
		
		
		//
		//     BUSINESS METHODS
		//
		
		/**
		 * Add a tuple.
		 * @param tuple A tuple
		 */
		private void add(final BioAssayNameArrayDatumTuple tuple) {
			this.tuples.add(tuple);
		}
		
		
		/**
		 * Return maximum value (including error).
		 * @return Maximum value including error
		 */
		private float maxValue() {
			float max = Float.MIN_VALUE;
			for (BioAssayNameArrayDatumTuple t : this.tuples) {
				ArrayDatum ad = t.arrayDatum;
				if (ad.valuePlusError() > max) {
					max = ad.valuePlusError();
				}
			}
			return max;
		}
	}
	
	
	/**
	 * This class iterates over selected reporters in a collection
	 * of experiments and returns common array datum groups.
	 * @author dhall
	 *
	 */
	private final class CommonArrayDatumGroupIterator {
		
		/** Groups to iterate over. */
		private final Iterator<CommonArrayDatumGroup> iterator;
		
		
		//
		//     CONSTRUCTORS
		//
		
		/**
		 * Constructor.
		 * @param experiments Experiments
		 */
		private CommonArrayDatumGroupIterator(
				final Collection<Experiment> experiments) {
			
			// Map of groups to reporter name
			Map<String, CommonArrayDatumGroup> groupsMap =
				new HashMap<String, CommonArrayDatumGroup>();
			for (Experiment exp : experiments) {
				for (Short chromosome : exp.getChromosomes()) {
					for (BioAssay ba : exp.getBioAssays()) {
						ChromosomeArrayData cad =
							getChromosomeArrayDataGetter().
							getChromosomeArrayData(ba, chromosome);
						if (cad != null) {
							for (ArrayDatum ad : cad.getArrayData()) {
								Reporter r = ad.getReporter();
								if (r.isSelected()) {
									CommonArrayDatumGroup group =
										groupsMap.get(r.getName());
									if (group == null) {
										group =
											new CommonArrayDatumGroup(
													r.getName());
										groupsMap.put(r.getName(), group);
									}
									group.add(
											new BioAssayNameArrayDatumTuple(
													ba.getName(), ad));
								}
							}
						}
					}
				}
			}
			
			this.iterator = groupsMap.values().iterator();
		}
		
		
		//
		//     BUSINESS METHODS
		//
		
		/**
		 * Is there anything left to iterate over?
		 * @return T/F
		 */
		private boolean hasNext() {
			return this.iterator.hasNext();
		}
		
		
		/**
		 * Get next.
		 * @return Next common array datum group
		 */
		private CommonArrayDatumGroup next() {
			return this.iterator.next();
		}
	}
}
