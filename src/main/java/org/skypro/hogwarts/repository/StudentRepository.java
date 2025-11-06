package org.skypro.hogwarts.repository;

import org.skypro.hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(Integer age);

    List<Student> findByAgeBetween(Integer ageFrom, Integer ageTo);
}
