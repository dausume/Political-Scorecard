package com.asc.politicalscorecard.databases.tableinitializers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NationInitializer {

    private final JdbcTemplate jdbcTemplate;

    public NationInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTable() {
        System.out.println("In Nation InitializeTable.");
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS nation (" +
            "id VARCHAR(255) PRIMARY KEY, " +
            "nation_name VARCHAR(255) NOT NULL, " +
            "home_planet VARCHAR(255), " + // Reference to the planet object
            "FOREIGN KEY (home_planet) REFERENCES planet(id)" +
            ")"
        );
    }
}
