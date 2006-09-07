/*
$Revision: 1.1 $
$Date: 2006-09-07 18:54:53 $

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


package org.rti.webcgh.graphics;

import java.awt.Color;
import java.awt.Point;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.rti.webcgh.graphics.event.GraphicEvent;
import org.rti.webcgh.graphics.event.GraphicEventResponse;
import org.rti.webcgh.graphics.primitive.Arc;
import org.rti.webcgh.graphics.primitive.Circle;
import org.rti.webcgh.graphics.primitive.Cursor;
import org.rti.webcgh.graphics.primitive.Curve;
import org.rti.webcgh.graphics.primitive.GraphicPrimitive;
import org.rti.webcgh.graphics.primitive.Hyperlink;
import org.rti.webcgh.graphics.primitive.Line;
import org.rti.webcgh.graphics.primitive.Polygon;
import org.rti.webcgh.graphics.primitive.Polyline;
import org.rti.webcgh.graphics.primitive.Rectangle;
import org.rti.webcgh.graphics.primitive.SvgText;
import org.rti.webcgh.graphics.primitive.Text;
import org.rti.webcgh.units.Direction;
import org.rti.webcgh.units.HorizontalAlignment;
import org.rti.webcgh.units.Orientation;
import org.rti.webcgh.util.XmlUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


/**
 * Implementation of <code>DrawingCanvas</code> interface
 * for an SVG canvas.
 */
public final class SvgDrawingCanvas implements DrawingCanvas {
	
	/** Insertion point element. */
	private final Element insertionPoint;
	
	/** Top element in document tree. */
	private final Element docElement;
	
	/** SVG element factory. */
	private final SvgElementFactory factory;
	
	/** XML document. */
	private final Document document;
	
	
	
    /**
     * @return Returns the document.
     */
    public Document getDocument() {
        return document;
    }
    
    
	/**
	 * Constructor.
	 * @param doc XML document
	 */
	public SvgDrawingCanvas(final Document doc) {
	    this.document = doc;
		Element elmt = doc.getElementById("insertionPoint");
		docElement = doc.getDocumentElement();
		if (elmt ==  null) {
			elmt = doc.getDocumentElement();
		}
		insertionPoint = elmt;
		factory = new SvgElementFactory(doc);
	}
	
	
	/**
	 * Constructor.
	 * @param document Document
	 * @param root Root element of XML document
	 * @param insertionPoint Insertion point element in document
	 * @param factory Factory for generating elements
	 */
	private SvgDrawingCanvas(
			final Document document, final Element root,
			final Element insertionPoint, final SvgElementFactory factory
	) {
	    this.document = document;
		this.docElement = root;
		this.insertionPoint = insertionPoint;
		this.factory = factory;
	}
		
		
	/**
	 * Set global line width property that applies to all lines
	 * withiin this canvas.
	 * @param width Line width
	 */
	public void setLineWidth(final int width) {
		insertionPoint.setAttribute("stroke-width", String.valueOf(width));
	}
	
	
	/**
	 * Set id of canvas.
	 * @param id Canvas id
	 */
	public void setId(final String id) {
		insertionPoint.setAttribute("id", id);
	}
	
	
	/**
	 * Add a canvas.
	 * @param canvas A canvas
	 */
	public void add(final DrawingCanvas canvas) {
		SvgDrawingCanvas can = (SvgDrawingCanvas) canvas;
		insertionPoint.appendChild(can.getInsertionPoint());
	}
	
	
	/**
	 * Rotate canvas.
	 * @param degrees Degrees of rotation
	 * @param x X-coordinate of rotation point
	 * @param y Y-coordinate of rotation point
	 */
	public void rotate(final int degrees, final int x,
			final int y) {
		String value = "rotate(" + degrees + ", " + x + ", " + y + ")";
		String attributeValue = insertionPoint.getAttribute("transform");
		if (attributeValue != null && attributeValue.length() > 0) {
			value += " " + attributeValue;
		}
		insertionPoint.setAttribute("transform", value);
	}
	
	

	/**
	 * Add a canvas.
	 * @param canvas Canvas to add
	 * @param x X-coordinate of top left corner of inner canvas
	 * @param y Y-coordinate of top left corder of inner canvas
	 */
	public void add(final DrawingCanvas canvas, final int x,
			final int y) {
		SvgDrawingCanvas can = (SvgDrawingCanvas) canvas;
		can.move(x, y);
		insertionPoint.appendChild(can.getInsertionPoint());
	}
	

	/**
	 * Add a graphic object.
	 * @param graphic Graphic object
	 */
	public void add(final GraphicPrimitive graphic) {
		add(graphic, true);
	}
	
	
	/**
	 * Add a graphic object.
	 * @param graphic Graphic object
	 * @param overwriteCanvasProperties Do properties set for this
	 * primitive take precedence over same named properties of canvas?
	 */
	public void add(final GraphicPrimitive graphic,
			final boolean overwriteCanvasProperties) {
		Element element = factory.newElement(graphic);
		insertionPoint.appendChild(element);
		if (!overwriteCanvasProperties) {
			stripRedundantProperties(element);
		}
	}
	
	/**
	 * Set width of canvas.
	 * @param width Width in pixels
	 */
	public void setWidth(final int width) {
		docElement.setAttribute("width", String.valueOf(width));
	}
	
	
	/**
	 * Set height of canvas.
	 * @param height Height in pixels
	 */
	public void setHeight(final int height) {
		docElement.setAttribute("height", String.valueOf(height));
	}
	
	
	/**
	 * Set artibrary attribute.
	 * @param name Attribute name
	 * @param value Attribute value
	 */
	public void setAttribute(final String name, final String value) {
		this.insertionPoint.setAttribute(name, value);
	}
	
	
	/**
	 * Strip properties from element that are already defined
	 * by ancestors.
	 * @param element An element
	 */
	private void stripRedundantProperties(final Element element) {
		Map atts = getAttributesInLineage();
		for (Iterator it = atts.keySet().iterator(); it.hasNext();) {
			String name = (String) it.next();
			element.removeAttribute(name);
		}
	}
	
	
	/**
	 * Get all attributes in lineage to document root.
	 * @return Name/value pairs
	 */
	private Map getAttributesInLineage() {
		Map<String, String> attributes = new HashMap<String, String>();
		Node node = insertionPoint;
		while (node != null) {
			if (node instanceof Element) {
				NamedNodeMap map = node.getAttributes();
				for (int i = 0; i < map.getLength(); i++) {
					Attr a = (Attr) map.item(i);
					String name = a.getName();
					if (!attributes.containsKey(name)) {
						attributes.put(name, a.getValue());
					}
				}
			}
			node = node.getParentNode();
		}
		return attributes;
	}
	
	
	/**
	 * Return new text element.
	 * @param value String value of text
	 * @param x X-coordinate of text
	 * @param y Y-coordinate of text
	 * @param fontSize Font size
	 * @param alignment Alignment relative to (x,y) coordinate
	 * @param color Color of text
	 * @return New text element
	 */
	public Text newText(
			final String value, final int x, final int y,
			final int fontSize, final HorizontalAlignment alignment,
			final Color color
	) {
		return new SvgText(value, x, y, fontSize, alignment, color);
	}
	
	
	/**
	 * Rendered with of given text.
	 * @param text Text element
	 * @param fontSize Font size
	 * @return Rendered with of given text
	 */
	public int renderedWidth(final String text, final int fontSize) {
		return (int) ((double) text.length() * (double) fontSize * 0.6);
	}
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas).
	 * @return A new drawing tile
	 */
	public DrawingCanvas newTile() {
		Element g = factory.newGroupElement();
		return new SvgDrawingCanvas(this.document, docElement, g, factory);
	}
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas).
	 * @param tileName Name of tile
	 * @return A new drawing tile
	 */
	public DrawingCanvas newTile(final String tileName) {
		Element g = factory.newGroupElement();
		g.setAttribute("id", tileName);
		return new SvgDrawingCanvas(this.document, docElement, g, factory);
	}
	
	
	/**
	 * Set global property for data set names.  This is a list of all
	 * data sets represented in graphic.
	 * @param names Data set names
	 */
	public void setDataSetNames(final String[] names) {
		Element s = factory.newScriptElement();
		StringBuffer buff =
			new StringBuffer("var experimentNames = new Array(");
		if (names != null) {
			int count = 0;
			for (int i = 0; i < names.length; i++) {
				String name = names[i];
				if (count++ > 0) {
					buff.append(", ");
				}
				buff.append("\"" + name.trim() + "\"");
			}
		}
		buff.append(");");
		CDATASection c = factory.newCDataSection(buff.toString());
		insertionPoint.appendChild(s);
		s.appendChild(c);
	}
	
	
	/**
	 * Return new plotting canvas.
	 * @return Plotting canvas
	 */
	public static SvgDrawingCanvas newPlottingCanvas() {
	    Document doc = 
    		XmlUtils.loadDocument("svg/plotTemplate.svg", false);
	    return new SvgDrawingCanvas(doc);
	}
	
	
	/**
	 * Add a response to an event.
	 * @param event An event
	 * @param response A response
	 */
	public void addGraphicEventResponse(final GraphicEvent event,
			final String response) {
		this.insertionPoint.setAttribute(event.getName(), response);
	}
    
    
    /**
     * Set origin coordinate of canvas.
     * @param point Origin on canvas coordinate system
     */
    public void setOrigin(final Point point) {
        
    }
		
	
    /**
     * Move canvas.
     * @param dx X-coordinate delta
     * @param dy Y-coordinate delta
     */
	private void move(final int dx, final int dy) {
		String attName = "transform";
		String attValue = insertionPoint.getAttribute(attName);
		if (attValue != null && attValue.length() > 0) {
			attValue += " ";
		}
		if (attValue == null) {
			attValue = "";
		}
		attValue += "translate(" + dx + "," + dy + ")";
		insertionPoint.setAttribute(attName, attValue);
	}
	
	
	/**
	 * Get insertion point for graphics.
	 * @return Insertion point for graphics.
	 */
	private Element getInsertionPoint() {
		return insertionPoint;
	}
	
	
	/**
	 * Generates SVG elements.
	 */
	static class SvgElementFactory {
	    
	    // ================================
	    //        Constants
	    // ================================
	    
		/** Bezier approximation. */
	    private static final double BEZIER_APPROXIMATION = 1.25;
		
	    /** Hyperlink color. */
		private static final Color HYPERLINK_COLOR = Color.blue;
		
		/** XML document. */
		private final Document document;
		
		
		/**
		 * Constructor.
		 * @param document An XML document
		 */
		public SvgElementFactory(final Document document) {
			this.document = document;
		}
		
		
		/**
		 * Generate new XML element.
		 * @param graphic Graphic
		 * @return New XML element
		 */
		public Element newElement(final GraphicPrimitive graphic) {
			Element element = null;
			if (graphic instanceof Circle) {
				element = newCircleElement((Circle) graphic);
			} else if (graphic instanceof Line) {
				element = newLineElement((Line) graphic);
			} else if (graphic instanceof Rectangle) {
				element = newRectElement((Rectangle) graphic);
			} else if (graphic instanceof Polygon) {
			    element = newPolygonElement((Polygon) graphic);
			} else if (graphic instanceof Polyline) {
				element = newPolylineElement((Polyline) graphic);
			} else if (graphic instanceof Text) {
				element = newTextElement((SvgText) graphic);
			} else if (graphic instanceof Arc) {
				element = newArcElement((Arc) graphic);
			} else if (graphic instanceof Curve) {
			    element = newCurveElement((Curve) graphic);
			}
			addProperties(element, graphic);
			this.addGraphicEventResponse(element,
					graphic.getGraphicEventResponses());
			if (graphic.getToolTipText() != null) {
				addToolTip(element, graphic.getToolTipText());
			}
			if (graphic.getHyperlink() != null) {
				element = addHyperlink(element, graphic.getHyperlink());
			}
			if (graphic.getCursor() == Cursor.POINTER
				&& graphic.getUrl() == null) {
				element = addCursorModifier(element);
			}
			return element;
		}
		
		
		/**
		 * Generate new "g" element.
		 * @return New "g" element
		 */
		public Element newGroupElement() {
			return document.createElement("g");
		}
		
		
		/**
		 * Generate new <script/> element.
		 * @return New <script/> element
		 */
		public Element newScriptElement() {
			Element s = document.createElement("script");
			s.setAttribute("type", "text/ecmascript");
			return s;
		}
		
		
		/**
		 * Create new CDATASection element.
		 * @param value CDATA text
		 * @return New CDATASection element
		 */
		public CDATASection newCDataSection(final String value) {
			return document.createCDATASection(value);
		}
		
		    
	    
		/**
		 * Create rectangle element.
		 * @param rect Graphic rectangle
		 * @return XML element
		 */
		private Element newRectElement(final Rectangle rect) {
			Element el = document.createElement("rect");
			el.setAttribute("x", String.valueOf(rect.getX()));
			el.setAttribute("y", String.valueOf(rect.getY()));
			el.setAttribute("width", String.valueOf(rect.getWidth()));
			el.setAttribute("height", String.valueOf(rect.getHeight()));
			el.setAttribute("fill", rgb(rect.getColor()));
			el.setAttribute("stroke", rgb(rect.getColor()));
			return el;
		}
		
		
		/**
		 * Create new polygon element.
		 * @param polygon A polygon
		 * @return An element
		 */
		private Element newPolygonElement(final Polygon polygon) {
		    Element el = document.createElement("polygon");
		    el.setAttribute("fill", rgb(polygon.getColor()));
		    el.setAttribute("stroke", "none");
		    StringBuffer pointsStr = new StringBuffer();
		    Point[] points = polygon.getPoints();
		    for (int i = 0; i < points.length; i++) {
		        if (i > 0) {
		            pointsStr.append(" ");
		        }
		        Point point = points[i];
		        pointsStr.append(point.x + "," + point.y);
		    }
		    el.setAttribute("points", pointsStr.toString());
		    return el;
		}
		
		
		/**
		 * Create a line element.
		 * @param line Graphic line
		 * @return Line element
		 */
		private Element newLineElement(final Line line) {
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
		 * Create text node.
		 * @param text Graphic text
		 * @return Text node
		 */
		private Element newTextElement(final SvgText text) {
			Element el = document.createElement("text");
			el.setAttribute("x", String.valueOf(text.getX()));
			el.setAttribute("y", String.valueOf(text.getY()));
			el.setAttribute("font-size", String.valueOf(text.getFontSize()));
			el.setAttribute("stroke", rgb(text.getColor()));
			el.setAttribute("text-anchor", textAnchor(text.getAlignment()));
			el.setAttribute("font-family", "monospace");
			if (text.getRotation() != 0) {
				el.setAttribute("transform",
						"rotate(" + text.getRotation() + "," + text.getX()
						+ "," + text.getY() + ")");
			}
			el.appendChild(document.createTextNode(text.getValue()));
			if (text.getUrl() != null) {
				el.setAttribute("text-decoration", "underline");
				el.setAttribute("stroke", rgb(HYPERLINK_COLOR));
			}
			return el;
		}
		
		
		/**
		 * Create new arc element.
		 * @param arc An arc
		 * @return Arc element
		 */
		private Element newArcElement(final Arc arc) {
			Element el = document.createElement("path");
			int largeArc = 0;
			if (arc.isLargeArc()) {
				largeArc = 1;
			}
			int sweep = 1;
			if (arc.getOpeningDir() == Direction.UP) {
				sweep = 0;
			}
			String d =
				"M " + arc.getX1() + ", " + arc.getY1() + " A "
				+ arc.getXRadius() + ", " + arc.getYRadius()
				+ " " + arc.getRotation()
				+ " " + largeArc + " " + sweep + " " + arc.getX2()
				+ ", " + arc.getY2();
			el.setAttribute("d", d);
			el.setAttribute("stroke", rgb(arc.getColor()));
			el.setAttribute("stroke-width", String.valueOf(arc.getLineWidth()));
			el.setAttribute("fill", "none");
			return el;
		}
		
		
		/**
		 * Create new curve element.
		 * @param curve A curve
		 * @return New curve element
		 */
		private Element newCurveElement(final Curve curve) {
		    Point controlPt1 = new Point(curve.getX1(), curve.getY1());
		    Point controlPt2 = new Point(curve.getX2(), curve.getY2());
		    int delta = (int) ((double) curve.getHeight()
		    		* BEZIER_APPROXIMATION);
		    if (curve.getOrientation() == Orientation.HORIZONTAL) {
		        controlPt1.x += delta;
		        controlPt2.x += delta;
		    } else if (curve.getOrientation() == Orientation.VERTICAL) {
		        controlPt1.y += delta;
		        controlPt2.y += delta;
		    }
		    String d = "M" + curve.getX1() + "," + curve.getY1() + " "
		    	+ "C" + controlPt1.x + "," + controlPt1.y + " "
		    	+ controlPt2.x + "," + controlPt2.y + " "
		    	+ curve.getX2() + "," + curve.getY2();
		    Element el = document.createElement("path");
		    el.setAttribute("d", d);
		    el.setAttribute("stroke", rgb(curve.getColor()));
			el.setAttribute("stroke-width", String.valueOf(
					curve.getLineWidth()));
			el.setAttribute("fill", "white");
		    return el;
		}

		/**
		 * Add hyperlink around element.
		 * @param element Element 
		 * @param hyperlink Hyperlink URL
		 * @return Enclosing hyperlinked element
		 */
		private Element addHyperlink(final Element element,
				final Hyperlink hyperlink) {
			Element a = document.createElement("a");
			URL url = hyperlink.getUrl();
			String target = hyperlink.getTargetWindow();
			if (url != null) {
				a.setAttribute("xlink:href", url.toExternalForm());
			}
			if (target != null) {
				a.setAttribute("target", target);
			}
			a.setAttribute("cursor", "pointer");
			a.appendChild(element);
			return a;
		}
		
		
		/**
		 * Add a tool tip.
		 * @param element Element
		 * @param text Text to display when mouse passes over
		 */
		private void addToolTip(final Element element,
				final String text) {
			String toolTipText = "showToolTip(evt, '" + text + "')";
			element.setAttribute("onmouseover", toolTipText);
		}
		
		
		/**
		 * Add graphic event response to given element.
		 * @param element An element
		 * @param eventResponses An event response
		 */
		private void addGraphicEventResponse(
				final Element element,
				final GraphicEventResponse[] eventResponses
		) {
			if (eventResponses != null) {
				for (int i = 0; i < eventResponses.length; i++) {
					GraphicEventResponse resp = eventResponses[i];
					element.setAttribute(resp.getEvent().getName(),
							resp.getResponse());
				}
			}
		}
		
		
		/**
		 * Add cursor modifier to given element.
		 * @param element An element
		 * @return A new element with cursor modifier
		 */
		private Element addCursorModifier(final Element element) {
			Element a = document.createElement("a");
			a.appendChild(element);
			a.setAttribute("cursor", "pointer");
			return a;
		}
		
			

		/**
		 * Create SVG circle.
		 * @param circle Graphic circle
		 * @return SVG circle
		 */
		private Element newCircleElement(
				final Circle circle
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
		 * Create SVG polyine element.
		 * @param poly Graphic polyline
		 * @return SVG polyline element
		 */
		private Element newPolylineElement(final Polyline poly) {
			Element p = document.createElement("polyline");
			p.setAttribute("stroke", rgb(poly.getColor()));
			p.setAttribute("stroke-width", String.valueOf(poly.getWidth()));
			StringBuffer buff = new StringBuffer();
			List points = poly.getPoints();
			if (points != null) {
				int count = 0;
				for (Iterator it = points.iterator(); it.hasNext();) {
					Point point = (Point) it.next();
					int x = (int) point.getX();
					int y = (int) point.getY();
					if (count++ > 0) {
						buff.append(", ");
					}
					buff.append(String.valueOf(x) + " " + String.valueOf(y));
				}
				p.setAttribute("points", buff.toString());
			}
			if (poly.getFillColor() == null) {
				p.setAttribute("fill", "none");
			} else {
				p.setAttribute("fill", rgb(poly.getFillColor()));
			}
			return p;
		}
		
		
		/**
		 * Convert a Color object into an "rgb" format string version.
		 * @param color Color object
		 * @return String version of color in "rgb" format
		 */
		private String rgb(final Color color) {
			return
				"rgb("
				+ color.getRed() + ","
				+ color.getGreen() + ","
				+ color.getBlue()
				+ ")";
		}
		
		
		/**
		 * Return text anchor text.
		 * @param alignment Alignment (i.e. <code>SvgGraphicText.START</code>
		 * <code>SvgGraphicText.MIDDLE</code>, <code>SvgGraphicText.END</code>)
		 * @return Text anchor string
		 */
		private String textAnchor(final HorizontalAlignment alignment) {
			String anchor = "middle";
			if (alignment == HorizontalAlignment.LEFT_JUSTIFIED) {
					anchor = "start";
			} else if (alignment == HorizontalAlignment.CENTERED) {
					anchor = "middle";
			} else if (alignment == HorizontalAlignment.RIGHT_JUSTIFIED) {
					anchor = "end";
			}
			return anchor;
		}
		
		
		/**
		 * Add properties in given graphic primitive
		 * to given element.
		 * @param e An element
		 * @param g A graphic primitive
		 */
		private void addProperties(final Element e,
				final GraphicPrimitive g) {
			Properties props = g.getProperties();
			for (Enumeration names = props.propertyNames();
				names.hasMoreElements();) {
				String name = (String) names.nextElement();
				String value = props.getProperty(name);
				e.setAttribute(name, value);
			}
		}
	}
}
