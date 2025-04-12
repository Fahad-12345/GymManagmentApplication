<%@ page import="models.UserClass" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Member</title>
</head>
<body>
    <h2>Edit Member</h2>
    
    <form action="adminServlet?action=updateMember" method="post">
        <input type="hidden" name="memberId" value="<%= request.getAttribute("member") != null ? ((UserClass) request.getAttribute("member")).getUserId() : "" %>">
        
        <label for="name">Name:</label>
        <input type="text" name="name" id="name" value="<%= request.getAttribute("member") != null ? ((UserClass) request.getAttribute("member")).getName() : "" %>" required><br><br>
        
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" value="<%= request.getAttribute("member") != null ? ((UserClass) request.getAttribute("member")).getEmail() : "" %>" required><br><br>
        
        <label for="phone">Phone:</label>
        <input type="text" name="phone" id="phone" value="<%= request.getAttribute("member") != null ? ((UserClass) request.getAttribute("member")).getPhone() : "" %>" required><br><br>
        
        <label for="password">Password:</label>
        <input type="password" name="password" id="password" value="<%= request.getAttribute("member") != null ? ((UserClass) request.getAttribute("member")).getPassword() : "" %>" required><br><br>
        
        <button type="submit">Update</button>
    </form>
</body>
</html>
