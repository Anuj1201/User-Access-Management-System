package controller;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// MySQL database connection details
	private final String URL = "jdbc:mysql://localhost:3306/user_access_mgmt"; // MySQL URL
	private final String USERNAME = "root"; // MySQL username
	private final String PASSWORD = "1234"; // MySQL password

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("email");

		if (userEmail == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		List<Map<String, Object>> softwareList = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			String query = "SELECT s.id, s.name, s.description, " + "COALESCE(r.status, 'No Request') AS accessStatus "
					+ "FROM software s " + "LEFT JOIN requests r ON s.id = r.software_id "
					+ "AND r.user_id = (SELECT id FROM users WHERE email = ?)";
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("email");

		if (userEmail == null) {
			response.sendRedirect("login.jsp");
			return;
		}

		int softwareId = Integer.parseInt(request.getParameter("softwareId"));
		String reason = request.getParameter("reason");

		try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			// Fetch user ID based on the email
			String userQuery = "SELECT id FROM users WHERE email = ?";
			PreparedStatement userStmt = conn.prepareStatement(userQuery);
			userStmt.setString(1, userEmail);
			ResultSet userRs = userStmt.executeQuery();

			if (!userRs.next()) {
				response.sendRedirect("login.jsp");
				return;
			}

			int userId = userRs.getInt("id");

			// Check if the request already exists
			String checkQuery = "SELECT status FROM requests WHERE user_id = ? AND software_id = ?";
			PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
			checkStmt.setInt(1, userId);
			checkStmt.setInt(2, softwareId);
			ResultSet rs = checkStmt.executeQuery();

			if (rs.next()) {
				String status = rs.getString("status");
				if ("Rejected".equals(status)) {
					// Update the existing rejected request to "Pending"
					String updateQuery = "UPDATE requests SET status = 'Pending', reason = ? WHERE user_id = ? AND software_id = ?";
					PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
					updateStmt.setString(1, reason);
					updateStmt.setInt(2, userId);
					updateStmt.setInt(3, softwareId);
					updateStmt.executeUpdate();
				}
			} else {
				// Insert a new request if it doesn't exist
				String insertQuery = "INSERT INTO requests (user_id, software_id, reason, status) VALUES (?, ?, ?, 'Pending')";
				PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
				insertStmt.setInt(1, userId);
				insertStmt.setInt(2, softwareId);
				insertStmt.setString(3, reason);
				insertStmt.executeUpdate();
			}

			// Redirect to the request list page
			response.sendRedirect("RequestServlet");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
