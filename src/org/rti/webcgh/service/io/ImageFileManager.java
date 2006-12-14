/*
$Revision: 1.5 $
$Date: 2006-12-14 02:24:56 $

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

package org.rti.webcgh.service.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.service.dao.ShoppingCartDao;
import org.rti.webcgh.util.SystemUtils;

/**
 * Manages image files containing plot images.  All images
 * are saved as PNG format.  This class should always be used
 * as a singleton.
 * @author dhall
 *
 */
public class ImageFileManager implements Serializable {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** File extension. */
	private static final String FILE_EXTENSION = ".png";
	
	/** Image type. */
	private static final String IMAGE_TYPE = "png";
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(ImageFileManager.class);
	
	/**
	 * Name of placeholder file in image directory.  Having
	 * at least one file in the plot directory
	 * ensures that CVS does not purge the directory.
	 */
	private static final String PLACEHOLDER_FILE_NAME = "placeholder.txt";
	
	/** Has this been initialized? */
	private boolean initialized = false;

	// =============================
	//         Attributes
	// =============================
	
	/** Directory containing images. */
	private File directory;
	
	/** File name generator. */
	private UniqueFileNameGenerator fileNameGenerator;
	
	/** Shopping cart DAO. */
	private ShoppingCartDao shoppingCartDao = null;
	
	
	// ============================
	//       Getters/setters
	// ============================
	
	/**
	 * Has this been initialized?
	 * @return T/F
	 */
	public final boolean isInitialized() {
		return initialized;
	}
	
	
	/**
	 * Set shopping cart data access object.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public final void setShoppingCartDao(
			final ShoppingCartDao shoppingCartDao) {
		this.shoppingCartDao = shoppingCartDao;
	}


	// ================================
	//     Constructors
	// ================================
	

	/**
	 * Constructor.
	 * @param imageDir Directory containing image files.
	 */
	public ImageFileManager(final File imageDir) {
		
		// Make sure args okay
		if (directory == null || !directory.exists()
				|| !directory.isDirectory()) {
			throw new IllegalArgumentException("Invalid directory '"
					+ directory.getAbsolutePath() + "'");
		}
		
		// Initialize properties
		this.directory = directory;
		this.fileNameGenerator =
			new UniqueFileNameGenerator(directory, FILE_EXTENSION);
		
		this.initialized = true;
	}
	
	
	/**
	 * Constructor.
	 */
	public ImageFileManager() {
		
	}
	
	
	/**
	 * Initialize this by setting image file directory.
	 * @param directory Image file directory
	 */
	public final void init(final File directory) {
		String directoryPath = directory.getAbsolutePath();
		if (!directory.exists() || !directory.isDirectory()) {
            LOGGER.error("Directory Path specified doesnt' exist "
            		+ "or isn't a directory. "
            		+ "Directory Path: [" + directoryPath + "]");
			throw new IllegalArgumentException("Invalid directory '"
					+ directoryPath + "'");
		}
		
		// Purge directory of unused image files
		Collection<String> filesToSave =
			this.shoppingCartDao.getAllImageFileNames();
		this.purge(filesToSave, directory);
		
		// Initialize properties
		this.directory = directory;
		this.fileNameGenerator =
			new UniqueFileNameGenerator(directory, FILE_EXTENSION);
		
	}
	
	
	/**
	 * Constructor.
	 * @param directoryPath Path to directory containing image files.
	 * @param shoppingCartDao Shopping cart data access object.
	 */
	public ImageFileManager(final String directoryPath,
			final ShoppingCartDao shoppingCartDao) {
		
		// Make sure args okay
		if (directoryPath == null) {
			throw new IllegalArgumentException("Invalid directory 'null'");
		}
		File directory = new File(directoryPath);
		this.shoppingCartDao = shoppingCartDao;
		this.init(directory);
	}
	
	
	/**
	 * Purge directory of unused image files.
	 * @param filesToSave Files to save in purge.
	 * @param directory Directory containing files.
	 */
	private void purge(final Collection<String> filesToSave,
			final File directory) {
		Set<String> fileSet = new HashSet<String>();
		if (filesToSave != null) {
			fileSet.addAll(filesToSave);
		}
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (!PLACEHOLDER_FILE_NAME.equals(file.getName())) {
				if (!fileSet.contains(file.getName())) {
					if (!file.delete()) {
						LOGGER.warn("Unable to delete file '"
								+ file.getAbsolutePath() + "'");
					}
				}
			}
		}
	}
	
	
	/**
	 * Save image.
	 * @param img Image to save.
	 * @return Name of file containing image, but not
	 * absolute path.
	 */
	public final String saveImage(final BufferedImage img) {
		String fileName = this.fileNameGenerator.next();
		String path = this.directory.getAbsolutePath() + "/"
			+ fileName;
		File file = new File(path);
		try {
			ImageIO.write(img, IMAGE_TYPE, file);
		} catch (IOException e) {
			throw new WebcghSystemException("Error writing image to file", e);
		}
		return fileName;
	}
	
	
	/**
	 * Delete image file.
	 * @param fileName Image file name
	 */
	public final void deleteImageFile(final String fileName) {
		String path = this.directory.getAbsolutePath() + "/" + fileName;
		File file = new File(path);
		if (!file.exists()) {
			LOGGER.warn("Image file '" + fileName + "' not found");
		}
		if (!file.delete()) {
			LOGGER.warn("Unable to delete file '" + path + "'");
		}
	}
}
