package org.skypro.hogwarts.service;

import org.apache.commons.lang3.StringUtils;
import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final Logger logger;

    Integer counter;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
        this.logger = LoggerFactory.getLogger(StudentService.class);
    }

    public Student createStudent(Student student) {
        this.logger.debug("Was invoked method for create a Student. Details: {}.", student);

        return this.repository.save(student);
    }

    public Student findStudent(long id) {
        this.logger.trace("Was invoked method for find a Student by id: {}.", id);

        return this.repository.getReferenceById(id);
    }

    public Student editStudent(Student student) {
        this.logger.trace("Was invoked method for update a Student. Details: {}.", student.toString());

        return this.repository.save(student);
    }

    public Student deleteStudent(long id) {
        Optional<Student> item = this.repository.findById(id);

        if (item.isPresent()) {
            this.repository.deleteById(id);
        } else {
            this.logger.warn("Trying to delete a Student by Id = {} but its not exists.", id);
        }

        return item.get();
    }

    public Collection<Student> filterStudentsByAge(int age) {
        this.logger.trace("Was invoked method for find Students with age: {}.", age);

        return this.repository.findByAge(age);
    }

    public Collection<Student> filterStudentsByAgeRange(int ageFrom, int ageTo) {
        this.logger.trace("Was invoked method for find Students with age range from: {} to: {}", ageFrom, ageTo);

        return this.repository.findByAgeBetween(ageFrom, ageTo);
    }

    public Faculty getStudentFaculty(long studentId) {
        Optional<Student> student = this.repository.findById(studentId);

        if (student.isPresent()) {
            return student.get().getFaculty();
        } else {
            this.logger.warn("Trying to get a Faculty by Student but Student ist exists. Id: {}.", studentId);
        }

        return new Student().getFaculty();
    }

    public int getTotalCount() {
        this.logger.trace("Was invoked method for get total count of Students.");

        return this.repository.getTotalCount();
    }

    public byte getAverageAge() {
        this.logger.trace("Was invoked method for get average age of Students.");

        return this.repository.getAverageAge();
    }

    public Collection<Student> findLastN(int n) {
        this.logger.debug("Trying to find {} last Students.", n);

        return this.repository.findLastN(n);
    }

    public Collection<Student> findStudentsWithNameStartsWithA() {
        List<Student> students = this.repository.findAll();
        return students.stream()
                .filter(student -> student.getName().startsWith("A") || student.getName().startsWith("a"))
                .peek(student -> student.setName(StringUtils.capitalize(student.getName())))
                .sorted()
                .collect(Collectors.toList());
    }

    public byte getAverageAgeOfStudentsUsingStream() {
        if (this.repository.getTotalCount() > 0) {
            return (byte) this.repository.findAll().stream()
                    .mapToInt(Student::getAge)
                    .average()
                    .getAsDouble();
        }

        return 0;
    }

    public void printStudentsParallelToConsole() {
        List<Student> students = this.repository.findAll();

        System.out.println(students.get(0).getName());
        System.out.println(students.get(1).getName());

        new Thread(() -> {
            System.out.println(students.get(2).getName());
            System.out.println(students.get(3).getName());
        }).start();

        new Thread(() -> {
            System.out.println(students.get(4).getName());
            System.out.println(students.get(5).getName());
        }).start();
    }

    public void printStudentsSynchronizedToConsole() {
        List<Student> students = this.repository.findAll();
        this.counter = 0;

        printStudentNameToConsole(students);
        printStudentNameToConsole(students);

        new Thread(() -> {
            printStudentNameToConsole(students);
            printStudentNameToConsole(students);
        }).start();

        new Thread(() -> {
            printStudentNameToConsole(students);
            printStudentNameToConsole(students);
        }).start();
    }

    private synchronized void printStudentNameToConsole(List<Student> student) {
        System.out.println(this.counter + " " + student.get(this.counter).getName());
        this.counter++;
    }
}
