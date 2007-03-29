/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:30 $

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

package org.rti.webgenome.webui.util;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.util.SystemUtils;


/**
 * Defines an area within a graphic where moving the mouse
 * over a series of horizontal or vertical rectangular
 * "stripes" triggers and event handler.  A typical response
 * will be to show some tool tip text.
 * @author dhall
 *
 */
public class MouseOverStripes implements Serializable {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");

	
	// ==========================
	//     Attributes
	// ==========================
	
	/**
	 * Orientation of stripes.  If horizontal,
	 * then the <code>start</code> and <code>end</code>
	 * properties of nested <code>MouseOverStripe</code>
	 * objects will correspond to X-coordinates.
	 * If vertical, the these properties will correspond
	 * to Y-coordinates.
	 */
	private final Orientation orientation;
	
	/** Width of mouseover area in pixels. */
	private final int width;
	
	/** Height of mouseover area in pixels. */
	private final int height;
	
	/** Origin of mouseover area relative to entire graphic. */
	private final Point origin;
	
	/**
	 * Individual mouseover "stripes."  The start and end
	 * coordinates of the stripes are relative to
	 * <code>origin</code>--i.e., they are not absolute
	 * coordinates relative to the entire graphic.
	 */
	private final List<MouseOverStripe> stripes =
		new ArrayList<MouseOverStripe>();
	
	
	// ===============================
	//      Getters
	// ===============================

	/**
	 * Get height of mouseover area.
	 * @return Height in pixels.
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * Get orientation of mouseover area.  If horizontal,
	 * then the <code>start</code> and <code>end</code>
	 * properties of nested <code>MouseOverStripe</code>
	 * objects will correspond to X-coordinates.
	 * If vertical, the these properties will correspond
	 * to Y-coordinates.
	 * @return Orientation
	 */
	public final Orientation getOrientation() {
		return orientation;
	}

	
	/**
	 * Get origin of mouseover area in relation
	 * to the entire graphic.
	 * @return Origin of mouseover area.
	 */
	public final Point getOrigin() {
		return origin;
	}

	
	/**
	 * Get mouseover stripes.  The start and end
	 * coordinates of the stripes are relative to
	 * <code>origin</code>--i.e., they are not absolute
	 * coordinates relative to the entire graphic.
	 * If <code>orientation</code> is horizontal,
	 * then the <code>start</code> and <code>end</code>
	 * properties of nested <code>MouseOverStripe</code>
	 * objects will correspond to X-coordinates.
	 * If vertical, the these properties will correspond
	 * to Y-coordinates.
	 * @return Mouseover stripes.
	 */
	public final List<MouseOverStripe> getStripes() {
		return stripes;
	}

	
	/**
	 * Get width of mouseover area.
	 * @return Width in pixels.
	 */
	public final int getWidth() {
		return width;
	}
	
	
	// ================================
	//       Constructors
	// ================================
	
	
	/**
	 * Constructor.
	 * @param orientation Orientation of stripes.  If horizontal,
	 * then the <code>start</code> and <code>end</code>
	 * properties of nested <code>MouseOverStripe</code>
	 * objects will correspond to X-coordinates.
	 * If vertical, the these properties will correspond
	 * to Y-coordinates.
	 * @param width Width of mouseover area in pixels.
	 * @param height Height of mouseover area in pixels.
	 */
	public MouseOverStripes(final Orientation orientation,
			final int width, final int height) {
		this(orientation, width, height, new Point(0, 0));
	}
	
	/**
	 * Constructor.
	 * @param orientation Orientation of stripes.  If horizontal,
	 * then the <code>start</code> and <code>end</code>
	 * properties of nested <code>MouseOverStripe</code>
	 * objects will correspond to X-coordinates.
	 * If vertical, the these properties will correspond
	 * to Y-coordinates.
	 * @param width Width of mouseover area in pixels.
	 * @param height Height of mouseover area in pixels.
	 * @param origin Origin of mouseover area relative to
	 * entire graphic
	 */
	public MouseOverStripes(final Orientation orientation,
			final int width, final int height,
			final Point origin) {
		super();
		this.orientation = orientation;
		this.width = width;
		this.height = height;
		this.origin = origin;
	}

	
	// =================================
	//      Additional business methods
	// =================================
	
	/**
	 * Add a mouseover stripe.
	 * @param stripe A mouseover stripe.
	 */
	public final void add(final MouseOverStripe stripe) {
		this.stripes.add(stripe);
	}
}
