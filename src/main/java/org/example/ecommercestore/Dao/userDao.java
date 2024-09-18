package org.example.ecommercestore.Dao;

import org.example.ecommercestore.model.users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class userDao {
    private Connection connection;

    public userDao(Connection connection) {
        this.connection = connection;
    }

    public void registerUser(String email, String firstname, String lastname, String password) throws SQLException {
        String query = "INSERT INTO users (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, firstname);
            pst.setString(2, lastname);
            pst.setString(3, email);
            pst.setString(4, password);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.err.println("Failed to register user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public users login(String email, String password) throws SQLException {
        users user = null;
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = new users();
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return user;
    }
}
