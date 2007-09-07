/*
$Revision: 1.4 $
$Date: 2007-09-07 22:21:30 $

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

package org.rti.webgenome.service.io;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.apache.log4j.Logger;
import org.rti.webgenome.domain.Array;
import org.rti.webgenome.domain.ArrayDatum;
import org.rti.webgenome.domain.BioAssay;
import org.rti.webgenome.domain.BioAssayData;
import org.rti.webgenome.domain.ChromosomeArrayData;
import org.rti.webgenome.domain.ChromosomeReporters;
import org.rti.webgenome.domain.DataColumnMetaData;
import org.rti.webgenome.domain.DataFileMetaData;
import org.rti.webgenome.domain.DataSerializedBioAssay;
import org.rti.webgenome.domain.Experiment;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.domain.Reporter;
import org.rti.webgenome.util.StopWatch;
import org.rti.webgenome.util.SystemUtils;


/**
 * This class manages I/O of array data to the file system.
 * @author dhall
 *
 */
public final class DataFileManager {
    
    /** Logger. */
    private static final Logger LOGGER =
        Logger.getLogger(DataFileManager.class);
    
    
    // =====================================
    //        Attributes
    // =====================================
    
    /** Helper objects to serialize/de-serialize objects. */
    private final Serializer serializer;
    
    /**
     * This map caches <code>ChromosomeReporters</code>
     * objects in memory.  Keys are the names of files
     * containing the corresponding serialized
     * objects.  This cache is used not only for performance
     * but to ensure that instances of <code>ArrayDatum</code>
     * objects that logically point to the same reporters,
     * actually reference the same <code>Reporter</code>
     * objects.
     */
    private Map<String, ChromosomeReporters> chromosomeReportersCache =
        new HashMap<String, ChromosomeReporters>();
        
    
    // =====================================
    //       Constructors
    // =====================================
    
    /**
     * Constructor.
     * @param dataDirectoryPath Path to directory containing data files.
     * These are files that contain serialized data, not source
     * input data.
     */
    public DataFileManager(final String dataDirectoryPath) {
        this.serializer = new FileSerializer(dataDirectoryPath);
    }
    
    
    // ======================================
    //     Business methods
    // ======================================

    /**
     * Serialize reporters in given reader as a new array object.
     * @param smdFileReader Reader of SMD format files.
     * @return Array object containing persisted reporters
     */
    public Array serializeReporters(final SmdFileReader smdFileReader) {
    	
        // Create new array object and store reporters
        LOGGER.info("Serializing reporter data");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Reporter> reporters = smdFileReader.getReporters();
        ChromosomeReporters cr = null;
        Array array = new Array();
        for (Reporter r : reporters) {
            if (cr == null || cr.getChromosome() != r.getChromosome()) {
                if (cr != null) {
                    String fileName = this.serializer.serialize(cr);
                    array.setChromosomeReportersFileName(cr.getChromosome(),
                            fileName);
                    this.chromosomeReportersCache.put(fileName, cr);
                }
                cr = new ChromosomeReporters(r.getChromosome());
            }
            cr.add(r);
        }
        if (cr != null) {
            String fileName = this.serializer.serialize(cr);
            array.setChromosomeReportersFileName(cr.getChromosome(),
                    fileName);
            this.chromosomeReportersCache.put(fileName, cr);
        }
        LOGGER.info("Completed serialization of reporters");
        LOGGER.info("Elapsed time: " + stopWatch.getFormattedLapTime());
        return array;
    }
    
    /**
     * Extract specified data from an SMD-format file.
     * @param smdFileReader Reader of SMD data files
     * @param experiment Experiment into which parsed data will be
     * deposited.
     * @param dataFile File containing data to parse
     * @param dataFileMetaData Metadata on data file
     * @param organism Organism that experiment was performed against
     * @param array Array used in experiment
     * @throws SmdFormatException If the file is not valid
     * SMD format
     * @see {@link org.rti.webgenome.service.io.SmdFileReader}
     */
    public void convertSmdData(
    		final SmdFileReader smdFileReader,
    		final Experiment experiment,
    		final File dataFile,
            final DataFileMetaData dataFileMetaData,
            final Organism organism,
            final Array array)
        throws SmdFormatException {
        
        // Create new bioassay objects and store array data
        LOGGER.info("Serializing array data");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (DataColumnMetaData meta
        		: dataFileMetaData.getDataColumnMetaData()) {
            DataSerializedBioAssay ba =
                new DataSerializedBioAssay(meta.getBioAssayName(), organism);
            experiment.add(ba);
            ba.setArray(array);
            BioAssayData bad = smdFileReader.getBioAssayData(dataFile,
            		meta.getColumnName(),
            		dataFileMetaData.getReporterNameColumnName(),
            		dataFileMetaData.getFormat());
            LOGGER.info("Serializing bioassay " + ba.getName());
            for (ChromosomeArrayData cad
                    : bad.getChromosomeArrayData().values()) {
                ArrayDataAttributes ada = new ArrayDataAttributes();
                for (Iterator<ArrayDatum> it = cad.getArrayData().iterator();
                    it.hasNext();) {
                    ArrayDatum ad = it.next();
                    ada.add(new ArrayDatumAttributes(ad.getValue(),
                            ad.getError()));
                    it.remove();
                }
                String fileName = this.serializer.serialize(ada);
                ba.registerChromosomeArrayData(cad, fileName);
            }
        }
        LOGGER.info("Completed serialization of array data");
        LOGGER.info("Elapsed time: " + stopWatch.getFormattedLapTime());
        LOGGER.info("Total elapsed time: "
                + stopWatch.getFormattedElapsedTime());
    }
    
    
    /**
     * Recover chromosome array data associated with
     * given bioassay from files.  Method is careful
     * to minimize memory usage.
     * @param bioAssay A bioassay
     * @param chromosome Chromosome number
     * @return Chromosome array data
     */
    public ChromosomeArrayData loadChromosomeArrayData(
            final DataSerializedBioAssay bioAssay, final short chromosome) {
        
        // Recover reporters
        String fname =
            bioAssay.getArray().getChromosomeReportersFileName(chromosome);
        ChromosomeReporters cr = this.loadChromosomeReporters(fname);
        
        // Recover array datum attributes
        fname = bioAssay.getFileName(chromosome);
        ArrayDataAttributes ada = (ArrayDataAttributes)
            this.serializer.deSerialize(fname);
        
        // Construct chromosome array data
        ChromosomeArrayData cad = new ChromosomeArrayData(chromosome);
        Iterator<Reporter> rIt = cr.getReporters().iterator();
        Iterator<ArrayDatumAttributes> aIt =
            ada.getArrayDatumAttributes().iterator();
        while (rIt.hasNext() && aIt.hasNext()) {
            Reporter r = rIt.next();
            ArrayDatumAttributes atts = aIt.next();
            if (!Float.isNaN(atts.getValue())) {
                ArrayDatum datum =
                    new ArrayDatum(atts.getValue(), atts.getError(), r);
                cad.add(datum);
            }
            aIt.remove();
        }
        
        return cad;
    }
    
    
    
    /**
     * Save chromsome array data to disk and update
     * given bioassay with the file location of save data.
     * @param bioAssay Bioassay to which chromosome array data
     * is associated
     * @param chromosomeArrayData Chromosome array data
     */
    public void saveChromosomeArrayData(final DataSerializedBioAssay bioAssay,
            final ChromosomeArrayData chromosomeArrayData) {
    	short chromNum = chromosomeArrayData.getChromosome();
    	LOGGER.info("Serializing chromosome array data for "
    			+ bioAssay.getName() + " chromosome " + chromNum);
        Array array = bioAssay.getArray();
        if (array == null) {
            throw new IllegalArgumentException("Unknown reporter");
        }
        
        // Save reporters if they have not been saved
        if (array.getChromosomeReportersFileName(chromNum) == null) {
        	LOGGER.info("Serializing reporters for " + bioAssay.getName()
        			+ " chromosome " + chromNum);
        	SortedSet<Reporter> reporters = chromosomeArrayData.getReporters();
        	ChromosomeReporters cr = new ChromosomeReporters(chromNum);
        	cr.setReporters(reporters);
        	String fileName = this.serializer.serialize(cr);
        	array.setChromosomeReportersFileName(chromNum, fileName);
        	LOGGER.info("Completed serialization of reporters");
        }
        
        // Save array datum
        ArrayDataAttributes ada = new ArrayDataAttributes();
        for (ArrayDatum ad : chromosomeArrayData.getArrayData()) {
            ada.add(new ArrayDatumAttributes(ad.getValue(),
                    ad.getError()));
        }
        String fileName = this.serializer.serialize(ada);
        bioAssay.registerChromosomeArrayData(chromosomeArrayData,
                fileName);
        LOGGER.info("Completed serialization of array data");
    }
    
    
    /**
     * Delete files associated with given experiment.
     * @param exp An experiment
     * @param deleteReporters If set to <code>true</code>,
     * files containing reporter data are also deleted.
     */
    public void deleteDataFiles(final Experiment exp,
            final boolean deleteReporters) {
        
        // Delete array datum attribute files
        LOGGER.info("Deleting data files associated with experiment '"
                + exp.getName() + "'");
        for (BioAssay ba : exp.getBioAssays()) {
            if (ba instanceof DataSerializedBioAssay) {
               DataSerializedBioAssay dsba = (DataSerializedBioAssay) ba;
               for (Iterator<Short> it = dsba.getChromosomeArrayDataFileIndex()
                       .keySet().iterator(); it.hasNext();) {
                   short chromosome = it.next();
                   String fname = dsba.getFileName(chromosome);
                   this.serializer.decommissionObject(fname);
                   it.remove();
               }
            }
        }
        
        // Delete reporters, if specified
        if (deleteReporters) {
            LOGGER.info("Deleting reporters associated with experiment '"
                    + exp.getName() + "'");
            
            // Collect list of reporter file names
            Set<String> fnames = new HashSet<String>();
            for (BioAssay ba : exp.getBioAssays()) {
                for (String fname : ba.getArray()
                        .getChromosomeReportersFileNames().values()) {
                    fnames.add(fname);
                }
            }
            
            // Delete files
            for (String fname : fnames) {
                this.serializer.decommissionObject(fname);
            }
        }
    }
    
    /**
     * Delete given data file.
     * @param fileName Name of file, not absolute path
     */
    public void deleteDataFile(final String fileName) {
    	this.serializer.decommissionObject(fileName);
    }
    
    
    /**
     * Get chromosome reporters that are stored in given file.
     * This method uses in-memory caching to (1) provide
     * good performance and (2) to ensure that all references
     * to the same logical reporter actually point to the
     * same <code>Reporter</code> object.
     * @param fileName Name of file
     * @return Chromosome reporters
     */
    private ChromosomeReporters
        loadChromosomeReporters(final String fileName) {
        ChromosomeReporters cr = this.chromosomeReportersCache.get(fileName);
        if (cr == null) {
            LOGGER.info("De-serializing reporters");
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            cr = (ChromosomeReporters)
                this.serializer.deSerialize(fileName);
            this.chromosomeReportersCache.put(fileName, cr);
            LOGGER.info("Completed de-serialization");
            LOGGER.info("Elapsed time: "
                    + stopWatch.getFormattedElapsedTime());
        }
        return cr;
    }
    
    
    /**
     * Aggregate of <code>ArrayDatumAttribute</code>
     * objects from same chromosome.  This is used
     * for two purposes: (1) Multiple
     * <code>ArrayDatum</code> objects that point to the same
     * reporter can be serialized to different files.
     * (Calling method must know how to de-serialize reporters
     * from different files and properly associate <code>Reporter</code>
     * objects.)  (2) This format reduces memory requirements.
     * @author dhall
     *
     */
    static class ArrayDataAttributes implements Serializable {
        
        /** Serialized version ID. */
        private static final long serialVersionUID = 
    		SystemUtils.getLongApplicationProperty("serial.version.uid");
        
        // =============================
        //        Attributes
        // =============================
        
        /** Array datum attributes. */
        private List<ArrayDatumAttributes> arrayDatumAttributes =
            new ArrayList<ArrayDatumAttributes>();

        
        // ============================
        //     Getters
        // ============================
        
        /**
         * Get array datum attributes.
         * @return Array datum attributes
         */
        public List<ArrayDatumAttributes> getArrayDatumAttributes() {
            return arrayDatumAttributes;
        }
        
        // ============================
        //    Constructors
        // ============================
        
        /**
         * Constructor.
         */
        public ArrayDataAttributes() {
            
        }
        
        // =========================
        //       Business methods
        // =========================
        
        /**
         * Add an array datum attribute.
         * @param arrayDatumAttribute An array datum attribute
         */
        public void add(final ArrayDatumAttributes arrayDatumAttribute) {
            this.arrayDatumAttributes.add(arrayDatumAttribute);
        }
        
    }
    
    
    /**
     * Class for serializing attributes of <code>ArrayDatum</code>
     * objects.  This is used for two purposes: (1) Multiple
     * <code>ArrayDatum</code> objects that point to the same
     * reporter can be serialized to different files.
     * (Calling method must know how to de-serialize reporters
     * from different files and properly associate <code>Reporter</code>
     * objects.)  (2) This format reduces memory requirements.
     * @author dhall
     *
     */
    static class ArrayDatumAttributes implements Serializable {
        
        /** Serialized version ID. */
        private static final long serialVersionUID = (long) 1;
        
        // ===============================
        //      Attributes
        // ===============================
        
        /**
         * Corresponds to <code>value</code> attribute of
         * <code>ArrayDatum</code>.
         * */
        private float value = Float.NaN;
        
        /**
         * Corresponds to <code>error</code> attribute of
         * <code>ArrayDatum</code>.
         * */
        private float error = (float) -1.0;

        
        // =============================
        //    Getters/setters
        // =============================
        
        /**
         * Get error, which corresponds to <code>error</code> attribute of
         * <code>ArrayDatum</code>.
         * @return Error property
         */
        public float getError() {
            return error;
        }

        
        /**
         * Set error, which corresponds to <code>error</code> attribute of
         * <code>ArrayDatum</code>.
         * @param error Error
         */
        public void setError(final float error) {
            this.error = error;
        }

        
        /**
         * Get value, which corresponds to <code>value</code> attribute of
         * <code>ArrayDatum</code>.
         * @return Value
         */
        public float getValue() {
            return value;
        }

        /**
         * Set value, which corresponds to <code>value</code> attribute of
         * <code>ArrayDatum</code>.
         * @param value Value
         */
        public void setValue(final float value) {
            this.value = value;
        }


        // ===================================
        //      Constructors
        // ===================================
        
        /**
         * Constructor.
         */
        public ArrayDatumAttributes() {
            
        }
        
        /**
         * Constructor.
         * @param value Value, which corresponds to
         * <code>value</code> attribute of
         * <code>ArrayDatum</code>.
         * @param error Error, which corresponds to
         * <code>error</code> attribute of
         * <code>ArrayDatum</code>.
         */
        public ArrayDatumAttributes(final float value, final float error) {
            this.value = value;
            this.error = error;
        }
    }
}
