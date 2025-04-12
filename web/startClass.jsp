<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Start Class</title>
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
            .container {
                width: 80%;
                margin: 0 auto;
            }
            .class-type {
                font-size: 2em;
                font-weight: bold;
                margin-top: 50px;
                color: #fff;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
            }
            table, th, td {
                border: 1px solid black;
            }
            th, td {
                padding: 8px;
                text-align: center;
            }
            /* Style for the first row (header) */
            thead tr {
                background-color: #4CAF50; /* Blue background */
                color: white;
            }
            .button-column {
                display: flex;
                justify-content: space-evenly;
                padding: 5px;
            }
            .button-column button {
                padding: 5px 10px;
                cursor: pointer;
                border: none;
                border-radius: 4px;
                font-size: 14px;
            }
            .present-btn {
                background-color: #4CAF50;
                color: white;
            }
            .absent-btn {
                background-color: #f44336;
                color: white;
            }
            .button-column button:hover {
                opacity: 0.8;
            }
            .success-message {
                color: green;
                font-weight: bold;
                margin-top: 20px;
            }
        </style>
    </head>
    <body>

        <div class="container">
            <% 
                // Display the attendance success or failure message if available
                String attendanceMessage = (String) request.getAttribute("attendanceMessage");
                if (attendanceMessage != null) {
            %>
                <div class="success-message"><%= attendanceMessage %></div>
                <!-- JavaScript for automatic redirection after 3 seconds -->
<!--        <script>
            setTimeout(function() {
                window.location.href = "http://localhost:8080/gymApp/trainerServlet";
            }, 3000); // Redirect after 3 seconds
        </script>-->
            <% 
                }

                // Retrieve the classType from the request attributes
                String classType = (String) request.getAttribute("classType");

                if (classType != null) {
            %>
                    <!-- Display classType in the middle of the page -->
                    <div class="class-type"><%= classType %></div>
            <% 
                } else {
            %>
                    <!--<p>No class type selected.</p>-->
            <% 
                }

                // Retrieve the member information from request attributes
                Integer memberId = (Integer) request.getAttribute("memberId");
                String memberName = (String) request.getAttribute("memberName");
            %>

            <!-- Table displaying Member_id, Member_name and buttons -->
            <table>
                <thead>
                    <tr>
                        <th>Member ID</th>
                        <th>Member Name</th>
                        <th>Present</th>
                        <th>Absent</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%= memberId != null ? memberId : " " %></td>
                        <td><%= memberName != null ? memberName : " " %></td>
                        <td>
                            <!-- Present button -->
                           <form action="startClassServlet" method="post" style="display:inline;">
                                <input type="hidden" name="memberId" value="<%= memberId %>">
                                <input type="hidden" name="status" value="Present">
                                <input type="hidden" name="classType" value="<%= classType %>">
                                <button type="submit" class="present-btn">Present</button>
                            </form>
                        </td>
                        <td>
                            <!-- Absent button -->
                            <form action="startClassServlet" method="post" style="display:inline;">
                                <input type="hidden" name="memberId" value="<%= memberId %>">
                                <input type="hidden" name="status" value="Absent">
                                <input type="hidden" name="action" value="start">
                                <input type="hidden" name="classType" value="<%= classType %>">
                                <button type="submit" class="absent-btn">Absent</button>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
           <div style="margin-top: 20px;">
    <form action="endClassServlet" method="post">
        <input type="hidden" name="memberId" value="<%= memberId %>">
        <input type="hidden" name="action" value="end">
        <input type="hidden" name="classType" value="<%= classType %>">
        <button type="submit" style="
            padding: 10px 20px;
            background-color:#f4d03f ;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
        ">End Class</button>
    </form>
</div>
        </div>

    </body>
</html>
