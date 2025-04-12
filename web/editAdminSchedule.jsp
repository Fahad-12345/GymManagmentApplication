<%@ page import="models.memberClass" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Admin Schedule</title>
</head>
<body>
    <h2>Edit Admin Schedule</h2>
    
    <form action="adminServlet?action=updateAdminSchedule" method="post">
        <!-- Hidden field to store class ID -->
        <input type="hidden" name="classId" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getClassID() : "" %>">
        
        <label for="date">Date:</label>
        <input type="date" name="date" id="date" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getDate() : "" %>" required><br><br>
        
        <label for="time">Time:</label>
        <input type="text" name="time" id="time" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getTime() : "" %>" required><br><br>
        
        <label for="classType">Class Type:</label>
        <input type="text" name="classType" id="classType" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getClassType() : "" %>" required><br><br>
        
        <label for="bookedId">Booked ID:</label>
        <input type="text" name="bookedID" id="bookedID" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getBookedID() : "" %>" required><br><br>
        
        <label for="trainerName">Trainer:</label>
<input type="text" name="trainerNameDisplay" id="trainerName" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getTrainer() : "" %>" disabled>

<input type="hidden" name="trainer" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getTrainer() : "" %>">
<br><br>
        <label for="classStatus">Class Status:</label>
        <input type="text" name="classStatus" id="classStatus" value="<%= request.getAttribute("admin") != null ? ((memberClass) request.getAttribute("admin")).getClassStatus() : "" %>" required><br><br>
        
        <button type="submit">Update</button>
    </form>
</body>
</html>
