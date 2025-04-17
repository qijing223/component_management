package com.lot.server.datasourse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
class SpringDataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testDataSourceConnection() throws Exception {
        Connection connection = dataSource.getConnection();
        assertNotNull(connection);
        System.out.println("successfully connect to database");
        connection.close();
    }
}
