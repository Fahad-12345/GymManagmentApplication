
package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "createClassServlet", urlPatterns = {"/createClassServlet"})
public class createClassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("createClassServlet started!");

        List<String> classTypes = new ArrayList<>();
        List<String> trainerNames = new ArrayList<>();

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            // Fetch class types
            PreparedStatement classTypeStmt = conn.prepareStatement("SELECT DISTINCT Class_type FROM classes");
            ResultSet classTypeRs = classTypeStmt.executeQuery();
            while (classTypeRs.next()) {
                classTypes.add(classTypeRs.getString("Class_type"));
            }

            // Fetch trainer names
            PreparedStatement trainerStmt = conn.prepareStatement(
                    "SELECT Name FROM user WHERE role = 'trainer'");
            ResultSet trainerRs = trainerStmt.executeQuery();
            while (trainerRs.next()) {
                trainerNames.add(trainerRs.getString("Name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass data to the JSP
        request.setAttribute("classTypes", classTypes);
        request.setAttribute("trainerNames", trainerNames);

        // Forward to createClass.jsp
        request.getRequestDispatcher("createClass.jsp").forward(request, response);
    }

@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("createClassServlet POST started!");

        // Retrieve form data from the request
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String classType = request.getParameter("classType");
        String trainerName = request.getParameter("trainer");

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            // SQL to get trainer_id by trainer_name
            String trainerIdSql = "SELECT User_id FROM user WHERE Name = ? AND role = 'trainer'";
            try (PreparedStatement trainerStmt = conn.prepareStatement(trainerIdSql)) {
                trainerStmt.setString(1, trainerName);
                ResultSet trainerRs = trainerStmt.executeQuery();

                if (trainerRs.next()) {
                    // Get the trainer_id
                    int trainerId = trainerRs.getInt("User_id");

                    // SQL statement to insert new class into the classes table
                    String sql = "INSERT INTO classes (Date, Time, Class_type, Trainer_id) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setString(1, date);
                        stmt.setString(2, time);
                        stmt.setString(3, classType);
                        stmt.setInt(4, trainerId); // Insert trainer_id instead of trainer_name

                        int rowsInserted = stmt.executeUpdate();
if (rowsInserted > 0) {
    // Set success message as request attribute
    request.setAttribute("successMessage", "Class created successfully!");

    // Forward back to createClass.jsp
    request.getRequestDispatcher("createClass.jsp").forward(request, response);
} else {
    // Handle failure
    request.setAttribute("errorMessage", "Failed to create class.");
    request.getRequestDispatcher("createClass.jsp").forward(request, response);
}


                    }
                } else {
                    // If the trainer is not found
                    request.setAttribute("errorMessage", "Trainer not found.");
                    request.getRequestDispatcher("createClass.jsp").forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception and forward error message
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("createClass.jsp").forward(request, response);
        }
    }
}