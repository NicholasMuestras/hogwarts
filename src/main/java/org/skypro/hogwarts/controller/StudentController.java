package org.skypro.hogwarts.controller;

import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student findStudent(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    @PutMapping
    public Student editStudent(Student student) {
        return studentService.editStudent(student);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable long id) {
        return studentService.deleteStudent(id);

    }

    @GetMapping("{age}")
    public Collection<Student> filterStudentsByAge(@PathVariable int age) {
        return studentService.filterStudentsByAge(age);
    }

    @GetMapping("{ageRange}")
    public Collection<Student> filterStudentsByAgeRange(@PathVariable int from, @PathVariable int to) {
        return studentService.filterStudentsByAgeRange(from, to);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getStudentFaculty(@PathVariable long studentId) {
        return studentService.getStudentFaculty(studentId);
    }
}
