package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author fahad
 */
@WebServlet(name = "NewUserServlet", urlPatterns = {"/NewUserServlet"})
public class NewUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("NewUserServlet started GET!");
        // Forward to the JSP to show the form
        request.getRequestDispatcher("newUser.jsp").forward(request, response);
    }
    
    

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("NewUserServlet started POST!");

        // Retrieve form data from the request
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        System.out.println("Form Data Received - Name: " + name + ", Role: " + role + 
                           ", Email: " + email + ", Phone: " + phone);

        // Database connection details
        String dbURL = "jdbc:mysql://localhost:3306/gymmanagement?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "12345";

        try (Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            System.out.println("Database connection successful!");

            // SQL statement to insert new user into the user table
            String sql = "INSERT INTO user (Name, Role, Email, Phone, Password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, role);
                stmt.setString(3, email);
                stmt.setString(4, phone);
                stmt.setString(5, password);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("User inserted successfully in database!");
                    request.setAttribute("successMessage", "User created successfully!");
                } else {
                    System.out.println("No rows inserted.");
                    request.setAttribute("errorMessage", "Failed to create user.");
                }

                // Forward to newUser.jsp
                request.getRequestDispatcher("newUser.jsp").forward(request, response);

            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
                e.printStackTrace();
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                request.getRequestDispatcher("newUser.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Connection error: " + e.getMessage());
            request.getRequestDispatcher("newUser.jsp").forward(request, response);
        }
    }
}
