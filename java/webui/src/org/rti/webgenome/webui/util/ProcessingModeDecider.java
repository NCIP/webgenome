/*
$Revision: 1.4 $
$Date: 2007-08-24 21:51:58 $

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

package org.rti.webgenome.webui.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.session.SessionMode;
import org.rti.webgenome.util.SystemUtils;
import org.rti.webgenome.webui.SessionTimeoutException;

/**
 * This class is responsible for deciding if
 * a particular operation should be performed
 * as a batch process in the background
 * (i.e., offloaded off the application server).
 * @author dhall
 *
 */
public final class ProcessingModeDecider {
	
	//
	//     STATICS
	//
	
	/** Logger. */
	private static final Logger LOGGER = Logger.getLogger(
			ProcessingModeDecider.class);
	
	/**
	 * Threshold number of individual array datum
	 * for background processing on an analytic
	 * server.  This may be initialized
	 * from a system property 'bg.processing.datum.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Integer.MAX_VALUE}.  Hence,
	 * all operations will be performed on the application
	 * server.
	 */
	private static final int BG_PROCESSING_DATUM_THRESHOLD;
	static {
		String propName = "bg.processing.datum.threshold";
		int threshold = Integer.MAX_VALUE;
		String thresholdProp = SystemUtils.getApplicationProperty(
				propName);
		if (thresholdProp != null) {
			try {
				threshold = Integer.parseInt(thresholdProp);
			} catch (NumberFormatException e) {
				LOGGER.warn("System property '"
						+ propName + "' is not a valid number");
			}
		}
		BG_PROCESSING_DATUM_THRESHOLD = threshold;
	}
	
	/**
	 * Threshold file size
	 * for background file processing on an analytic
	 * server.  This may be initialized
	 * from a system property 'bg.processing.file.size.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Long.MAX_VALUE}.  Hence,
	 * all operations will be performed on the application
	 * server.
	 */
	private static final long BG_PROCESSING_FILE_SIZE_THRESHOLD;
	static {
		String propName = "bg.processing.file.size.threshold";
		long threshold = Long.MAX_VALUE;
		String thresholdProp = SystemUtils.getApplicationProperty(
				propName);
		if (thresholdProp != null) {
			try {
				threshold = Long.parseLong(thresholdProp);
			} catch (NumberFormatException e) {
				LOGGER.warn("System property '"
						+ propName + "' is not a valid number");
			}
		}
		BG_PROCESSING_FILE_SIZE_THRESHOLD = threshold;
	}

	//
	//     CONSTRUCTORS
	//
	
	/**
	 * Constructor.
	 */
	private ProcessingModeDecider() {
		
	}
	
	
	//
	//     BUSINESS METHODS
	//
	
	/**
	 * Determines whether some compute-intensive process
	 * should be performed in the background.  It does this
	 * by determining if the total number of
	 * {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.datum.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Integer.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param experiment Experiment that will be processed
	 * by process in question.
	 * @param request Servlet request
	 * @return {@code true} if the total number of
	 * enclosed {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean processInBackground(
			final Experiment experiment, final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			background = experiment.numDatum() >= BG_PROCESSING_DATUM_THRESHOLD;
		}
		return background;
	}
	
	
	/**
	 * Determines whether load on the contents of
	 * the given files should be performed in
	 * the background.  It does this
	 * by determining if the total file size in bytes
	 * is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.file.size.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Long.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param upload Upload properties
	 * @param request Servlet request
	 * @param ioService File I/O service
	 * @return T/F
	 * @throws SessionTimeoutException if the session mode
	 * cannot be recovered indicating a session timeout
	 */
	public static boolean processInBackground(
			final UploadDataSourceProperties upload,
			final HttpServletRequest request,
			final IOService ioService)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			Collection<File> files = new ArrayList<File>();
			files.add(ioService.getWorkingFile(
					upload.getReporterLocalFileName()));
			for (DataFileMetaData meta : upload.getDataFileMetaData()) {
				files.add(ioService.getWorkingFile(meta.getLocalFileName()));
			}
			long totalLen = 0;
			for (File file : files) {
				totalLen += file.length();
			}
			background = totalLen > BG_PROCESSING_FILE_SIZE_THRESHOLD;
		}
		return background;
	}
	
	/**
	 * Determines whether some compute-intensive process
	 * should be performed in the background.  It does this
	 * by determining if the total number of
	 * {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.datum.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Integer.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param experiments Experiments that will be processed
	 * by process in question.
	 * @param request Servlet request
	 * @return {@code true} if the total number of
	 * enclosed {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean processInBackground(
			final Collection<Experiment> experiments,
			final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			int numDatum = 0;
			for (Experiment exp : experiments) {
				numDatum += exp.numDatum();
			}
			background = numDatum >= BG_PROCESSING_DATUM_THRESHOLD;
		}
		return background;
	}
	
	
	/**
	 * Determines whether some compute-intensive process
	 * should be performed in the background.  It does this
	 * by determining if the total number of
	 * {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects in the given genome intervals
	 * is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.datum.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Integer.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * For computational efficiency, the method estimates
	 * the number of {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects by assuming a uniform distribution of reporters
	 * across the chromosome and multiplying the
	 * total number of {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects on each chromosome referenced by the
	 * genome intervals by the fraction of the entire
	 * chromosome each interval covers.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param experiments Experiments that will be processed
	 * by process in question.
	 * @param genomeIntervals Genome intervals that will
	 * be processed
	 * @param request Servlet request
	 * @return {@code true} if the total number of
	 * enclosed {@link org.rti.webgenome.domain.ArrayDatum}
	 * objects is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean processInBackground(
			final Collection<Experiment> experiments,
			final Collection<GenomeInterval> genomeIntervals,
			final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			int numDatum = 0;
			for (Experiment exp : experiments) {
				for (GenomeInterval interval : genomeIntervals) {
					int totalNum = exp.numDatum(interval.getChromosome());
					double fraction = (double) interval.length()
						/ (double) exp.inferredChromosomeSize(
								interval.getChromosome());
					numDatum += (int) ((double) totalNum * fraction);
				}
			}
			background = numDatum >= BG_PROCESSING_DATUM_THRESHOLD;
		}
		return background;
	}
	
	
	/**
	 * Determines whether some compute-intensive process
	 * on the contents of the given fle
	 * should be performed in the background.  It does this
	 * by determining if the file size in bytes
	 * is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.file.size.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Long.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param file File that will be processed
	 * @param request Servlet request
	 * @return {@code true} if the total file
	 * size is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean processInBackground(
			final File file, final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			background = file.length() > BG_PROCESSING_FILE_SIZE_THRESHOLD;
		}
		return background;
	}
}
