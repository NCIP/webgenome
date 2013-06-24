/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $


*/


package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.rti.webgenome.graphics.event.GraphicEvent;
import org.rti.webgenome.graphics.event.GraphicEventResponse;

/**
 * Abstract base class for primitive graphic objects.
 */
public abstract class GraphicPrimitive {
	
	/** Color of primitive. */
	private Color color = Color.black;
	
	/** Hyperlink associated with primitive. */
	private Hyperlink hyperlink = null;
	
	/** Tool tip associated with primitive. */
	private String toolTipText = null;
	
	/** Event responses associated with primitive. */
	private List<GraphicEventResponse> eventResponses =
		new ArrayList<GraphicEventResponse>();
	
	/** Cursor to display when pointer is over primitive. */
	private Cursor cursor = Cursor.NORMAL;
	
	/** Configurable properties associated with primitive. */
	private Properties properties = new Properties();
	
	
	// =================================
	//        Getters/setters
	// =================================
	
	/**
	 * Get configurable properties associated with
	 * primitive.
	 * @return Configurable properties associated with
	 * primitive.
	 */
	public final Properties getProperties() {
		return this.properties;
	}
	
	
	/**
	 * Get event responses associated with
	 * graphic primitive.
	 * @return Event responses associated with
	 * graphic primitive.
	 */
	public final List getEventResponses() {
		return eventResponses;
	}


	/**
	 * Set event responses associated with
	 * graphic primitive.
	 * @param eventResponses Event responses
	 * associated with graphic primitive.
	 */
	public final void setEventResponses(
			final List<GraphicEventResponse> eventResponses) {
		this.eventResponses = eventResponses;
	}


	/**
	 * Set configurable properties associated with
	 * primitive.
	 * @param properties Configurable properties
	 * associated with primitive.
	 */
	public final void setProperties(final Properties properties) {
		this.properties = properties;
	}


	/**
	 * Get color of graphic primitive.
	 * @return Color of graphic primitive.
	 */
	public final Color getColor() {
		return color;
	}

	
	/**
	 * Set color of graphic primitive.
	 * @param color Color of graphic primitive.
	 */
	public final void setColor(final Color color) {
		this.color = color;
	}


	/**
	 * Get text of tool tip associated with
	 * graphic primitive.
	 * @return Text of tool tip associated with
	 * graphic primitive.
	 */
	public final String getToolTipText() {
		return toolTipText;
	}

	/**
	 * Set text of tool tip associated with
	 * graphic primitive.
	 * @param string Text of tool tip associated with
	 * graphic primitive.
	 */
	public final void setToolTipText(final String string) {
		toolTipText = string;
	}
	
	/**
	 * Get sytle of cursor for when pointer is over this
	 * graphic primitive.
	 * @return Sytle of cursor for when pointer is over this
	 * graphic primitive. 
	 */
	public final Cursor getCursor() {
		return cursor;
	}

	/**
	 * Set sytle of cursor for when pointer is over this
	 * graphic primitive.
	 * @param c Sytle of cursor for when pointer is over this
	 * graphic primitive.
	 */
	public final void setCursor(final Cursor c) {
		cursor = c;
	}

	/**
	 * Get hyperlink associated with this graphic
	 * primitive.
	 * @return Returns the hyperlink associated with this graphic
	 * primitive.
	 */
	public final Hyperlink getHyperlink() {
		return hyperlink;
	}
	
	
	/**
	 * Set hyperlink associated with this graphic
	 * primitive.
	 * @param hyperlink Hyperlink associated with this graphic
	 * primitive.
	 */
	public final void setHyperlink(final Hyperlink hyperlink) {
		this.hyperlink = hyperlink;
	}
	
	
	// ========================
	//     Constructors
	// ========================
	
	/**
	 * Constructor.
	 */
	protected GraphicPrimitive() {
		
	}
	
	/**
	 * Constructor.
	 * @param color Color of this graphic primitive.
	 */
	protected GraphicPrimitive(final Color color) {
		super();
		this.color = color;
	}
	
	// =========================
	//     Business methods
	// =========================


	/**
	 * Set a property to associate with this graphic
	 * primitive.
	 * @param name Name of property.
	 * @param value Value of property.
	 */
	public final void setProperty(final String name, final String value) {
		properties.setProperty(name, value);
	}
	
	/**
	 * Get URL of hyperlink associated with this graphic
	 * primitive.
	 * @return URL of hyperlink associated with this graphic
	 * primitive.
	 */
	public final URL getUrl() {
		URL url = null;
		if (hyperlink != null) {
			url = hyperlink.getUrl();
		}
		return url;
	}

	/**
	 * Set URL of hyperlink associated with this graphic
	 * primitive.
	 * @param url URL of hyperlink associated with this graphic
	 * primitive.
	 */
	public final void setUrl(final URL url) {
		if (hyperlink == null) {
			hyperlink = new Hyperlink();
		}
		hyperlink.setUrl(url);
	}
	
	/**
	 * Add a response to an event.
	 * @param event An event
	 * @param response A response
	 */
	public final void addGraphicEventResponse(
			final GraphicEvent event, final String response) {
		eventResponses.add(new GraphicEventResponse(event, response));
	}
	
	// TODO: Rename to getEventResponsesAsArray
	/**
	 * Get responses to graphic events.
	 * @return Responses to graphic event as an array
	 */
	public final GraphicEventResponse[] getGraphicEventResponses() {
		GraphicEventResponse[] responses = new GraphicEventResponse[0];
		responses = (GraphicEventResponse[]) eventResponses.toArray(responses);
		return responses;
	}
	
	// ===============================
	//       Abstract methods
	// ===============================
	
	/**
	 * Move graphic primitive.
	 * @param deltaX Change in X-coordinates in pixels
	 * @param deltaY Change in Y-coordinates in pixels
	 */
	public abstract void move(int deltaX, int deltaY);
}
