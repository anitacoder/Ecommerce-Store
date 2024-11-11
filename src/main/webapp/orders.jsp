<%@ page import="org.example.ecommercestore.model.Cart" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.ecommercestore.model.users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    users user = (users) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Retrieve the cart list from the session
    List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
    double totalAmount = 0; // Initialize total amount

    // Calculate the total amount globally
    if (cartList != null && !cartList.isEmpty()) {
        for (Cart item : cartList) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart - AniE-Store</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="index.jsp">AniE-Store</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="productServlet">Home</a>
                </li>
                <li class="nav-item active">
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

<div class="container mt-5">
    <div class="row">
        <div class="col-md-8">
            <h1>Orders</h1>
        </div>
        <div class="col-md-4 text-right">
            <h3>Total: $<%= totalAmount %></h3>
        </div>
    </div>

    <table class="table table-bordered mt-3">
        <thead>
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (cartList != null && !cartList.isEmpty()) {
                for (Cart item : cartList) {
                    double itemTotal = item.getPrice() * item.getQuantity();
        %>
        <tr>
            <td><%=item.getProductName()%></td>
            <td>$<%=item.getPrice()%></td>
            <td>
                <div class="d-flex align-items-center">
                    <form action="updateCartServlet" method="post" style="display: inline-block;">
                        <input type="hidden" name="itemId" value="<%=item.getId()%>">
                        <input type="hidden" name="action" value="decrease">
                        <button type="submit" class="btn btn-sm" style="color: blue">
                            <i class="fas fa-minus-square"></i>
                        </button>
                    </form>
                    <span class="mx-2"><%=item.getQuantity()%></span>
                    <form action="updateCartServlet" method="post" style="display: inline-block;">
                        <input type="hidden" name="itemId" value="<%=item.getId()%>">
                        <input type="hidden" name="action" value="increase">
                        <button type="submit" class="btn btn-sm" style="color: blue">
                            <i class="fas fa-plus-square"></i>
                        </button>
                    </form>
                </div>
            </td>
            <td>$<%=itemTotal%></td>
            <td>
                <form action="removeFromCartServlet" method="post">
                    <input type="hidden" name="itemId" value="<%=item.getId()%>">
                    <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        <%
        } else {
        %>
        <tr>
            <td colspan="5" class="text-center">Your cart is empty.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <div class="text-right">
        <form action="checkoutServlet" method="post">
            <button type="submit" class="btn btn-custom-blue">Checkout</button>
        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
