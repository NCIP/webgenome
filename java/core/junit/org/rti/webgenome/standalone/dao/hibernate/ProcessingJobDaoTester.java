/*
$Revision: 1.2 $
$Date: 2007-04-13 02:52:13 $

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

package org.rti.webgenome.standalone.dao.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.rti.webgenome.domain.ShoppingCart;
import org.rti.webgenome.standalone.ProcessingJob;
import org.rti.webgenome.standalone.ProcessingJobStatus;
import org.rti.webgenome.standalone.dao.ProcessingJobDao;

import junit.framework.TestCase;
import java.util.HashSet;
import java.util.Set ;
import java.util.Date ;

/**
 * Tester for <code>ProcessingJobDao</code>.
 * @author djackman
 */
public final class ProcessingJobDaoTester extends TestCase {
    
    /**
     * Test methods.
     */
    public void testMethods() {
    	
    	assertTrue(true);
        
//        //
//        //    T E S T    S E T U P
//        //
//        ApplicationContext ctx = new ClassPathXmlApplicationContext(
//            "org/rti/webgenome/standalone/dao/hibernate/beans.xml");
//        ProcessingJobDao dao = (ProcessingJobDao) ctx.getBean("processingJobDao");
//        
//        String user = "aUser";
//        
//        //
//        //    A D D    T E S T
//        //
//        ProcessingJob processingJob = new ProcessingJob () ;
//        processingJob.setUserId( user ) ;
//        processingJob.setJobProperties( "job properties" ) ;
//        processingJob.setPercentComplete( 99 ) ;
//        processingJob.setRequestId( "request-id-0001") ;
//        processingJob.setType( "job-type" ) ;
//        
//        ProcessingJobStatus processingJobStatus1 = new ProcessingJobStatus() ;
//        processingJobStatus1.setStatus( "status1" ) ;
//        processingJobStatus1.setDatetime( new Date() ) ;
//        processingJob.add( processingJobStatus1 ) ;
//        
//        ProcessingJobStatus processingJobStatus2 = new ProcessingJobStatus() ;
//        processingJobStatus2.setStatus( "status2" ) ;
//        processingJob.add ( processingJobStatus2 ) ;
//        
//        Long id = dao.add ( processingJob ) ;
//        
//        //
//        //    G E T    T E S T
//        //
//        ProcessingJob processingJobRead = dao.getByPrimaryKey( id ) ;
//        assertNotNull ( processingJobRead ) ;
//        assertEquals ( processingJobRead.getRequestId(), processingJob.getRequestId() ) ;
//        
//        //
//        //    U P D A T E    T E S T
//        //
//        processingJob.setPercentComplete( 20 ) ;
//        ProcessingJobStatus status3 = new ProcessingJobStatus() ;
//        status3.setStatus( "status3" ) ;
//        processingJob.add( status3 ) ;
//
//        dao.update( processingJob ) ;
//        
//        processingJobRead = dao.getByPrimaryKey( processingJob.getJobId() ) ;
//        assertEquals ( 3, processingJobRead.getProcessingJobStatuses().size() ) ;
//        assertEquals ( 20, processingJobRead.getPercentComplete().intValue() ) ;
//        
//        //
//        //    U P D A T E    P E R C E N T    C O M P L E T E
//        //
//        dao.updatePercentComplete(id, 36 ) ;
//        processingJobRead = dao.getByPrimaryKey( id ) ;
//        assertEquals ( 36, processingJobRead.getPercentComplete().intValue() ) ;
//        
//        //
//        //    D E L E T E    T E S T
//        //
//        dao.delete( processingJob ) ;
//        processingJobRead = dao.getByPrimaryKey ( id ) ;
//        assertNull ( processingJobRead ) ;
    }
}
