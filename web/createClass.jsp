<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create Class</title>
    <script>
    // Check if success message is present
    window.onload = function() {
        const successMessage = document.querySelector(".message.success");
        if (successMessage) {
            console.log("Success message detected. Redirecting in 3 seconds...");

            // Redirect after 3 seconds
            setTimeout(function() {
                window.location.href = "http://localhost:8080/gymApp/trainerServlet";
            }, 3000);
        } else {
            console.log("No success message detected.");
        }
    };
</script>
    <style>
        body {
             font-family: Arial, sans-serif;
    background-image: url('images/background.jpeg');
    background-size: cover; 
    background-position: center; 
    text-align: center;
    background-repeat: no-repeat; 
    background-attachment: fixed; 
    color: white; /* Default text color for readability */
    margin: 0;
    padding: 50px;
        }

        form {
            max-width: 500px;
            margin: 0 auto;
        }

        label {
            display: block;
            margin: 10px 0 5px;
        }

        input, select, button {
            width: 90%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
           background-color: #333;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        .message {
            max-width: 500px;
            margin: 0 auto 20px;
            padding: 10px;
            border-radius: 5px;
        }

        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <h1>Create a New Class</h1>
    
    <!-- Display success or error message -->
    <% 
        String successMessage = (String) request.getAttribute("successMessage");
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (successMessage != null) { 
    %>
        <div class="message success"><%= successMessage %></div>
    <% 
        } else if (errorMessage != null) { 
    %>
        <div class="message error"><%= errorMessage %></div>
    <% } %>

    <!-- Form for creating a new class -->
    <form action="createClassServlet" method="POST">
        <label for="date">Date:</label>
        <input type="date" id="date" name="date" required>

        <label for="time">Time:</label>
        <input type="time" id="time" name="time" required>

        <label for="classType">Class Type:</label>
        <select id="classType" name="classType" required>
            <% 
                List<String> classTypes = (List<String>) request.getAttribute("classTypes");
                if (classTypes != null) {
                    for (String type : classTypes) { 
            %>
                <option value="<%= type %>"><%= type %></option>
            <% } } %>
        </select>

        <label for="trainer">Trainer:</label>
        <select id="trainer" name="trainer" required>
            <% 
                List<String> trainerNames = (List<String>) request.getAttribute("trainerNames");
                if (trainerNames != null) {
                    for (String trainer : trainerNames) { 
            %>
                <option value="<%= trainer %>"><%= trainer %></option>
            <% } } %>
        </select>

        <button type="submit">Create Class</button>
    </form>
</body>
</html>
