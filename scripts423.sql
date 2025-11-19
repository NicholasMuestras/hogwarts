SELECT
s.name AS student_name,
s.age AS student_age,
f.name AS faculty_name
FROM students s
LEFT JOIN faculties f ON s.faculty_id = f.id;

SELECT
s.*
FROM students s
INNER JOIN avatars a ON s.id = a.student_id;
