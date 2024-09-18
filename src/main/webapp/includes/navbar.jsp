
<%@ page import="org.example.ecommercestore.model.users" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    users auth = (users) request.getSession().getAttribute("auth");
    if(auth != null) {
        response.sendRedirect("login.jsp");
    }
%><html>
<head>
<title>Title</title>

</head>
<body>
<nav class="navbar navbar expand-lg navbar-light bg-light">
    <div class="container">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="cart.jsp">Cart</a>
                </li>
                <%
                    if(auth != null){%>
                <li class="nav-item">
                    <a class="nav-link " href="orders.jsp">Orders</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="LogoutServlet">LogOut</a>
                </li>
                <%} else {%>


                <li class="nav-item">
                    <a class="nav-link" href="login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=""></a>
                </li>
                <%}
                %>
            </ul>


        </div>
    </div>
</nav>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" ></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>
</body>
</html>