package org.skypro.hogwarts.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skypro.hogwarts.model.Faculty;
import org.skypro.hogwarts.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentControllerRestTemplateTest {

    private static final String BASE_PATH = "/student";

    @LocalServerPort
    protected int port;

    @Autowired
    protected TestRestTemplate restTemplate;

    protected String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    void getStudentsByFaculty() {
        Assertions
                .assertThat(
                        this.restTemplate.getForObject(this.getBaseUrl() + BASE_PATH + "/1/faculty", String.class)
                )
                .isNotEmpty();
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student();
        student.setName("Test Student");
        student.setAge(20);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                getBaseUrl() + "/student",
                student,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Student", response.getBody().getName());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student();
        student.setName("Ben Gun");
        student.setAge(53);

        ResponseEntity<Student> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/student",
                student,
                Student.class
        );
        Long studentId = createResponse.getBody().getId();

        ResponseEntity<Student> response = restTemplate.getForEntity(
                getBaseUrl() + "/student/" + studentId,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().getId());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student();
        student.setName("Student to Update");
        student.setAge(22);

        ResponseEntity<Student> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/student",
                student,
                Student.class
        );
        Long studentId = createResponse.getBody().getId();

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setName("Updated Student");
        updatedStudent.setAge(23);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON));
        HttpEntity<Student> entity = new HttpEntity<>(updatedStudent, headers);

        ResponseEntity<Student> response = restTemplate.exchange(
                getBaseUrl() + "/student",
                HttpMethod.PUT,
                entity,
                Student.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Student", response.getBody().getName());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student();
        student.setName("Student to delete");
        student.setAge(24);

        ResponseEntity<Student> createResponse = restTemplate.postForEntity(
                getBaseUrl() + BASE_PATH,
                student,
                Student.class
        );
        Long studentId = createResponse.getBody().getId();

        restTemplate.delete(getBaseUrl() + "/student/" + studentId);

        ResponseEntity<Student> response = restTemplate.getForEntity(
                getBaseUrl() + "/student/" + studentId,
                Student.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetStudentsByAge() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/student/age/24",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFindByAgeBetween() {
        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/student/age-between?min=18&max=25",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        faculty.setColor("Blue");

        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity(
                getBaseUrl() + "/faculty",
                faculty,
                Faculty.class
        );
        Long facultyId = facultyResponse.getBody().getId();

        Student student = new Student();
        student.setName("Student with Faculty");
        student.setAge(25);
        student.setFaculty(facultyResponse.getBody());

        ResponseEntity<Student> studentResponse = restTemplate.postForEntity(
                getBaseUrl() + "/student",
                student,
                Student.class
        );

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                getBaseUrl() + "/student/" + studentResponse.getBody().getId() + "/faculty",
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Faculty", response.getBody().getName());
    }
}
