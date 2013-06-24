/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.sandbox.graphics;

import java.awt.Canvas;
import java.awt.Graphics;

import javax.swing.JFrame;

import flanagan.interpolation.CubicSpline;


/**
 * Tests curve drawing methods.
 * @author dhall
 *
 */
public class CurveTest extends JFrame {
	
	/** Serialized version. */
	private static final long serialVersionUID = 1;
	
	/** X-coordinates for test points. */
	private static final double[] X_COORDS =
	{100, 120, 200, 240, 250, 255, 280, 300, 325, 350};

	/** Y-coordinates for test points. */
	private static final double[] Y_COORDS =
	{100, 230, 70, 75, 90, 160, 200, 270, 200, 290};
	
	/** Width of point in pixels. */
	private static final int POINT_WIDTH = 8;
	
	/**
	 * Main method.
	 * @param args Command line args
	 */
	public static void main(final String[] args) {
		CurveTest ct = new CurveTest();
		ct.getContentPane().add(ct.new TestCanvas());
		ct.setSize(500, 500);
		ct.setVisible(true);
	}
	
	
	/**
	 * Canvas that will draw test curves.
	 * @author dhall
	 *
	 */
	class TestCanvas extends Canvas {
		
		/** Serialized version. */
		private static final long serialVersionUID = 1;

		/**
		 * Paing canvas.
		 * @param g Graphics
		 */
		@Override
		public void paint(final Graphics g) {
			for (int i = 0; i < X_COORDS.length; i++) {
				g.fillRect((int) X_COORDS[i], (int) Y_COORDS[i],
						(int) POINT_WIDTH,
						(int) POINT_WIDTH);
			}
			CubicSpline spline = new CubicSpline(X_COORDS, Y_COORDS);
			int x1 = (int) X_COORDS[0];
			int y1 = (int) Y_COORDS[0];
			int n = X_COORDS.length;
			for (double d = X_COORDS[1]; d <= X_COORDS[n - 1]; d += 1) {
				int x2 = (int) d;
				int y2 = (int) spline.interpolate(d);
				g.drawLine(x1, y1, x2, y2);
				x1 = x2;
				y1 = y2;
			}
			g.drawLine(x1, y1, (int) X_COORDS[n - 1], (int) Y_COORDS[n - 1]);
		}
	}
}
