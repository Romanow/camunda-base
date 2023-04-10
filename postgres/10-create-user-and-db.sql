-- file: 10-create-user-and-db.sql
CREATE DATABASE camunda;
CREATE USER program WITH PASSWORD 'test';
GRANT ALL PRIVILEGES ON DATABASE camunda TO program;
