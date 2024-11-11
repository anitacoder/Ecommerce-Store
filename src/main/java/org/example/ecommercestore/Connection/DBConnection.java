package org.example.ecommercestore.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            try {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("MySQL JDBC Driver Registered!");

                // Connect to MySQL database
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/EcommerceStore", "root", "P@ssw0rd2024#Secure");
                System.out.println("Connection established successfully!");

            } catch (ClassNotFoundException e) {
                System.err.println("MySQL JDBC Driver not found:");
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
