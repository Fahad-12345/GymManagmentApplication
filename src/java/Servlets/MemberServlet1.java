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
import jakarta.servlet.http.HttpSession;



public class MemberServlet1 extends HttpServlet {
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    System.out.println("MemberServlet started!");

    // Retrieve the userId from the session
    HttpSession session = request.getSession();
    Integer userId = (Integer) session.getAttribute("userId"); 
    System.out.println("userID:"+ userId);

    if (userId == null) {
        // If the user is not logged in, redirect to login page
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

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
            String sql = "SELECT Class_id as ClassID, class.Date as date, class.Time as time,"
                + " class.Class_type as class_type, usr.Name as trainer"
                + " , class.status as class_status "
                + "FROM classes as class "
                + "LEFT JOIN user as usr ON class.trainer_id = usr.user_id AND usr.role = 'trainer'"
                + " WHERE class.plan_list = 'added'"; // Add a condition to filter by userId
        System.out.println("Query executed: " + sql);

        // Execute the query with the userId as a parameter
        stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, userId); // Set the userId parameter to the session's userId
        rs = stmt.executeQuery();

        // Process the result set
        while (rs.next()) {
            memberClass plan = new memberClass();
            plan.setDate(rs.getString("date"));
            plan.setTime(rs.getString("time"));
            plan.setClassType(rs.getString("class_type"));
            plan.setTrainer(rs.getString("trainer"));
            plan.setClassStatus(rs.getString("class_status"));
            plan.setClassID(rs.getInt("ClassID"));
            classPlans.add(plan);
            System.out.println("Added class plan: " + plan.getDate() + ", " + plan.getTrainer());
        }

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("An error occurred during database access: " + e.getMessage());
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

    // Pass the list to the JSP
    request.setAttribute("classPlans", classPlans);

    // Forward to the member.jsp page
    System.out.println("Forwarding request to member.jsp");
    request.getRequestDispatcher("member.jsp").forward(request, response);
}
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Remove action triggered!");

        // Get the ClassID to remove
        String classID = request.getParameter("classID"); // Assuming classID is passed as a request parameter

        // Define the database connection variables
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement"
                + "?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver loaded successfully.");

            // Establish database connection
            conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Connected to the database.");

            // Define the SQL query to update the plan_list for the given ClassID
            String sql = "UPDATE classes SET plan_list = 'not-added' WHERE Class_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, classID); // Set the ClassID in the query
            int rowsAffected = stmt.executeUpdate();

            // Check if any rows were updated
            if (rowsAffected > 0) {
                System.out.println("Class with ClassID " + classID + " removed successfully.");
            } else {
                System.out.println("No class found with ClassID " + classID);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred during database access: " + e.getMessage());
        } finally {
            // Ensure resources are cleaned up
            try {
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

        // Redirect to the doGet to refresh the page with updated data
        doGet(request, response); // Call doGet to fetch the updated list of classes
    }
}
