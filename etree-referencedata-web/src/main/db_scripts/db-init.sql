-- Login as postgres
psql postgres postgres

CREATE USER referencedata_dba SUPERUSER;

ALTER USER referencedata_dba WITH PASSWORD '123';

-- relogin to postgres DB as referencedata_dba and create the below
psql postgres referencedata_dba

create database referencedata_db;

-- relogin to referencedata__db DB as referencedata_dba and create the below
psql referencedata_db referencedata_dba

CREATE USER referencedata_tbls_user NOSUPERUSER;

ALTER USER referencedata_tbls_user WITH PASSWORD '123';

CREATE USER referencedata_user NOSUPERUSER;

ALTER USER referencedata_user WITH PASSWORD '123';

GRANT ALL PRIVILEGES ON DATABASE referencedata_db TO referencedata_tbls_user;

GRANT CONNECT ON DATABASE referencedata_db TO referencedata_user;

REVOKE ALL ON SCHEMA public FROM public;

REVOKE CONNECT on DATABASE referencedata_db FROM public;

