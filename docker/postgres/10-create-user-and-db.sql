-- file: 10-create-user-and-db.sql
CREATE USER program WITH PASSWORD 'test';
CREATE DATABASE camunda WITH OWNER program;