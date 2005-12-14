/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/Neighborhood.java,v $
$Revision: 1.1 $
$Date: 2005-12-14 19:43:01 $

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
“Research Triangle Institute? and "RTI" must not be used to endorse or promote 
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

package org.rti.regression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * define the neighborhood
 */
public class Neighborhood {
	
	private final int capacity;
	private List neighbors = new ArrayList();

	/**
	 * Constructor - creates a neighborhood of size capacity number of neighbors
	 * @param capacity
	 */
	public Neighborhood (int capacity) {
		this.capacity = capacity;
	}
	
	/**
	 * Looks to see whether the given point/neighbor should be added to the neighborhood
	 * @param n - a neighbor to consider adding to the neighborhood
	 * @return true/false
	 */
	public boolean withinNeighborhood(Neighbor n){
		// If there is still enough room just keep adding  
		if ( neighbors.size() < capacity ) {
			return true;
		}
		// Or if distance is less than the max distance in the neighborhood
		else if ( getMaxDistance() > n.getDistance() ) {
			return true;
		}
		// Otherwise point doesn't belong in the neighborhood
		else
			return false;		
	}
	
	/**
	 * Inserts Neighbor objects into the list neighborhood
	 * @param n - a neighbor to be inserted into list based on distance
	 */
	public void insert(Neighbor n){
		// Add neighbors to list and sort updated list (by neighbor property distance) until capacity is reached 
		if (neighbors.size() < capacity) {
			neighbors.add(n);
 			Collections.sort(neighbors);
		// Otherwise insert new element at proper space and remove last element 
		} else {
			if (n.lessThan((Neighbor)neighbors.get(neighbors.size() - 1))) {
				neighbors.add(n);
				Collections.sort(neighbors);
				neighbors.remove(neighbors.size() - 1);
			}
		}		
	}

	/**
	 * Assign Weight to each neighbor in the neighborhood list once the 
	 * neighborhood list has been finalized. Sets weights for whole list.
	 */
	public void assignWeight(){
		double weight = 0.0;
		for (int i=0; i < neighbors.size(); i++){
			Neighbor curr = null;		
			curr = (Neighbor) neighbors.get(i);
			weight = Math.pow(1 - Math.pow( ( Math.abs( curr.getDistance() ) / getMaxDistance() ),3) ,3);
			//System.out.println("Index=" + curr.getNeighborIndex() + "\t\tDist=" + curr.getDistance() + "\t\tmaxdistance=" + getMaxDistance() + "\t\tweight=" + weight);
			curr.setWeight(weight);
		}	

		// Remove neighbors that have small weights and won't contribute much
		for (int i=0; i < neighbors.size(); i++){
			Neighbor curr = null;		
			curr = (Neighbor) neighbors.get(i);
			if (curr.getWeight() <.01) 
				neighbors.remove(i);
		}			
	}
	
	
	/**
	 * Returns an array with all of the weights for the given neighborhood
	 * @return double []
	 */
	public double[] returnWeightsAsArray(){
		double[] weightArray = new double[ capacity ];
		Neighbor curr = null;

		//System.out.println("calling: returnWeightsAsArray");
		for (int i=0; i < neighbors.size(); i++){				
			curr = (Neighbor) neighbors.get(i);
			weightArray[i] = curr.getWeight();
			//System.out.println("Weight for element [" + i + "]: weight=" + weightArray[i]);
		}
		return weightArray;
	}
	

	/**
	 * Returns a array with all of the original x or y vector indices for the given neighborhood
	 * @return double []
	 */
	public int[] returnIndicesAsArray(){
		int[] indexArray = new int[ capacity ];
		Neighbor curr = null;
		
		//System.out.println("calling: returnIndicesAsArray");
		for (int i=0; i < neighbors.size(); i++){				
			curr = (Neighbor) neighbors.get(i);
			indexArray[i] = curr.getNeighborIndex();
			//System.out.println("Original data Index Index[" + i + "]=" + indexArray[i]);
		}
		return indexArray;
	}

	
	
	/**
	 * Gets maximum distance in the neighborhood 
	 * @return distance from the last neighbor in the neighborhood
	 */
	public double getMaxDistance(){
		if (neighbors.size() > 0) {
			Neighbor curr = null;		
			curr = (Neighbor) neighbors.get( neighbors.size() - 1 );			
			return curr.getDistance();
		}
		// else handles first observation
		else 
			return 0.0;
	}
	
	
	/**
	 * Number of neighbors
	 * @return Number of neighbors
	 */
	public int numNeighbors() {
		return neighbors.size();
	}
	
	
	/**
	 * Get neighbor at given index
	 * @param p Index
	 * @return Neighbor at given index
	 */
	public Neighbor getNeighborAt(int p) {
		return (Neighbor)neighbors.get(p);
	}
	
	
	/**
	 * 
	 * @return Capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	
	
}

