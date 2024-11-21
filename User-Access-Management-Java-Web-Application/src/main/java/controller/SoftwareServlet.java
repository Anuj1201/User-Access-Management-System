package controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
/*
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dao.Software;
import dao.SoftwareDAO;

//@WebServlet("/SoftwareServlet")
@SuppressWarnings("serial")
public class SoftwareServlet extends HttpServlet {
    private SoftwareDAO softwareDAO = new SoftwareDAO();

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Software> softwares = softwareDAO.getAllSoftwares();
        request.setAttribute("softwares", softwares);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ManageSoftware.jsp");
        dispatcher.forward(request, response);
    }

    
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = request.getParameter("deleteSoftware");
        
        // If the delete button was pressed
        if (action != null) {
            // Get the ID of the software to delete
            int id = Integer.parseInt(request.getParameter("id"));
            softwareDAO.deleteSoftware(id); // Call DAO method to delete software
            response.sendRedirect("SoftwareServlet"); // Redirect back to the list page
        } else {
            // Existing code for adding software
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            
            softwareDAO.addSoftware(new Software(name, description));
            
            	
            
            response.sendRedirect("SoftwareServlet"); // Redirect back to the list page
        
        }
        /*
        softwareDAO.addSoftware(new Software(name, description));
        response.sendRedirect("SoftwareServlet"); // Redirect to the GET method to refresh the list
        */
        
        
           
    	/*
    	PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        
        RequestDispatcher dispatcher = null;
        Connection con = null;
        PreparedStatement pst = null; // Added to close later
        
        try {
            final String URL = "jdbc:oracle:thin:@MSI:1521:orcl";
            final String USERNAME = "system";
            final String PASSWORD = "Bhagyajyoti768";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            pst = con.prepareStatement("INSERT INTO software (name, description) VALUES (?, ?)");
            
            
            pst.setString(1, name);
            pst.setString(2, description);
            
            int rowcount = pst.executeUpdate();

            dispatcher = request.getRequestDispatcher("ManageSoftware.jsp");
            
            if (rowcount > 0) {
            	//If data is inserted, then "status" will be "success", help for sweet alert
                request.setAttribute("status", "success");
            } else {
            	//If data is inserted, then "status" will be "error", help for sweet alert
                request.setAttribute("status", "error");
            }
            dispatcher.forward(request, response);
    	
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
            // Optionally, set an error message in the request
            request.setAttribute("status", "error");
            dispatcher.forward(request, response);
		}
        finally {
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
        */
    }
}

