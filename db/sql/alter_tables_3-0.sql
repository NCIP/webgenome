ALTER TABLE principal ADD domain VARCHAR2(128);
ALTER TABLE job ADD user_domain VARCHAR2(128);
ALTER TABLE shopping_cart ADD user_domain VARCHAR2(128);
