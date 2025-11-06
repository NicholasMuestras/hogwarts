package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Student;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {
    HashMap<Long, Student> studentHashMap = new HashMap<>();
    long lastId = 0;

    public Student createStudent(Student student) {
        student.setId(++lastId);
        studentHashMap.put(lastId, student);
        return student;
    }

    public Student findStudent(long id) {
        return studentHashMap.get(id);
    }

    public Student editStudent(Student student) {
        studentHashMap.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return studentHashMap.remove(id);
    }

    public Collection<Student> sortStudents(int age) {
        return studentHashMap.values()
                .stream()
                .filter(student -> studentHashMap.containsValue(student.getAge()))
                .collect(Collectors.toUnmodifiableList());
    }
}
