/*
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $


*/

package org.rti.webgenome.service.plot;

import java.util.List;

import org.rti.webgenome.domain.Cytoband;
import org.rti.webgenome.domain.CytologicalMap;
import org.rti.webgenome.domain.Organism;
import org.rti.webgenome.service.dao.CytologicalMapDao;

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
