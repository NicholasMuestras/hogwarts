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
public class FacultyControllerTest {

    private static final String BASE_PATH = "/faculty";

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
                .assertThat(this.restTemplate.getForObject(this.getBaseUrl() + BASE_PATH + "/1/students", String.class))
                .isNotEmpty();
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        faculty.setColor("Red");

        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                getBaseUrl() + BASE_PATH,
                faculty,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Faculty", response.getBody().getName());
    }

    @Test
    public void testGetFacultyById() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty for Get");
        faculty.setColor("Green");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl() + BASE_PATH,
                faculty,
                Faculty.class
        );
        Long facultyId = createResponse.getBody().getId();

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                getBaseUrl() + BASE_PATH + "/" + facultyId,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(facultyId, response.getBody().getId());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty to Update");
        faculty.setColor("Yellow");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/faculty",
                faculty,
                Faculty.class
        );
        Long facultyId = createResponse.getBody().getId();

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(facultyId);
        updatedFaculty.setName("Updated Faculty");
        updatedFaculty.setColor("Purple");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType(MediaType.APPLICATION_JSON));
        HttpEntity<Faculty> entity = new HttpEntity<>(updatedFaculty, headers);

        ResponseEntity<Faculty> response = restTemplate.exchange(
                getBaseUrl() + "/faculty",
                HttpMethod.PUT,
                entity,
                Faculty.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Faculty", response.getBody().getName());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty to Delete");
        faculty.setColor("Orange");

        ResponseEntity<Faculty> createResponse = restTemplate.postForEntity(
                getBaseUrl() + "/faculty",
                faculty,
                Faculty.class
        );
        Long facultyId = createResponse.getBody().getId();

        restTemplate.delete(getBaseUrl() + "/faculty/" + facultyId);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                getBaseUrl() + "/faculty/" + facultyId,
                Faculty.class
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetFacultiesByColor() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/faculty/color/Blue",
                Faculty[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testFindFacultiesByNameOrColor() {
        ResponseEntity<Faculty[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/faculty/filter?color=Red&name=Test",
                Faculty[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentsByFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Faculty for Students");
        faculty.setColor("Brown");

        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity(
                getBaseUrl() + "/faculty",
                faculty,
                Faculty.class
        );
        Long facultyId = facultyResponse.getBody().getId();

        Student student = new Student();
        student.setName("Student in Faculty");
        student.setAge(26);
        student.setFaculty(facultyResponse.getBody());

        restTemplate.postForEntity(
                getBaseUrl() + "/student",
                student,
                Student.class
        );

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                getBaseUrl() + "/faculty/" + facultyId + "/students",
                Student[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
