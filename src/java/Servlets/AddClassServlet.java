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

@WebServlet("/AddClassServlet")
public class AddClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
    
    // Check if session exists and user is logged in
    if (session != null && session.getAttribute("userId") != null) {
        int userId = (int) session.getAttribute("userId");

        List<memberClass> classPlans = new ArrayList<>();

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT plan_list as planList,Date as date, Time as time, Class_type as class_type, " +
                     "usr.Name as trainer " +
                     "FROM classes LEFT JOIN user as usr ON classes.trainer_id = usr.user_id " +
                     "WHERE usr.role = 'trainer'")) {
                 
//            stmt.setInt(1, userId); 
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberClass plan = new memberClass();
                plan.setDate(rs.getString("date"));
                plan.setTime(rs.getString("time"));
                plan.setClassType(rs.getString("class_type"));
                plan.setTrainer(rs.getString("trainer"));
                plan.setplanList(rs.getString("planList"));
                classPlans.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass data to the JSP
        request.setAttribute("classPlans", classPlans);

        // Forward to addClass.jsp
        request.getRequestDispatcher("addClass.jsp").forward(request, response);
    }
    else {
        // If no session exists, redirect to login page
        response.sendRedirect("login.jsp");
    }
}


@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String date = request.getParameter("date");
    String time = request.getParameter("time");
    String classType = request.getParameter("classType");
    String trainer = request.getParameter("trainer");

    // Debugging point: Check input parameters
    System.out.println("Received parameters: Date=" + date + ", Time=" + time + ", Class Type=" + classType + ", Trainer=" + trainer);

    // Database connection details
    String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
    String dbUsername = "root";
    String dbPassword = "12345";

    try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

        // Debugging point: Confirm successful connection
        System.out.println("Connected to database successfully.");
        // Assuming trainer's name is unique, you can use it to fetch the trainer's user_id
        String trainerSql = "SELECT user_id FROM user WHERE Name = ? AND role = 'trainer'";
        PreparedStatement trainerStmt = conn.prepareStatement(trainerSql);
        trainerStmt.setString(1, trainer);
        
        // Debugging point: Log the query before execution
        System.out.println("Executing query: " + trainerSql + " with trainer name=" + trainer);
        
        ResultSet rs = trainerStmt.executeQuery();

        int trainerId = 0;
        if (rs.next()) {
            trainerId = rs.getInt("user_id");
            // Debugging point: Log the fetched trainer ID
            System.out.println("Found trainer ID: " + trainerId);
        } else {
            // Debugging point: If trainer not found, log this
            System.out.println("No trainer found with name: " + trainer);
        }
        // Now, update the plan_list to 'added' for the specific class plan
    String sqlUpdate = "UPDATE classes SET plan_list = 'added' WHERE Date = ? AND Time = ? AND Class_type = ? AND trainer_id = ?";
    PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
    stmtUpdate.setString(1, date);
    stmtUpdate.setString(2, time);
    stmtUpdate.setString(3, classType);
    stmtUpdate.setInt(4, trainerId);
        
        // Debugging point: Log the query parameters before execution
        System.out.println("Executing insert query with Date=" + date + ", Time=" + time + ", Class Type=" + classType + ", Trainer ID=" + trainerId);
        
        stmtUpdate.executeUpdate();

        // Set success message
        request.setAttribute("message", "Class added successfully!");
        
        // Fetch updated class plans to display in the table
        List<memberClass> classPlans = new ArrayList<>();
        try (PreparedStatement stmtClassPlans = conn.prepareStatement(
                "SELECT plan_list as planList,Date as date, Time as time, Class_type as class_type, usr.Name as trainer " +
                "FROM classes LEFT JOIN user as usr ON classes.trainer_id = usr.user_id " +
                "WHERE usr.role = 'trainer'")) {

            // Debugging point: Log the query being executed
            System.out.println("Executing class plans query: " + stmtClassPlans.toString());
            
            ResultSet rsClassPlans = stmtClassPlans.executeQuery();

            while (rsClassPlans.next()) {
                memberClass plan = new memberClass();
                plan.setDate(rsClassPlans.getString("date"));
                plan.setTime(rsClassPlans.getString("time"));
                plan.setClassType(rsClassPlans.getString("class_type"));
                plan.setTrainer(rsClassPlans.getString("trainer"));
                plan.setplanList(rsClassPlans.getString("planList"));
                classPlans.add(plan);
            }
        }

        // Debugging point: Log the number of class plans retrieved
        System.out.println("Retrieved " + classPlans.size() + " class plans.");

        // Pass updated classPlans to the JSP
        request.setAttribute("classPlans", classPlans);

        // Forward to addClass.jsp
        request.getRequestDispatcher("addClass.jsp").forward(request, response);

    } catch (Exception e) {
        // Debugging point: Log exception details
        e.printStackTrace();
        // Optionally, set an error message to display on the JSP
        request.setAttribute("errorMessage", "An error occurred while adding the class. Please try again.");
        request.getRequestDispatcher("addClass.jsp").forward(request, response);
    }
}


}