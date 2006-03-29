/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/ShoppingCart.java,v $
$Revision: 1.2 $
$Date: 2006-03-29 22:26:30 $

The Web CGH Software License, Version 1.0

Copyright 2003 RTI. This software was developed in conjunction with the National 
Cancer Institute, and so to the extent government employees are co-authors, any 
rights in such works shall be subject to Title 17 of the United States Code, 
section 105.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

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
FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO EVENT SHALL THE NATIONAL 
CANCER INSTITUTE, RTI, OR THEIR AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/
package org.rti.webcgh.array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



/**
 * Contains data selected for plotting
 */
public class ShoppingCart {
    
    
    // ====================================================
    //          State variables
    // ====================================================
    
    private final Collection items = new ArrayList();
    private final Map itemsMap = new HashMap();
    private final Map assemblies = new HashMap();
    
    
    
    // ===================================================
    //             Constructors
    // ===================================================
    
    /**
     * Constructor
     */
    public ShoppingCart() {}
    
    
    
    // ===================================================
    //        Public methods
    // ===================================================
    
    /**
     * Filter out experiments from given list that have already been selected
     * @param experiment Data to filter
     * @return Filtered array experiment meta data
     */
    public Experiment[] filterOutSelectedExperiments(Experiment[] experiment) {
        Collection metaCol = new ArrayList();
        for (int i = 0; i < experiment.length; i++) {
            Object key = experiment[i].getCacheKey();
            if (! itemsMap.containsKey(key))
                metaCol.add(experiment[i]);
        }
        Experiment[] newMetaData = new Experiment[0];
        newMetaData = (Experiment[])metaCol.toArray(newMetaData);
        return newMetaData;
    }
    
    
    /**
     * Add data
     * @param experiments Genome array data sets
     */
    public void add(Experiment[] experiments) {
    	
    	//iterate over experiments to be added
        for (int i = 0; i < experiments.length; i++) {
            Experiment experiment = experiments[i];
            
            // delete any old duplicate copies of current experiment to be added
            Experiment target = null;
            for (Iterator it = this.items.iterator(); it.hasNext() && target == null;) {
            	Experiment exp = (Experiment) it.next();
            	if (experiment.sameExperiment(exp))
            		target = exp;
            }
            if (target != null)
            	this.remove(target.getDatabaseName(), target.getName());
            
            // now add current experiment
            this.items.add(experiment);
            this.itemsMap.put(experiments[i].getCacheKey(), experiment);
        }
    }
    
    
    public void purgeClientData() {
    	Collection tempExps = new ArrayList(this.items);
    	for (Iterator it = tempExps.iterator(); it.hasNext();) {
    		Experiment exp = (Experiment)it.next();
    		if (exp.fromClientApp())
    			this.purge(exp);
    	}
    }
    
    
    /**
     * Remove genome array data set with given database and name
     * @param dbName Name of database
     * @param expName Experiment name
     */
    public void remove(String dbName, String expName) {
        Object key = Experiment.cacheKey(dbName, expName);
        Experiment experiment = (Experiment)this.itemsMap.get(key);
        this.purge(experiment);
    }
    
    
    private void purge(Experiment experiment) {
    	Object key = experiment.getCacheKey();
        this.itemsMap.remove(key);
        this.items.remove(experiment);
        
        // If genome array data set represents last in cart from
        // a particular organism, remove associated assembly
        Organism organism = this.getOrganism(experiment);
        if (organism != null && ! this.containsOrganism(organism))
            this.removeAssembly(organism);
    }
    
    
    /**
     * Is cart empty?
     * @return T/F
     */
    public boolean isEmpty() {
        return this.items.size() == 0;
    }
    
    
    /**
     * Get genome array data sets in cart
     * @return Genome array data sets
     */
    public Experiment[] getExperiments() {
        Experiment[] gads = new Experiment[0];
        gads = (Experiment[])this.items.toArray(gads);
        return gads;
    }
    
    
    /**
     * Set genome assembly
     * @param assembly Assembly
     */
    public void addAssembly(GenomeAssembly assembly) {
        Organism organism = assembly.getOrganism();
        Long key = organism.getId();
        this.assemblies.put(key, assembly);
    }
    
    
    /**
     * Add assemblies
     * @param assemblies Genome assemblies
     */
    public void addAssemblies(GenomeAssembly[] assemblies) {
        for (int i = 0; i < assemblies.length; i++) {
            GenomeAssembly assembly = assemblies[i];
            this.addAssembly(assembly);
        }
    }
    
    
    /**
     * Get genome assembly associated with given organism
     * @param organism An organism
     * @return A genome assembly
     */
    public GenomeAssembly getGenomeAssembly(Organism organism) {
        Long key = organism.getId();
        return (GenomeAssembly)this.assemblies.get(key);
    }
    
    
    /**
     * Get all genome assemblies
     * @return Genome assemblies
     */
    public GenomeAssembly[] getGenomeAssemblies() {
        GenomeAssembly[] assemblies = new GenomeAssembly[0];
        Collection assemblyCol = this.assemblies.values();
        assemblies = (GenomeAssembly[])assemblyCol.toArray(assemblies);
        return assemblies;
    }
    
    
    /**
     * Get all organisms in cart
     * @return Organisms
     */
    public Organism[] getOrganisms() {
        Organism[] organisms = new Organism[0];
        Collection orgCol = new ArrayList();
        for (Iterator it = this.assemblies.values().iterator(); it.hasNext();) {
            GenomeAssembly assembly = (GenomeAssembly)it.next();
            Organism organism = assembly.getOrganism();
            orgCol.add(organism);
        }
        organisms = (Organism[])orgCol.toArray(organisms);
        return organisms;
    }
    
    
    /**
     * Are there any data sets in cart mapped to given
     * assembly?
     * @param assembly Genome assembly
     * @return T/F
     */
    public boolean containsAssembly(GenomeAssembly assembly) {
    	boolean contains = false;
    	for (Iterator it = this.assemblies.values().iterator(); 
    		it.hasNext() && ! contains;) {
    		GenomeAssembly currAssembly = (GenomeAssembly)it.next();
    		if (assembly.getId() == currAssembly.getId())
    			contains = true;
    	}
    	return contains;
    }
    
    
    /**
     * Is shopping cart compatible with given assembly?
     * (i.e. Is assembly associated with data in cart or
     * is cart empty?)
     * @param assembly Genome assembly
     * @return T/F
     */
    public boolean compatible(GenomeAssembly assembly) {
        return this.items.size() == 0 || this.containsAssembly(assembly);
    }
    
    
    /**
     * Get set of quantitation types
     * @return Set of QuantitationType objects
     */
    public Set quantitationTypes() {
        Set qTypes = new HashSet();
        if (this.items != null)
            for (Iterator it = this.items.iterator(); it.hasNext();) {
                Experiment exp = (Experiment)it.next();
                qTypes.addAll(exp.quantitationTypes());
            }
        return qTypes;
    }
    
    
    /**
     * Get quantitation type associated with given key
     * @param key Key
     * @return Quantitation type
     */
    public QuantitationType getQuantitationType(String key) {
        QuantitationType qt = null;
        for (Iterator it = this.quantitationTypes().iterator(); it.hasNext() && qt == null;) {
            QuantitationType tempQt = (QuantitationType)it.next();
            if (key.equals(tempQt.getName()))
                qt = tempQt;
        }
        return qt;
    }
    
    
    // =====================================================
    //            Private methods
    // =====================================================
    
    
    private Organism getOrganism(Experiment gads) {
        Organism org = null;
        for (BioAssayIterator it = gads.bioAssayIterator(); it.hasNext() && org == null;) {
            BioAssay gad = it.next();
            org = gad.getOrganism();
        }
        return org;
    }
    
    
    private boolean containsOrganism(Organism organism) {
        boolean contains = false;
        for (Iterator it = this.items.iterator(); it.hasNext() && ! contains;) {
            Experiment gads = (Experiment)it.next();
            Organism currOrganism = this.getOrganism(gads);
            if (organism.getId() == currOrganism.getId())
                contains = true;
        }
        return contains;
    }
    
    
    private void removeAssembly(Organism organism) {
        Long key = organism.getId();
        this.assemblies.remove(key);
    }    
}
