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
            text-align: center;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
            color: white;
            margin: 0;
            padding: 0;
        }

        h1 {
            color: #fff;
            text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.7);
        }

        h2 {
            color: #f4d03f;
            text-align: left;
            margin-left: 10%;
            text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.7);
        }

        table {
            margin: 20px auto;
            border-collapse: collapse;
            width: 80%;
            max-width: 800px;
            background-color: rgba(255, 255, 255, 0.9);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.5);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            border: 1px solid rgba(255, 255, 255, 0.5);
            padding: 15px;
            text-align: left;
            font-size: 16px;
            color: #333;
        }

        th {
            background-color: rgba(0, 0, 0, 0.8);
            color: #fff;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: rgba(255, 255, 255, 0.8);
        }

        tr:nth-child(odd) {
            background-color: rgba(0, 0, 0, 0.1);
        }

        tr:hover {
            background-color: rgba(255, 255, 255, 0.6);
            color: #000;
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

        /* Default row style */
        .normal-row {
            background-color: rgba(0, 0, 0, 0.1);
        }

        /* Highlighted row style for 'added' plan */
        .highlight-row {
            background-color: rgba(0, 255, 0, 0.3); /* Lighter green for better visibility */
            color: #000; /* Ensure text is readable */
        }
    </style>
</head>
<body>
    <h1>Schedule</h1>

    <% 
        String message = (String) request.getAttribute("message");
        if (message != null) {
    %>
        <div style="color: green; font-size: 18px;">
            <%= message %>
        </div>
    <% 
        }
    %>

    <table>
        <tr>
            <td colspan="5" class="plan-heading">Plan</td>
        </tr>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>Class Type</th>
            <th>Trainer</th>
            <th>Action</th>
        </tr>

        <tbody>
            <%
                List<memberClass> classPlans = (List<memberClass>) request.getAttribute("classPlans");
                if (classPlans != null) {
                    for (memberClass plan : classPlans) {
                        // Check if the planList is 'added'
                        String rowClass = "normal-row"; // Default row class
                        if ("added".equals(plan.getplanList())) {
                            rowClass = "highlight-row"; // Highlighted row class for 'added' plan
                        }
            %>
                <tr class="<%= rowClass %>">
                    <td><%= plan.getDate() %></td>
                    <td><%= plan.getTime() %></td>
                    <td><%= plan.getClassType() %></td>
                    <td><%= plan.getTrainer() %></td>
                    <td>
                        <form action="AddClassServlet" method="post">
                            <input type="hidden" name="date" value="<%= plan.getDate() %>">
                            <input type="hidden" name="time" value="<%= plan.getTime() %>">
                            <input type="hidden" name="classType" value="<%= plan.getClassType() %>">
                            <input type="hidden" name="trainer" value="<%= plan.getTrainer() %>">
                             <% 
            // Check if the plan has been added and show the appropriate button text
            String buttonText = "Add";  // Default button text
            if ("added".equals(plan.getplanList())) {
                buttonText = "Already Added";
            }
        %>
        <button type="submit"><%= buttonText %></button>
                        </form>
                    </td> 
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="5">No class plans available.</td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <div class="button-container">
        <a href="/gymApp/member">Home Page</a>
    </div>
</body>
</html>
