/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

ALTER TABLE principal ADD (first_name VARCHAR2(40));
ALTER TABLE principal ADD (last_name VARCHAR2(40));
ALTER TABLE principal ADD (email VARCHAR2(40));
ALTER TABLE principal ADD (institution VARCHAR2(255));
ALTER TABLE principal ADD (department VARCHAR2(255));
ALTER TABLE principal ADD (position VARCHAR2(255));
ALTER TABLE principal ADD (degree VARCHAR2(40));
ALTER TABLE principal ADD (phone VARCHAR2(40));
ALTER TABLE principal ADD (address VARCHAR2(400));
ALTER TABLE principal ADD (is_feedbacks CHAR(1));
