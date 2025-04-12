<%@ page import="java.util.List" %>
<%@ page import="models.memberClass" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Member Dashboard</title>
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

h1 {
    color: #fff; 
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.7); 
}

h2 {
    color: #f4d03f; /* Bright color for visibility */
    text-align: left;
    margin-left: 10%;
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.7);
}

table {
    margin: 20px auto;
    border-collapse: collapse;
    width: 80%;
    max-width: 800px;
    background-color: rgba(255, 255, 255, 0.9); /* Semi-transparent white */
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5); /* Stronger shadow for better distinction */
    border-radius: 8px; /* Smooth corners */
    overflow: hidden;
}

th, td {
    border: 1px solid rgba(255, 255, 255, 0.5); /* Softer white borders for blending */
    padding: 15px; /* Slightly increased padding */
    text-align: left;
    font-size: 16px; /* Adjusted font size for clarity */
    color: #333; /* Dark text for contrast inside the table */
}

th {
    background-color: rgba(0, 0, 0, 0.8); /* Dark semi-transparent for headers */
    color: #fff; /* White text for contrast */
    font-weight: bold;
}

tr:nth-child(even) {
    background-color: rgba(255, 255, 255, 0.8); /* Light semi-transparent row */
}

tr:nth-child(odd) {
    background-color: rgba(0, 0, 0, 0.1); /* Subtle dark row background */
}

tr:hover {
    background-color: rgba(255, 255, 255, 0.6); /* Highlighted row on hover */
    color: #000; /* Change text color on hover for readability */
}

        .plan-heading {
            text-align: left;
            font-weight: bold;
            font-size: 1.2rem;
            background-color: #333;
            color: white;
            padding: 10px;
        }
        .button-container {
            margin-top: 20px;
            display: flex;
            justify-content: center;
            gap: 15px;
        }

        .button-container a {
            text-decoration: none;
            background-color: #333;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s ease;
        }

        .button-container a:hover {
            background-color: #0056b3;
        }
         .remove-button {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .remove-button:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <h1>Welcome member!!</h1>
    
    
    
    <table>
        <tr>
            <td colspan="6" class="plan-heading">Plan</td>
        </tr>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>Class Type</th>
            <th>Trainer</th>
            <th>Class Status</th>
            <th>Action</th>
        </tr>
       <tbody>
            <%
                List<memberClass> classPlans = (List<memberClass>) request.getAttribute("classPlans"); // Updated attribute name
                if (classPlans != null) {
                    for (memberClass plan : classPlans) {
            %>
                <tr>
                    <td><%= plan.getDate() %></td>
                    <td><%= plan.getTime() %></td>
                    <td><%= plan.getClassType() %></td>
                    <td><%= plan.getTrainer() %></td>
                    <td><%= plan.getClassStatus() %></td>
                    <td>
                        <form action="member" method="post">
                        <input type="hidden" name="classID" value="<%= plan.getClassID() %>">
                        <button type="submit" class="remove-button">Remove</button>
                    </form>
                    </td> 
                </tr>
            <% 
                    }
                } 
else {
            %>
                <!--<tr><td colspan="6">No data available</td></tr>-->
            <% } %>
        </tbody>
    </table>
    
    <div class="button-container">
        <a href="/gymApp/AddClassServlet">Add Class</a>
        <a href="/gymApp/attendanceHistoryServlet">Attendance History</a>
        <a href="/gymApp/login">Sign Out</a>
    </div>
</body>
</html>
