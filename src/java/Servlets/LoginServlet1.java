
package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LoginServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("NewUserServlet started GET!");
        // Forward to the JSP to show the form
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("LoginServlet started!");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Define the database connection variables
       String dbURL = "jdbc:mysql://localhost:3306/gymmanagement"
               + "?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";  
        String dbUsername = "root";  
        String dbPassword = "12345"; 

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Manually load the MySQL JDBC driver (optional, JDBC 4.0 handles this)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // Query to check if the username and password match
            String sql = "SELECT User_id as userId, role FROM user WHERE Name = ? AND Password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            // If the user exists and credentials are correct
            if (rs.next()) {
                int userId = rs.getInt("userId"); // Get the unique userId
                String role = rs.getString("role");
               
             // Store user-specific information in the session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
                session.setAttribute("role", role);

    // Redirect based on the role
    if ("admin".equals(role)) {
        // Redirect to admin dashboard (change this to servlet mapping if using servlets for admin)
        response.sendRedirect(request.getContextPath() + "/adminServlet");
    } else if ("member".equals(role)) {
        // Redirect to member dashboard (change this to servlet mapping if using servlets for member)
        response.sendRedirect(request.getContextPath() + "/member");
    } else if ("trainer".equals(role)) {
        // Redirect to trainer dashboard (change this to servlet mapping if using servlets for trainer)
        response.sendRedirect(request.getContextPath() + "/trainerServlet");
    } else {
        // If the role is invalid, show error message
        response.getWriter().println("Invalid role.");
    }
} else {
    // Invalid username or password
    response.getWriter().println("Invalid credentials!");
}


        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("An error occurred while processing your request.");
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
