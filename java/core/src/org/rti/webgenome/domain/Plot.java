/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:32 $

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


package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.rti.webgenome.service.plot.PlotParameters;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.util.ClickBoxes;
import org.rti.webgenome.webui.util.MouseOverStripes;

/**
 * Represents a plot of data.  A plot is an
 * aggregate of image files with some additional
 * metadata.  If there are N bioassays in the
 * plot, there will be N + 1 separate image files.
 * In one file, no bioassays will be highlighted.
 * In the remainder, one bioassay will be highlighted.
 */
public class Plot implements Serializable {
	
	/** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // =============================
    //      Attributes
    // =============================
    
    /** Primary key identifier for persistence. */
    private Long id = null;
    
    /** Name of default image files. */
    private String defaultImageFileName = null;
    
    /**
     * Map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
     */
    private Map<String, String> imageFileMap = new HashMap<String, String>();
    
    /**
     * Click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
     */
    private Collection<ClickBoxes> clickBoxes = null;
    
    /**
     * Mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
     */
    private Collection<MouseOverStripes> mouseOverStripes = null;
    
    /** Width of entire plot image in pixels. */
    private int width = 0;
    
    /** Height of entire plot image in pixels. */
    private int height = 0;
    
    /** Plot parameters. */
    private PlotParameters plotParameters = null;
    
    /** IDs of experiments in plot. */
    private Collection<Long> experimentIds = new ArrayList<Long>();
    
    // ================================
    //       Getters/setters
    // ================================

    /**
     * Get primary key identifier.
     * @return Primary key identifier.
     */
	public final Long getId() {
		return id;
	}

	/**
	 * Set primary key identifier.
	 * @param id Primary key identifier.
	 */
	public final void setId(final Long id) {
		this.id = id;
	}

	
	/**
	 * Get IDs of experiments in plot.
	 * @return Experiment IDs
	 */
	public final Collection<Long> getExperimentIds() {
		return experimentIds;
	}

	/**
	 * Set IDs of experiments in plot.
	 * @param experimentIds Experiment IDs
	 */
	public final void setExperimentIds(final Collection<Long> experimentIds) {
		this.experimentIds = experimentIds;
	}

	/**
	 * Get plot parameters used in creation of plot.
	 * @return Plot parameters.
	 */
	public final PlotParameters getPlotParameters() {
		return plotParameters;
	}


	/**
	 * Set plot parameters used in creation of plot.
	 * @param plotParameters Plot parameters
	 */
	public final void setPlotParameters(
			final PlotParameters plotParameters) {
		this.plotParameters = plotParameters;
	}

	/**
	 * Get height of entire plot image in pixels.
	 * @return Height of entire plot image in pixels.
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Set height of entire plot image.
	 * @param height Height in pixels.
	 */
	public final void setHeight(final int height) {
		this.height = height;
	}

	/**
	 * Get width of entire plot image in pixels.
	 * @return Height of entire plot image in pixels.
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * Set width of entire plot image.
	 * @param width Width of entire plot image in pixels.
	 */
	public final void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Get name of default image file.
	 * @return Default image file name.
	 */
	public final String getDefaultImageFileName() {
		return defaultImageFileName;
	}

	/**
	 * Set name of default image file.
	 * @param defaultImageFileName Default image file name.
	 */
	public final void setDefaultImageFileName(
			final String defaultImageFileName) {
		this.defaultImageFileName = defaultImageFileName;
	}

	/**
	 * Get map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @return Map of image names to image file names.
	 */
	public final Map<String, String> getImageFileMap() {
		return imageFileMap;
	}

	
	/**
	 * Set map of image names to image file names. 
     * Note that file names are not absolute paths;
     * clients should
     * know how to generate absolute paths.
	 * @param imageFileMap Map of image names to image file
	 * names.
	 */
	public final void setImageFileMap(final Map<String, String> imageFileMap) {
		this.imageFileMap = imageFileMap;
	}

	/**
	 * Get click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
	 * @return Click boxes
	 */
	public final Collection<ClickBoxes> getClickBoxes() {
		return clickBoxes;
	}

	
	/**
	 * Set click boxes representing rectangular pixel regions
     * within the plot.  Text values of the click boxes
     * give the names of images that should be displayed
     * when the user clicks in the regions.
	 * @param clickBoxes Click boxes
	 */
	public final void setClickBoxes(final Collection<ClickBoxes> clickBoxes) {
		this.clickBoxes = clickBoxes;
	}

	/**
	 * Get mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
	 * @return Mouse over stripes.
	 */
	public final Collection<MouseOverStripes> getMouseOverStripes() {
		return mouseOverStripes;
	}

	/**
	 * Set mouse over stripes reprsenting rectangular
     * pixel regions within the plot.  Text values
     * of each strip gives mouseover text (reporter
     * names).
	 * @param mouseOverStripes Mouse over stripes
	 */
	public final void setMouseOverStripes(
			final Collection<MouseOverStripes> mouseOverStripes) {
		this.mouseOverStripes = mouseOverStripes;
	}
	
	// ============================
	//     Constructors
	// ============================
	

	/**
	 * Constructor.
	 */
	public Plot() {
		
	}
	
	
	// ===================================
	//        Business methods
	// ===================================
	
	/**
	 * Add image file.
	 * @param imageName Name of image.
	 * @param fileName Name of image file.  Note this
	 * is not an absolute path; clients should know how
	 * to generate an absolute path.
	 */
	public final void addImageFile(final String imageName,
			final String fileName) {
		this.imageFileMap.put(imageName, fileName);
	}
	
	
	/**
	 * Get names of all image files associated with plot.
	 * @return Names of all image files associated with plot.
	 */
	public final Collection<String> getAllImageFileNames() {
		Collection<String> names = new ArrayList<String>();
		names.add(this.defaultImageFileName);
		names.addAll(this.imageFileMap.values());
		return names;
	}
	
	
	/**
	 * Add experiment ID.
	 * @param id Experiment ID.
	 */
	public final void addExperimentId(final Long id) {
		this.experimentIds.add(id);
	}
}