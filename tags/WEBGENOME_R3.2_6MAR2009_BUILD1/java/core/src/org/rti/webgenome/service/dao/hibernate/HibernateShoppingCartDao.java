/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*
$Revision: 1.6 $
$Date: 2007-12-04 23:06:40 $


*/

package org.rti.webgenome.service.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.domain.Plot;
import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.graphics.event.MouseOverStripes;
import org.rti.webgenome.graphics.io.ClickBoxes;
import org.rti.webgenome.service.dao.ShoppingCartDao;
import org.rti.webgenome.service.io.DataFileManager;
import org.rti.webgenome.util.DbUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Implementation of <code>ShoppingCartDao</code> using Hibernate.
 * @author dhall
 *
 */
public final class HibernateShoppingCartDao extends HibernateDaoSupport
    implements ShoppingCartDao {
	
	/** Data source for JDBC queries. */
	private DataSource dataSource = null;
	
	/** Data file manager for serializing plot interactivity objects. */
	private DataFileManager dataFileManager = null;
	
	
    /**
     * Set data source for JDBC queries.
     * @param dataSource Data source
     */
    public void setDataSource(final DataSource dataSource) {
		this.dataSource = dataSource;
	}
    
    
    /**
     * Set data file manager for serializing plot interactivity objects.
     * @param dataFileManager Data file manager
     */
	public void setDataFileManager(final DataFileManager dataFileManager) {
		this.dataFileManager = dataFileManager;
	}




	/**
     * Save to persistent storage.
     * @param shoppingCart A shopping cart
     */
    public void save(final ShoppingCart shoppingCart) {
        this.persistPlotInteractivityObjects(shoppingCart);
        this.getHibernateTemplate().save(shoppingCart);
    }
    
    /**
     * Persist all embedded objects involved in plot interactivity
     * using serialization.
     * @param shoppingCart A shopping cart
     */
    private void persistPlotInteractivityObjects(
    		final ShoppingCart shoppingCart) {
    	for (Plot plot : shoppingCart.getPlots()) {
        	if (plot.getClickBoxesFileName() == null
        			&& plot.getClickBoxes() != null) {
        		plot.setClickBoxesFileName(
        				this.dataFileManager.saveClickBoxes(
        						plot.getClickBoxes()));
        	}
        	if (plot.getMouseOverStripesFileName() == null
        			&& plot.getMouseOverStripes() != null) {
        		plot.setMouseOverStripesFileName(
        				this.dataFileManager.saveMouseOverStripes(
        						plot.getMouseOverStripes()));
        	}
        }
    }
    
    /**
     * Recover all embedded objects involved in plot interactivity
     * using serialization.
     * @param shoppingCart A shopping cart
     */
    private void recoverPlotInteractivityObjects(
    		final ShoppingCart shoppingCart) {
    	for (Plot plot : shoppingCart.getPlots()) {
    		if (plot.getClickBoxes() == null
    				&& plot.getClickBoxesFileName() != null) {
    			plot.setClickBoxes((Set<ClickBoxes>)
    					this.dataFileManager.recoverObject(
    							plot.getClickBoxesFileName()));
    		}
    		if (plot.getMouseOverStripes() == null
    				&& plot.getMouseOverStripesFileName() != null) {
    			plot.setMouseOverStripes((Set<MouseOverStripes>)
    					this.dataFileManager.recoverObject(
    							plot.getMouseOverStripesFileName()));
    		}
    	}
    }
    
    
    /**
     * Delete embedded plot interactivity objects.
     * @param shoppingCart A shopping cart
     */
    private void deletePlotInteractivityObjects(
    		final ShoppingCart shoppingCart) {
    	for (Plot plot : shoppingCart.getPlots()) {
    		if (plot.getClickBoxesFileName() != null) {
    			this.dataFileManager.deleteDataFile(
    					plot.getClickBoxesFileName());
    			plot.setClickBoxesFileName(null);
    		}
    		if (plot.getMouseOverStripesFileName() != null) {
    			this.dataFileManager.deleteDataFile(
    					plot.getMouseOverStripesFileName());
    			plot.setMouseOverStripesFileName(null);
    		}
    	}
    }
    
    
    /**
     * Load cart with given identifier from persistent storage.
     * @param id Shopping cart identifier
     * @return A shopping cart
     */
    public ShoppingCart load(final Long id) {
        ShoppingCart cart = (ShoppingCart)
            this.getHibernateTemplate().load(ShoppingCart.class, id);
        this.recoverPlotInteractivityObjects(cart);
        return cart;
    }
    
    
    /**
     * Load cart associated with given user name from
     * persistent storage.
     * @param userName User name
     * @param domain Domain in which user name applies
     * @return A shopping cart
     */
    public ShoppingCart load(final Long userId, final String domain) {
        String query = "from ShoppingCart cart where cart.userId = ? "
        	+ "and cart.userDomain = ?";
        Object[] args = new Object[] {userId, domain};
        List carts = this.getHibernateTemplate().find(query, args);
        ShoppingCart cart = null;
        if (carts != null && carts.size() > 0) {
            cart = (ShoppingCart) carts.get(0);
            this.recoverPlotInteractivityObjects(cart);
        }
        return cart;
    }
    
    
    /**
     * Update persistently stored properties of given cart.
     * @param shoppingCart A shopping cart
     */
    public void update(final ShoppingCart shoppingCart) {
    	this.persistPlotInteractivityObjects(shoppingCart);
        this.getHibernateTemplate().update(shoppingCart);
    }
    
    
    /**
     * Delete given cart from persistent storage.
     * @param shoppingCart A shopping cart
     */
    public void delete(final ShoppingCart shoppingCart) {
    	this.deletePlotInteractivityObjects(shoppingCart);
        this.getHibernateTemplate().delete(shoppingCart);
    }

    
    /**
     * Get names of all image files in shopping
     * cart.
     * @return Names of all image files in shopping
     * cart.
     */
    public Collection<String> getAllImageFileNames() {
    	Collection<String> fileNames = new ArrayList<String>();
    	PreparedStatement stmt = null;
    	ResultSet rset = null;
    	String sql = "SELECT def_img_file_name FROM plot";
    	try {
			Connection con = this.dataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rset = stmt.executeQuery();
			while (rset.next()) {
				fileNames.add(rset.getString(1));
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error getting image file names", e);
		} finally {
			DbUtils.close(rset);
			DbUtils.close(stmt);
		}
    	sql = "SELECT file_name FROM img_file_map";
    	try {
			Connection con = this.dataSource.getConnection();
			stmt = con.prepareStatement(sql);
			rset = stmt.executeQuery();
			while (rset.next()) {
				fileNames.add(rset.getString(1));
			}
		} catch (SQLException e) {
			throw new WebGenomeSystemException(
					"Error getting image file names", e);
		} finally {
			DbUtils.close(rset);
			DbUtils.close(stmt);
		}
    	return fileNames;
    }


    /**
     * {@inheritDoc}
     */
	public Set<String> getAllDataFileNames() {
		Set<String> fNames = new HashSet<String>();
		try {
			fNames.addAll(DbUtils.getStringValues(this.dataSource,
					"array_data_file_index", "file_name"));
			fNames.addAll(DbUtils.getStringValues(this.dataSource,
					"plot", "cb_file_name"));
			fNames.addAll(DbUtils.getStringValues(this.dataSource,
					"plot", "mos_file_name"));
			fNames.addAll(DbUtils.getStringValues(this.dataSource,
					"reporter_file_names", "file_name"));
		} catch (Exception e) {
			throw new WebGenomeSystemException(
					"Error retrieving all current data files from "
					+ "the database", e);
		}
		return fNames;
	}
}
