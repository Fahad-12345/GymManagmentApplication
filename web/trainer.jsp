<%@ page import="java.util.List" %>
<%@ page import="models.memberClass" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trainer Dashboard</title>
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
    color: #fff; /* White for better contrast */
    text-shadow: 1px 1px 5px rgba(0, 0, 0, 0.7); /* Subtle shadow for readability */
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
    width: 100%;
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

.remove-button, .start-button {
    border: none;
    padding: 8px 12px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
}

.remove-button {
    background-color: #dc3545;
    color: white;
}

.remove-button:hover {
    background-color: #c82333;
}

.start-button {
    background-color: #28a745;
    color: white;
}

.start-button:hover {
    background-color: #218838;
}
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
        // Loop through all the rows and check if the status is cancelled in localStorage
        $('tr').each(function() {
            let bookedID = $(this).attr('id');
            if (localStorage.getItem(bookedID) === 'cancelled') {
                // Apply the cancellation styles
                $(this).css("text-decoration", "line-through");
                $(this).css("background-color", "lightcoral");
                $(this).find('.cancel-cell').html("Cancelled");
                let startClassButton = $(this).find('button[type="submit"]');
                if (startClassButton.length) {
                    startClassButton.prop('disabled', true).text('Class Cancelled');
                }
            }
        });
    });
        function cancelClass(bookedID, classType) {
            $.ajax({
                url: "/gymApp/trainerServlet",  // Update URL to match your Servlet endpoint
                method: "POST",
                data: {
                    bookedID: bookedID,
                    classType: classType
                },
                success: function(response) {
                    // On successful cancellation, update the row's appearance
                    let row = document.getElementById(bookedID); // Use bookedID as row ID
                    localStorage.setItem(bookedID, 'cancelled');
                    row.style.textDecoration = "line-through";
                    row.style.backgroundColor = "lightcoral";
                    row.querySelector('.cancel-cell').innerHTML = "Cancelled"; // Change Cancel button to "Cancelled"
                    // Disable the Start Class button
            let startClassButton = row.querySelector('button[type="submit"]');
            if (startClassButton) {
                startClassButton.disabled = true; // Disable the button
                startClassButton.innerHTML = "Class Cancelled"; // Change button text to indicate cancellation
            }
                },
                error: function(xhr, status, error) {
                    console.error("Error: " + error);
                }
            });
        }
    </script>
</head>
<body>
   <h1>Welcome trainer!!</h1> 
    <table>
        <tr>
            <td colspan="7" class="plan-heading">This is your schedule</td>
        </tr>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>Class</th>
            <th>Class Status</th>
            <th>Booked ID</th>
            <th>Start Class</th>
            <th>Cancel Class</th>
        </tr>
        <tbody>
            <%
                List<memberClass> classPlans = (List<memberClass>) request.getAttribute("classPlans");
                if (classPlans != null && !classPlans.isEmpty()) {
                    for (memberClass plan : classPlans) {
                        boolean isCancelled = "cancelled".equalsIgnoreCase(plan.getClassStatus());
            %>
                <tr id="<%= plan.getBookedID() %>" style="<%= isCancelled ? "text-decoration: line-through; background-color: lightcoral;" : "" %>">
                    <td><%= plan.getDate() %></td>
                    <td><%= plan.getTime() %></td>
                    <td><%= plan.getClassType() %></td>
                    <td><%= plan.getClassStatus() %></td>
                    <td><%= plan.getBookedID() %></td>
                    <td>
                        <% if (!isCancelled) { %>
                        <form id="start-class-form" action="/gymApp/startClassServlet" method="get">
                            <input type="hidden" name="bookedID" value="<%= plan.getBookedID() %>">
                            <input type="hidden" name="classType" value="<%= plan.getClassType() %>">
                            <button type="submit" id="start-class-button">Start Class</button>
                        </form>
                        <% } else { %>
                        <span>Cancelled</span>
                        <% } %>
                    </td>
                    <td class="cancel-cell">
                        <% if (!isCancelled) { %>
                        <button type="button" onclick="cancelClass('<%= plan.getBookedID() %>', '<%= plan.getClassType() %>')" class="remove-button">Cancel Class</button>
                        <% } else { %>
                        <span>Cancelled</span>
                        <% } %>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr><td colspan="7">No data available</td></tr>
            <%
                }
            %>
        </tbody>
    </table>
    
    <div class="button-container">
        <a href="/gymApp/createClassServlet">Create Class</a>
        <a href="/gymApp/login">Sign Out</a>
    </div>
</body>
</html>