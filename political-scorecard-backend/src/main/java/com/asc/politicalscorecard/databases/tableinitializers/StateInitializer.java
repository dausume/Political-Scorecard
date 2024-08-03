package com.asc.politicalscorecard.databases.tableinitializers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StateInitializer {

    private final JdbcTemplate jdbcTemplate;

    public StateInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTable() {
        System.out.println("In State InitializeTable.");
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS state (" +
            "id VARCHAR(255) PRIMARY KEY, " +
            "state_name VARCHAR(255) NOT NULL, " +
            "parent_nation VARCHAR(255), " + // Reference to the planet object
            "FOREIGN KEY (parent_nation) REFERENCES nation(id)" +
            ")"
        );
    }
}
