package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 *
 * @author fahad
 */
@WebServlet(name = "endClassServlet", urlPatterns = {"/endClassServlet"})
public class endClassServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Attendance POST method triggered!");

        
        String classType = request.getParameter("classType"); // Get classType from the form
        System.out.println("Class Type received in endClassServlet: " + classType);

        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        int classId = getClassIdByType(classType); // Fetch Class_id based on classType
        if (classId == -1) {
            response.getWriter().write("Invalid class type.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            // SQL query to insert attendance status with Class_id
            String updateClassTypeSQL = "UPDATE classes SET Status = 'completed' WHERE Class_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateClassTypeSQL)) {
                stmt.setInt(1, classId); // Set Class_id based on classType
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // Set success message
                    request.setAttribute("completionMessage", "Class is completed.");
                    // Forward to the startClass.jsp with the success message
                    response.sendRedirect("http://localhost:8080/gymApp/trainerServlet");
                } else {
                    response.getWriter().write("Failed to record attendance.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }

    // Helper method to get Class_id based on classType
    private int getClassIdByType(String classType) {
        int classId = -1;
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        // Query to fetch Class_id based on classType
        String sql = "SELECT Class_id FROM classes WHERE Class_type = ?";
        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, classType);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    classId = rs.getInt("Class_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classId;
    }
}