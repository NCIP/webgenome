/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.3 $
$Date: 2008-02-22 18:24:44 $


*/

package org.rti.webgenome.service.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.service.session.WebGenomeDbService;
import org.rti.webgenome.util.SystemUtils;

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
	
	/** Facade for transactional database operations. */
	private WebGenomeDbService dbService = null;
	
	
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
	 * Set facade for transactional database operations.
	 * @param dbService Facade for transactional database
	 * operations
	 */
	public void setDbService(final WebGenomeDbService dbService) {
		this.dbService = dbService;
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
			this.dbService.getAllValidImageFileNames();
		this.purge(filesToSave, directory);
		
		// Initialize properties
		this.directory = directory;
		this.fileNameGenerator =
			new UniqueFileNameGenerator(directory, FILE_EXTENSION);
		this.initialized = true;
	}
	
	
	/**
	 * Constructor.
	 * @param directoryPath Path to directory containing image files.
	 * @param dbService Facade for transactional database operations
	 */
	public ImageFileManager(final String directoryPath,
			final WebGenomeDbService dbService) {
		
		// Make sure args okay
		if (directoryPath == null) {
			throw new IllegalArgumentException("Invalid directory 'null'");
		}
		File directory = new File(directoryPath);
		this.dbService = dbService;
		this.init(directory);
	}
	
	
	/**
	 * Purge directory of unused image files.
	 * @param filesToSave Files to save in purge.
	 * @param directory Directory containing files.
	 */
	private void purge(final Collection<String> filesToSave,
			final File directory) {
		LOGGER.info("Purging orphaned image files");
		Set<String> fileSet = new HashSet<String>();
		if (filesToSave != null) {
			fileSet.addAll(filesToSave);
		}
		File[] files = directory.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isFile()) {
				if (!PLACEHOLDER_FILE_NAME.equals(file.getName())) {
					if (!fileSet.contains(file.getName())) {
						if (!file.delete()) {
							LOGGER.warn("Unable to delete file '"
									+ file.getAbsolutePath() + "'");
						}
					}
				}
			} else {
				LOGGER.info("Not deleting sub-directory '"
						+ file.getName() + "' in the plot directory");
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
			throw new WebGenomeSystemException(
					"Error writing image to file", e);
		}
		return fileName;
	}
	
	
	/**
	 * Delete image file.
	 * @param fileName Image file name
	 */
	public final void deleteImageFile(final String fileName) {
		LOGGER.info("Deleting image file '" + fileName + "'");
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
