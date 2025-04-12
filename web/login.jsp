<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        /* Center the content of the page */
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-image: url('images/background.jpeg');
            background-size: cover; 
            background-position: center; 
            background-repeat: no-repeat; 
            background-attachment: fixed; 
            color: white;
            background-color: #f4f4f4;
        }

        /* Style for the form container */
        form {
            background-color: #fff; /* Light background for the form */
            padding: 20px;
            border-radius: 10px;
            width: 300px;
            text-align: center;
            color: black;
        }

        /* Style for the title text above the form */
        h1 {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }

        /* Style for the labels */
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
        }

        /* Style for the input fields */
        input[type="text"], input[type="password"] {
            width: 90%;
            padding: 10px;
            margin-bottom: 15px;
            border: 2px solid #ccc;
            border-radius: 5px;
            background-color: #f9f9f9;
            color: black;
        }

        /* Change the color of the placeholder text */
        input::placeholder {
            color: #888;
        }

        /* Style for the submit button */
        button {
            padding: 10px 20px;
            background-color: #000; /* Black button */
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        /* Hover effect for the button */
        button:hover {
            background-color: #333;
        }
    </style>
</head>
<body>
    <form action="/gymApp/login" method="POST">
        <h1>Motion GYM</h1> <!-- Title above the form -->
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" placeholder="Enter Username" required /><br/>
        
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Enter Password" required /><br/>
        
        <button type="submit">Login</button>
    </form>
</body>
</html>
