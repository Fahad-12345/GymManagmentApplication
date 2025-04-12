<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 0;
            padding: 50px;
            background-image: url('images/background.jpeg');
            background-size: cover; 
            background-position: center; 
            background-repeat: no-repeat; 
            background-attachment: fixed; 
            color: white;
            background-color: #f4f4f4;
        }

        h1 {
            color: #fff;
            margin-bottom: 30px;
        }

        .button-container {
            display: grid;
            grid-template-columns: 1fr;
            gap: 20px;
            max-width: 400px;
            width: 100%;
        }

        /* Unified button styles for both anchor and form buttons */
        .btn, .button-container form button[type="submit"] {
            padding: 10px 20px;
            background-color: #333;
            color: white;
            border: none;
            border-radius: 5px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
        }

        /* Hover effect for both button types */
        .btn:hover, .button-container form button[type="submit"]:hover {
            background-color: #0056b3;
        }

        /* Optional: Center buttons horizontally */
        .button-container {
            display: flex;
            flex-direction: column;
            gap: 20px;
            align-items: center;
        }
    </style>
</head>
<body>
    <h1>Welcome, Admin!</h1>
    <div class="button-container">
        <a href="/gymApp/NewUserServlet" class="btn">Create New User</a>
        <a href="adminServlet?action=allmembers" class="btn">View All Members</a>
        <a href="adminServlet?action=alltrainers" class="btn">View All Trainers</a>
        <a href="adminServlet?action=adminSchedule" class="btn">View Schedule</a>
        <a href="/gymApp/login" class="btn">Sign Out</a>
    </div>
</body>
</html>
