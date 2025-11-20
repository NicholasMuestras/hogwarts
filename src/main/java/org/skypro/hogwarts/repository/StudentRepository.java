package org.skypro.hogwarts.repository;

import org.skypro.hogwarts.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int ageFrom, int ageTo);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    int getTotalCount();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    byte getAverageAge();

    @Query(value = "SELECT * FROM students ORDER BY id DESC LIMIT :n", nativeQuery = true)
    List<Student> findLastN(int n);
}
