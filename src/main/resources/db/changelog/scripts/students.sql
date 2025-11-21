-- liquibase formatted sql

-- changeset nobraztsov:1

CREATE TABLE IF NOT EXISTS students (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER DEFAULT 20 NOT NULL,
    faculty_id BIGINT,
    CONSTRAINT uk_students_name_unique UNIQUE (name),
    CONSTRAINT chk_students_age_min CHECK (age >= 16),
    CONSTRAINT chk_students_name_not_zero CHECK (name <> '0'),
    CONSTRAINT fk_students_faculty_id
        FOREIGN KEY (faculty_id) REFERENCES faculties(id)
);

CREATE INDEX IF NOT EXISTS idx_students_faculty_id ON students(faculty_id);
CREATE INDEX IF NOT EXISTS idx_students_age ON students(age);
