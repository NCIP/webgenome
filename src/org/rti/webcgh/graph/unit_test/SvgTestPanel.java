/*

$Source$
$Revision$
$Date$

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/


package org.rti.webcgh.graph.unit_test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.drawing.DrawingCanvas;
import org.rti.webcgh.drawing.SvgDrawingCanvas;
import org.rti.webcgh.graph.widgit.PlotPanel;
import org.rti.webcgh.util.IOUtils;
import org.rti.webcgh.util.XmlUtils;
import org.w3c.dom.Document;

/**
 * Instances of this class are created to help with the testing
 * of plotting components including individual widgits and entire
 * plots.  The class creates a drawing canvas that enables
 * the plot components to create an SVG document.  It also provides
 * a method for writing the SVG document to a file.
 *
 */
public class SvgTestPanel extends PlotPanel {
	
	private static final String CLASSPATH_RELATIVE_PATH_TO_SVG_TEMPLATE =
		"/svg/plotTemplate.svg";
	
	// =====================================
	//      Attributes
	// =====================================
	
	private File svgDirectory = new File("C:\temp");
	private Document doc = null;
	
	/**
	 * Get directory that output SVG files are written to
	 * @return A directory
	 */
	public File getSvgDirectory() {
		return svgDirectory;
	}


	/**
	 * Set directory that output SVG files are written to
	 * @param svgDirectory A directory
	 */
	public void setSvgDirectory(File svgDirectory) {
		this.svgDirectory = svgDirectory;
	}



	// ====================================
	//      Constructors
	// ====================================
	
	/**
	 * Constructor
	 * @param canvas A drawing canvas
	 * @param doc A document
	 */
	private SvgTestPanel(DrawingCanvas canvas, Document doc) {
		super(canvas);
		this.doc = doc;
	}
	
	
	// ===================================
	//    Static methods
	// ===================================
		
	/**
	 * Factory method for creating a new SvgTestPanel object
	 */
	public static SvgTestPanel newSvgTestPanel() {
		
		// Load SVG plotting template document
		Document doc = 
			XmlUtils.loadDocument(CLASSPATH_RELATIVE_PATH_TO_SVG_TEMPLATE, false);
		if (doc == null)
			throw new WebcghSystemException("Unable to load SVG template document");
		
		// Instantiate drawing canvas
		DrawingCanvas drawingCanvas = new SvgDrawingCanvas(doc);
		
		// Return new panel
		return new SvgTestPanel(drawingCanvas, doc);
	}
	
	
	// =====================================
	//       Other public methods
	// =====================================
	
	/**
	 * Create SVG file with 
	 */
	public void toSvgFile(String fileName) {
		File outFile = 
			new File(this.svgDirectory.getAbsolutePath() + "/" + fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outFile);
			XmlUtils.forwardDocument(this.doc, new FileWriter(outFile));
		} catch (IOException e) {
			throw new WebcghSystemException("Error creating SVG file", e);
		} finally {
			IOUtils.close(writer);
		}
	}
}
