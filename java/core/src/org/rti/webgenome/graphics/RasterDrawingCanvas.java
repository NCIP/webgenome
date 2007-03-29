/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $

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

package org.rti.webgenome.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebcghSystemException;
import org.rti.webgenome.graphics.event.GraphicEvent;
import org.rti.webgenome.graphics.primitive.Arc;
import org.rti.webgenome.graphics.primitive.Circle;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Line;
import org.rti.webgenome.graphics.primitive.Polygon;
import org.rti.webgenome.graphics.primitive.Polyline;
import org.rti.webgenome.graphics.primitive.RasterText;
import org.rti.webgenome.graphics.primitive.Rectangle;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.units.Direction;
import org.rti.webgenome.units.HorizontalAlignment;

/**
 * A drawing canvas that is used to generate
 * raster graphic files, such as PNG and GIF.
 * @author dhall
 *
 */
public final class RasterDrawingCanvas implements DrawingCanvas {
    
    
    // ==============================
    //      Constants
    // ==============================
    
    /** Type of buffered image. */
    public static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;
    
    /** Logger. */
    private static final Logger LOGGER =
    	Logger.getLogger(RasterDrawingCanvas.class);
    
    /** Background color. */
    private static final Color BG_COLOR = new Color(255, 255, 204);
    
    
    // =====================================
    //         Attributes
    // =====================================
    
    /** Graphic primitives to draw on this canvas at rendering time. */
    private final List<GraphicPrimitive> graphicPrimitives =
        new ArrayList<GraphicPrimitive>();
    
    /** Width of plot in pixels. */
    private int width = 0;
    
    /** Height of plot in pixels. */
    private int height = 0;
        
        
    // =================================
    //      Constructors
    // =================================

    /**
     * Constructor.
     */
    public RasterDrawingCanvas() {
   
    }
    
    
    // ==================================
    //         DrawingCanvas interface
    // ==================================
    
    /**
     * Add a graphic primitive.
     * @param element Primitive element
     */
    public void add(final GraphicPrimitive element) {
        if (element == null) {
            throw new IllegalArgumentException(
                    "Graphic primitive cannot be null");
        }
        LOGGER.debug("Adding graphic element of type "
        		+ element.getClass().getName());
        this.graphicPrimitives.add(element);
    }
    
    
    /**
     * Rotate canvas.
     * @param degrees Degrees of rotation
     * @param x X-coordinate of rotation point
     * @param y Y-coordinate of rotation point
     */
    public void rotate(final int degrees, final int x, final int y) {
        
    }
    
    
    /**
     * Add a graphic primitive.
     * @param graphic Graphic primitive
     * @param overwriteCanvasProperties Overwrite global properties
     * for graphic primitives set within canvas?
     */
    public void add(final GraphicPrimitive graphic,
            final boolean overwriteCanvasProperties) {
        this.graphicPrimitives.add(graphic);
    }
    
    
    /**
     * Return new drawing tile (i.e. a portion of canvas).
     * @return A new drawing tile
     */
    public DrawingCanvas newTile() {
        return new RasterDrawingCanvas();
    }
    
    
    /**
     * Return new drawing tile (i.e. a portion of canvas).
     * @param tileName Name of tile
     * @return A new drawing tile
     */
    public DrawingCanvas newTile(final String tileName) {
        return null;
    }
    
    
    /**
     * Add a canvas.
     * @param canvas A canvas
     */
    public void add(final DrawingCanvas canvas) {
        
    }
    
    
    /**
     * Add a canvas at specified location.
     * @param canvas A canvas
     * @param x X-coordinate of insertion point
     * @param y Y-coordinate of insertion point
     */
    public void add(final DrawingCanvas canvas, final int x, final int y) {
        
    }
    
    
    /**
     * Set global line width property that applies to all lines
     * within this canvas.
     * @param width Line width
     */
    public void setLineWidth(final int width) {
        
    }
    
    
    /**
     * Set id of canvas.
     * @param id Canvas id
     */
    public void setId(final String id) {
        
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
            final String value, final int x, final int y, final int fontSize,
            final HorizontalAlignment alignment, final Color color
    ) {
        return new RasterText(value, x, y, fontSize, alignment, color);
    }
    
    
    /**
     * Set global property for data set names.  This is a list of all
     * data sets represented in graphic.
     * @param names Data set names
     */
    public void setDataSetNames(final String[] names) {
        
    }
    
    
    /**
     * Rendered with of given text.
     * @param text Text element
     * @param fontSize Font size
     * @return Rendered with of given text
     */
    public int renderedWidth(final String text, final int fontSize) {
       BufferedImage img = new BufferedImage(1, 1,
                IMAGE_TYPE);
       Graphics graphics = img.getGraphics();
       Font font = graphics.getFont();
       Font newFont = new Font(font.getFontName(), font.getStyle(), fontSize);
       graphics.setFont(newFont);
       FontMetrics fm = graphics.getFontMetrics();
       return fm.stringWidth(text);
    }
    
    
    /**
     * Set width of canvas.
     * @param width Width in pixels
     */
    public void setWidth(final int width) {
        this.width = width;
    }
    
    
    /**
     * Set height of canvas.
     * @param height Height in pixels
     */
    public void setHeight(final int height) {
        this.height = height;
    }
    
    
    /**
     * Set artibrary attribute.
     * @param name Attribute name
     * @param value Attribute value
     */
    public void setAttribute(final String name, final String value) {
        
    }
    
    /**
     * Add a response to an event.
     * @param event An event
     * @param response A response
     */
    public void addGraphicEventResponse(final GraphicEvent event,
            final String response) {
        
    }
    
    
    /**
     * Set origin coordinate of canvas.
     * @param origin Origin on canvas coordinate system
     */
    public void setOrigin(final Point origin) {
        
    }
    
    
    // ==================================
    //    Additional business methods
    // ==================================
    
    /**
     * Render canvas as a buffered image that can be
     * output in various graphical formats (e.g., PNG, GIF).
     * @return A buffered image
     */
    public BufferedImage toBufferedImage() {
        
        // Initialize graphics
        BufferedImage img = new BufferedImage(this.width, this.height,
                IMAGE_TYPE);
        Graphics2D graphics = (Graphics2D) img.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Add white background
        Rectangle bg = new Rectangle(0, 0, this.width, this.height,
                BG_COLOR);
        this.render(bg, graphics);
        
        // Render elements
        this.render(this, graphics);
        return img;
    }
    
    
    /**
     * Recursive method to render the contents of given
     * canvas using the given graphics object.  This method
     * is recursive because a <code>RasterDrawingCanvas</code>
     * object may contain <code>RasterDrawingCanvas</code>
     * children.
     * @param canvas A canvas
     * @param graphics A graphics object
     */
    private void render(final RasterDrawingCanvas canvas,
            final Graphics2D graphics) {
        for (GraphicPrimitive prim : canvas.graphicPrimitives) {
            this.render(prim, graphics);
        }
    }
    
    
    /**
     * Render given graphic primitive using given graphics.
     * @param prim A graphic primitive
     * @param graphics Graphics
     */
    private void render(final GraphicPrimitive prim,
            final Graphics2D graphics) {
        graphics.setColor(prim.getColor());
        if (prim instanceof Circle) {
            Circle c = (Circle) prim;
            int correction = c.getRadius();
            if (c.isFilled()) {
	            graphics.fillOval(c.getX() - correction,
	            		c.getY() - correction, c.getRadius() * 2,
	                    c.getRadius() * 2);
            } else {
            	graphics.drawOval(c.getX() - correction,
	            		c.getY() - correction, c.getRadius() * 2,
	                    c.getRadius() * 2);
            }
        } else if (prim instanceof Line) {
            Line l = (Line) prim;
            graphics.setStroke(new BasicStroke(l.getWidth()));
            graphics.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        } else if (prim instanceof Rectangle) {
            Rectangle r = (Rectangle) prim;
            graphics.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        } else if (prim instanceof Polyline) {
            Polyline p = (Polyline) prim;
            graphics.setStroke(new BasicStroke(p.getWidth()));
            List<Point> points = p.getPoints();
            if (points != null) {
                for (int i = 1; i < points.size(); i++) {
                    Point point1 = points.get(i - 1);
                    Point point2 = points.get(i);
                    graphics.drawLine(point1.x, point1.y, point2.x, point2.y);
                }
            }
        } else if (prim instanceof Polygon) {
            Polygon p = (Polygon) prim;
            Point[] points = p.getPoints();
            if (points != null) {
                int[] x = new int[points.length];
                int[] y = new int[points.length];
                for (int i = 0; i < points.length; i++) {
                    Point point = points[i];
                    x[i] = point.x;
                    y[i] = point.y;
                }
                graphics.fillPolygon(x, y, points.length);
            }
        } else if (prim instanceof Text) {
            Text t = (Text) prim;
            Font font = graphics.getFont();
            Font newFont = new Font(font.getFontName(), font.getStyle(),
                    t.getFontSize());
            graphics.setFont(newFont);
            AffineTransform transform = null;
            if (t.getRotation() != 0) {
            	transform =
            		AffineTransform.getRotateInstance(t.getRotation(),
            				t.getX(), t.getY());
            	graphics.transform(transform);
            }
            int x = 0;
            if (t.getAlignment() == HorizontalAlignment.LEFT_JUSTIFIED
            		|| t.getAlignment() == HorizontalAlignment.LEFT_OF) {
            	x = t.getX();
            } else if (t.getAlignment()
            		== HorizontalAlignment.RIGHT_JUSTIFIED
            		|| t.getAlignment()
            		== HorizontalAlignment.RIGHT_OF) {
            	x = t.getX() - renderedWidth(t.getValue(), t.getFontSize());
            } else if (t.getAlignment() == HorizontalAlignment.CENTERED
            		|| t.getAlignment() == HorizontalAlignment.ON_ZERO) {
            	x = t.getX()
            	- renderedWidth(t.getValue(), t.getFontSize()) / 2;
            }
            graphics.drawString(t.getValue(), x, t.getY());
            if (transform != null) {
            	try {
					graphics.transform(transform.createInverse());
				} catch (NoninvertibleTransformException e) {
					throw new WebcghSystemException(
							"Error reversing affine transform", e);
				}
            }
        } else if (prim instanceof Arc) {
        	Arc a = (Arc) prim;
        	int x = a.getX() - a.getWidth() / 2;
        	int y = a.getY() - a.getHeight() / 2;
        	int startAngle = 0, arcAngle = 0;
        	if (a.getDirection() == Direction.UP) {
        		startAngle = 180;
        		arcAngle = -180;
        	} else if (a.getDirection() == Direction.DOWN) {
        		startAngle = 180;
        		arcAngle = 180;
        	} else if (a.getDirection() == Direction.LEFT) {
        		startAngle = 90;
        		arcAngle = 180;
        	} else if (a.getDirection() == Direction.RIGHT) {
        		startAngle = 90;
        		arcAngle = -180;
        	}
        	graphics.drawArc(x, y, a.getWidth(), a.getHeight(),
        			startAngle, arcAngle);
        }
    }
}
