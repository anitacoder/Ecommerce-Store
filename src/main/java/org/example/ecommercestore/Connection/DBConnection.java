package org.example.ecommercestore.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                System.out.println("PostgresSQL JDBC Driver Registered!");

                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EcommerceStore", "postgres", "anita");
                System.out.println("Connection established successfully!");

            } catch (ClassNotFoundException e) {
                System.err.println("PostgresSQL JDBC Driver not found:");
                e.printStackTrace();
                throw e;
            } catch (SQLException e) {
                System.err.println("Connection Failed:");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }
}