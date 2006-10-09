/*
$Revision: 1.2 $
$Date: 2006-10-09 00:02:17 $

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

package org.rti.webcgh.io;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Generates a sequence of unique file names.
 */
public class UniqueFileNameGenerator implements Serializable {
	
	/** Serial version ID. */
	private static final long serialVersionUID = 1;
	
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
