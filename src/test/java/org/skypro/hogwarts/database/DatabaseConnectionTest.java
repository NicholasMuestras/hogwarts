package org.skypro.hogwarts.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Test
    void testConnectionToDatabase() {
        Assertions.assertNotNull(this.jdbc);
        Assertions.assertEquals(123, jdbc.queryForObject("SELECT 123", Integer.class));
    }
}
