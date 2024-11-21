<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.Software" %>
<%
    // Check if the user is logged in and is an Employee
    /*String role = (String) session.getAttribute("role");
    if (session.getAttribute("name") == null || role == null || !"Employee".equals(role)) {
        // Redirect to login page if user is not logged in or not an Employee
        response.sendRedirect("login.jsp");
        return;
    }*/

    // Retrieve the software list and access status from the request
    List<Map<String, Object>> softwareList = (List<Map<String, Object>>) request.getAttribute("softwareList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Request Access to Software</title>
    <!-- Favicon-->
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <!-- Font Awesome icons (free version)-->
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
    <!-- Google fonts-->
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
    <link href="https://fonts.googleapis.com/css?family=Lato:400,700,400italic,700italic" rel="stylesheet" type="text/css" />
    <!-- Core theme CSS (includes Bootstrap)-->
    <link href="css/index-styles.css" rel="stylesheet" />
</head>
<body id="page-top">
    <!-- Navigation-->
    <nav class="navbar navbar-expand-lg bg-secondary text-uppercase fixed-top" id="mainNav">
        <div class="container">
            <a class="navbar-brand" href="#page-top">Role: <%= session.getAttribute("role") %></a>
            <button class="navbar-toggler text-uppercase font-weight-bold bg-primary text-white rounded" type="button" 
                    data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" 
                    aria-expanded="false" aria-label="Toggle navigation">
                Menu <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item mx-0 mx-lg-1 bg-danger"><a class="nav-link py-3 px-0 px-lg-3 rounded" href="Logout"><%= session.getAttribute("name") %></a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Request Access Form -->
    <header class="masthead bg-primary text-white text-center">
        <div class="container d-flex align-items-center flex-column">
            <h1 class="masthead-heading text-uppercase mb-0">Request Access to Software</h1>
            <p class="masthead-subheading font-weight-light mb-0">Employee Portal</p>
        </div>
    </header>

    <section class="page-section">
        <div class="container">
            <h2 class="text-center mb-4">Available Software</h2>
            
            <!-- Display software list -->
            <table class="table table-striped">
    <thead>
        <tr>
            <th>Serial No.</th>
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Status</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <%
        int serialNo = 1;
        for (Map<String, Object> software : softwareList) {
            String status = (String) software.get("accessStatus");
            int softwareId = (int) software.get("id");
        %>
        <tr>
            <td><%= serialNo++ %></td>
            <td><%= softwareId %></td>
            <td><%= software.get("name") %></td>
            <td><%= software.get("description") %></td>
            <td><%= status %></td>
            <td>
                <% if ("No Request".equals(status) || "Rejected".equals(status)) { %>
                    <form action="RequestServlet" method="post" style="display:inline;">
                        <input type="hidden" name="softwareId" value="<%= softwareId %>">
                        <input type="text" name="reason" placeholder="Reason for access" required>
                        <button type="submit" name="requestAccess" class="btn btn-primary">Request</button>
                    </form>
                <% } else if ("Approved".equals(status)) { %>
                    <span class="badge bg-success">Approved</span>
                <% } else { %>
                    <span class="badge bg-warning">Pending</span>
                <% } %>
            </td>
        </tr>
        <%
        }
        %>
    </tbody>
</table>

        </div>
    </section>

    <!-- Footer -->
    <footer class="bg-light text-center text-lg-start">
        <div class="text-center p-3">
            © 2024 User Access Management System
        </div>
    </footer>
    
    <!-- Bootstrap JS (optional) -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
</body>
</html>
