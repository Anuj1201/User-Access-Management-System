package controller;

import java.io.*; 
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Requests")
public class Requests extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final String URL = "jdbc:mysql://localhost:3306/user_access_mgmt"; // MySQL connection URL
    private final String USERNAME = "root"; // MySQL username (change as per your setup)
    private final String PASSWORD = "1234"; // MySQL password (change as per your setup)

    // Handles GET requests to display pending requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, String>> pendingRequests = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC Driver
            
            // SQL query to fetch pending requests
            String query = "SELECT r.id, u.username AS userEmail, s.name AS softwareName, r.access_type AS accessType, r.reason, r.status " +
                           "FROM requests r " +
                           "JOIN software s ON r.software_id = s.id " +
                           "JOIN users u ON r.user_id = u.id " +
                           "WHERE r.status = 'Pending'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map<String, String> requestMap = new HashMap<>();
                requestMap.put("id", rs.getString("id"));
                requestMap.put("userEmail", rs.getString("userEmail"));
                requestMap.put("softwareName", rs.getString("softwareName"));
                requestMap.put("accessType", rs.getString("accessType"));
                requestMap.put("reason", rs.getString("reason"));
                requestMap.put("status", rs.getString("status"));
                pendingRequests.add(requestMap);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        request.setAttribute("pendingRequests", pendingRequests);
        request.getRequestDispatcher("Requests.jsp").forward(request, response);
    }

    // Handles POST requests for approving/rejecting requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestId = request.getParameter("requestId");
        String action = request.getParameter("action");

        String status = "Pending";
        if ("approve".equals(action)) {
            status = "Approved";
        } else if ("reject".equals(action)) {
            status = "Rejected";
        }

        // Database connection to update the request status
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String updateQuery = "UPDATE requests SET status = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, status);
            stmt.setInt(2, Integer.parseInt(requestId));
            stmt.executeUpdate();

            // Redirect to refresh the requests list
            response.sendRedirect("Requests");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
