package controller;

import jakarta.servlet.RequestDispatcher; 
import jakarta.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String uemail = request.getParameter("username");
        String upwd = request.getParameter("password");

        RequestDispatcher dispatcher = null;
        Connection con = null;
        PreparedStatement pst = null;

        try {
            // MySQL Connection Details
            final String URL = "jdbc:mysql://localhost:3306/user_access_mgmt"; // Database name
            final String USERNAME = "root"; // MySQL username (default is 'root')
            final String PASSWORD = "1234"; // MySQL password 
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL JDBC Driver

            // Establish connection
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            pst = con.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
            pst.setString(1, uemail);
            pst.setString(2, upwd);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                session.setAttribute("name", rs.getString("username"));
                session.setAttribute("email", uemail); // Set email in session

                String role = rs.getString("role");
                session.setAttribute("role", role);

                // Redirect based on role
                if ("Employee".equalsIgnoreCase(role)) {
                    dispatcher = request.getRequestDispatcher("index.jsp");
                } else if ("Manager".equalsIgnoreCase(role)) {
                    dispatcher = request.getRequestDispatcher("PendingRequest.jsp");
                } else if ("Admin".equalsIgnoreCase(role)) {
                    dispatcher = request.getRequestDispatcher("CreateSoftware.jsp");
                }
            } else {
                request.setAttribute("status", "error");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}