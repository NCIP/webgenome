/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/drawing/SvgElementFactory.java,v $
$Revision: 1.2 $
$Date: 2006-02-16 14:05:43 $

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
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Generates SVG elements
 */
public class SvgElementFactory {
    
    // ================================
    //        Constants
    // ================================
    
    private static final double BEZIER_APPROXIMATION = 1.25;
	
	private static final Color HYPERLINK_COLOR = Color.blue;
	
	private final Document document;
	
	
	/**
	 * Constructor
	 * @param document
	 */
	public SvgElementFactory(Document document) {
		this.document = document;
	}
	
	
	/**
	 * Generate new XML element
	 * @param graphic Graphic
	 * @return New XML element
	 */
	public Element newElement(GraphicPrimitive graphic) {
		Element element = null;
		if (graphic instanceof GraphicCircle)
			element = newCircleElement((GraphicCircle)graphic);
		else if (graphic instanceof GraphicLine)
			element = newLineElement((GraphicLine)graphic);
		else if (graphic instanceof GraphicRect)
			element = newRectElement((GraphicRect)graphic);
		else if (graphic instanceof GraphicPolygon)
		    element = newPolygonElement((GraphicPolygon)graphic);
		else if (graphic instanceof GraphicPolyline)
			element = newPolylineElement((GraphicPolyline)graphic);
		else if (graphic instanceof GraphicText)
			element = newTextElement((SvgGraphicText)graphic);
		else if (graphic instanceof GraphicArc)
			element = newArcElement((GraphicArc)graphic);
		else if (graphic instanceof GraphicCurve)
		    element = newCurveElement((GraphicCurve)graphic);
		addProperties(element, graphic);
		addGraphicEventResponses(element, graphic.getGraphicEventResponses());
		if (graphic.getToolTipText() != null)
			addToolTip(element, graphic.getToolTipText());
		if (graphic.getHyperlink() != null)
			element = addHyperlink(element, graphic.getHyperlink());
		if (graphic.getCursor() == Cursor.POINTER &&
			graphic.getUrl() == null)
			element = addCursorModifier(element);
		return element;
	}
	
	
	/**
	 * Generate new "g" element
	 * @return New "g" element
	 */
	public Element newGroupElement() {
		return document.createElement("g");
	}
	
	
	/**
	 * Generate new <script/> element
	 * @return New <script/> element
	 */
	public Element newScriptElement() {
		Element s = document.createElement("script");
		s.setAttribute("type", "text/ecmascript");
		return s;
	}
	
	
	/**
	 * Create new CDATASection element
	 * @param value CDATA text
	 * @return New CDATASection element
	 */
	public CDATASection newCDataSection(String value) {
		return document.createCDATASection(value);
	}
	
	    
    
	/**
	 * Create rectangle element
	 * @param rect Graphic rectangle
	 * @return XML element
	 */
	private Element newRectElement(GraphicRect rect){
		Element el = document.createElement("rect");
		el.setAttribute("x", String.valueOf(rect.getX()));
		el.setAttribute("y", String.valueOf(rect.getY()));
		el.setAttribute("width", String.valueOf(rect.getWidth()));
		el.setAttribute("height", String.valueOf(rect.getHeight()));
		el.setAttribute("fill", rgb(rect.getColor()));
		el.setAttribute("stroke", rgb(rect.getColor()));
		return el;
	}
	
	
	private Element newPolygonElement(GraphicPolygon polygon) {
	    Element el = document.createElement("polygon");
	    el.setAttribute("fill", rgb(polygon.getColor()));
	    el.setAttribute("stroke", "none");
	    StringBuffer pointsStr = new StringBuffer();
	    Point[] points = polygon.getPoints();
	    for (int i = 0; i < points.length; i++) {
	        if (i > 0)
	            pointsStr.append(" ");
	        Point point = points[i];
	        pointsStr.append(point.x + "," + point.y);
	    }
	    el.setAttribute("points", pointsStr.toString());
	    return el;
	}
	
	
	/**
	 * Create a line element
	 * @param line Graphic line
	 * @return Line element
	 */
	private Element newLineElement(GraphicLine line){
		Element e = document.createElement("line");
		e.setAttribute("x1", String.valueOf(line.getX1()));
		e.setAttribute("y1", String.valueOf(line.getY1()));
		e.setAttribute("x2", String.valueOf(line.getX2()));
		e.setAttribute("y2", String.valueOf(line.getY2()));
		e.setAttribute("stroke-width", String.valueOf(line.getWidth()));
		e.setAttribute("stroke", rgb(line.getColor()));
		return e;
	}
	
	
	
	/**
	 * Create text node
	 * @param text Graphic text
	 * @return Text node
	 */
	private Element newTextElement (SvgGraphicText text){
		Element el = document.createElement("text");
		el.setAttribute("x", String.valueOf(text.getX()));
		el.setAttribute("y", String.valueOf(text.getY()));
		el.setAttribute("font-size", String.valueOf(text.getFontSize()));
		el.setAttribute("stroke", rgb(text.getColor()));
		el.setAttribute("text-anchor", textAnchor(text.getAlignment()));
		el.setAttribute("font-family", "monospace");
		if (text.getRotation() != 0)
			el.setAttribute("transform", "rotate(" + text.getRotation() + "," + text.getX() +
				"," + text.getY() + ")");
		el.appendChild(document.createTextNode(text.getValue()));
		if (text.getUrl() != null) {
			el.setAttribute("text-decoration", "underline");
			el.setAttribute("stroke", rgb(HYPERLINK_COLOR));
		}
		return el;
	}
	
	
	private Element newArcElement(GraphicArc arc) {
		Element el = document.createElement("path");
		int largeArc = arc.isLargeArc()? 1 : 0;
		int sweep = (arc.getOpeningDir() == Direction.UP)? 0 : 1;
		String d =
			"M " + arc.getX1() + ", " + arc.getY1() + " A " + arc.getXRadius() + 
			", " + arc.getYRadius() + " " + arc.getRotation() + " " + largeArc + " " +
			sweep + " " + arc.getX2() + ", " + arc.getY2();
		el.setAttribute("d", d);
		el.setAttribute("stroke", rgb(arc.getColor()));
		el.setAttribute("stroke-width", String.valueOf(arc.getLineWidth()));
		el.setAttribute("fill", "none");
		return el;
	}
	
	
	private Element newCurveElement(GraphicCurve curve) {
	    Point controlPt1 = new Point(curve.getX1(), curve.getY1());
	    Point controlPt2 = new Point(curve.getX2(), curve.getY2());
	    int delta = (int)((double)curve.getHeight() * BEZIER_APPROXIMATION);
	    if (curve.getOrientation() == Orientation.HORIZONTAL) {
	        controlPt1.x += delta;
	        controlPt2.x += delta;
	    } else if (curve.getOrientation() == Orientation.VERTICAL) {
	        controlPt1.y += delta;
	        controlPt2.y += delta;
	    }
	    String d = "M" + curve.getX1() + "," + curve.getY1() + " " +
	    	"C" + controlPt1.x + "," + controlPt1.y + " " + controlPt2.x + "," +
	    	controlPt2.y + " " + curve.getX2() + "," + curve.getY2();
	    Element el = document.createElement("path");
	    el.setAttribute("d", d);
	    el.setAttribute("stroke", rgb(curve.getColor()));
		el.setAttribute("stroke-width", String.valueOf(curve.getLineWidth()));
		el.setAttribute("fill", "white");
	    return el;
	}

	/**
	 * Add hyperlink around element
	 * @param element Element 
	 * @param hyperlink Hyperlink URL
	 * @return Enclosing hyperlinked element
	 */
	private Element addHyperlink(Element element, Hyperlink hyperlink) {
		Element a = document.createElement("a");
		URL url = hyperlink.getUrl();
		String target = hyperlink.getTargetWindow();
		if (url != null)
			a.setAttribute("xlink:href", url.toExternalForm());
		if (target != null)
			a.setAttribute("target", target);
		a.setAttribute("cursor", "pointer");
		a.appendChild(element);
		return a;
	}
	
	
	/**
	 * Add a tool tip
	 * @param element Element
	 * @param text Text to display when mouse passes over
	 */
	private void addToolTip(Element element, String text) {
		String toolTipText = "showToolTip(evt, '" + 
			text + "')";
		element.setAttribute("onmouseover", toolTipText);
	}
	
	
	private void addGraphicEventResponses
	(
		Element element, GraphicEventResponse[] eventResponses
	) {
		if (eventResponses != null) {
			for (int i = 0; i < eventResponses.length; i++) {
				GraphicEventResponse resp = eventResponses[i];
				element.setAttribute(resp.getEvent().getName(), resp.getResponse());
			}
		}
	}
	
	
	private Element addCursorModifier(Element element) {
		Element a = document.createElement("a");
		a.appendChild(element);
		a.setAttribute("cursor", "pointer");
		return a;
	}
	
		

	/**
	 * Create SVG circle
	 * @param circle Graphic circle
	 * @return SVG circle
	 */
	private Element newCircleElement
	(
		GraphicCircle circle
	) {
		Element c = document.createElement("circle");
		c.setAttribute("cx", String.valueOf(circle.getX()));
		c.setAttribute("cy", String.valueOf(circle.getY()));
		c.setAttribute("r", String.valueOf(circle.getRadius()));
		c.setAttribute("stroke", rgb(circle.getColor()));
		c.setAttribute("fill", rgb(circle.getColor()));
		return c;
	}
	
		
	
	/**
	 * Create SVG polyine element
	 * @param poly Graphic polyline
	 * @return SVG polyline element
	 */
	private Element newPolylineElement(GraphicPolyline poly){
		Element p = document.createElement("polyline");
		p.setAttribute("stroke", rgb(poly.getColor()));
		p.setAttribute("stroke-width", String.valueOf(poly.getWidth()));
		StringBuffer buff = new StringBuffer();
		List points = poly.getPoints();
		if (points != null) {
			int count = 0;
			for (Iterator it = points.iterator(); it.hasNext();) {
				Point point = (Point)it.next();
				int x = (int)point.getX();
				int y = (int)point.getY();
				if (count++ > 0)
					buff.append(", ");
				buff.append(String.valueOf(x) + " " + String.valueOf(y));
			}
			p.setAttribute("points", buff.toString());
		}
		if (poly.getFillColor() == null)
			p.setAttribute("fill", "none");
		else
			p.setAttribute("fill", rgb(poly.getFillColor()));
		return p;
	}
	
	
	/**
	 * Convert a Color object into an "rgb" format string version
	 * @param color Color object
	 * @return String version of color in "rgb" format
	 */
	private String rgb(Color color) {
		return
			"rgb(" +
			color.getRed() + "," +
			color.getGreen() + "," +
			color.getBlue() +
			")";
	}
	
	
	/**
	 * Return text anchor text
	 * @param alignment Alignment (i.e. <code>SvgGraphicText.START</code>
	 * <code>SvgGraphicText.MIDDLE</code>, <code>SvgGraphicText.END</code>)
	 * @return Text anchor string
	 */
	private String textAnchor(HorizontalAlignment alignment) {
		String anchor = "middle";
		if (alignment == HorizontalAlignment.LEFT_JUSTIFIED)
				anchor = "start";
		else if (alignment == HorizontalAlignment.CENTERED)
				anchor = "middle";
		else if (alignment == HorizontalAlignment.RIGHT_JUSTIFIED)
				anchor = "end";
		return anchor;
	}
	
	
	private void addProperties(Element e, GraphicPrimitive g) {
		Properties props = g.getProperties();
		for (Enumeration names = props.propertyNames(); names.hasMoreElements();) {
			String name = (String)names.nextElement();
			String value = props.getProperty(name);
			e.setAttribute(name, value);
		}
	}
}
