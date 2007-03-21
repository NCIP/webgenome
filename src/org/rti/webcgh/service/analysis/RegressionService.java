/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/service/analysis/RegressionService.java,v $
$Revision: 1.3 $
$Date: 2007-03-21 21:59:13 $

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


package org.rti.webcgh.service.analysis;

import org.apache.log4j.Logger;
import org.rosuda.JRclient.REXP;
import org.rosuda.JRclient.RList;
import org.rosuda.JRclient.RSrvException;
import org.rosuda.JRclient.Rconnection;
import org.rti.webcgh.analysis.AcghData;
import org.rti.webcgh.core.WebcghSystemException;
import org.rti.webcgh.util.SystemUtils;



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
            throw new WebcghSystemException(
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
			throw new WebcghSystemException(
					"Error setting up RServe for aCGH run", e);
		}
		return c;
	}
	
}
