package com.asc.politicalscorecard.databases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class DatabaseService {

    private final DatabaseConnection databaseConnection; 

    @Autowired
    public DatabaseService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void performDatabaseOperation() {
        Connection connection = null;
        System.out.println("About to start connection");
        try {
            connection = databaseConnection.openConnection();
            // Perform your database operations here
            System.out.println("Performing database operations.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection(connection);
        }
    }
}
