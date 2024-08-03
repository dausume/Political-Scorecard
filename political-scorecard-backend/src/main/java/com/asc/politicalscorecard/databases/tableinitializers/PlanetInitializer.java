package com.asc.politicalscorecard.databases.tableinitializers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlanetInitializer {

    private final JdbcTemplate jdbcTemplate;

    public PlanetInitializer(JdbcTemplate jdbcTemplate) 
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void initializeTable() {
        System.out.println("In Planet InitializeTable.");
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS planet (" +
            "id VARCHAR(255) PRIMARY KEY, " +
            "planet_name VARCHAR(255) NOT NULL" +
            ")"
            );
    }
}
