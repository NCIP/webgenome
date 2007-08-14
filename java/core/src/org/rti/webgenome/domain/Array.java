/*
$Revision: 1.2 $
$Date: 2007-08-14 22:42:07 $

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
		System.out.println("Equals");
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
		System.out.println("Hashcode");
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
