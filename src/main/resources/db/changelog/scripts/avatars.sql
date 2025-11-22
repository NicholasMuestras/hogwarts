-- liquibase formatted sql

-- changeset nobraztsov:1
CREATE TABLE IF NOT EXISTS avatars (
    id BIGSERIAL PRIMARY KEY,
    file_path VARCHAR(500) NOT NULL UNIQUE,
    file_size BIGINT NOT NULL,
    media_type VARCHAR(100) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    original_file_name VARCHAR(255) NOT NULL,
    student_id BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_avatars_student_id
        FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    CONSTRAINT chk_avatars_file_size_min CHECK (file_size > 0)
);

CREATE INDEX IF NOT EXISTS idx_avatars_student_id ON avatars(student_id);
CREATE INDEX IF NOT EXISTS idx_avatars_file_path ON avatars(file_path);
