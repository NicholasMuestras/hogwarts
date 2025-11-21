-- liquibase formatted sql

-- changeset nobraztsov:1
CREATE TABLE IF NOT EXISTS faculties (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    CONSTRAINT uk_faculties_name_color_unique UNIQUE (name, color)
);

CREATE INDEX IF NOT EXISTS idx_faculties_name ON faculties(name);

-- changeset nobraztsov:2

CREATE INDEX IF NOT EXISTS idx_faculties_name_color ON faculties(name, color);
