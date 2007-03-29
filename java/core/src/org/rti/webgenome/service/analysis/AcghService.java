/*

$Source: /share/content/gforge/webcgh/webgenome/java/core/src/org/rti/webgenome/service/analysis/AcghService.java,v $
$Revision: 1.1 $
$Date: 2007-03-29 17:03:36 $

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


package org.rti.webgenome.service.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.rosuda.JRclient.REXP;
import org.rosuda.JRclient.RList;
import org.rosuda.JRclient.RSrvException;
import org.rosuda.JRclient.Rconnection;
import org.rti.webgenome.analysis.AcghData;
import org.rti.webgenome.core.WebcghSystemException;
import org.rti.webgenome.util.IOUtils;
import org.rti.webgenome.util.SystemUtils;


/**
 * Apply Acgh package in R to smooth AcghData
 * @author Kungyen
 */
public class AcghService {
	private static final Logger LOGGER = Logger.getLogger(AcghService.class);
	private static final List<String> R_COMMAND_LIST;
	
	static {
		R_COMMAND_LIST = getCommandList();
	}
    
    /**
     * Constructor
     */
    public AcghService ( ) {
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
	 * Read R commands from file. 
	 * @return Return a list of R commands
	 */
	private static List<String> getCommandList() {
	    BufferedReader rcmdIn = new BufferedReader(new InputStreamReader(
	    		IOUtils.getInputStream("aCGH_smooth.R")));
	    List<String> rcmdList = new ArrayList<String>();
	    try {
			String line = rcmdIn.readLine();
			while (line != null) {
				rcmdList.add(line);
				line = rcmdIn.readLine();
			}
			rcmdIn.close();
		} catch (IOException e) {
			throw new WebcghSystemException("Error reading R command file", e);
		}
	    return rcmdList;
	}

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


    /**
	 * Execute smoothing R commands and retrieve smoothed values. 
	 * @param data The data given as an AcghData object
	 * @throws RSrvException if an error occurs during R operation
	 */
	public void run(AcghData data) throws RSrvException {
		LOGGER.info("Trying new Rserve connection to ip address '" +
                    this.getRserveIpAddress() + 
                    "' port '" + this.getRservePort() + "' ...");	
		Rconnection c = this.setup(data);

        String incompleteCmd = "";
        boolean isCompleteCmd = true;
        int sum;
        // loop through commands and execute each
		for (String cmd : R_COMMAND_LIST) {
			// check whether cmd is finished in one line
			if (!isCompleteCmd) {
				cmd = incompleteCmd + cmd;
			}
			
			sum = 0;
			for (int t = 0 ; t < cmd.length() ; t++) {
				if (cmd.charAt(t) == '(') {sum++;}
				else if (cmd.charAt(t) == ')') {sum--;}
			}
			
			if (sum%2 == 0) { // cmd is complete
				isCompleteCmd = true;
				incompleteCmd = "";
				
				// neither blank string nor comment is a valid cmd for Rserve
				if (!cmd.equals("") && !cmd.startsWith("#")) {
				    System.out.print("Trying R command: \"" + cmd + "\"...");
				    c.voidEval(cmd);
				    
				    // cmd which retrieves smoothed values from R
				    if (cmd.startsWith("result <-")) {
				    	RList l = c.eval(cmd).asList();
				    	//double[] lx = (double[]) l.at(4).getContent(); //observed values
				    	double[] lx = (double[]) l.at(3).getContent(); //smoothed values			
				    	//double[] lx = (double[]) l.at(2).getContent(); //states
				    	//double[] lx = (double[]) l.at(1).getContent(); //kb
				    	//double[] lx = (double[]) l.at(0).getContent(); //chromosome
				    	
				    	data.setSmoothedRatios(lx);
				    }
				    
				    System.out.println("OK");
				}
			}
			else { // incomplete cmd
				isCompleteCmd = false;
				incompleteCmd = cmd;
			}
		}
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
			LOGGER.info("Connected OK!\n");
			
			// load aCGH library
			c.eval("library(aCGH)");
			
			// prepare data before running R commands from file
			int[] narray = new int[data.getSize()];
			for (int i = 0; i < data.getSize(); i++) {
				narray[i] = i + 1;
			}
			c.assign("clonesInfo_n", 
					new REXP(REXP.XT_ARRAY_INT, narray));
			c.assign("clonesInfo_Clone", 
					new REXP(REXP.XT_ARRAY_STR, data.getClones()));
			c.assign("clonesInfo_Target", 
					new REXP(REXP.XT_ARRAY_STR, data.getTargets()));
			c.assign("clonesInfo_Chrom", 
					new REXP(REXP.XT_ARRAY_INT, data.getChromosomes()));
			c.assign("clonesInfo_kb", 
					new REXP(REXP.XT_ARRAY_INT, data.getPositions()));
			c.assign("log2ratios_log2TR", 
					new REXP(REXP.XT_ARRAY_DOUBLE, data.getLog2Ratios()));
			
			c.eval("clones_info <-data.frame(n=clonesInfo_n, " 
					+ "Clone=clonesInfo_Clone, Target=clonesInfo_Target, " 
					+ "Chrom=clonesInfo_Chrom, kb=clonesInfo_kb)");
			c.eval("log2_ratios <-data.frame(ID=clonesInfo_Clone, "
				    + "log2TR=log2ratios_log2TR)");
			
			/*
			c.eval("capture.output(log2_ratios, " +
					"file = \"C:/test/log2_ratiosx.txt\", append = FALSE)");
			c.eval("capture.output(clones_info, " +
					"file = \"C:/test/clones_infox.txt\", append = FALSE)");
			*/
		} catch (RSrvException e) {
			throw new WebcghSystemException(
					"Error setting up RServe for aCGH run", e);
		}
		return c;
	}
	
}
