package com.asc.politicalscorecard.databases.tableinitializers;

//import com.asc.politicalscorecard.databases.tableinitializers.PlanetInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;

@Service
public class DatabaseInitializer {

    private final PlanetInitializer planetInitializer;
    private final NationInitializer nationInitializer;
    private final StateInitializer stateInitializer;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInitializer(
        JdbcTemplate jdbcTemplate, 
        PlanetInitializer planetInitializer, 
        NationInitializer nationInitializer,
        StateInitializer stateInitializer
        ) 
    {
        this.jdbcTemplate = jdbcTemplate;
        this.planetInitializer = planetInitializer;
        this.nationInitializer = nationInitializer;
        this.stateInitializer = stateInitializer;
    }

    @PostConstruct
    public void initialize() {
        createDatabaseIfNotExists();
        initializeTables();
    }

    private void createDatabaseIfNotExists() {
        String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS `political-scorecard`";

        try {
            // Switch to the default database to run the create database command
            jdbcTemplate.execute("USE `mysql`");
            jdbcTemplate.execute(createDatabaseSql);
            System.out.println("Database 'political-scorecard' created or already exists.");
        } catch (Exception e) {
            System.err.println("Error creating database: " + e.getMessage());
        }
    }

    public void initializeTables() {
        System.out.println("In initialize tables");
        planetInitializer.initializeTable();
        nationInitializer.initializeTable(); // Since Nation depends on planet, it must come after.
        stateInitializer.initializeTable();
    }
}
