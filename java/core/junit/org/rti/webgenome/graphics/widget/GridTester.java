/*
$Revision: 1.2 $
$Date: 2007-04-09 22:19:50 $


*/

package org.rti.webgenome.graphics.widget;

import java.awt.Color;

import junit.framework.TestCase;

import org.rti.webgenome.graphics.widget.Axis;
import org.rti.webgenome.graphics.widget.Background;
import org.rti.webgenome.graphics.widget.Grid;
import org.rti.webgenome.units.HorizontalAlignment;
import org.rti.webgenome.units.Location;
import org.rti.webgenome.units.Orientation;
import org.rti.webgenome.units.VerticalAlignment;
import org.rti.webgenome.util.UnitTestUtils;

/**
 * Test class for <code>Grid</code>.
 * @author dhall
 *
 */
public final class GridTester extends TestCase {
    
    // ===============================
    //     Constants
    // ===============================
    
    /**
     * Name of directory holding graphic files produced
     * during tests.  The absolute path will be a
     * concatenation of the 'test.dir' property in
     * the file 'unit_test.properties' and this
     * constant.
     */
    private static final String TEST_DIR_NAME = "grid-tester";
    
    /**
     * Draw horizontal grid.
     */
    public void testHorizontal() {
        RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME));
        Axis axis = new Axis(0, 10, 400, Orientation.VERTICAL,
                Location.LEFT_OF, panel.getDrawingCanvas());
        Grid grid = axis.newGrid(400, 400, Color.white,
                panel);
        Background background = new Background(400, 400, Color.yellow);
        panel.add(background, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED);
        panel.add(axis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.BOTTOM_JUSTIFIED);
        panel.toPngFile("horizontal.png");
    }
    
    
    /**
     * Draw vertical grid.
     */
    public void testVertical() {
        RasterFileTestPlotPanel panel =
            new RasterFileTestPlotPanel(
            		UnitTestUtils.createUnitTestDirectory(TEST_DIR_NAME));
        Axis axis = new Axis(0, 10, 400, Orientation.HORIZONTAL,
                Location.ABOVE, panel.getDrawingCanvas());
        Grid grid = axis.newGrid(400, 400, Color.white,
                panel);
        Background background = new Background(400, 400, Color.yellow);
        panel.add(background, HorizontalAlignment.CENTERED,
                VerticalAlignment.CENTERED);
        panel.add(grid, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.TOP_JUSTIFIED);
        panel.add(axis, HorizontalAlignment.LEFT_JUSTIFIED,
                VerticalAlignment.TOP_JUSTIFIED);
        panel.toPngFile("vertical.png");
    }
}
