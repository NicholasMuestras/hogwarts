package org.skypro.hogwarts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.skypro.hogwarts.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    @Test
    public void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Poter");
        student.setAge(17);

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Poter\", \"age\": 17}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Poter"))
                .andExpect(jsonPath("$.age").value(17));
    }

    @Test
    public void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Germiona");
        student.setAge(17);

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Germiona"))
                .andExpect(jsonPath("$.age").value(17));
    }

    @Test
    public void testGetStudentById_NotFound() throws Exception {
        when(studentService.findStudent(999L)).thenReturn(null);

        mockMvc.perform(get("/student/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setName("R");
        updatedStudent.setAge(18);

        when(studentService.editStudent(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Ron\", \"age\": 18}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ron"))
                .andExpect(jsonPath("$.age").value(18));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        when(studentService.deleteStudent(1L)).thenReturn(any(Student.class));

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteStudent_NotFound() throws Exception {
        when(studentService.deleteStudent(999L)).thenReturn(any(Student.class));

        mockMvc.perform(delete("/student/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetStudentsByAge() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student1");
        student1.setAge(17);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student2");
        student2.setAge(17);

        List<Student> students = Arrays.asList(student1, student2);

        when(studentService.filterStudentsByAge(17)).thenReturn(students);

        mockMvc.perform(get("/student/age/17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].age").value(17))
                .andExpect(jsonPath("$[1].age").value(17));
    }

    @Test
    public void testFindByAgeBetween() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Student1");
        student1.setAge(17);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Student2");
        student2.setAge(18);

        List<Student> students = Arrays.asList(student1, student2);

        when(studentService.filterStudentsByAgeRange(16, 19)).thenReturn(students);

        mockMvc.perform(get("/student/searchByAgeRange?from=16&to=19"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetFacultyByStudent() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Qwerty");
        faculty.setColor("Red");

        Student student = new Student();
        student.setId(1L);
        student.setName("Student");
        student.setAge(16);
        student.setFaculty(faculty);

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Qwerty"))
                .andExpect(jsonPath("$.color").value("Red"));
    }

    @Test
    public void testGetFacultyByStudent_StudentNotFound() throws Exception {
        when(studentService.findStudent(999L)).thenReturn(null);

        mockMvc.perform(get("/student/999/faculty"))
                .andExpect(status().isNotFound());
    }
}
