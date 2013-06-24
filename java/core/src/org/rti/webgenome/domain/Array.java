/*
$Revision: 1.3 $
$Date: 2007-08-17 19:02:17 $


*/

package org.rti.webgenome.domain;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

import org.rti.webgenome.util.StringUtils;
import org.rti.webgenome.util.SystemUtils;

/**
 * Represents a physical microarray device.
 * @author dhall
 *
 */
public class Array implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");
    
    // ===============================
    //      Constants
    // ===============================
    
    /** Unknown array. */
    public static final Array UNKNOWN_ARRAY = new Array("Unknown");
    
    // =================================
    //     Attributes
    // =================================
    
    /** Identifier used as primary key for persistence. */
    private Long id = null;
    
    /** Array name. */
    private String name = null;
    
    /**
     * Some arrays may be persisted in the database for
     * an indefinate time.  These would typically correspond
     * to well-known array designs from a commercial vendor.
     * Special system functionality will be used to load
     * and delete such arrays from the database.  Such array
     * objects are not "disposable."  In contrast, some
     * array objects are used by single experiments, such as
     * when a user uploads an SMD file.  Such arrays are considered
     * "disposable" and are removed from the database when the
     * corresponding experiment is removed.
     */
    private boolean isDisposable = false;
    
    /**
     * Map providing index to names of files containing
     * serialized <code>ChromosomeReporters</code> objects.
     * Keys are chromosome numbers.  Values are relative
     * file names, not absolute paths.
     */
    private SortedMap<Short, String> chromosomeReportersFileNames =
        new TreeMap<Short, String>();
    
    
    // ===================================
    //      Getters/setters
    // ===================================

    
    /**
     * Get map providing index to names of files containing
     * serialized <code>ChromosomeReporters</code> objects.
     * Keys are chromosome numbers.  Values are relative
     * file names, not absolute paths.
     * @return Index to file names
     */
    public final SortedMap<Short, String> getChromosomeReportersFileNames() {
        return chromosomeReportersFileNames;
    }

    /**
     * Set map providing index to names of files containing
     * serialized <code>ChromosomeReporters</code> objects.
     * Keys are chromosome numbers.  Values are relative
     * file names, not absolute paths.
     * @param chromosomeReportersFileNames Index to file names
     */
    public final void setChromosomeReportersFileNames(
            final SortedMap<Short, String> chromosomeReportersFileNames) {
        this.chromosomeReportersFileNames = chromosomeReportersFileNames;
    }
    
    
    /**
     * Some arrays may be persisted in the database for
     * an indefinate time.  These would typically correspond
     * to well-known array designs from a commercial vendor.
     * Special system functionality will be used to load
     * and delete such arrays from the database.  Such array
     * objects are not "disposable."  In contrast, some
     * array objects are used by single experiments, such as
     * when a user uploads an SMD file.  Such arrays are considered
     * "disposable" and are removed from the database when the
     * corresponding experiment is removed.
     * @return {@code true} if the array is disposable,
     * {@code false} otherwise
     */
    public boolean isDisposable() {
		return isDisposable;
	}

    /**
     * Some arrays may be persisted in the database for
     * an indefinate time.  These would typically correspond
     * to well-known array designs from a commercial vendor.
     * Special system functionality will be used to load
     * and delete such arrays from the database.  Such array
     * objects are not "disposable."  In contrast, some
     * array objects are used by single experiments, such as
     * when a user uploads an SMD file.  Such arrays are considered
     * "disposable" and are removed from the database when the
     * corresponding experiment is removed.
     * @param isDisposable Is the array disposable?
     */
	public void setDisposable(final boolean isDisposable) {
		this.isDisposable = isDisposable;
	}

	/**
     * Get primary key identifier used for persistence.
     * @return Primary key identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set primary key identifier used for persistence.
     * @param id Primary key identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get name of array.
     * @return Array name
     */
    public final String getName() {
        return name;
    }

    /**
     * Set name of array.
     * @param name Array name
     */
    public final void setName(final String name) {
        this.name = name;
    }
    
    
    // ==================================
    //       Constructors
    // ==================================
    
    /**
     * Default constructor.
     */
    public Array() {
        
    }

    /**
     * Constructor.
     * @param name Name of array
     */
    public Array(final String name) {
        this.name = name;
    }
    
    
    // =============================
    //        Business methods
    // =============================
    
    /**
     * Set name of file containing serialized
     * <code>ChromosomeReporters</code> object
     * from given chromosome.
     * @param chromosome Chromosome number
     * @param fileName Relative file name, not
     * absolute path
     */
    public final void setChromosomeReportersFileName(final short chromosome,
            final String fileName) {
        this.chromosomeReportersFileNames.put(chromosome, fileName);
    }
    
    
    /**
     * Get name of file containing serialized
     * <code>ChromosomeReporters</code> object
     * associated with given chromosome.
     * @param chromosome Chromosome number
     * @return Name of file containing serialized
     * <code>ChromosomeReporters</code>
     * object
     */
    public final String getChromosomeReportersFileName(
            final short chromosome) {
        return this.chromosomeReportersFileNames.get(chromosome);
    }
    
    //
    //  O V E R R I D E S
    //

    /**
     * {@inheritDoc}
     */
	@Override
	public boolean equals(final Object obj) {
		boolean equals = false;
		if (obj instanceof Array) {
			Array array = (Array) obj;
			if (StringUtils.equal(this.name, array.name)) {
				equals = true;
				for (Short key : this.chromosomeReportersFileNames.keySet()) {
					String fname1 = this.chromosomeReportersFileNames.get(key);
					String fname2 = array.chromosomeReportersFileNames.get(key);
					if (!StringUtils.equal(fname1, fname2)) {
						equals = false;
						break;
					}
				}
			}
		}
		return equals;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		StringBuffer buff = new StringBuffer();
		if (this.name != null) {
			buff.append(this.name);
		}
		for (Short key : this.chromosomeReportersFileNames.keySet()) {
			String value = this.chromosomeReportersFileNames.get(key);
			buff.append(key);
			buff.append(value);
		}
		return buff.toString().hashCode();
	}
}
