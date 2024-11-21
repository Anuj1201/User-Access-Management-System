package controller;

import java.io.IOException;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/




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
        
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact"); // Fixed typo
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        PreparedStatement pst = null; // Added to close later

        try {
            final String URL = "jdbc:oracle:thin:@MSI:1521:orcl";
            final String USERNAME = "system";
            final String PASSWORD = "Bhagyajyoti768";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            pst = con.prepareStatement("INSERT INTO users (uname, upwd, uemail, umobile) VALUES (?, ?, ?, ?)");
            pst.setString(1, uname);
            pst.setString(2, upwd);
            pst.setString(3, uemail);
            pst.setString(4, umobile);
            
            //rowcount : will get the no. of data inserted
            int rowcount = pst.executeUpdate();

            dispatcher = request.getRequestDispatcher("registration.jsp");
            
            
            if (rowcount > 0) {
            	//If data is inserted, then "status" will be "success", help for sweet alert
                request.setAttribute("status", "success");
            } else {
            	//If data is inserted, then "status" will be "error", help for sweet alert
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
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
