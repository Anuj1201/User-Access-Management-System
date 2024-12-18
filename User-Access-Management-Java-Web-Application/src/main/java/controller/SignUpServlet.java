package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SignUpServlet
 */
public class SignUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SignUpServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Get the form parameters
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact");

        // Password hashing (to securely store the password)
        String hashedPassword = hashPassword(upwd);

        RequestDispatcher dispatcher = null;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            // MySQL Database Connection Details
            final String URL = "jdbc:mysql://localhost:3306/user_access_mgmt"; // DB name
            final String USERNAME = "root"; // MySQL username
            final String PASSWORD = "1234"; // MySQL password
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection to MySQL database
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // SQL Query to insert user into users table
            String sql = "INSERT INTO users (username, password, email, mobile, role) VALUES (?, ?, ?, ?, 'Employee')";
            pst = con.prepareStatement(sql);
            pst.setString(1, uname);
            pst.setString(2, upwd); // Store the hashed password
            pst.setString(3, uemail);
            pst.setString(4, umobile);

            // Execute the insert statement
            int rowcount = pst.executeUpdate();

            dispatcher = request.getRequestDispatcher("registration.jsp");

            if (rowcount > 0) {
                // If data inserted, set success status
                request.setAttribute("status", "success");
            } else {
                // If data not inserted, set error status
                request.setAttribute("status", "error");
            }
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, set an error message in the request
            request.setAttribute("status", "error");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        } finally {
            // Close resources
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Utility method to hash the password using SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
