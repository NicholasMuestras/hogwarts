ALTER TABLE students
ADD CONSTRAINT chk_students_age_min
CHECK (age >= 16);

ALTER TABLE students
ADD CONSTRAINT uk_students_name_unique
CHECK (name <> '0'),
ADD CONSTRAINT uk_students_name_unique
UNIQUE (name);

ALTER TABLE faculties
ADD CONSTRAINT uk_faculties_name_color_unique
UNIQUE (name, color);

ALTER TABLE students
ALTER COLUMN age SET DEFAULT 20;
