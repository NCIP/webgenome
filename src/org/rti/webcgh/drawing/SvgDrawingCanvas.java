/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/SvgDrawingCanvas.java,v $
$Revision: 1.6 $
$Date: 2006-09-05 14:06:44 $

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


package org.rti.webcgh.drawing;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.util.XmlUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * Implementation of <code>DrawingCanvas</code> interface
 * for an SVG canvas
 */
public class SvgDrawingCanvas implements DrawingCanvas {
	
	
	private final Element insertionPoint;
	private final Element docElement;
	private final SvgElementFactory factory;
	private final Document document;
	
	
	
    /**
     * @return Returns the document.
     */
    public Document getDocument() {
        return document;
    }
    
    
	/**
	 * Constructor
	 * @param doc XML document
	 */
	public SvgDrawingCanvas(Document doc) {
	    this.document = doc;
		Element elmt = doc.getElementById("insertionPoint");
		docElement = doc.getDocumentElement();
		if (elmt ==  null)
			elmt = doc.getDocumentElement();
		insertionPoint = elmt;
		factory = new SvgElementFactory(doc);
	}
	
	
	/**
	 * Constructor
	 * @param document Document
	 * @param root Root element of XML document
	 * @param insertionPoint Insertion point element in document
	 * @param factory Factory for generating elements
	 */
	private SvgDrawingCanvas
	(
		Document document, Element root, Element insertionPoint,
		SvgElementFactory factory
	) {
	    this.document = document;
		this.docElement = root;
		this.insertionPoint = insertionPoint;
		this.factory = factory;
	}
		
		
	/**
	 * Set global line width property that applies to all lines
	 * withiin this canvas
	 * @param width Line width
	 */
	public void setLineWidth(int width) {
		insertionPoint.setAttribute("stroke-width", String.valueOf(width));
	}
	
	
	/**
	 * Set id of canvas
	 * @param id Canvas id
	 */
	public void setId(String id) {
		insertionPoint.setAttribute("id", id);
	}
	
	
	/**
	 * Add a canvas
	 * @param canvas A canvas
	 */
	public void add(DrawingCanvas canvas) {
		SvgDrawingCanvas can = (SvgDrawingCanvas)canvas;
		insertionPoint.appendChild(can.getInsertionPoint());
	}
	
	
	/**
	 * Rotate canvas
	 * @param degrees Degrees of rotation
	 * @param x X-coordinate of rotation point
	 * @param y Y-coordinate of rotation point
	 */
	public void rotate(int degrees, int x, int y) {
		String value = "rotate(" + degrees + ", " + x + ", " + y + ")";
		String attributeValue = insertionPoint.getAttribute("transform");
		if (attributeValue != null && attributeValue.length() > 0)
			value += " " + attributeValue;
		insertionPoint.setAttribute("transform", value);
	}
	
	

	/**
	 * Add a canvas
	 * @param canvas Canvas to add
	 * @param x X-coordinate of top left corner of inner canvas
	 * @param y Y-coordinate of top left corder of inner canvas
	 */
	public void add(DrawingCanvas canvas, int x, int y) {
		SvgDrawingCanvas can = (SvgDrawingCanvas)canvas;
		can.move(x, y);
		insertionPoint.appendChild(can.getInsertionPoint());
	}
	

	/**
	 * Add a graphic object
	 * @param graphic Graphic object
	 */
	public void add(GraphicPrimitive graphic) {
		add(graphic, true);
	}
	
	
	/**
	 * Add a graphic object
	 * @param graphic Graphic object
	 * @param overwriteCanvasProperties Do properties set for this
	 * primitive take precedence over same named properties of canvas?
	 */
	public void add(GraphicPrimitive graphic, boolean overwriteCanvasProperties) {
		Element element = factory.newElement(graphic);
		insertionPoint.appendChild(element);
		if (! overwriteCanvasProperties)
			stripRedundantProperties(element);
	}
	
	/**
	 * Set width of canvas
	 * @param width Width in pixels
	 */
	public void setWidth(int width) {
		docElement.setAttribute("width", String.valueOf(width));
	}
	
	
	/**
	 * Set height of canvas
	 * @param height Height in pixels
	 */
	public void setHeight(int height) {
		docElement.setAttribute("height", String.valueOf(height));
	}
	
	
	/**
	 * Set artibrary attribute
	 * @param name Attribute name
	 * @param value Attribute value
	 */
	public void setAttribute(String name, String value) {
		this.insertionPoint.setAttribute(name, value);
	}
	
	
	private void stripRedundantProperties(Element element) {
		Map atts = getAttributesInLineage();
		for (Iterator it = atts.keySet().iterator(); it.hasNext();) {
			String name = (String)it.next();
			element.removeAttribute(name);
		}
	}
	
	
	/**
	 * Get all attributes in lineage to document root
	 * @return Name/value pairs
	 */
	private Map getAttributesInLineage() {
		Map attributes = new HashMap();
		Node node = insertionPoint;
		while (node != null) {
			if (node instanceof Element) {
				NamedNodeMap map = node.getAttributes();
				for (int i = 0; i < map.getLength(); i++) {
					Attr a = (Attr)map.item(i);
					String name = a.getName();
					if (! attributes.containsKey(name))
						attributes.put(name, a.getValue());
				}
			}
			node = node.getParentNode();
		}
		return attributes;
	}
	
	
	/**
	 * Return new text element
	 * @param value String value of text
	 * @param x X-coordinate of text
	 * @param y Y-coordinate of text
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color of text
	 * @return New text element
	 */
	public Text newText
	(
		String value, int x, int y, int fontSize, HorizontalAlignment alignment, Color color
	) {
		return new SvgText(value, x, y, fontSize, alignment, color);
	}
	
	
	/**
	 * Rendered with of given text
	 * @param text Text element
	 * @param fontSize Font size
	 * @return Rendered with of given text
	 */
	public int renderedWidth(String text, int fontSize) {
		return (int)((double)text.length() * (double)fontSize * 0.6);
	}
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas)
	 * @return A new drawing tile
	 */
	public DrawingCanvas newTile() {
		Element g = factory.newGroupElement();
		return new SvgDrawingCanvas(this.document, docElement, g, factory);
	}
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas)
	 * @param tileName Name of tile
	 * @return A new drawing tile
	 */
	public DrawingCanvas newTile(String tileName) {
		Element g = factory.newGroupElement();
		g.setAttribute("id", tileName);
		return new SvgDrawingCanvas(this.document, docElement, g, factory);
	}
	
	
	/**
	 * Set global property for data set names.  This is a list of all
	 * data sets represented in graphic
	 * @param names Data set names
	 */
	public void setDataSetNames(String[] names) {
		Element s = factory.newScriptElement();
		StringBuffer buff = new StringBuffer("var experimentNames = new Array(");
		if (names != null) {
			int count = 0;
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				if (count++ > 0)
					buff.append(", ");
				buff.append("\"" + name.trim() + "\"");
			}
		}
		buff.append(");");
		CDATASection c = factory.newCDataSection(buff.toString());
		insertionPoint.appendChild(s);
		s.appendChild(c);
	}
	
	
	/**
	 * Return new plotting canvas
	 * @return Plotting canvas
	 */
	public static SvgDrawingCanvas newPlottingCanvas() {
	    Document doc = 
    		XmlUtils.loadDocument("svg/plotTemplate.svg", false);
	    return new SvgDrawingCanvas(doc);
	}
	
	
	/**
	 * Add a response to an event
	 * @param event An event
	 * @param response A response
	 */
	public void addGraphicEventResponse(GraphicEvent event, String response) {
		this.insertionPoint.setAttribute(event.getName(), response);
	}
    
    
    /**
     * Set origin coordinate of canvas.
     * @param point Origin on canvas coordinate system
     */
    public void setOrigin(final Point point) {
        
    }
		
	
	private void move(int dx, int dy) {
		String attName = "transform";
		String attValue = insertionPoint.getAttribute(attName);
		if (attValue != null && attValue.length() > 0)
			attValue += " ";
		if (attValue == null)
			attValue = "";
		attValue += "translate(" + dx + "," + dy + ")";
		insertionPoint.setAttribute(attName, attValue);
	}
	
	
	private Element getInsertionPoint() {
		return insertionPoint;
	}
}
