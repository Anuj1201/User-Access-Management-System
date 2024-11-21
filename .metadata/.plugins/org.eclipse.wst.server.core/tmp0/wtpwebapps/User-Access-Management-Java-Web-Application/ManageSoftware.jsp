<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="dao.Software" %>

<%
    // Check if the user is logged in and is an Employee
    String role = (String) session.getAttribute("role");
    if (session.getAttribute("name") == null || role == null || !"Admin".equals(role)) {
        // Redirect to login page if user is not logged in or not an Admin
        response.sendRedirect("login.jsp");
        return;
    }

    // Retrieve the software list from the request attribute
    List<Software> softwares = (List<Software>) request.getAttribute("softwares");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Software Management</title>
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" />
    <link href="css/index-styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v5.15.4/js/all.js" crossorigin="anonymous"></script>
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

    <!-- Header -->
    <header class="masthead bg-primary text-white text-center">
        <div class="container d-flex align-items-center flex-column">
            <h1 class="masthead-heading text-uppercase mb-0">Software Management</h1>
            <p class="masthead-subheading font-weight-light mb-0">Manage Software Applications</p>
        </div>
    </header>

    <section class="page-section">
        <div class="container">
            <!-- Form to add new software -->
            <h2 class="text-center mb-4">Add New Software</h2>
            <form action="SoftwareServlet" method="post">
                <div class="form-group">
                    <label for="name">Software Name:</label>
                    <input type="text" id="name" name="name" class="form-control" required>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" class="form-control" required></textarea>
                </div>
                <input type="submit" class="btn btn-primary" value="Create Software">
            </form>

            <h2 class="text-center mb-4">Available Software</h2>

            <!-- Display software list -->
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Serial No.</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        if (softwares != null) {
                            int serialNo = 1; // Initialize serial number
                            for (Software software : softwares) {
                    %>
                    <tr>
                        <td><%= serialNo++ %></td> <!-- Display serial number -->
                        <td><%= software.getId() %></td>
                        <td><%= software.getName() %></td>
                        <td><%= software.getDescription() %></td>
                        <td>
                            <form action="SoftwareServlet" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="<%= software.getId() %>">
                                <button type="submit" name="deleteSoftware" class="btn btn-danger" 
                                        onclick="return confirm('Are you sure you want to delete this software? ID: <%= software.getId() %>, Name: <%= software.getName() %>');">
                                        <i class="fas fa-trash-alt"></i> Delete
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                            }
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
