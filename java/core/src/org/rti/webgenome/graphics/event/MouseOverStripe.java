/*
$Revision: 1.4 $
$Date: 2008-06-16 16:53:40 $


*/
package org.rti.webgenome.graphics.event;

import java.io.Serializable;

import org.rti.webgenome.util.SystemUtils;



/**
 * MouseOver Stripe information.
 * @author mzmuda
 */
public class MouseOverStripe implements Serializable {
	
	/** Serialized version ID. */
	private static final long serialVersionUID = 
		SystemUtils.getLongApplicationProperty("serial.version.uid");


    // =============================
    //       Attributes
    // =============================
	
	/** Primary key value for persistence. */
	private Long id = null;

    /**
     * Start coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
     */
    private int start = 0;

    /**
     * End coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
     */
    private int end = 0;

    /**
     * Stripe text.
     */
    private String text = "";
    

    // =========================================
    //      Constructors
    // =========================================
	/**
     * Constructor.
     */
    public MouseOverStripe() {

    }
    
    /**
     * Constructor.
     * @param start Start coordinate
     * @param end End coordinate
     * @param text Stripe text
     */
    public MouseOverStripe(final int start, final int end, final String text) {
    	this.start = start;
    	this.end = end;
    	this.text = text;
    }


    // =========================================
    //      Getters and Setters
    // =========================================
    
    /**
     * Get primary key value for persistence.
     * @return Primary key value
     */
    public Long getId() {
		return id;
	}
    
    /**
     * Set primary key value for persistence.
     * @param id Primary key value
     */
	public void setId(final Long id) {
		this.id = id;
	}
	
	
	/**
     * Get end coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
	 * @return Returns the end.
	 */
	public int getEnd() {
		return end;
	}
	
	
	/**
	 * Set end coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
	 * @param end The end to set.
	 */
	public void setEnd(final int end) {
		this.end = end;
	}
	
	
	/**
	 * Get start coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
	 * @return Returns the start.
	 */
	public int getStart() {
		return start;
	}
	
	
	/**
	 * Set start coordinate.  This may be an
     * X- or Y-coordinate, depending on the
     * orientation of the container <code>MouseOverStripes</code>.
	 * @param start The start to set.
	 */
	public void setStart(final int start) {
		this.start = start;
	}
	
	
	/**
	 * Get text value.
	 * @return Returns the text.
	 */
	public String getText() {
		return text;
	}
	
	
	/**
	 * Set text value.
	 * @param text The text to set.
	 */
	public void setText(final String text) {
		this.text = text;
	}
}
