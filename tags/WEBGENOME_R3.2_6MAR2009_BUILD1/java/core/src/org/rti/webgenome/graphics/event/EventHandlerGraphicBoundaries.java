/*
$Revision: 1.2 $
$Date: 2007-07-05 13:23:30 $

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
