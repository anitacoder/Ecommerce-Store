package org.example.ecommercestore.servletConnection;

import org.example.ecommercestore.Connection.DBConnection;
import org.example.ecommercestore.Dao.userDao;
import org.example.ecommercestore.model.users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/loginServlet")
public class loginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            userDao userDao = new userDao(DBConnection.getConnection());
            users user = userDao.login(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/productServlet"); // Redirect to index.jsp after successful login
            } else {
                // Set the attribute to indicate login failure
                request.setAttribute("loginFailed", "Login failed. Please check your email and password.");
                // Forward to login.jsp to display the error message
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Set the attribute for an error message in case of exceptions
            request.setAttribute("loginFailed", "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
