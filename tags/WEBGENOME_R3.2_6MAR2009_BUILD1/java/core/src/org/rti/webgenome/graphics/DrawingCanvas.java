/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/


package org.rti.webgenome.graphics;

import java.awt.Color;
import java.awt.Point;

import org.rti.webgenome.graphics.event.GraphicEvent;
import org.rti.webgenome.graphics.primitive.GraphicPrimitive;
import org.rti.webgenome.graphics.primitive.Text;
import org.rti.webgenome.units.HorizontalAlignment;


/**
 * Canvas that supports basic drawing functionality.
 */
public interface DrawingCanvas {
	
	
	/**
	 * Add a graphic primitive.
	 * @param element Primitive element
	 */
	void add(GraphicPrimitive element);
	
	
	/**
	 * Rotate canvas.
	 * @param degrees Degrees of rotation
	 * @param x X-coordinate of rotation point
	 * @param y Y-coordinate of rotation point
	 */
	void rotate(int degrees, int x, int y);
	
	
	/**
	 * Add a graphic primitive.
	 * @param graphic Graphic primitive
	 * @param overwriteCanvasProperties Overwrite global properties
	 * for graphic primitives set within canvas?
	 */
	void add(GraphicPrimitive graphic, boolean overwriteCanvasProperties);
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas).
	 * @return A new drawing tile
	 */
	DrawingCanvas newTile();
	
	
	/**
	 * Return new drawing tile (i.e. a portion of canvas).
	 * @param tileName Name of tile
	 * @return A new drawing tile
	 */
	DrawingCanvas newTile(String tileName);
	
	
	/**
	 * Add a canvas.
	 * @param canvas A canvas
	 */
	void add(DrawingCanvas canvas);
	
	
	/**
	 * Add a canvas at specified location.
	 * @param canvas A canvas
	 * @param x X-coordinate of insertion point
	 * @param y Y-coordinate of insertion point
	 */
	void add(DrawingCanvas canvas, int x, int y);
	
	
	/**
	 * Set global line width property that applies to all lines.
	 * withiin this canvas
	 * @param width Line width
	 */
	void setLineWidth(int width);
	
	
	/**
	 * Set id of canvas.
	 * @param id Canvas id
	 */
	void setId(String id);
	
	
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
	Text newText(
		String value, int x, int y, int fontSize,
        HorizontalAlignment alignment, Color color
	);
	
	
	/**
	 * Set global property for data set names.  This is a list of all
	 * data sets represented in graphic.
	 * @param names Data set names
	 */
	void setDataSetNames(String[] names);
	
	
	/**
	 * Rendered with of given text.
	 * @param text Text element
	 * @param fontSize Font size
	 * @return Rendered with of given text
	 */
	int renderedWidth(String text, int fontSize);
	
	
	/**
	 * Set width of canvas.
	 * @param width Width in pixels
	 */
	void setWidth(int width);
	
	
	/**
	 * Set height of canvas.
	 * @param height Height in pixels
	 */
	void setHeight(int height);
	
	
	/**
	 * Set artibrary attribute.
	 * @param name Attribute name
	 * @param value Attribute value
	 */
	void setAttribute(String name, String value);
	
	/**
	 * Add a response to an event.
	 * @param event An event
	 * @param response A response
	 */
	void addGraphicEventResponse(GraphicEvent event, String response);
    
    
    /**
     * Set origin coordinate of canvas.
     * @param origin Origin on canvas coordinate system
     */
    void setOrigin(Point origin);
}
