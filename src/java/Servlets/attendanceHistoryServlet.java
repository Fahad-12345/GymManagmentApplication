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

@WebServlet(name = "attendanceHistoryServlet", urlPatterns = {"/attendanceHistoryServlet"})
public class attendanceHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<memberClass> classPlans = new ArrayList<>();

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT class.Date as date, class.Time as time, class.Class_type as class_type,  " +
                     "usr.Name as trainer , class.status as class_status " +
                     "FROM classes as class"
                   + " LEFT JOIN user as usr ON class.trainer_id = usr.user_id AND usr.role = 'trainer' " +
                     "LEFT JOIN attendance ON attendance.Class_id = class.Class_id AND attendance.status = 'present'"
                             + "WHERE class.Status = 'Completed'")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberClass plan = new memberClass();
                plan.setDate(rs.getString("date"));
                plan.setTime(rs.getString("time"));
                plan.setClassType(rs.getString("class_type"));
                plan.setTrainer(rs.getString("trainer"));
                plan.setClassStatus(rs.getString("class_status"));
                classPlans.add(plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass data to the JSP
        request.setAttribute("classPlans", classPlans);

        // Forward to addClass.jsp
        request.getRequestDispatcher("attendanceHistory.jsp").forward(request, response);
    }
}