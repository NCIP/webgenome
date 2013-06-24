/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;

import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

import junit.framework.TestCase;

/**
 * Tester for <code>Background</code>.
 * @author dhall
 *
 */
public final class BackgroundTester extends TestCase {
	
    /**
     * Name of directory holding graphic files produced
     * during tests.  The absolute path will be a
     * concatenation of the 'test.dir' property in
     * the file 'unit_test.properties' and this
     * constant.
     */
    private static final String TEST_DIR_NAME = "background-tester";
    
    /**
     * Test that the background is being painted
     * properly.
     */
    public void testPaint() {
    	RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME));
    	Background bg1 = new Background(400, 200, Color.yellow);
        panel.add(bg1, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        Background bg2 = new Background(400, 200, Color.green);
        panel.add(bg2, HorizontalAlignment.CENTERED,
                VerticalAlignment.BELOW);
        panel.toPngFile("background.png");
    }
}
