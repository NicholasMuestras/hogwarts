package org.skypro.hogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ObjectMapper objectMapper;

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

    @GetMapping("/searchByAgeRange")
    public Collection<Student> filterStudentsByAgeRange(int from, int to) {
        return studentService.filterStudentsByAgeRange(from, to);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty getStudentFaculty(@PathVariable long studentId) {
        return studentService.getStudentFaculty(studentId);
    }

    @GetMapping("/totalCount")
    public Map<String, Integer> getTotalCount() {
        Map<String, Integer> o = new HashMap<>(1);
        o.put("totalCount", studentService.getTotalCount());

        return o;
    }

    @GetMapping("/averageAge")
    public Map<String, Byte> getAverageAge() {
        Map<String, Byte> o = new HashMap<>(1);
        o.put("averageAge", studentService.getAverageAge());

        return o;
    }

    @GetMapping("/lastFive")
    public Collection<Student> lastFive() {
        return studentService.findLastN(5);
    }
}
