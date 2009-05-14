--
-- This SQL script must be used to prepare the WebGenome database instance
-- for correct operation under Release 3.3.
--
-- Author: Dean Jackman
-- May 2009
-- 

alter table experiment add quant_type_label VARCHAR2(256) ;

--
--
--

commit ;