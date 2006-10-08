/*
$Revision: 1.3 $
$Date: 2006-10-08 21:51:28 $

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

package org.rti.webcgh.webui.struts.cart;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.rti.webcgh.core.PlotType;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.domain.GenomeInterval;
import org.rti.webcgh.domain.GenomeIntervalFormatException;
import org.rti.webcgh.domain.QuantitationType;
import org.rti.webcgh.service.plot.PlotParameters;
import org.rti.webcgh.service.plot.ScatterPlotParameters;
import org.rti.webcgh.units.BpUnits;
import org.rti.webcgh.util.ValidationUtils;
import org.rti.webcgh.webui.struts.BaseForm;

/**
 * Form for input of plot parameters.
 * @author dhall
 *
 */
public class PlotParametersForm extends BaseForm {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 1;
	
	// ===========================
	//      Constants
	// ===========================
	
	/** Default genome intervals. */
	private static final String DEF_GENOME_INTERVALS = "1";
	
	/** Default plot type. */
	private static final String DEF_PLOT_TYPE = PlotType.SCATTER.getName();
	
	/** Default units. */
	private static final String DEF_UNITS = BpUnits.KB.getName();
	
	/** Default plots per row. */
	private static final String DEF_NUM_PLOTS_PER_ROW = "4";
	
	/** Default plot width. */
	private static final String DEF_WIDTH = "600";
	
	/** Default plot height. */
	private static final String DEF_HEIGHT = "250";
	
	/** Default quantitation type. */
	private static final String DEF_QUANTITATION_TYPE =
		QuantitationType.LOG_2_RATIO.getName();
	
	// ===========================
	//     Attributes
	// ===========================
	
	/** Name of plot type. */
	private String plotType = DEF_PLOT_TYPE;
	
	/**
	 * Genome intervals encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 */
	private String genomeIntervals = DEF_GENOME_INTERVALS;
	
	/** Genome interval units (i.e., BP, KB, MB). */
	private String units = DEF_UNITS;
	
	/**
	 * Number of plots per row.  If more than one genome
	 * interval is specified, each will be plotted in
	 * a separate graph within the same graphics file.
	 */
	private String numPlotPerRow = DEF_NUM_PLOTS_PER_ROW;

	/** Minimum Y-axis value. */
	private String minY = "";
	
	/** Maximum Y-axis value. */
	private String maxY = "";
	
	/** Width of plot in pixels. */
	private String width = DEF_WIDTH;
	
	/** Height of plot in pixels. */
	private String height = DEF_HEIGHT;
	
	/** Quantitation type. */
	private String quantitationType = DEF_QUANTITATION_TYPE;
	
	// ================================
	//      Getters/setters
	// ================================

	/**
	 * Get genome intervals.
	 * Genome intervals are encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 * @return Genome intervals
	 */
	public final String getGenomeIntervals() {
		return genomeIntervals;
	}

	/**
	 * Set genome intervals.
	 * Genome intervals are encoded like '1:1000-2000;2:50-150'.
	 * This string encodes two genome intervals.  The first
	 * is chromosome 1 from position 1000 to 2000.  The positions
	 * units may be base pairs or a larger multiple of base pairs.
	 * @param genomeIntervals Genome intervals
	 */
	public final void setGenomeIntervals(final String genomeIntervals) {
		this.genomeIntervals = genomeIntervals;
	}
	
	
	/**
	 * Get quantitation type.
	 * @return Quantitation type.
	 */
	public final String getQuantitationType() {
		return quantitationType;
	}


	/**
	 * Set quantitation type.
	 * @param quantitationType Quantitation type.
	 */
	public final void setQuantitationType(final String quantitationType) {
		this.quantitationType = quantitationType;
	}

	/**
	 * Get maximum Y-axis value.
	 * @return Maximum Y-axis value.
	 */
	public final String getMaxY() {
		return maxY;
	}

	/**
	 * Set maximum Y-axis value.
	 * @param maxY Maximum Y-axis value.
	 */
	public final void setMaxY(final String maxY) {
		this.maxY = maxY;
	}

	/**
	 * Get minimum Y-axis value.
	 * @return Minimum Y-axis value.
	 */
	public final String getMinY() {
		return minY;
	}

	/**
	 * Set minimum Y-axis value.
	 * @param minY Minimum Y-axis value.
	 */
	public final void setMinY(final String minY) {
		this.minY = minY;
	}

	/**
	 * Get number of plots per row.
	 * @return Number of plots per row.
	 */
	public final String getNumPlotPerRow() {
		return numPlotPerRow;
	}

	/**
	 * Set number of plots per row.
	 * @param numPlotPerRow Number of plots per row.
	 */
	public final void setNumPlotPerRow(final String numPlotPerRow) {
		this.numPlotPerRow = numPlotPerRow;
	}

	/**
	 * Get plot type.
	 * @return Plot type.
	 */
	public final String getPlotType() {
		return plotType;
	}

	/**
	 * Set plot type.
	 * @param plotType Plot type.
	 */
	public final void setPlotType(final String plotType) {
		this.plotType = plotType;
	}
	

	/**
	 * Get plot height.
	 * @return Height in pixels.
	 */
	public final String getHeight() {
		return height;
	}

	/**
	 * Set plot height.
	 * @param height Height in pixels.
	 */
	public final void setHeight(final String height) {
		this.height = height;
	}

	/**
	 * Get plot width.
	 * @return Width in pixels.
	 */
	public final String getWidth() {
		return width;
	}

	/**
	 * Set plot width.
	 * @param width Width in pixels.
	 */
	public final void setWidth(final String width) {
		this.width = width;
	}

	/**
	 * Get base pair units.
	 * @return Base pair units.
	 */
	public final String getUnits() {
		return units;
	}

	/**
	 * Set base pair units.
	 * @param units Base pair units.
	 */
	public final void setUnits(final String units) {
		this.units = units;
	}
	
	// ===================================
	//       Overrides
	// ===================================

	/**
	 * Reset form values.
	 * @param actionMappings Action mappings
	 * @param request Servlet request
	 */
	@Override
	public final void reset(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		this.plotType = DEF_PLOT_TYPE;
		this.genomeIntervals = DEF_GENOME_INTERVALS;
		this.units = DEF_UNITS;
		this.numPlotPerRow = DEF_NUM_PLOTS_PER_ROW;
		this.minY = "";
		this.maxY = "";
		this.width = DEF_WIDTH;
		this.height = DEF_HEIGHT;
	}

	/**
	 * Validate form fields.
	 * @param actionMappings Action mappings.
	 * @param request Servlet request.
	 * @return Action errors
	 */
	@Override
	public final ActionErrors validate(final ActionMapping actionMappings,
			final HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		
		// genomeIntervals
		if (this.genomeIntervals != null && this.genomeIntervals.length() > 0) {
			try {
				List<GenomeInterval> intervals = GenomeInterval.decode(
						this.genomeIntervals);
				for (GenomeInterval interval : intervals) {
					short chrom = interval.getChromosome();
					long start = interval.getStartLocation();
					long end = interval.getEndLocation();
					if (chrom < 0 || end < start || (start < 0 && end >= 0)
							|| (start >= 0 && end < 0)) {
						errors.add("genomeIntervals",
								new ActionError("invalid.field"));
					}
				}
			} catch (GenomeIntervalFormatException e) {
				errors.add("genomeIntervals", new ActionError("invalid.field"));
			}
		}
		
		// numPlotsPerRow
		if (!ValidationUtils.validNumber(this.numPlotPerRow)) {
			errors.add("numPlotsPerRow", new ActionError("invalid.field"));
		}
		
		// minY and maxY
		boolean validateRelationship = true;
		if (this.minY != null && this.minY.length() > 0) {
			if (!ValidationUtils.validNumber(this.minY)) {
				errors.add("minY", new ActionError("invalid.field"));
				validateRelationship = false;
			}
		} else {
			validateRelationship = false;
		}
		if (this.maxY != null && this.maxY.length() > 0) {
			if (!ValidationUtils.validNumber(this.maxY)) {
				errors.add("maxY", new ActionError("invalid.field"));
				validateRelationship = false;
			}
		} else {
			validateRelationship = false;
		}
		if (validateRelationship) {
			if (Double.parseDouble(this.minY) > Double.parseDouble(this.maxY)) {
				errors.add("minY", new ActionError("invalid.field"));
				errors.add("maxY", new ActionError("invalid.field"));
			}
		}
		
		// Global error
		if (errors.size() > 0) {
			errors.add("global", new ActionError("invalid.fields"));
		}
		
		return errors;
	}

	
	// ====================================
	//     Additional business methods
	// ====================================
	
	/**
	 * Extract plot parameters.
	 * @return Plot parameters
	 */
	public final PlotParameters getPlotParameters() {
		PlotParameters p = null;
		PlotType type = PlotType.getPlotType(this.plotType);
		
		// Scatter plot
		if (type == PlotType.SCATTER) {
			p = new ScatterPlotParameters();
			float minYFloat = Float.NaN;
			if (this.minY != null && this.minY.length() > 0) {
				minYFloat = Float.parseFloat(this.minY);
			}
			float maxYFloat = Float.NaN;
			if (this.maxY != null && this.maxY.length() > 0) {
				maxYFloat = Float.parseFloat(this.maxY);
			}
			((ScatterPlotParameters) p).setMinY(minYFloat);
			((ScatterPlotParameters) p).setMaxY(maxYFloat);
			((ScatterPlotParameters) p).setWidth(
					Integer.parseInt(this.width));
			((ScatterPlotParameters) p).setHeight(
					Integer.parseInt(this.height));
		}
		
		// Common attributes
		if (p != null) {
			try {
				p.setGenomeIntervals(GenomeInterval.decode(
						this.genomeIntervals));
			} catch (GenomeIntervalFormatException e) {
				throw new WebcghSystemException(
						"Error extracting plot parameters", e);
			}
			p.setNumPlotsPerRow(Integer.parseInt(this.numPlotPerRow));
			p.setUnits(BpUnits.getUnits(this.units));
			p.setQuantitationType(QuantitationType.getQuantitationType(
					this.quantitationType));
		}
		
		return p;
	}
}
