/*
$Revision: 1.4 $
$Date: 2007-09-09 17:16:05 $

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
�Research Triangle Institute�, and "RTI" must not be used to endorse or promote 
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

package org.rti.webgenome.service.plot;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.domain.AnnotationType;

/**
 * Parameters for generating an annotation plot.
 * @author dhall
 *
 */
public class AnnotationPlotParameters extends HeatMapPlotParameters {
	
	//
	//  C O N S T A N T S
	//
	
	/** Default value for drawing feature labels. */
	public static final boolean DEF_DRAW_FEATURE_LABELS = false;

	//
	//     ATTRIBUTES
	//
	
	/** Annotation types to plot. */
	private Set<AnnotationType> annotationTypes =
		new HashSet<AnnotationType>();

	/** Width of plot excluding track labels to the left in pixels. */
	private int width = DEF_WIDTH;
	
	/** Draw labels above features? */
	private boolean drawFeatureLabels = DEF_DRAW_FEATURE_LABELS;
	
	//
	//     GETTERS/SETTERS
	//
	
	/**
	 * Get annotation types to plot.
	 * @return Annotation type names
	 */
	public final Set<AnnotationType> getAnnotationTypes() {
		return annotationTypes;
	}

	/**
	 * Set annotation types to plot.
	 * @param annotationTypes Annotation type names
	 */
	public final void setAnnotationTypes(
			final Set<AnnotationType> annotationTypes) {
		this.annotationTypes = annotationTypes;
	}

	/**
	 * Draw labels above features?
	 * @return T/F
	 */
	public final boolean isDrawFeatureLabels() {
		return drawFeatureLabels;
	}

	/**
	 * Sets whether to draw labels above features.
	 * @param drawFeatureLabels Draw labels above features?
	 */
	public final void setDrawFeatureLabels(final boolean drawFeatureLabels) {
		this.drawFeatureLabels = drawFeatureLabels;
	}

	/**
	 * Get width of plot excluding track labels to the left in pixels.
	 * @return Width in pixels
	 */
	public final int getWidth() {
		return width;
	}

	
	/**
	 * Set width of plot excluding track labels to the left in pixels.
	 * @param width Width in pixels
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}
	
	
	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	public AnnotationPlotParameters() {
		
	}
	
	/**
	 * Constructor that uses deep copy to initialize properties.
	 * @param params Parameters to deep copy
	 */
	public AnnotationPlotParameters(final AnnotationPlotParameters params) {
		super(params);
		this.drawFeatureLabels = params.drawFeatureLabels;
		this.width = params.width;
		this.annotationTypes = new HashSet<AnnotationType>(
				params.getAnnotationTypes());
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Add annotation type.
	 * @param type An annotation type
	 */
	public final void add(final AnnotationType type) {
		this.annotationTypes.add(type);
	}
	
	
	/**
	 * Get annotatation types by name.  This is used to persist
	 * the enumeration values.
	 * @return Annotation types by name.
	 */
	public final Set<String> getAnnotationTypesByName() {
		Set<String> names = new HashSet<String>();
		for (AnnotationType type : this.annotationTypes) {
			names.add(type.name());
		}
		return names;
	}
	
	
	/**
	 * Set annotation types by names of enumeration values.
	 * @param names Names of enumeration values
	 */
	public final void setAnnotationTypesByName(
			final Collection<String> names) {
		for (String name : names) {
			this.add(AnnotationType.valueOf(name));
		}
	}
	
	
	//
	//     ABSTRACTS
	//
	
    /**
     * Return clone of this object derived by deep copy of
     * all attributes.
     * @return Clone of this object
     */
    public PlotParameters deepCopy() {
    	return new AnnotationPlotParameters(this);
    }
}