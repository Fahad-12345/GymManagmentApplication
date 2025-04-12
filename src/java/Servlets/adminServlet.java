package Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.UserClass;
import models.memberClass;

@WebServlet(name = "adminServlet", urlPatterns = {"/adminServlet"})
public class adminServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null || action.isEmpty()) {
            // Navigate to admin.jsp by default
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (action) {
            case "allmembers":
                handleAllMembers(request, response);
                break;
            case "editMember":
                handleEditMember(request, response);
                break;
            case "alltrainers":
                handleAllTrainers(request, response);
                break;
            case "editTrainers":
                handleEditTrainers(request, response);
                break;
            case "adminSchedule":
                handleAdminSchedule(request, response);
                break;
            case "editAdminSchedule":
                handleEditAdminSchedule(request, response);
                break;     
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action!");
        }
    }

    private void handleAllMembers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<UserClass> members = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM user WHERE role = 'member'";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    UserClass member = new UserClass();
                    member.setUserId(rs.getInt("User_id"));
                    member.setName(rs.getString("Name"));
                    member.setEmail(rs.getString("Email"));
                    member.setPhone(rs.getString("Phone"));
                    member.setPassword(rs.getString("Password"));
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("members", members);
        RequestDispatcher dispatcher = request.getRequestDispatcher("allmembers.jsp");
        dispatcher.forward(request, response);
    }

    
   private void handleEditMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String memberId = request.getParameter("memberId");
    System.out.println("Received memberId: " + memberId); // Debug statement

    if (memberId == null || memberId.isEmpty()) {
        System.out.println("Error: memberId is missing in the request.");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Member ID is required.");
        return;
    }

    UserClass member = null;

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT * FROM user WHERE User_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(memberId));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    member = new UserClass();
                    member.setUserId(rs.getInt("User_id"));
                    member.setName(rs.getString("Name"));
                    member.setEmail(rs.getString("Email"));
                    member.setPhone(rs.getString("Phone"));
                    member.setPassword(rs.getString("Password"));
                    System.out.println("Member found: " + member.getName()); // Debug statement
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        return;
    }

    if (member != null) {
        // Set the member object as an attribute to be accessed in the JSP
        request.setAttribute("member", member);
        System.out.println("Forwarding to editmember.jsp"); // Debug statement
        RequestDispatcher dispatcher = request.getRequestDispatcher("editmembers.jsp");
        dispatcher.forward(request, response);
    } else {
        System.out.println("Error: Member not found with ID: " + memberId); // Debug statement
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Member not found!");
    }
}

    private void handleAllTrainers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserClass> trainers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM user WHERE role = 'trainer'";
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    UserClass trainer = new UserClass();
                    trainer.setUserId(rs.getInt("User_id"));
                    trainer.setName(rs.getString("Name"));
                    trainer.setEmail(rs.getString("Email"));
                    trainer.setPhone(rs.getString("Phone"));
                    trainer.setPassword(rs.getString("Password"));
                    trainers.add(trainer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("trainers", trainers);
        RequestDispatcher dispatcher = request.getRequestDispatcher("alltrainers.jsp");
        dispatcher.forward(request, response);
    }

    private void handleEditTrainers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String trainerId = request.getParameter("trainerId");
    System.out.println("Received trainerId: " + trainerId); // Debug statement

    if ( trainerId == null || trainerId.isEmpty()) {
        System.out.println("Error: trainerId is missing in the request.");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "trainer ID is required.");
        return;
    }

    UserClass trainer = null;

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT * FROM user WHERE User_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(trainerId));
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    trainer = new UserClass();
                    trainer.setUserId(rs.getInt("User_id"));
                    trainer.setName(rs.getString("Name"));
                    trainer.setEmail(rs.getString("Email"));
                    trainer.setPhone(rs.getString("Phone"));
                    trainer.setPassword(rs.getString("Password"));
                    System.out.println("trainer found: " + trainer.getName()); // Debug statement
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        return;
    }

    if (trainer != null) {
        // Set the member object as an attribute to be accessed in the JSP
        request.setAttribute("trainer", trainer);
        System.out.println("Forwarding to editTrainer.jsp"); // Debug statement
        RequestDispatcher dispatcher = request.getRequestDispatcher("editTrainer.jsp");
        dispatcher.forward(request, response);
    } else {
        System.out.println("Error: Trainer not found with ID: " + trainerId); // Debug statement
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trainer not found!");
    }
}
    
    
    private void handleAdminSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    List<memberClass> schedules = new ArrayList<>();

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT classes.Class_id AS classID, Date, Time, Class_type, Booking_id AS bookID, "
                + "user.Name AS trainer, Status AS class_status "
                + "FROM classes "
                + "LEFT JOIN user ON classes.Trainer_id = user.User_id AND user.role = 'trainer' "
                + "LEFT JOIN schedule ON classes.Class_id = schedule.Class_id";
        
        // Debugging: print the query to ensure it's being formed correctly
        System.out.println("Executing query: " + query);
        
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                memberClass schedule = new memberClass();

                // Fetch and debug each value to check for nulls
                int classID = rs.getInt("classID");
                System.out.println("classID: " + classID); // Debugging output
                
                String date = rs.getString("date");
                if (date == null) {
                    System.out.println("Warning: Date is null for classID: " + classID);
                }
                System.out.println("Date: " + date); // Debugging output
                
                String time = rs.getString("time");
                if (time == null) {
                    System.out.println("Warning: Time is null for classID: " + classID);
                }
                System.out.println("Time: " + time); // Debugging output
                
                String classType = rs.getString("Class_type");
                if (classType == null) {
                    System.out.println("Warning: Class Type is null for classID: " + classID);
                }
                System.out.println("Class Type: " + classType); // Debugging output
                
                String bookedID = rs.getString("bookID");
                if (bookedID == null) {
                    System.out.println("Warning: Booked ID is null for classID: " + classID);
                }
                System.out.println("Booked ID: " + bookedID); // Debugging output
                
                String trainer = rs.getString("trainer");
                if (trainer == null) {
                    System.out.println("Warning: Trainer is null for classID: " + classID);
                }
                System.out.println("Trainer: " + trainer); // Debugging output
                
                String classStatus = rs.getString("class_status");
                if (classStatus == null) {
                    System.out.println("Warning: Class Status is null for classID: " + classID);
                }
                System.out.println("Class Status: " + classStatus); // Debugging output

                // Set values in the memberClass object
                schedule.setClassID(classID);
                schedule.setDate(date);
                schedule.setTime(time);
                schedule.setClassType(classType);
                schedule.setBookedID(bookedID);
                schedule.setTrainer(trainer);
                schedule.setClassStatus(classStatus);
                
                schedules.add(schedule);
            }
        }
    } catch (SQLException e) {
        System.out.println("SQLException occurred: " + e.getMessage()); // Debugging: print the error message
        e.printStackTrace();
    }

    // Debugging: Check if schedules list is empty
    if (schedules.isEmpty()) {
        System.out.println("No schedules were found.");
    } else {
        System.out.println("Schedules found: " + schedules.size());
    }

    request.setAttribute("schedules", schedules);

    // Debugging: Print the size of the schedules list before forwarding
    System.out.println("Forwarding to adminSchedule.jsp with " + schedules.size() + " schedules.");
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("adminSchedule.jsp");
    dispatcher.forward(request, response);
}


    private void handleEditAdminSchedule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Retrieve parameters from the request
String classId = request.getParameter("classId");
String date = request.getParameter("date");
String time = request.getParameter("time");
String classType = request.getParameter("classType");
String bookedID = request.getParameter("bookedID");
String trainer = request.getParameter("trainer");
String classStatus = request.getParameter("classStatus");

// Debugging: Print received parameters
System.out.println("Received parameters: adminId=" + classId + ", date=" + date + ", time=" + time +
                   ", classType=" + classType + ", bookedID=" + bookedID + ", trainer=" + trainer +
                   ", classStatus=" + classStatus);

// Check if adminId is provided
if (classId == null || classId.isEmpty()) {
    System.out.println("Error: classId is missing in the request.");
    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "adminId is required.");
    return;
}

memberClass admin = null;

try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    // Define the SQL query with all filters
    String query = "SELECT classes.Class_id AS classID , Date, Time, Class_type, Booking_id AS bookID, "
                 + "user.Name AS trainer, Status AS class_status "
                 + "FROM classes "
                 + "LEFT JOIN user ON classes.Trainer_id = user.User_id AND user.role = 'trainer' "
                 + "LEFT JOIN schedule ON classes.Class_id = schedule.Class_id "
                 + "WHERE classes.Class_id = ? AND Date = ? AND Time = ? AND Class_type = ? AND Booking_id = ? "
                 + "AND user.Name = ? AND Status = ?";

    // Debugging: Print the query
    System.out.println("Executing query: " + query);

    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        // Set parameters in the prepared statement
        stmt.setInt(1, Integer.parseInt(classId)); 
        stmt.setString(2, date);
        stmt.setString(3, time);
        stmt.setString(4, classType);
        stmt.setString(5, bookedID);
        stmt.setString(6, trainer);
        stmt.setString(7, classStatus);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    admin = new memberClass();
                    admin.setClassID(rs.getInt("classID"));
                    admin.setDate(rs.getString("date"));
                    admin.setTime(rs.getString("time"));
                    admin.setClassType(rs.getString("Class_type"));
                    admin.setBookedID(rs.getString("bookID"));
                    admin.setTrainer(rs.getString("trainer"));
                    admin.setClassStatus(rs.getString("class_status"));
               
                    
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        return;
    }

    if (admin != null) {
        // Set the member object as an attribute to be accessed in the JSP
        request.setAttribute("admin", admin);
        System.out.println("Forwarding to editAdminSchedule.jsp"); // Debug statement
        RequestDispatcher dispatcher = request.getRequestDispatcher("editAdminSchedule.jsp");
        dispatcher.forward(request, response);
    } else {
        System.out.println("Error: admin not found with ID: " + classId); // Debug statement
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trainer not found!");
    }
}
    

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action != null && action.equals("updateMember")) {
        handleUpdateMember(request, response);
    }
    if (action != null && action.equals("deleteMember")) {
        handleDeleteMember(request, response);
    }
    if (action != null && action.equals("updateTrainer")){
        handleUpdateTrainer(request, response);
    }
    if (action != null && action.equals("deleteTrainer")){
        handleDeleteTrainer(request, response);
    }
    if (action != null && action.equals("updateAdminSchedule")){
        handleUpdateAdmin(request, response);
    }
    if (action != null && action.equals("deleteAdminSchedule")){
        handleDeleteAdmin(request, response);
    }
    
}

private void handleUpdateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String memberId = request.getParameter("memberId");
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String password = request.getParameter("password");

    if (memberId != null) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE user SET Name = ?, Email = ?, Phone = ?, Password = ? WHERE User_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, password);
                stmt.setInt(5, Integer.parseInt(memberId));
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("adminServlet?action=allmembers");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update member.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}

  private void handleDeleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String memberId = request.getParameter("memberId");
    System.out.println("Received memberId for deletion: " + memberId);

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "DELETE FROM user WHERE User_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(memberId));
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Redirecting to allmembers...");
                response.sendRedirect("adminServlet?action=allmembers");
            } else {
                System.out.println("No member found for deletion.");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Member not found for deletion!");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
  
  private void handleUpdateTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String trainerId = request.getParameter("trainerId");
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String phone = request.getParameter("phone");
    String password = request.getParameter("password");

    if (trainerId != null) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE user SET Name = ?, Email = ?, Phone = ?, Password = ? WHERE User_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, password);
                stmt.setInt(5, Integer.parseInt(trainerId));
                
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("adminServlet?action=alltrainers");
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update trainer.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }
}
  
private void handleDeleteTrainer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String trainerId = request.getParameter("trainerId");
    System.out.println("Received trainerId for deletion: " + trainerId);

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "DELETE FROM user WHERE User_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, Integer.parseInt(trainerId));
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Redirecting to alltrainers...");
                response.sendRedirect("adminServlet?action=alltrainers");
            } else {
                System.out.println("No trainer found for deletion.");
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Member not found for deletion!");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  
 private void handleUpdateAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // Retrieve parameters from the request
    String classId = request.getParameter("classId");
    String date = request.getParameter("date");
    String time = request.getParameter("time");
    String classType = request.getParameter("classType");
    String bookedID = request.getParameter("bookedID");
    String trainer = request.getParameter("trainer");
    String classStatus = request.getParameter("classStatus");

    System.out.println("Debug: Received parameters:");
    System.out.println("classId = " + classId);
    System.out.println("date = " + date);
    System.out.println("time = " + time);
    System.out.println("classType = " + classType);
    System.out.println("bookedID = " + bookedID);
    System.out.println("trainer = " + trainer);
    System.out.println("classStatus = " + classStatus);

    if (classId != null) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Debug: Database connection established.");
            
            // Start transaction
            conn.setAutoCommit(false);
            System.out.println("Debug: Transaction started.");

            try {
                // Step 1: Retrieve Trainer_id from the users table
                String getTrainerIdSQL = "SELECT User_id as Trainer_id FROM user WHERE Name = ?";
                int trainerId = 0;
                System.out.println("Debug: Preparing to retrieve Trainer_id for trainer: " + trainer);

                try (PreparedStatement stmt = conn.prepareStatement(getTrainerIdSQL)) {
                    stmt.setString(1, trainer);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            trainerId = rs.getInt("Trainer_id");
                            System.out.println("Debug: Retrieved Trainer_id: " + trainerId);
                        } else {
                            System.out.println("Error: Trainer not found with the given name: " + trainer);
                            throw new Exception("Trainer not found with the given name: " + trainer);
                        }
                    }
                }

                // Step 2: Update the classes table
                String updateClassesSQL = "UPDATE classes SET Date = ?, Time = ?, Class_type = ?, Status = ? WHERE Class_id = ?";
                int rowsUpdated;
                System.out.println("Debug: Preparing to update classes table for Class_id: " + classId);

                try (PreparedStatement stmt = conn.prepareStatement(updateClassesSQL)) {
                    stmt.setString(1, date);
                    stmt.setString(2, time);
                    stmt.setString(3, classType);
                    stmt.setString(4, classStatus);
                    stmt.setString(5, classId);
                    rowsUpdated = stmt.executeUpdate();
                    System.out.println("Debug: Classes table updated. Rows affected: " + rowsUpdated);

                    if (rowsUpdated == 0) {
                        System.out.println("Error: No class found with the given Class_id: " + classId);
                        throw new Exception("No class found with the given Class_id: " + classId);
                    }
                }

                // Step 3: Update the schedule table
                String updateScheduleSQL = "UPDATE schedule SET Booking_id = ? WHERE Class_id = ?";
                System.out.println("Debug: Preparing to update schedule table for Class_id: " + classId);

                try (PreparedStatement stmt = conn.prepareStatement(updateScheduleSQL)) {
                    stmt.setString(1, bookedID);
                    stmt.setString(2, classId);
                    int scheduleRowsUpdated = stmt.executeUpdate();
                    System.out.println("Debug: Schedule table updated. Rows affected: " + scheduleRowsUpdated);
                }

                // Commit transaction
                conn.commit();
                System.out.println("Debug: Transaction committed successfully.");

                // Redirect to the admin schedule page after successful update
                if (rowsUpdated > 0) {
                    System.out.println("Debug: Redirecting to admin schedule.");
                    response.sendRedirect("adminServlet?action=adminSchedule");
                }
            } catch (Exception e) {
                // Rollback transaction on error
                System.out.println("Error: Exception occurred, rolling back transaction.");
                conn.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                // Reset auto-commit to true
                conn.setAutoCommit(true);
                System.out.println("Debug: Auto-commit reset to true.");
            }
        } catch (Exception e) {
            // Handle exceptions (log or rethrow)
            System.out.println("Error: An exception occurred during database operations.");
            e.printStackTrace();
        }
    } else {
        // Handle case when classId is null
        System.out.println("Error: classId is null. Cannot proceed with the update.");
    }
}

  
  private void handleDeleteAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String classId = request.getParameter("classId");
//    String trainer = request.getParameter("trainer");
//    trainer = URLDecoder.decode(trainer, StandardCharsets.UTF_8.name());
    
//    System.out.println("Received classId for deletion: " + classId + ", trainer: " + trainer);

    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        // Start transaction
        conn.setAutoCommit(false);

        try {
            // Step 1: Update the user's role to 'none'
            String updateUserQuery = "DELETE FROM classes WHERE Class_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateUserQuery)) {
                stmt.setString(1, classId); // Trainer's name, passed from form
                int rowsAffected = stmt.executeUpdate();
                System.out.println("Rows affected in user table: " + rowsAffected);

                if (rowsAffected == 0) {
//                    System.out.println("No trainer found with the given name: " + trainer);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trainer not found!");
                    return;  // Stop processing if trainer not found
                }
            }

//            // Step 2: Delete the class from the classes table
//            String deleteClassQuery = "DELETE FROM classes WHERE Class_id = ?";
//            try (PreparedStatement stmt = conn.prepareStatement(deleteClassQuery)) {
//                stmt.setInt(1, Integer.parseInt(classId));  // Assuming classId is an integer
//                int rowsDeleted = stmt.executeUpdate();
//                System.out.println("Rows affected in classes table: " + rowsDeleted);
//
//                if (rowsDeleted == 0) {
//                    System.out.println("No class found with the given Class_id: " + classId);
//                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Class not found!");
//                    return;  // Stop processing if class not found
//                }
//            }

            // Commit transaction if both operations are successful
            conn.commit();
            System.out.println("Both operations succeeded. Redirecting...");
            response.sendRedirect("adminServlet?action=adminSchedule");

        } catch (SQLException e) {
            // Rollback transaction on error
            conn.rollback();
            System.out.println("Transaction failed, rolling back changes.");
            throw e;  // Re-throw exception after rollback
        }

    } catch (SQLException e) {
        // Handle exception (log or rethrow)
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred during the operation.");
    }
}

}
