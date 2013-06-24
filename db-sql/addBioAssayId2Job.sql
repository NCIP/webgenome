/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

select * from data_src_props t

ALTER TABLE job ADD (bioassay_id NUMBER null);
