package org.example.ecommercestore.servletConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.ecommercestore.model.Cart;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "RemoveFromCartServlet", value = "/RemoveFromCartServlet")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        HttpSession session = request.getSession();
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        if (cartList != null) {
            cartList.removeIf(item -> item.getId() == itemId);
        }

        response.sendRedirect("cart.jsp");
    }
}