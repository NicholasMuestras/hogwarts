package org.skypro.hogwarts.controller;

import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.service.FacultyService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public Faculty findFaculty(@PathVariable long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping
    public Faculty editFaculty(Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);

    }

    @GetMapping("{color}")
    public Collection<Faculty> getFacultiesByColor(@PathVariable String color) {
        return facultyService.filterFacultyByColor(color);
    }

    @GetMapping("{name}")
    public Collection<Faculty> getFacultiesByName(@PathVariable String name) {
        return facultyService.filterFacultyByName(name);
    }

    @GetMapping("/{facultyId}/students")
    public Collection<Student> getStudentsByFaculty(@PathVariable long facultyId) {
        return facultyService.getStudentsByFaculty(facultyId);
    }
}
