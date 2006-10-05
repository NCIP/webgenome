/*
$Revision$
$Date$

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

package org.rti.webcgh.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Represents an on-line data shopping cart.
 * @author dhall
 *
 */
public class ShoppingCart implements Serializable {
    
    /** Serialized version ID. */
    private static final long serialVersionUID = (long) 1;
    
    // ====================================
    //       Attributes
    // ====================================
    
    /** Identifier used as primary key for persistent storage. */
    private Long id = null;
    
    /** Experiments in shopping cart. */
    private Set<Experiment> experiments = new HashSet<Experiment>();
    
    /** Plots in shopping cart. */
    private Set<Plot> plots = new HashSet<Plot>();
   
    /** User name associated with cart. */
    private String userName = null;
    
    
    // =============================
    //     Getters/setters
    // =============================

    /**
     * Get plots in cart.
     * @return Plots in cart.
     */
    public final Set<Plot> getPlots() {
		return plots;
	}

    /**
     * Set plots in cart.
     * @param plots Plots in cart.
     */
	public final void setPlots(final Set<Plot> plots) {
		this.plots = plots;
	}

	/**
     * Get identifier used as a primary key in persitence.
     * @return Identifier
     */
    public final Long getId() {
        return id;
    }

    /**
     * Set identifier used as primary key in persistence.
     * @param id Identifier
     */
    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Get experiments in cart.
     * @return Experiments in cart
     */
    public final Set<Experiment> getExperiments() {
        return experiments;
    }

    /**
     * Set experiments in cart.
     * @param experiments Experiments in cart.
     */
    public final void setExperiments(final Set<Experiment> experiments) {
        this.experiments = experiments;
    }

    /**
     * Get user name associated with cart.
     * @return User name
     */
    public final String getUserName() {
        return userName;
    }

    /**
     * Set user name associated with cart.
     * @param userName User name
     */
    public final void setUserName(final String userName) {
        this.userName = userName;
    }

    
    // ================================
    //      Constructors
    // ================================
    
    /**
     * Default constructor.
     */
    public ShoppingCart() {
        
    }
    
    /**
     * Constructor.
     * @param userName User name
     */
    public ShoppingCart(final String userName) {
        this.userName = userName;
    }
   

    // =================================
    //      Other public methods
    // =================================
    
    /**
     * Add an experiment to cart.
     * @param experiment An experiment
     */
    public final void add(final Experiment experiment) {
        this.experiments.add(experiment);
    }
    
    
    /**
     * Add experiments to cart.
     * @param experiments Experiments
     */
    public final void add(final Collection<Experiment> experiments) {
    	this.experiments.addAll(experiments);
    }
    
    
    /**
     * Remove an experiment from cart.
     * @param experiment An experiment.
     */
    public final void remove(final Experiment experiment) {
        this.experiments.remove(experiment);
    }
    
    
    /**
     * Add plot to cart.
     * @param plot A plot.
     */
    public final void add(final Plot plot) {
    	this.plots.add(plot);
    }
    
    
    /**
     * Remove plot from cart.
     * @param plot A plot.
     */
    public final void remove(final Plot plot) {
    	this.plots.remove(plot);
    }
    
    
    /**
     * Remove experiment with given id from cart.
     * @param id Experiment primary key identifier.
     */
    public final void removeExperiment(final Long id) {
    	Iterator<Experiment> it = this.experiments.iterator();
    	while (it.hasNext()) {
    		if (id.equals(it.next().getId())) {
    			it.remove();
    			break;
    		}
    	}
    }
    
    
    /**
     * Remove plot with given id from cart.
     * @param id Plot primary key identifier.
     */
    public final void removePlot(final Long id) {
    	Iterator<Plot> it = this.plots.iterator();
    	while (it.hasNext()) {
    		if (id.equals(it.next().getId())) {
    			it.remove();
    			break;
    		}
    	}
    }
}
