create user c##intbank identified by intbank;

GRANT create session TO c##intbank;
GRANT create table TO c##intbank;
GRANT create any table TO c##intbank;
GRANT resource to c##intbank;
GRANT create view TO c##intbank;
GRANT create any trigger TO c##intbank;
GRANT create any procedure TO c##intbank;
GRANT create sequence TO c##intbank;
GRANT create synonym TO c##intbank;

alter user c##intbank quota unlimited on users;

-----------------------------------------------------------

insert into ib_user_profile (type) values('ADMIN');
insert into ib_user_profile (type) values('CLIENT');

insert into ib_d_country values (1,'Česká republika');
insert into ib_d_country values (2,'Slovenská republika');

