package org.skypro.hogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("SomeFacultyName");
        faculty.setColor("Green");

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"SomeFacultyName\", \"color\": \"Green\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("SomeFacultyName"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void testGetFacultyById() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("SomeFacultyTwo");
        faculty.setColor("Blue");

        when(facultyService.findFaculty(1L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("SomeFacultyTwo"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    public void testGetFacultyById_NotFound() throws Exception {
        when(facultyService.findFaculty(999L)).thenReturn(null);

        mockMvc.perform(get("/faculty/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFaculty() throws Exception {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(1L);
        updatedFaculty.setName("Updated Hufflepuff");
        updatedFaculty.setColor("Gold");

        when(facultyService.editFaculty(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Hufflepuff\", \"color\": \"Gold\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Hufflepuff"))
                .andExpect(jsonPath("$.color").value("Gold"));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        when(facultyService.deleteFaculty(1L)).thenReturn(any(Faculty.class));

        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteFaculty_NotFound() throws Exception {
        when(facultyService.deleteFaculty(999L)).thenReturn(any(Faculty.class));

        mockMvc.perform(delete("/faculty/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Faculty1");
        faculty1.setColor("Purple");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Faculty2");
        faculty2.setColor("Purple");

        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.filterFacultyByColor("Purple")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/color/Purple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].color").value("Purple"))
                .andExpect(jsonPath("$[1].color").value("Purple"));
    }

    @Test
    public void testFindFacultiesByName() throws Exception {
        Faculty faculty1 = new Faculty();
        faculty1.setId(1L);
        faculty1.setName("Test Faculty");
        faculty1.setColor("Green");

        Faculty faculty2 = new Faculty();
        faculty2.setId(2L);
        faculty2.setName("Another Faculty");
        faculty2.setColor("Red");

        List<Faculty> faculties = Arrays.asList(faculty1, faculty2);

        when(facultyService.filterFacultyByName("Test")).thenReturn(faculties);

        mockMvc.perform(get("/faculty/filter?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetStudentsByFaculty() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("StudentOne");
        student1.setAge(18);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("StudentTwo");
        student2.setAge(18);

        List<Student> students = Arrays.asList(student1, student2);

        when(facultyService.getStudentsByFaculty(1L)).thenReturn(students);

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("StudentOne"))
                .andExpect(jsonPath("$[1].name").value("StudentTwo"));
    }

    @Test
    public void testGetStudentsByFaculty_NoStudents() throws Exception {
        when(facultyService.getStudentsByFaculty(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/faculty/1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
