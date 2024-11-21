package controller;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class RequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");
        
        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Map<String, Object>> softwareList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@MSI:1521:orcl", "system", "Bhagyajyoti768")) {
            String query = "SELECT s.id, s.name, s.description, COALESCE(r.status, 'No Request') AS accessStatus " +
                           "FROM software s LEFT JOIN requests r ON s.id = r.software_id AND r.user_email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, userEmail);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Map<String, Object> software = new HashMap<>();
                software.put("id", rs.getInt("id"));
                software.put("name", rs.getString("name"));
                software.put("description", rs.getString("description"));
                software.put("accessStatus", rs.getString("accessStatus"));
                softwareList.add(software);
            }

            request.setAttribute("softwareList", softwareList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("RequestAccess.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("email");

        if (userEmail == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int softwareId = Integer.parseInt(request.getParameter("softwareId"));
        String reason = request.getParameter("reason");

        try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@MSI:1521:orcl", "system", "Bhagyajyoti768")) {
            String checkQuery = "SELECT status FROM requests WHERE user_email = ? AND software_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, userEmail);
            checkStmt.setInt(2, softwareId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String status = rs.getString("status");
                if ("Rejected".equals(status)) {
                    String updateQuery = "UPDATE requests SET status = 'Pending', reason = ? WHERE user_email = ? AND software_id = ?";
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setString(1, reason);
                    updateStmt.setString(2, userEmail);
                    updateStmt.setInt(3, softwareId);
                    updateStmt.executeUpdate();
                }
            } else {
                String insertQuery = "INSERT INTO requests (user_email, software_id, reason) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, userEmail);
                insertStmt.setInt(2, softwareId);
                insertStmt.setString(3, reason);
                insertStmt.executeUpdate();
            }

            response.sendRedirect("RequestServlet");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
