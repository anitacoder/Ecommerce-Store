<%@ page import="org.example.ecommercestore.model.products" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.ecommercestore.model.users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Retrieve the user object from the session
    users user = (users) session.getAttribute("user");

    // Check if the user is logged in
    if (user == null) {
        // If the user is not logged in, redirect to the login page
        response.sendRedirect("login.jsp");
        return;
    }

    // Retrieve the product list from the session
    List<products> productList = (List<products>) session.getAttribute("products");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - Surulere Market</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">Surulere Market</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="productServlet">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cart.jsp">Cart</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="orders.jsp">Orders</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout.jsp">LogOut</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="signup.jsp">Sign Up</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="mt-5">Welcome to Surulere Market</h1>
    <div class="row">
        <%
            if (productList != null && !productList.isEmpty()) {
                for (products p : productList) {
        %>
        <div class="col-md-4">
            <div class="card mb-4">
                <img class="card-img-top" src="<%= request.getContextPath() + "/images/" + p.getImage() %>" alt="product image">
                <div class="card-body">
                    <h5 class="card-title"><%= p.getName() %></h5>
                    <p class="card-text">Category: <%= p.getProductName() %></p>
                    <p class="card-text">Price: $<%= p.getPrice() %></p>
                    <form action="cartServlet" method="post">
                        <input type="hidden" name="productId" value="<%= p.getId() %>">
                        <button type="submit" class="btn btn-primary">Add to Cart</button>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        } else {
        %>
        <p>No products available.</p>
        <%
            }
        %>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
