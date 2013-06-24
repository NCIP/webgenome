/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-09-08 22:27:24 $


*/

package org.rti.webgenome.graphics.primitive;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.rti.webgenome.graphics.RasterDrawingCanvas;
import org.rti.webgenome.units.HorizontalAlignment;

/**
 * Extension of <code>Text</code> for raster
 * drawing canvases.
 * @author dhall
 *
 */
public final class RasterText extends Text {
    
    
    
    // ============================
    //     Overridden methods
    // ============================

    /**
     * Constructor.
     * @param value Text value
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param fontSize Font size
     * @param alignment Alignment relative to (x,y) coordinate
     * @param color Color
     */
    public RasterText(final String value, final int x,
            final int y, final int fontSize,
            final HorizontalAlignment alignment,
            final Color color) {
        super(value, x, y, fontSize, alignment, color);
    }

    /**
     * @override
     * @return Rendered width in pixels
     */
    public int renderedWidth() {
    	int width = 0;
    	if (this.getValue() != null) {
	       BufferedImage img = new BufferedImage(1, 1,
	                RasterDrawingCanvas.IMAGE_TYPE);
	       Graphics graphics = img.getGraphics();
	       Font font = graphics.getFont();
	       Font newFont = new Font(font.getFontName(), font.getStyle(),
	    		   this.getFontSize());
	       graphics.setFont(newFont);
	       FontMetrics fm = graphics.getFontMetrics();
	       width = fm.stringWidth(this.getValue());
    	}
    	return width;
    }
}
