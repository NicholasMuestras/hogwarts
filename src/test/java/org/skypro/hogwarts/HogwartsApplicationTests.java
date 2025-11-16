package org.skypro.hogwarts;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skypro.hogwarts.controller.FacultyController;
import org.skypro.hogwarts.controller.StudentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
		Assertions.assertThat(facultyController).isNotNull();
    }
}
