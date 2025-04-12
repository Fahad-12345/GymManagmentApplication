<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder" %>
<%@page import="java.util.List" %>
<%@page import="models.memberClass" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Schedule</title>
        <style>
             body {
                font-family: Arial, sans-serif;
                background-image: url('images/background.jpeg'); 
                background-size: cover; 
                background-position: center;
                text-align: center;
                background-repeat: no-repeat; /* Prevent tiling */
                background-attachment: fixed; /* Fix the background on scroll */
                color: white; /* Default text color for readability */
                margin: 0;
                padding: 0;
            }
            table {
                width: 90%;
                border-collapse: collapse;
                margin: 20px auto;
                background-color: #5d5c5d;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3); 
                border-radius: 8px; 
                overflow: hidden;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #007bff; /* Blue color for header */
                color: white;
                font-weight: bold;
            }
            .action-button {
                padding: 5px 10px;
                margin: 0 5px;
                border: none;
                color: white;
                cursor: pointer;
                text-decoration: none;
            }
            .edit-button {
                background-color: #4CAF50; /* Green */
            }
            .delete-button {
                background-color: #f44336; /* Red */
            }
             .home-button {
                background-color: #333; /* Light black background */
                color: white; /* Text color */
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-decoration: none;
                font-size: 18px;
                margin: 20px 0;
                display: inline-block;
            }
            .home-button:hover {
                background-color: rgba(0, 0, 0, 0.8); /* Darker shade on hover */
            }
        </style>
    </head>
    <body>
        <h1 style="text-align: center;">Schedule</h1>
        
        <table>
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Time</th>
                    <th>Class Type</th>
                    <th>Booked ID</th>
                    <th>Trainer</th>
                    <th>Class Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Assuming "schedules" is a list of memberClass objects passed as a request attribute
                    List<models.memberClass> schedules = (List<models.memberClass>) request.getAttribute("schedules");

                    // Ensure schedules is not null and not empty
                    if (schedules != null && !schedules.isEmpty()) {
                        // Iterate through the schedules list
                        for (models.memberClass schedule : schedules) {
                            // Check if the schedule object itself is not null before accessing its properties
                            if (schedule != null) {
                                // Perform null checks for each property of the schedule object
                                String date = schedule.getDate() != null ? schedule.getDate() : "";
                                String time = schedule.getTime() != null ? schedule.getTime() : "";
                                String classType = schedule.getClassType() != null ? schedule.getClassType() : "";
                                String bookedID = schedule.getBookedID() != null ? schedule.getBookedID() : "";
                                String trainer = schedule.getTrainer() != null ? schedule.getTrainer() : "";
                                String classStatus = schedule.getClassStatus() != null ? schedule.getClassStatus() : "";

                                // Output the values inside a table row
                %>
                <tr>
                    <td><%= schedule.getDate() %></td>
                    <td><%= schedule.getTime() %></td>
                    <td><%= schedule.getClassType() %></td>
                    <td><%= schedule.getBookedID() %></td>
                    <td><%= schedule.getTrainer() %></td>
                    <td><%= schedule.getClassStatus() %></td>
                    <td>
                       <a href="adminServlet?action=editAdminSchedule&classId=<%= schedule.getClassID() %>&date=<%= URLEncoder.encode(schedule.getDate(), "UTF-8") %>&time=<%= URLEncoder.encode(schedule.getTime(), "UTF-8") %>&classType=<%= URLEncoder.encode(schedule.getClassType(), "UTF-8") %>&bookedID=<%= URLEncoder.encode(String.valueOf(schedule.getBookedID()), "UTF-8") %>&trainer=<%= URLEncoder.encode(schedule.getTrainer(), "UTF-8") %>&classStatus=<%= URLEncoder.encode(schedule.getClassStatus(), "UTF-8") %>"
                          class="btn">Edit</a>
                        <form action="adminServlet?action=deleteAdminSchedule" method="POST" style="display:inline;">
                            <input type="hidden" name="classId" value="<%= schedule.getClassID() %>" />
                            <input type="hidden" name="trainer" value="<%= URLEncoder.encode(schedule.getTrainer(), "UTF-8") %>" />
                            <button type="submit" class="btn">Delete</button>
                        </form>
                    </td>
                </tr>
                <% 
                            }
                        }
                    } else { // Start the 'else' block
                %>
                <tr>
                    <td colspan="7">No schedules found.</td>
                </tr>
                <% 
                    } // End of the 'else' block
                %>
            </tbody>
        </table>
             <a href="http://localhost:8080/gymApp/adminServlet" class="home-button">Home Page</a>
    </body>
</html>
