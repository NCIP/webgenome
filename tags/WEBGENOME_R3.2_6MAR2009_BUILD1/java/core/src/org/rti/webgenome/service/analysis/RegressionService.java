/*L
 *  Copyright RTI International
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/webgenome/LICENSE.txt for details.
 */

/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/service/analysis/RegressionService.java,v $
$Revision: 1.2 $
$Date: 2007-03-29 18:02:05 $



*/


package org.rti.webgenome.service.analysis;

import org.apache.log4j.Logger;
import org.rosuda.JRclient.REXP;
import org.rosuda.JRclient.RList;
import org.rosuda.JRclient.RSrvException;
import org.rosuda.JRclient.Rconnection;
import org.rti.webgenome.analysis.AcghData;
import org.rti.webgenome.core.WebGenomeSystemException;
import org.rti.webgenome.util.SystemUtils;



/**
 * Invoke regression functions in R and get fitted values
 * @author Kungyen
 */
public class RegressionService {
	private static final Logger LOGGER = Logger.getLogger(RegressionService.class);
	
    /**
     * IP Address of RServe
     */
    private String rserveIpAddress = "localhost" ;
    
    /**
     * Port of RServe
     */
    private int rservePort = 6311 ; // default port

    /** Get method for RServ IP Address
     * @return RServ IP Address
     */
    public String getRserveIpAddress() {
        return rserveIpAddress;
    }

    /** Set method for RServe IP Address */
    public void setRserveIpAddress(String rserveIpAddress) {
        this.rserveIpAddress = rserveIpAddress;
    }

    /** Get method for RServ Port
     * @return RServe Port
     */
    public int getRservePort() {
        return rservePort;
    }
    
    /* Set method for RServe Port */
    public void setRservePort(int rservePort) {
        this.rservePort = rservePort;
    }
    
    public RegressionService () {
        this.rserveIpAddress = SystemUtils.getApplicationProperty("rserve.ipAddress");
        try {
            Integer rServeInteger = new Integer(SystemUtils.getApplicationProperty("rserve.port"));
            this.rservePort = rServeInteger.intValue() ;
        }
        catch ( NumberFormatException e ) {
            throw new WebGenomeSystemException(
                    "Error obtaining RServ Port Number from application properties file.", e);
        }
    }

	/**
	 * Run linear model regression. 
	 * @param data The data given as an AcghData object
	 * @throws RSrvException if an error occurs during R operation
	 */
	public void runLinearRegression(AcghData data) throws RSrvException {
		this.run(data, "lm", "log2TR ~ kb");
	}
	
	/**
	 * Run loess regression. 
	 * @param data The data given as an AcghData object
	 * @throws RSrvException if an error occurs during R operation
	 */
	public void runLoess(AcghData data) throws RSrvException {
		this.run(data, "loess", "log2TR ~ kb");
	}

	/**
	 * Run regression R commands and retrieve fitted values. 
	 * @param data The data given as an AcghData object
	 * @param func R command to be invoked
	 * @param model Regression model assigned
	 * @throws RSrvException if an error occurs during R operation
	 */
	private void run(AcghData data, String func, String model) 
		throws RSrvException {
        LOGGER.info("Trying new Rserve connection to ip address '" +
                this.getRserveIpAddress() + 
                "' port '" + this.getRservePort() + "' ...");    
		Rconnection c = this.setup(data);
		LOGGER.info("OK");
		
		String cmd = "regResult <- " + func + "(" + model + ", regDataSet)";
		LOGGER.info("Trying R command: \"" + cmd + "\"...");	
		RList l = c.eval(cmd).asList();
		LOGGER.info("OK");
		
		int rListIdx = 0;
		if (func.equals("lm")) {
			rListIdx = 4;
		}
		else if (func.equals("loess")) {
			rListIdx = 1;
		}
		double[] fittedValues = (double[]) l.at(rListIdx).getContent();
		data.setSmoothedRatios(fittedValues);

		c.close();
	}
	

	/**
	 * Create a new Rserve connection and transform AcghData to data types in R.
	 * @return Rserve connection
	 */
	private Rconnection setup(AcghData data) {
		Rconnection c;
		try {
            c = new Rconnection( this.getRserveIpAddress(), this.getRservePort() );
			
			// prepare data before running regression
			c.assign("kb", 
					new REXP(REXP.XT_ARRAY_INT, data.getPositions()));
			c.assign("log2TR", 
					new REXP(REXP.XT_ARRAY_DOUBLE, data.getLog2Ratios()));
			c.eval("regDataSet <- data.frame(kb, log2TR)");
			
		} catch (RSrvException e) {
			throw new WebGenomeSystemException(
					"Error setting up RServe for aCGH run", e);
		}
		return c;
	}
	
}
