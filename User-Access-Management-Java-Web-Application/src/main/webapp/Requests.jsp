<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Pending Requests</title>
    <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
    <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
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
            <h1 class="masthead-heading text-uppercase mb-0">Pending Requests</h1>
            <p class="masthead-subheading font-weight-light mb-0">Manage Software Access Requests</p>
        </div>
    </header>

    <section class="page-section">
        <div class="container">
            <h2 class="text-center mb-4">Pending Requests</h2>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Employee Email</th>
                        <th>Software Name</th>
                        <th>Reason</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Map<String, String>> pendingRequests = (List<Map<String, String>>) request.getAttribute("pendingRequests");
                        for (Map<String, String> req : pendingRequests) {
                    %>
                    <tr>
                        <td><%= req.get("userEmail") %></td>
                        <td><%= req.get("softwareName") %></td>
                        <td><%= req.get("reason") %></td>
                        <td>
                            <span class="badge <%= "Approved".equals(req.get("status")) ? "bg-success" : "Rejected".equals(req.get("status")) ? "bg-danger" : "bg-warning" %>">
                                <%= req.get("status") %>
                            </span>
                        </td>
                        <td>
                            <form action="Requests" method="post" style="display:inline;">
                                <input type="hidden" name="requestId" value="<%= req.get("id") %>">
                                <button name="action" value="approve" class="btn btn-success">Approve</button>
                                <button name="action" value="reject" class="btn btn-danger">Reject</button>
                            </form>
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
