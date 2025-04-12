<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="models.UserClass" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trainers List</title>
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
                width: 80%;
                border-collapse: collapse;
                margin: 20px auto;
                background-color: #5d5c5d; /* Semi-transparent white background */
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3); /* Add shadow for a polished look */
                border-radius: 8px; /* Rounded corners */
                overflow: hidden;
                
            }
            th, td {
                border: 1px solid #ddd;
                padding: 10px;
                text-align: center;
            }
            th {
                background-color: #007bff; /* Blue color */
                color: white; /* Text color inside the header */
                font-weight: bold;
            }
/*            tr:nth-child(even) {
                background-color: #f9f9f9;
            }*/
/*            tr:hover {
                background-color: #f1f1f1;
            }*/
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
        <h1 style="text-align: center;">Trainers List</h1>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Password</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    // Correctly cast the 'trainers' attribute to List<UserClass>
                    List<UserClass> trainers = (List<UserClass>) request.getAttribute("trainers");
                    if (trainers != null && !trainers.isEmpty()) {
                        for (UserClass trainer : trainers) {
                %>
                <tr>
                    <td><%= trainer.getUserId() %></td>
                    <td><%= trainer.getName() %></td>
                    <td><%= trainer.getEmail() %></td>
                    <td><%= trainer.getPhone() %></td>
                    <td><%= trainer.getPassword() %></td>
                    <td>
                        <a href="adminServlet?action=editTrainers&trainerId=<%= trainer.getUserId() %>" class="btn">Edit</a>
    <form action="adminServlet?action=deleteTrainer" method="POST" style="display:inline;">
    <input type="hidden" name="trainerId" value="<%= trainer.getUserId() %>" />
    <button type="submit" class="btn">Delete</button>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6">No trainers found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
            <a href="http://localhost:8080/gymApp/adminServlet" class="home-button">Home Page</a>
    </body>
</html>
