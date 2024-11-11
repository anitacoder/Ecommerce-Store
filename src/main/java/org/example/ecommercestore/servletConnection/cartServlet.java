package org.example.ecommercestore.servletConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ecommercestore.Dao.productDao;
import org.example.ecommercestore.model.Cart;
import org.example.ecommercestore.model.products;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "cartServlet", value = "/cartServlet")
public class cartServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/EcommerceStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "P@ssw0rd2024#Secure";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        HttpSession session = request.getSession();

        try (Connection connection = getConnection()) {
            productDao productDao = new productDao(connection);
            products product = productDao.getProductById(productId);

            if (product != null) {
                // Retrieve the cart from the session, or create a new one if it doesn't exist
                List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
                if (cartList == null) {
                    cartList = new ArrayList<>();
                }

                // Add the product to the cart
                boolean productExists = false;
                for (Cart item : cartList) {
                    if (item.getId() == product.getId()) {
                        item.setQuantity(item.getQuantity() + 1); // Increase quantity if product already in cart
                        productExists = true;
                        break;
                    }
                }
                if (!productExists) {
                    Cart cartItem = new Cart();
                    cartItem.setId(product.getId());
                    cartItem.setProductName(product.getName());
                    cartItem.setPrice(product.getPrice());
                    cartItem.setQuantity(1); // Add new product with quantity 1
                    cartList.add(cartItem);
                }

                // Save the updated cart in the session
                session.setAttribute("cart", cartList);
            }

            response.sendRedirect("cart.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error: " + e.getMessage());
        }
    }
}