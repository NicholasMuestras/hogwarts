package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.LongStream;

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
        Optional<Faculty> faculty = this.repository.findById(facultyId);

        if (faculty.isPresent()) {
            return faculty.get().getStudents();
        }

        return new Faculty().getStudents();
    }

    public String getLongestFacultyName() {
        if (this.repository.count() > 0) {
            return this.repository.findAll()
                    .stream()
                    .max((f1, f2) -> f1.getName().length() - f2.getName().length())
                    .get()
                    .getName();
        }

        return "";
    }

    public long getPaschalFeature() {
        return LongStream
                .iterate(1, a -> a + 1)
                .limit(10_000_000)
                .parallel()
                .reduce(0, (a, b) -> a + b);
    }
}
