/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.graphics.io;

/**
 * Type of raster graphic file.
 * @author dhall
 *
 */
public enum RasterGraphicFileType {
	
	/** PNG. */
	PNG("png"),
	
	/** JPEG. */
	JPEG("jpg"),
	
	/** GIF. */
	GIF("gif");
	
	// =========================
	//       Attributes
	// =========================
	
	/** Name of file type. */
	private final String name;
	
	// =====================
	//      Constructors
	// =====================
	
	/**
	 * Constructor.
	 * @param name Name of file type.
	 */
	private RasterGraphicFileType(final String name) {
		this.name = name;
	}
	
	// =======================
	//     Methods
	// =======================
	
	/**
	 * Get name of file type.
	 * @return Name of file type
	 */
	public String getName() {
		return this.name;
	}
}
