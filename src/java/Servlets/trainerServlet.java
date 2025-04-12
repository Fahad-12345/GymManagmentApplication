/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.memberClass;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "trainerServlet", urlPatterns = {"/trainerServlet"})
public class trainerServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        System.out.println("TrainerServlet started!");
        // Retrieve the userId from the session
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("userId"); 
    System.out.println("userID:"+ userId);

        List<memberClass> classPlans = new ArrayList<>();

        // Define the database connection variables
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement"
                + "?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");

            // Establish database connection
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connected to the database.");

            // Define the SQL query
            String sql = "SELECT classes.Class_id AS classID,Date as date, Time as time, Status as classStatus, " 
           + "Class_type as class_type, "
           + "schedule.Booking_id AS bookedID "
           + "FROM classes "
           + "LEFT JOIN schedule ON classes.class_id = schedule.class_id LEFT JOIN user ON user.User_id = classes.Trainer_id WHERE classes.plan_list = 'added' AND classes.Trainer_id = ?";
           System.out.println("Query executed: " + sql);

            // Execute the query
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            // Check if any rows are returned
            if (!rs.next()) {
                System.out.println("No rows returned from the query.");
            } else {
                // Reset the cursor and iterate over the result set
                do {
                    memberClass plan = new memberClass();
                    plan.setClassID(rs.getInt("classID"));
                    plan.setClassStatus(rs.getString("classStatus"));
                    plan.setDate(rs.getString("date"));
                    plan.setTime(rs.getString("time"));
                    plan.setClassType(rs.getString("class_type"));
                    plan.setBookedID(rs.getString("bookedID"));
                    classPlans.add(plan);
                } while (rs.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are cleaned up
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println("Error closing resources: " + ex.getMessage());
            }
        }

        // Debugging: Check if classPlans is empty
        if (classPlans.isEmpty()) {
            System.out.println("classPlans is empty.");
        } else {
            System.out.println("classPlans contains " + classPlans.size() + " entries.");
        }

        // Pass the list to the JSP
        request.setAttribute("classPlans", classPlans); // Set the classPlans attribute

        
        request.getRequestDispatcher("trainer.jsp").forward(request, response);
    }

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    List<memberClass> classPlans = new ArrayList<>();
    String bookedID = request.getParameter("bookedID");
    String classType = request.getParameter("classType");

    // Database connection variables
    String dbURL = "jdbc:mysql://localhost:3306/gymmanagement"
            + "?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";
    String dbUsername = "root";
    String dbPassword = "12345";

    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Load JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish database connection
        conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

        // Update query to cancel the class
        String sql = "UPDATE classes SET status = 'cancelled' WHERE Class_type = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, classType);

        // Execute update
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Class with Class_type " + classType + " has been cancelled.");
        }

        // Fetch the updated list of class plans
        String selectSQL = "SELECT Date as date, Time as time,Status as classStatus "
                + "Class_type as class_type, schedule.Booking_id AS bookedID "
                + "FROM classes "
                + "LEFT JOIN schedule ON classes.class_id = schedule.class_id";
        stmt = conn.prepareStatement(selectSQL);
        ResultSet rs = stmt.executeQuery();

        // Populate classPlans with the updated data
        while (rs.next()) {
            memberClass plan = new memberClass();
            plan.setDate(rs.getString("date"));
            plan.setTime(rs.getString("time"));
            plan.setClassType(rs.getString("class_type"));
            plan.setBookedID(rs.getString("bookedID"));
            plan.setClassStatus(rs.getString("classStatus"));
            classPlans.add(plan);
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Debugging: Check if classPlans is empty
    if (classPlans.isEmpty()) {
        System.out.println("classPlans is empty.");
    } else {
        System.out.println("classPlans contains " + classPlans.size() + " entries.");
    }

    // Set the updated classPlans attribute and forward the request to JSP
    request.setAttribute("classPlans", classPlans); // Set the classPlans attribute

    // Forward the request to the JSP to display the updated class plans
    request.getRequestDispatcher("trainer.jsp").forward(request, response);
}

}
