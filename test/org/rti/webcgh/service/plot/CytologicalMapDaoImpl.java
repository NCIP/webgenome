/*
$Revision: 1.1 $
$Date: 2007-03-21 23:09:37 $

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

package org.rti.webcgh.service.plot;

import java.util.List;

import org.rti.webcgh.domain.Cytoband;
import org.rti.webcgh.domain.CytologicalMap;
import org.rti.webcgh.domain.Organism;
import org.rti.webcgh.service.dao.CytologicalMapDao;

/**
 * Test implementation of <code>CytologicalMapDao</code>.
 * @author dhall
 */
public final class CytologicalMapDaoImpl implements CytologicalMapDao {
	
	/** Default chromosome. */
	private static final short DEF_CHROMOSOME = (short) 1;
	
	/** Height of centromere in native units. */
	private static final long CENTROMERE_HEIGHT = 10000000;

	/** Cytological map that gets returned every time. */
	private CytologicalMap map = null;
	
	// ===================================
	//     CytologicalMapDao interface
	// ===================================
	
	/**
	 * Does nothing in this implementation.
	 * @param cytologicalMap Cytological map.
	 */
	public void save(final CytologicalMap cytologicalMap) {
		
	}
	
	/**
	 * Returns a dummy cytological map.
	 * @param id Primary key identifier of
	 * cytological map.
	 * @return Cytological map corresponding to
	 * given id.  Throws an exception if a cytological
	 * map is not available with given ID.
	 */
	public CytologicalMap load(final Long id) {
		return this.map;
	}
	
	/**
	 * Returns a dummy cytological map.
	 * @param organism An organism
	 * @param chromosome Chromosome number
	 * @return A cytological map or null if there
	 * is no map from given organism and chromosome.
	 */
	public CytologicalMap load(final Organism organism,
			final short chromosome) {
		this.map.setChromosome(chromosome);
		return this.map;
	}

	/**
	 * Does nothing in this implementation.
	 * @param cytologicalMap Cytological map to delete.
	 */
	public void delete(final CytologicalMap cytologicalMap) {
		
	}
	
	
	/**
	 * Load all.  Does nothing in this implementations.
	 * @return Always returns null.
	 */
	public List<CytologicalMap> loadAll() {
		return null;
	}
	
	
	/**
	 * Delete all cytological maps from given organism.
	 * Does nothhing in this implementation.
	 * @param organism Organism.
	 */
	public void deleteAll(final Organism organism) {
		
	}
	
	
	// ==============================
	//     Constructor
	// ==============================
	
	/**
	 * Constructor.
	 * @param size Size of chromosome
	 * @param numCytobands Number of cytobands
	 */
	public CytologicalMapDaoImpl(final long size, final int numCytobands) {
        long centMid = size / 2;
        long centStart = centMid - CENTROMERE_HEIGHT / 2;
        long centEnd = centMid + CENTROMERE_HEIGHT / 2;
        this.map =
        	new CytologicalMap(DEF_CHROMOSOME, centStart, centEnd, null);
        String[] stains = {"gpos33", "gpos66", "gpos50", "gpos100"};
        long cytobandSize = size / numCytobands;
        for (int i = 0; i < numCytobands; i++) {
        	Cytoband c = new Cytoband("c",
        			i * cytobandSize, (i + 1) * cytobandSize,
        			stains[i % stains.length]);
        	map.addCytoband(c);
        }
	}
}
