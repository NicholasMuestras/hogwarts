package org.skypro.hogwarts.service;

import org.skypro.hogwarts.model.Faculty;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    HashMap<Long, Faculty> facultyHashMap = new HashMap<>();
    long lastId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++lastId);
        facultyHashMap.put(lastId, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return facultyHashMap.get(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        facultyHashMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultyHashMap.remove(id);
    }

    public Collection<Faculty> sortFaculty(String color) {
        return facultyHashMap
                .values()
                .stream()
                .filter(faculty -> facultyHashMap.containsValue(color))
                .collect(Collectors.toList());
    }
}
