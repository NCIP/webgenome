/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/regression/Neighbor.java,v $
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

import java.lang.Comparable;

/**
 * define the neighbor
 */
public class Neighbor implements Comparable {
	
	protected int neighborIndex = 0;
	protected double distance = 0.0;
	protected double weight = 0.0;

	/**
	 * Constructor - Creates a neighbor object for a given data point
	 * @param neighborIndex
	 * @param distance
	 */
	public Neighbor (int neighborIndex, double distance) {
		this.neighborIndex = neighborIndex;
		this.distance = distance;		
	}
	
	/**
	 * Constructor - Creates a neighbor object for a given data point
	 * @param neighborIndex
	 * @param distance
	 * @param weight
	 */
	public Neighbor(int neighborIndex, double distance, double weight) {
		this(neighborIndex, distance);
		this.weight = weight;
	}
	
	/**
	 * Setter for property neighborIndex
	 * @param neighborIndex Index of neighbor in the X matrix DoubleList
	 */
	public void setNeighborIndex(int neighborIndex) {
		this.neighborIndex = neighborIndex;
	}

	/**
	 * Getter for property neighborIndex
	 * @return neighborIndex Index of neighbor in the X matrix DoubleList
	 */
	public int getNeighborIndex() {
		return neighborIndex;
	}


	/**
	 * Setter for property distance
	 * @param distance distance between neighbor and the point of interest in the X matrix DoubleList
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Getter for property distance
	 * @return neighborIndex Distance between neighbor neighborIndex and the point of interest 
	 * in the X matrix DoubleList
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Setter for property weight
	 * @param weight: weight for the point of interest in the X matrix DoubleList
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Getter for property weight
	 * @return weight - weight of neighbor in the neighborhood of point of interest 
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * Less than operation
	 * @param n A neighbor to the point of interest
	 * @return T/F
	 */
	public boolean lessThan(Neighbor n) {
		boolean rval = false;
		if (this.getDistance() < n.getDistance())
			rval = true;
		return rval;
	}
	
	/**
	 * Equals operation
	 * @param n A neighbor to the point of interest
	 * @return T/F
	 */
	public boolean equals(Neighbor n) {
		boolean equalTo = false;
		return ( this.getDistance() == n.getDistance() );		
	}
	
	/**
	 * Is each field identical?
	 * @param n A neighbor to the point of interest
	 * @return T/F
	 */
	public boolean equalsExactly(Neighbor n) {
		return 
			( this.getDistance() == n.getDistance() &&
			  this.getNeighborIndex() == n.getNeighborIndex() );
	}	

	
	/**
	 * Greater than operation
	 * @param n A neighbor to the point of interest
	 * @return T/F
	 */
	public boolean greaterThan(Neighbor n) {
		boolean rval = false;
		return ( this.getDistance() > n.getDistance() );
	}

	/**
	 * Comparison operation.  Returns -1 if object less than,
	 * 0 if object equal to, and 1 if object greater than.
	 * @param o: the object to compare with
	 @return the result of compairson, either <,= or >
	 */
	public int compareTo(Object o) {
		int rval = 0;
		Neighbor n = (Neighbor)o;
		if (this.lessThan(n))
			rval = -1;
		else if (this.equals(n))
			rval = 0;
		else if (this.greaterThan(n))
			rval = 1;
		return rval;
	}
	
}
