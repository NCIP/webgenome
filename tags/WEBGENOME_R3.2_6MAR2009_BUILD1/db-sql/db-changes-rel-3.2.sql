/*L
   Copyright RTI International

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/webgenome/LICENSE.txt for details.
L*/

--
-- This SQL script must be used to prepare the WebGenome database instance
-- for correct operation under Release 3.2.
--
-- Author: Dean Jackman
-- March 2009
-- 

-- Populate missing email values with name - some older
-- accounts don't have email values, so we just do the best we can and
-- move the name value over for these rows.

update principal set email = name where email is null;

--
-- We have moved to an email address-based login - rather than name-value login.
--
alter table principal drop column name ;

--
-- Change user_name in shopping cart to user_id
--

alter table shopping_cart add user_id NUMBER(32) ;

update shopping_cart s set user_id = (select p.id from principal p where p.email = s.user_name ) ;

alter table shopping_cart drop column user_name ;

--
-- Change user_id in Job from VARCHAR2 to NUMBER(32) - and migrate user_id values (numeric)
-- into this column
--

alter table job add user_id_temp NUMBER(32) ;

update job j set user_id_temp = (select p.id from principal p where p.email = j.user_id ) ;

alter table job drop column user_id ;

alter table job rename column user_id_temp to user_id ;

--
--
--

commit ;