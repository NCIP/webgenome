/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

CREATE SEQUENCE SQ_Processing_Job
    MINVALUE 1 MAXVALUE 9999999999999999999999999999
    START WITH 1 INCREMENT BY 1
    NOCACHE ORDER
;
CREATE SEQUENCE SQ_Processing_Job_Status
    MINVALUE 1 MAXVALUE 9999999999999999999999999999
    START WITH 1 INCREMENT BY 1
    NOCACHE ORDER
;
