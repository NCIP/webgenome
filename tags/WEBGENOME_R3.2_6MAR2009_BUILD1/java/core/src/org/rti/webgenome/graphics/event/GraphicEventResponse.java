/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/graphics/event/GraphicEventResponse.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:27 $



*/


// TODO: Make handling event more generic, if possible
package org.rti.webgenome.graphics.event;

/**
 * Responses to graphic events
 */
public class GraphicEventResponse {
	
	protected final GraphicEvent event;
	protected final String response;
	
	
	/**
	 * Constructor
	 * @param event Event
	 * @param response String describing response
	 */
	public GraphicEventResponse(GraphicEvent event, String response) {
		this.event = event;
		this.response = response;
	}
	
	
	/**
	 * Get event
	 * @return Event
	 */
	public GraphicEvent getEvent() {
		return event;
	}
	
	
	/**
	 * Get reponse
	 * @return Response
	 */
	public String getResponse() {
		return response;
	}

}
