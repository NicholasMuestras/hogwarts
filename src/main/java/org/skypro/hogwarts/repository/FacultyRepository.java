package org.skypro.hogwarts.repository;

import org.skypro.hogwarts.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColor(String color);

    Collection<Faculty> findByNameIgnoreCase(String name);

    @Query("SELECT f FROM Faculty f JOIN f.students s WHERE s.id = :studentId")
    Optional<Faculty> findByStudentId(@Param("studentId") Long studentId);
}
