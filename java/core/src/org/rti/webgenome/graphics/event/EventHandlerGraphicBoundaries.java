/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-07-05 13:23:30 $


*/

package org.rti.webgenome.graphics.event;

import java.util.HashSet;
import java.util.Set;

import org.rti.webgenome.graphics.io.ClickBoxes;

/**
 * Constains graphic features that can be used to presentation
 * tier code to create client-side event handling code.
 * @author dhall
 *
 */
public class EventHandlerGraphicBoundaries {

	/** Mouseover stripes. */
	private Set<MouseOverStripes> mouseOverStripes =
		new HashSet<MouseOverStripes>();
	
	/** Click boxes. */
	private Set<ClickBoxes> clickBoxes = new HashSet<ClickBoxes>();

	/**
	 * Get click boxes.
	 * @return Click boxes.
	 */
	public final Set<ClickBoxes> getClickBoxes() {
		return clickBoxes;
	}

	/**
	 * Set click boxes.
	 * @param clickBoxes Click boxes.
	 */
	public final void setClickBoxes(
			final Set<ClickBoxes> clickBoxes) {
		this.clickBoxes = clickBoxes;
	}

	/**
	 * Get mouseover stripes.
	 * @return Mouseover stripes.
	 */
	public final Set<MouseOverStripes> getMouseOverStripes() {
		return mouseOverStripes;
	}

	/**
	 * Set mouseover stripes.
	 * @param mouseOverStripes Mouseover stripes.
	 */
	public final void setMouseOverStripes(
			final Set<MouseOverStripes> mouseOverStripes) {
		this.mouseOverStripes = mouseOverStripes;
	}
	
	
	/**
	 * Constructor.
	 */
	public EventHandlerGraphicBoundaries() {
		
	}

	/**
	 * Constructor.
	 * @param mouseOverStripes Mouseover stripes
	 * @param clickBoxes Click boxes
	 */
	public EventHandlerGraphicBoundaries(
			final Set<MouseOverStripes> mouseOverStripes,
			final Set<ClickBoxes> clickBoxes) {
		this.mouseOverStripes = mouseOverStripes;
		this.clickBoxes = clickBoxes;
	}
	
	
	/**
	 * Add mouseover stripes.
	 * @param stripes Mouseover stripes.
	 */
	public final void add(final MouseOverStripes stripes) {
		this.mouseOverStripes.add(stripes);
	}
	
	
	/**
	 * Add mouseover stripes.
	 * @param stripes Mouseover stripes
	 */
	public final void addAll(final Set<MouseOverStripes> stripes) {
		this.mouseOverStripes.addAll(stripes);
	}
	
	
	/**
	 * Add click boxes.
	 * @param clickBoxes Click boxes
	 */
	public final void add(final ClickBoxes clickBoxes) {
		this.clickBoxes.add(clickBoxes);
	}
}
