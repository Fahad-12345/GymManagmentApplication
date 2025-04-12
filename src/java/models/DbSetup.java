package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbSetup {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String dbName = "gymmanagement";
        String dbUser = "root"; // Replace with your username
        String dbPassword = "12345"; // Replace with your password

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             Statement statement = connection.createStatement()) {

            // Check if Database Exists
            ResultSet dbCheck = statement.executeQuery("SHOW DATABASES LIKE '" + dbName + "'");
            if (dbCheck.next()) {
                System.out.println("Database '" + dbName + "' already exists.");
            } else {
                // Create Database if not exists
                statement.executeUpdate("CREATE DATABASE " + dbName);
                System.out.println("Database '" + dbName + "' created.");
            }

            // Use Database
            statement.executeUpdate("USE " + dbName);

            // Check if User Table Exists
            ResultSet userTableCheck = statement.executeQuery("SHOW TABLES LIKE 'User'");
            if (userTableCheck.next()) {
                System.out.println("User table already exists.");
            } else {
                // Create User Table
                statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS User (
                        User_id INT AUTO_INCREMENT PRIMARY KEY,
                        Name VARCHAR(100) NOT NULL,
                        Role ENUM('member', 'admin', 'trainer','none') NOT NULL,
                        Email VARCHAR(150) UNIQUE NOT NULL,
                        Phone VARCHAR(15),
                        Password VARCHAR(255) NOT NULL
                    );
                """);
                System.out.println("User table created.");
            }

            // Check if Classes Table Exists
            ResultSet classesTableCheck = statement.executeQuery("SHOW TABLES LIKE 'Classes'");
            if (classesTableCheck.next()) {
                System.out.println("Classes table already exists.");
            } else {
                // Create Classes Table
                statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Classes (
                        Class_id INT AUTO_INCREMENT PRIMARY KEY,
                        Date DATE NOT NULL,
                        Time TIME NOT NULL,
                        Class_type VARCHAR(100) NOT NULL,
                        Trainer_id INT NOT NULL,
                        Status ENUM('started', 'pending', 'completed','cancelled') DEFAULT 'pending',
                        plan_list ENUM('added','not-added') DEFAULT 'not-added',
                        FOREIGN KEY (Trainer_id) REFERENCES User(User_id)
                        ON DELETE CASCADE 
                        ON UPDATE CASCADE
                    );
                """);
                System.out.println("Classes table created.");
            }

            // Check if Attendance Table Exists
           ResultSet attendanceTableCheck = statement.executeQuery("SHOW TABLES LIKE 'Attendance'");
if (attendanceTableCheck.next()) {
    System.out.println("Attendance table already exists.");
} else {
    // Create Attendance Table
    statement.executeUpdate("""
        CREATE TABLE IF NOT EXISTS Attendance (
            Attendance_id INT AUTO_INCREMENT PRIMARY KEY,
            Class_id INT NOT NULL,
            Member_id INT NOT NULL,
            Status ENUM('present', 'absent') DEFAULT 'absent',
            FOREIGN KEY (Class_id) REFERENCES Classes(Class_id) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (Member_id) REFERENCES User(User_id) ON DELETE CASCADE ON UPDATE CASCADE
        );
    """);
    System.out.println("Attendance table created.");
}

// Check if Schedule Table Exists
ResultSet scheduleTableCheck = statement.executeQuery("SHOW TABLES LIKE 'Schedule'");
if (scheduleTableCheck.next()) {
    System.out.println("Schedule table already exists.");
} else {
    // Create Schedule Table
    statement.executeUpdate("""
        CREATE TABLE IF NOT EXISTS Schedule (
            Schedule_id INT AUTO_INCREMENT PRIMARY KEY,
            Class_id INT NOT NULL,
            Member_id INT NOT NULL,
            Booking_id INT NOT NULL,
            FOREIGN KEY (Class_id) REFERENCES Classes(Class_id) ON DELETE CASCADE ON UPDATE CASCADE,
            FOREIGN KEY (Member_id) REFERENCES User(User_id) ON DELETE CASCADE ON UPDATE CASCADE
        );
    """);
    System.out.println("Schedule table created.");
}


            // Check and Insert Data into User Table
            ResultSet userCheck = statement.executeQuery("SELECT COUNT(*) FROM User");
            userCheck.next();
            if (userCheck.getInt(1) == 0) {
                statement.executeUpdate("""
                    INSERT INTO User (Name, Role, Email, Phone, Password)
                    VALUES 
                    ('John Doe', 'member', 'johndoe@gmail.com', '1234567890', 'password123'),
                    ('Jane Smith', 'admin', 'janesmith@domain.com', '9876543210', 'securepass'),
                    ('Mike Johnson', 'trainer', 'mikej@fitness.com', '4561237890', 'trainerpass'),
                    ('Sarah Williams', 'member', 'sarahw@domain.com', '3216549870', 'password456'),
                    ('David Brown', 'trainer', 'davidb@trainers.com', '7891234560', 'trainhard'),
                    ('Emily Davis', 'member', 'emilyd@domain.com', '6549873210', 'mypassword'),
                    ('Paul Wilson', 'trainer', 'paulw@fitness.com', '7418529630', 'fitpass'),
                    ('Anna Garcia', 'admin', 'annag@domain.com', '9638527410', 'adminpass'),
                    ('Peter Martinez', 'member', 'peterm@domain.com', '8529637410', 'pass123'),
                    ('Laura Lee', 'member', 'laural@domain.com', '1472583690', 'lauralpass');
                """);
                System.out.println("Data inserted into User table.");
            } else {
                System.out.println("User table already contains data.");
            }

            // Check and Insert Data into Classes Table
            ResultSet classesCheck = statement.executeQuery("SELECT COUNT(*) FROM Classes");
            classesCheck.next();
            if (classesCheck.getInt(1) == 0) {
                statement.executeUpdate("""
                    INSERT INTO Classes (Date, Time, Class_type, Trainer_id )
                    VALUES 
                    (CURRENT_DATE, CURRENT_TIME, 'Yoga', 3 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Pilates', 5 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Cardio', 7 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Strength', 3 ),
                    (CURRENT_DATE, CURRENT_TIME, 'HIIT', 5 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Dance Fitness', 7 ),
                    (CURRENT_DATE, CURRENT_TIME, 'CrossFit', 3 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Zumba', 5 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Spin Class', 7 ),
                    (CURRENT_DATE, CURRENT_TIME, 'Barre', 3 );
                """);
                System.out.println("Data inserted into Classes table.");
            } else {
                System.out.println("Classes table already contains data.");
            }

            // Insert data into Attendance table only if it's empty
            ResultSet AttendanceCheck = statement.executeQuery("SELECT COUNT(*) AS count FROM Attendance");
            AttendanceCheck.next();
            if (AttendanceCheck.getInt("count") == 0) {
                statement.executeUpdate("""
                    INSERT INTO Attendance (Class_id, Member_id, Status)
                    VALUES 
                    (1, 1, 'present'),
                    (1, 4, 'absent'),
                    (2, 6, 'present'),
                    (3, 9, 'present'),
                    (4, 10, 'absent'),
                    (5, 1, 'present'),
                    (6, 4, 'present'),
                    (7, 6, 'absent'),
                    (8, 9, 'present'),
                    (9, 10, 'present');
                """);
                System.out.println("Data inserted into Attendance table.");
            }else {
                System.out.println("Attendance table already contains data.");
            }

            // Insert data into Schedule table only if it's empty
            ResultSet ScheduleCheck = statement.executeQuery("SELECT COUNT(*) AS count FROM Schedule");
            ScheduleCheck.next();
            if (ScheduleCheck.getInt("count") == 0) {
                statement.executeUpdate("""
                    INSERT INTO Schedule (Class_id, Member_id, Booking_id)
                    VALUES 
                    (1, 1, 1001),
                    (2, 4, 1002),
                    (3, 6, 1003),
                    (4, 9, 1004),
                    (5, 10, 1005),
                    (6, 1, 1006),
                    (7, 4, 1007),
                    (8, 6, 1008),
                    (9, 9, 1009),
                    (10, 10, 1010);
                """);
                System.out.println("Data inserted into Schedule table.");
            }else {
                System.out.println("Schedule table already contains data.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
