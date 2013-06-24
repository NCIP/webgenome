/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $


*/

package org.rti.webgenome.graphics.io;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.rti.webgenome.core.WebGenomeSystemException;

/**
 * Utility class for reading and writing graphic files.
 * @author dhall
 */
public final class GraphicFileUtils {

	/**
	 * Constructor.
	 */
	private GraphicFileUtils() {
		
	}
	
	/**
	 * Write image to file.
	 * @param file File
	 * @param image Image
	 * @param fileType File type
	 */
	public static void write(final File file, final RenderedImage image,
			final RasterGraphicFileType fileType) {
		try {
			ImageIO.write(image, fileType.getName(), file);
		} catch (IOException e) {
			throw new WebGenomeSystemException("Error writing image to file", e);
		}
	}
	
	
	/**
	 * Write image to file of given file name in given directory.
	 * @param directory A directory
	 * @param fileName File Name of file that will be created
	 * @param image Image
	 * @param fileType File type
	 */
	public static void write(final File directory, final String fileName,
			final RenderedImage image, final RasterGraphicFileType fileType) {
		if (!directory.exists() || !directory.isDirectory()) {
			throw new IllegalArgumentException("Not valid directory: "
					+ directory.getAbsolutePath());
		}
		File file = new File(directory.getAbsolutePath() + "/" + fileName);
		write(file, image, fileType);
	}
}
