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

@WebServlet(name = "startClassServlet", urlPatterns = {"/startClassServlet"})
public class startClassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("startClassServlet started!");

        // Get the BookedID and ClassType from the request
        String bookedID = request.getParameter("bookedID");
        String classType = request.getParameter("classType");  // Retrieve classType from request

        if (bookedID == null || bookedID.isEmpty()) {
            response.getWriter().write("Invalid or missing BookedID.");
            return;
        }

        if (classType == null || classType.isEmpty()) {
            response.getWriter().write("Invalid or missing ClassType.");
            return;
        }

        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            // SQL query to get Member_id and Member_Name based on BookedID
            String sql = "SELECT user.User_id AS Member_id, user.Name AS Member_Name " +
                         "FROM schedule " +
                         "LEFT JOIN user ON schedule.Member_id = user.User_id " +
                         "WHERE schedule.Booking_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, bookedID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int memberId = rs.getInt("Member_id");
                    String memberName = rs.getString("Member_Name");

                    // Debugging output
                    System.out.println("Member_id: " + memberId);
                    System.out.println("Member_Name: " + memberName);

                    // Set attributes for forwarding to JSP
                    request.setAttribute("memberId", memberId);
                    request.setAttribute("memberName", memberName);
                    request.setAttribute("classType", classType);  // Set classType as a request attribute

                    // Forward to startClass.jsp
                    request.getRequestDispatcher("startClass.jsp").forward(request, response);
                    
                    
                } else {
                    response.getWriter().write("No member found for BookedID: " + bookedID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    System.out.println("Attendance POST method triggered!");

    // Get the memberId, status, and classType from the form
    String action = request.getParameter("memberId");
    String memberId = request.getParameter("memberId");
    String status = request.getParameter("status");
    String classType = request.getParameter("classType");

    System.out.println("Received Parameters:");
     System.out.println("action: " + action);
    System.out.println("Member ID: " + memberId);
    System.out.println("Status: " + status);
    System.out.println("Class Type: " + classType);

    if (memberId == null || memberId.isEmpty() || status == null || status.isEmpty() || classType == null || classType.isEmpty()) {
        System.out.println("Validation failed: Missing or invalid input parameters.");
        response.getWriter().write("Invalid or missing memberId, status, or classType.");
        return;
    }

    String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
    String dbUsername = "root";
    String dbPassword = "12345";

    // Fetch Class_id based on classType
    int classId = getClassIdByType(classType);
    System.out.println("Resolved Class ID: " + classId);
    if (classId == -1) {
        System.out.println("Invalid class type provided: " + classType);
        response.getWriter().write("Invalid class type.");
        return;
    }

    try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
        System.out.println("Database connection established successfully.");

        // SQL queries
        String sql = "INSERT INTO attendance (Member_id, Status, Class_id) VALUES (?, ?, ?)";
        String updateClassTypeSQL = "UPDATE classes SET Status = ? WHERE Class_id = ?";
        System.out.println("Prepared SQL for attendance insertion: " + sql);
        System.out.println("Prepared SQL for class status update: " + updateClassTypeSQL);

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             PreparedStatement updateStmt = conn.prepareStatement(updateClassTypeSQL)) {

            // Setting parameters for attendance insertion
            stmt.setString(1, memberId);
            stmt.setString(2, status);
            stmt.setInt(3, classId);
            System.out.println("Executing attendance insertion with parameters: " +
                    "Member ID: " + memberId + ", Status: " + status + ", Class ID: " + classId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected by attendance insertion: " + rowsAffected);

            if (rowsAffected > 0) {
                String classStatus = "pending"; // Default status

                // Determine class status based on attendance status
                if ("present".equalsIgnoreCase(status)) {
                    classStatus = "started";
                }
                System.out.println("Determined class status: " + classStatus);

                // Update the class status
                updateStmt.setString(1, classStatus);
                updateStmt.setInt(2, classId);
                System.out.println("Executing class status update with parameters: " +
                        "Class Status: " + classStatus + ", Class ID: " + classId);
                int updateRows = updateStmt.executeUpdate();
                System.out.println("Rows affected by class status update: " + updateRows);

                if (updateRows > 0) {
                    System.out.println("Class status updated successfully.");
                    request.setAttribute("attendanceMessage", "Attendance recorded and class status updated successfully.");
                } else {
                    System.out.println("Class status update failed.");
                    request.setAttribute("attendanceMessage", "Attendance recorded, but failed to update class status.");
                }
                
//                   request.setAttribute("memberId", memberId);
                   request.setAttribute("classType", classType);
                   request.setAttribute("status", status);

                request.getRequestDispatcher("startClass.jsp").forward(request, response);
            } else {
                System.out.println("Attendance insertion failed.");
                response.getWriter().write("Failed to record attendance.");
            }
        }
    } catch (Exception e) {
        System.out.println("Exception occurred during database operation: " + e.getMessage());
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
    System.out.println("Executing query to fetch Class ID for Class Type: " + classType);

    try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, classType);
        System.out.println("Prepared SQL for Class ID retrieval: " + sql);
        System.out.println("Executing query with parameter: Class Type: " + classType);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                classId = rs.getInt("Class_id");
                System.out.println("Retrieved Class ID: " + classId);
            } else {
                System.out.println("No Class ID found for Class Type: " + classType);
            }
        }
    } catch (Exception e) {
        System.out.println("Exception occurred during Class ID retrieval: " + e.getMessage());
        e.printStackTrace();
    }
    return classId;
}

}
