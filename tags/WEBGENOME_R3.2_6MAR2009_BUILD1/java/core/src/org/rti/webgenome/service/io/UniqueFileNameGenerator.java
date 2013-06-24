/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:29 $


*/

package org.rti.webgenome.service.io;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.rti.webgenome.util.SystemUtils;

/**
 * Generates a sequence of unique file names.
 */
public class UniqueFileNameGenerator implements Serializable {
	
	/** Serial version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
	
	/** Logger. */
	private static final Logger LOGGER =
		Logger.getLogger(UniqueFileNameGenerator.class);
	
	/** Next object id in the sequence. */
	private long nextInSequence = (long) 0;
	
	/** File extension. */
	private final String fileExtension;
	
	/**
	 * Constructor.
     * @param directory Directory containing files.
     * @param fileExtension File extension.
	 */
	public UniqueFileNameGenerator(final File directory,
			final String fileExtension) {
		this.fileExtension = fileExtension;
		
		// Set next in sequence
		if (directory == null || !directory.isDirectory()) {
			throw new IllegalArgumentException("'"
                    + directory.getAbsolutePath()
                    + "' is not a valid directory");
        }
		File[] files = directory.listFiles();
		this.nextInSequence = (long) -1;
		for (int i = 0; i < files.length; i++) {
			long candidate = this.getFileNamePrefix(files[i]);
			if (candidate >= 0 && candidate > this.nextInSequence) {
				this.nextInSequence = candidate;
            }
		}
		this.nextInSequence++;
	}
	
	/**
	 * Return next file name.
	 * @return A file name.
	 */
	public final String next() {
		long value = this.nextInSequence;
		if (this.nextInSequence == Long.MAX_VALUE) {
			this.nextInSequence = (long) 0;
        } else {
			this.nextInSequence++;
        }
		Date date = new Date();
		return date.getTime() + value + this.fileExtension;
	}
	
	
	/**
	 * Get long format prefix of file name, i.e. everything upstream
	 * of the file extension.
	 * @param file A file
	 * @return Long format file name prefix
	 * or -1 if file name prefix is not numeric.
	 */
	private long getFileNamePrefix(final File file) {
		long oid = (long) -1;
		String fname = file.getName();
		int p = fname.indexOf(fileExtension);
		if (p >= 0) {
			try {
				oid = Long.parseLong(fname.substring(0, p));
			} catch (NumberFormatException e) {
	            LOGGER.warn("Data directory contains non-webGenonome file '"
	                    + file.getName() + "'");
	        }
		}
		return oid;
	}
}
