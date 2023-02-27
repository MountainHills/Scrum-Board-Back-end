-- liquibase formatted sql

--changeset anton:1.0.0-create-task-table
CREATE SEQUENCE  IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE task (
  id BIGINT NOT NULL,
   title TEXT NOT NULL,
   description TEXT,
   status VARCHAR(255) NOT NULL,
   deleted BOOLEAN NOT NULL,
   creation_time TIMESTAMP WITHOUT TIME ZONE,
   update_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   CONSTRAINT pk_task PRIMARY KEY (id)
);