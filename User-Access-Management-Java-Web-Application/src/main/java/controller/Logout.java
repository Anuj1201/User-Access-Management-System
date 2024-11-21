package controller;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
*/




/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Logout() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Get existing session
        if (session != null) {
            session.invalidate(); // Invalidate the session
            System.out.println("Logged out !");
        }
        /*
         session.invalidate();: This line ensures that the session is terminated, clearing any user data associated with it.
session = request.getSession(false);: This gets the current session, if it exists, to avoid creating a new one. This is useful in case the logout request is made when there is no active session.
The System.out.println statement helps you log the action of logging out, but remember this is only useful for debugging. In production, you might want to use a logging framework instead. 
         */
        
        
        

        response.sendRedirect("login.jsp"); // Redirect to login page
    }
}
