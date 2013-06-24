/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.8 $
$Date: 2008-05-23 21:08:56 $


*/

package org.rti.webgenome.webui.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.GenomeInterval;
import org.rti.webgenome.domain.UploadDataSourceProperties;
import org.rti.webgenome.service.io.IOService;
import org.rti.webgenome.service.plot.AnnotationPlotParameters;
import org.rti.webgenome.service.plot.PlotParameters;
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
	 * for background processing of a plotting
	 * operation.  This may be initialized
	 * from a system property 'bg.processing.plotting.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Integer.MAX_VALUE}.  Hence,
	 * all plotting operations will be performed immediately.
	 */
	private static final int BG_PROCESSING_PLOTTING_THRESHOLD;
	static {
		String propName = "bg.processing.plotting.threshold";
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
		BG_PROCESSING_PLOTTING_THRESHOLD = threshold;
	}
	
	/**
	 * Threshold number of individual array datum
	 * for background processing of an analytic
	 * operation.  This may be initialized
	 * from a system property 'bg.processing.analysis.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Integer.MAX_VALUE}.  Hence,
	 * all plotting operations will be performed immediately.
	 */
	private static final int BG_PROCESSING_ANALYSIS_THRESHOLD;
	static {
		String propName = "bg.processing.analysis.threshold";
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
		BG_PROCESSING_ANALYSIS_THRESHOLD = threshold;
	}
	
	/**
	 * Threshold number of individual array datum
	 * for background processing of download raw data
	 * operation.  This may be initialized
	 * from a system property 'bg.processing.download.data.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Integer.MAX_VALUE}.  Hence,
	 * all plotting operations will be performed immediately.
	 */
	private static final int BG_PROCESSING_DOWNLOAD_DATA_THRESHOLD;
	static {
		String propName = "bg.processing.download.data.threshold";
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
		BG_PROCESSING_DOWNLOAD_DATA_THRESHOLD = threshold;
	}
	
	
	/**
	 * Threshold file size
	 * for background file processing.  This may be initialized
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
	
	/**
	 * Threshold genome interval size
	 * for background file processing of SOME plots
	 * on an analytic
	 * server.  This may be initialized
	 * from a system property 'bg.processing.file.size.threshold.'
	 * If this property is not set, the threshold will be
	 * set to {@link java.lang.Long.MAX_VALUE}.  Hence,
	 * all operations will be performed on the application
	 * server.
	 */
	private static final long BG_PROCESSING_GENOME_INTERVAL_THRESHOLD;
	static {
		String propName = "bg.processing.genome.interval.threshold";
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
		BG_PROCESSING_GENOME_INTERVAL_THRESHOLD = threshold;
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
	 * Determines whether some compute-intensive
	 * analysis process
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
	public static boolean analysisInBackground(
			final Experiment experiment, final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			background = experiment.numDatum()
				>= BG_PROCESSING_ANALYSIS_THRESHOLD;
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
	 * Determines whether some compute-intensive
	 * analysis process
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
	public static boolean analysisInBackground(
			final Collection<Experiment> experiments,
			final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			int numDatum = 0;
			for (Experiment exp : experiments) {
				numDatum += exp.numDatum();
			}
			background = numDatum >= BG_PROCESSING_ANALYSIS_THRESHOLD;
		}
		return background;
	}
	
	
	/**
	 * Determines whether some compute-intensive plotting process
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
	public static boolean plotInBackground(
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
					double fraction = 0.0;
					if (interval.endpointsSpecified()) {
						fraction = (double) interval.length()
						/ (double) exp.inferredChromosomeSize(
								interval.getChromosome());
					} else {
						fraction = 1.0;
					}
					numDatum += (int) ((double) totalNum * fraction);
				}
			}
			background = numDatum >= BG_PROCESSING_PLOTTING_THRESHOLD;
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
	
	
	/**
	 * Determines whether plotting
	 * should be performed in the background.
	 * It does this
	 * by determining if the genome interval size
	 * is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.genome.interval.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Long.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * Only plot types that involve retrieval of annotations
	 * from the database are considered.
	 * @param params Plotting parameters
	 * @param request Servlet request
	 * @return {@code true} if the total genome interval size
	 * size is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean plotInBackground(
			final PlotParameters params,
			final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			if (params instanceof AnnotationPlotParameters) {
				long totalInterval = 0;
				for (GenomeInterval ival : params.getGenomeIntervals()) {
					totalInterval += ival.length();
				}
				background = totalInterval
					> BG_PROCESSING_GENOME_INTERVAL_THRESHOLD;
			}
		}
		return background;
	}
	
	
	/**
	 * Determines whether download a list of ArrayDatum that 
	 * contain bioassay raw data should be performed in the background.  
	 * It does this by determining if the list of ArrayDatum size 
	 * is greater than a threshold.  This treshold
	 * may be set using the system property
	 * {@code bg.processing.download.data.threshold}.  If this
	 * property is not set, then the threshold is set
	 * to {@link java.lang.Long.MAX_VALUE}, so
	 * the method will always return {@code false}.
	 * If the session mode is CLIENT, then operations are
	 * never performed in the background.
	 * @param List<ArrayDatum> that will be processed
	 * @param request Servlet request
	 * @return {@code true} if the total file
	 * size is greater than a threshold and should, hence,
	 * indicate that the process in question should be
	 * performed in the background.  Returns {@code false}
	 * otherwise.
	 * @throws SessionTimeoutException If the session mode cannot
	 * be determined indicating a timeout
	 */
	public static boolean downloadInBackground(
			final List<ArrayDatum> arrDatums, final HttpServletRequest request)
	throws SessionTimeoutException {
		boolean background = false;
		if (PageContext.getSessionMode(request) != SessionMode.CLIENT) {
			background = arrDatums.size() > BG_PROCESSING_DOWNLOAD_DATA_THRESHOLD;
		}
		return background;
	}
	
	
	
}
