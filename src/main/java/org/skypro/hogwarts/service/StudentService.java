package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student createStudent(Student student) {
        return this.repository.save(student);
    }

    public Student findStudent(long id) {
        return this.repository.getReferenceById(id);
    }

    public Student editStudent(Student student) {
        return this.repository.save(student);
    }

    public Student deleteStudent(long id) {
        Optional<Student> item = Optional.of(this.repository.getReferenceById(id));
        item.ifPresent(faculty -> this.repository.deleteById(id));

        return item.get();
    }

    public Collection<Student> filterStudentsByAge(int age) {
        return this.repository.findByAge(age);
    }

    public Collection<Student> filterStudentsByAgeRange(int ageFrom, int ageTo) {
        return this.repository.findByAgeBetween(ageFrom, ageTo);
    }

    public Faculty getStudentFaculty(long studentId) {
        return this.repository.getReferenceById(studentId).getFaculty();
    }
}
