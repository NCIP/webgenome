/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

--
-- This SQL script must be used to prepare the WebGenome database instance
-- for correct operation under Release 3.3.
--
-- Author: Dean Jackman
-- May 2009
-- 

alter table experiment add quant_type_label VARCHAR2(256) ;

alter table data_src_props add quant_type_label VARCHAR2(256) ;

--
--
--

commit ;