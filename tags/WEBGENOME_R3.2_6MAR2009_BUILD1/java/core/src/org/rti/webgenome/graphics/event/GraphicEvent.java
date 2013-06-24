/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/event/GraphicEvent.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $



*/

package org.rti.webgenome.graphics.event;

/**
 * Event fired by graphic
 */
public class GraphicEvent {
	
	/**
	 * Fired when left mouse button depressed
	 */
	public static final GraphicEvent mouseDownEvent = 
		new GraphicEvent("onmousedown");
	
	/**
	 * Fired when mouse moves
	 */
	public static final GraphicEvent mouseMoveEvent =
		new GraphicEvent("onmousemove");
	
	/**
	 * Fired when mouse clicked
	 */
	public static final GraphicEvent mouseClickEvent =
		new GraphicEvent("onclick");
		
		
	private final String name;
	
	
	/**
	 * Constructor
	 * @param name Name of event
	 */
	private GraphicEvent(String name) {
		this.name = name;
	}
	
	
	/**
	 * Get event name
	 * @return Name of event
	 */
	public String getName() {
		return name;
	}

}
