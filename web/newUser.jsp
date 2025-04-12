<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create New User</title>
        <script>
            // Check if success message is present
            window.onload = function() {
                const successMessage = document.querySelector(".message.success");
                if (successMessage) {
                    console.log("Success message detected. Redirecting in 3 seconds...");
                    setTimeout(function() {
                        window.location.href = "http://localhost:8080/gymApp/adminServlet";
                    }, 3000);
                } else {
                    console.log("No success message detected.");
                }

                const errorMessage = document.querySelector(".message.error");
                if (errorMessage) {
                    console.error("Error message detected: " + errorMessage.textContent);
                }
            };
        </script>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f7f7f7;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                background-image: url('images/background.jpeg');
            background-size: cover; 
            background-position: center; 
            background-repeat: no-repeat; 
            background-attachment: fixed; 
            color: white;
            background-color: #f4f4f4;
                
            }
            h1 {
                text-align: center;
                color: #fff;
                font-size: 28px;
                margin-bottom: 30px;
            }
            form {
                background: rgba(0, 0, 0, 0.1);
                width: 100%;
                max-width: 600px;
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                border: 1px solid #ddd;
            }
            label {
                display: block;
                font-size: 16px;
                font-weight: 600;
                color: #fff;
                margin-bottom: 8px;
            }
            input[type="text"],
            input[type="email"],
            input[type="password"],
            input[type="tel"] {
                width: 100%;
                padding: 12px;
                margin-bottom: 20px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 16px;
                background-color: #ddd;
                transition: border-color 0.3s ease;
            }
            input[type="text"]:focus,
            input[type="email"]:focus,
            input[type="password"]:focus,
            input[type="tel"]:focus {
                border-color: #007bff;
                outline: none;
            }
            .radio-group {
                margin-bottom: 20px;
                display: flex;
                flex-wrap: wrap;
                justify-content: flex-start;
            }
            .radio-group label {
                font-weight: 400;
                margin-right: 20px;
                display: inline-block;
                font-size: 16px;
            }
            .radio-group input[type="radio"] {
                margin-right: 5px;
            }
            button {
                width: 100%;
                padding: 12px;
                background-color: #333;
                color: #fff;
                border: none;
                border-radius: 4px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }
            button:hover {
                background-color: #0056b3;
            }
            button:active {
                background-color: #003f7f;
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
        <div>
            <h1>Create New User</h1>

            <!-- Display Success or Error Message -->
            <c:if test="${not empty successMessage}">
                <div class="message success">${successMessage}</div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message error">${errorMessage}</div>
            </c:if>
            
            <!-- Form -->
            <form action="NewUserServlet" method="POST" autocomplete="off" >
                <!-- Name -->
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" placeholder="Enter Full Name" autocomplete="off" required>

                <!-- Role -->
                <label>Role</label>
                <div class="radio-group">
                    <label><input type="radio" name="role" value="member" required> Member</label>
                    <label><input type="radio" name="role" value="trainer"> Trainer</label>
                    <label><input type="radio" name="role" value="admin"> Admin</label>
                </div>

                <!-- Email -->
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" placeholder="Enter Email Address" autocomplete="off" required>

                <!-- Phone -->
                <label for="phone">Phone Number</label>
                <input type="tel" id="phone" name="phone" placeholder="Enter Phone Number" autocomplete="off" required>

                <!-- Password -->
                <label for="password">Password</label>
                <input type="password" id="password" name="password" placeholder="Enter Password" autocomplete="new-password" required>

                <!-- Submit Button -->
                <button type="submit">Create User</button>
            </form>
        </div>
    </body>
</html>
