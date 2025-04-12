# 🏋️‍♂️ Gym Management System (Java - NetBeans)

A fully functional desktop-based **Gym Management Application** built using Java (Swing), JDBC, and MySQL, deployed on a GlassFish server. The system provides role-based dashboards for Admins, Trainers, and Members to manage classes, attendance, and bookings.

---

## 💻 Tech Stack

- Java (NetBeans IDE)
- JDBC (MySQL Connector)
- MySQL Database
- GlassFish Server
- Java Swing (for GUI)
- MVC Architecture

---

## 🧑‍💼 Roles & Features

### 🔐 Admin Dashboard
- View, edit, delete all members & trainers
- Assign roles (admin/trainer/member)
- Monitor bookings & attendance

### 🧘 Member Dashboard
- Book a class with a trainer
- View attendance history
- See upcoming sessions

### 🏋️ Trainer Dashboard
- Mark attendance
- Start and end sessions
- View assigned members

---

## ⚙️ Setup Instructions

### ✅ Prerequisites

- NetBeans IDE
- GlassFish Server (installed in NetBeans)
- MySQL Server (running)
- MySQL Connector/J (added to project libraries)

---

### 🚀 Getting Started
Clone the Repository
git clone https://github.com/your-username/gym-management-system.git
Open the Project in NetBeans

Launch NetBeans IDE.

Click on File > Open Project.

Browse to the cloned repository and open it.

Set Up the MySQL Database

Open MySQL Workbench or phpMyAdmin.

Import the SQL file located at:
/database/gym_management.sql
This will create all required tables and insert sample data.

Configure the JDBC Database Connection

Open the class (e.g., DBConnection.java) that handles the database connection.

Update the credentials according to your local MySQL setup:
String url = "jdbc:mysql://localhost:3306/gym_management";
String user = "your_mysql_username";
String password = "your_mysql_password";
Add the MySQL JDBC Driver

Right-click on your project in NetBeans > Properties.

Go to the Libraries section.

Click Add JAR/Folder and select the mysql-connector-java-X.X.X.jar file.

(Download from: https://dev.mysql.com/downloads/connector/j/)

Deploy on GlassFish Server

Ensure GlassFish is installed and running within NetBeans.

Right-click the project > Run or Deploy.

This will compile and launch your application on GlassFish.

Log In and Explore

Use default credentials (if provided in your SQL file) to log in as:
Admin
Trainer
Member
Explore dashboards and test the full functionality.
