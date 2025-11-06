package org.skypro.hogwarts.controller;

import org.skypro.hogwarts.model.Faculty;
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
    public Collection<Faculty> sortFacultyByColor(@PathVariable String color) {
        return facultyService.sortFaculty(color);
    }
}
