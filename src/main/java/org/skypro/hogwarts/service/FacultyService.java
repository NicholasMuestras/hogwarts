package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FacultyService {
    private final FacultyRepository repository;

    @Autowired
    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return this.repository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return this.repository.getReferenceById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return this.repository.save(faculty);
    }

    public Faculty deleteFaculty(long id) {
        Optional<Faculty> item = Optional.of(this.repository.getReferenceById(id));
        item.ifPresent(faculty -> this.repository.deleteById(id));

        return item.get();
    }

    public Collection<Faculty> filterFacultyByColor(String color) {
        return this.repository.findByColor(color);
    }

    public Collection<Faculty> filterFacultyByName(String name) {
        return this.repository.findByNameIgnoreCase(name);
    }

    public Collection<Student> getStudentsByFaculty(long facultyId) {
        return this.repository.getReferenceById(facultyId).getStudents();
    }
}
