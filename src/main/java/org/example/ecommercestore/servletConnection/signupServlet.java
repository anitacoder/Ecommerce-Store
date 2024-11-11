package org.example.ecommercestore.servletConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ecommercestore.Connection.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

@WebServlet(name = "signupServlet", value = "/signupServlet")
public class signupServlet extends HttpServlet {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Received sign-up data: " + firstname + ", " + lastname + ", " + email);
        if (!isValidEmail(email)) {
            request.setAttribute("signupFailed", "Invalid email format.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        if (!isValidPassword(password)) {
            request.setAttribute("signupFailed", "Password must be at least 8 characters long and contain at least one letter and one number.");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Database connection established.");

            String query = "INSERT INTO `users` (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
                pst.setString(1, firstname);
                pst.setString(2, lastname);
                pst.setString(3, email);
                pst.setString(4, password);
                int rowsAffected = pst.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User inserted successfully!");
                    response.sendRedirect("index.jsp");
                } else {
                    System.out.println("Signup failed: No rows affected.");
                    request.setAttribute("signupFailed", "Signup failed. Please try again.");
                    request.getRequestDispatcher("signup.jsp").forward(request, response);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error during signup process:");
            e.printStackTrace();
            request.setAttribute("signupFailed", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }
}
