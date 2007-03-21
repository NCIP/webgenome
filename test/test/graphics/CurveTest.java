/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:34 $

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

package test.graphics;

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
