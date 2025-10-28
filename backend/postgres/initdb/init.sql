-- init.sql: initialization SQL for chatdb
-- This script runs automatically on first container startup when using the
-- official postgres image entrypoint (it looks in /docker-entrypoint-initdb.d).

CREATE ROLE chat_admin WITH LOGIN PASSWORD 'pass';
CREATE DATABASE chatdb OWNER chat_admin;

-- create a schema (optional)
CREATE SCHEMA IF NOT EXISTS chat;

-- You can add initial tables if you prefer JPA not to auto-create them.
-- Example table (optional):
-- CREATE TABLE chat.chat_message (
--   id VARCHAR PRIMARY KEY,
--   room_id VARCHAR,
--   sender_id VARCHAR,
--   receiver_id VARCHAR,
--   content TEXT,
--   time_stamp TIMESTAMP
-- );
