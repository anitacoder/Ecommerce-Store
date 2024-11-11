package org.example.ecommercestore.servletConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ecommercestore.Dao.productDao;
import org.example.ecommercestore.model.products;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "productServlet", value = "/productServlet")
public class productServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/EcommerceStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "P@ssw0rd2024#Secure";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(); // Retrieve the session

        try (Connection connection = getConnection()) {
            // Pass the connection object to the productDao constructor
            productDao productDao = new productDao(connection);
            List<products> productList = productDao.getAllProducts();

            // Store the product list in the session
            session.setAttribute("products", productList);

            System.out.println("Retrieved products: " + productList.size());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }
}
