package com.asc.politicalscorecard.databases;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    /*
    private static String jdbcUrl = "jdbc:mariadb://mariadb:3306/political-scorecard";
    private static String user = "admin";
    private static String password = "admin";
    */

    
        @Value("${SPRING_DATASOURCE_URL}")
        private String jdbcUrl;

        @Value("${SPRING_DATASOURCE_USERNAME}")
        private String user;

        @Value("${SPRING_DATASOURCE_PASSWORD}")
        private String password;

    /*
     * Using a static block to load the JDBC driver class ensures that the driver is registered with the DriverManager 
     * and available for creating connections. This approach provides explicit control and avoids issues where the driver 
     * might not be automatically registered, especially in environments where the driver might not be discovered automatically.
     */
    // Static block to load the MariaDB JDBC driver
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MariaDB JDBC driver", e);
        }
    }

    public Connection openConnection() throws SQLException {
        System.out.println("jdbcURL: " + jdbcUrl);
        System.out.println("user: " + user);
        System.out.println("password: " + password);
        System.out.println("Getting Connnection via openConnection in DatabaseConnection");
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
