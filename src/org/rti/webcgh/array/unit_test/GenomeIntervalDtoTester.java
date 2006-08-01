/*

$Source: /share/content/gforge/webcgh/webgenome/src/org/rti/webcgh/array/unit_test/GenomeIntervalDtoTester.java,v $
$Revision: 1.2 $
$Date: 2006-08-01 19:37:11 $

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
package org.rti.webcgh.array.unit_test;

import org.rti.webcgh.array.GenomeIntervalDto;
import org.rti.webcgh.plot.Units;

import junit.framework.TestCase;

/**
 * Tester for GenomeIntervalDto
 */
public class GenomeIntervalDtoTester extends TestCase {
    
    
    /**
     * 
     * @throws Exception
     */
    public void testParse() throws Exception {
        String s1 = "1:100-200";
        GenomeIntervalDto[] dtos = GenomeIntervalDto.parse(s1, Units.BP);
        assertEquals(dtos.length, 1);
        assertEquals(dtos[0].getChromosome(), 1);
        assertEquals(dtos[0].getStart(), 100.0, 0.0);
        assertEquals(dtos[0].getEnd(), 200.0, 0.0);
        
        s1 = "1 : 100 - 200 ";
        dtos = GenomeIntervalDto.parse(s1, Units.BP);
        assertEquals(dtos.length, 1);
        assertEquals(dtos[0].getChromosome(), 1);
        assertEquals(dtos[0].getStart(), 100.0, 0.0);
        assertEquals(dtos[0].getEnd(), 200.0, 0.0);
        
        s1 = "1:100-200;2:300-450";
        dtos = GenomeIntervalDto.parse(s1, Units.BP);
        assertEquals(dtos.length, 2);
        assertEquals(dtos[0].getChromosome(), 1);
        assertEquals(dtos[0].getStart(), 100.0, 0.0);
        assertEquals(dtos[0].getEnd(), 200.0, 0.0);
        assertEquals(dtos[1].getChromosome(), 2);
        assertEquals(dtos[1].getStart(), 300.0, 0.0);
        assertEquals(dtos[1].getEnd(), 450.0, 0.0);
        
        s1 = "1";
        dtos = GenomeIntervalDto.parse(s1, Units.BP);
        assertEquals(dtos.length, 1);
        assertEquals(dtos[0].getChromosome(), 1);
        assertTrue(Double.isNaN(dtos[0].getStart()));
        assertTrue(Double.isNaN(dtos[0].getEnd()));
        
        s1 = "1:";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = ":";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = ":100-200";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = "1:100-";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = "1:100";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = "1:-200";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
        
        s1 = "1:100-200;2:";
        try {
            GenomeIntervalDto.parse(s1, Units.BP);
            fail();
        } catch (Exception e){}
    }

}
