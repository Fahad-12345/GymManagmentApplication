
package models;

public class UserClass {
    
 private int userId;       // Corresponds to User_id in the table
    private String name;      // Name of the user
    private String role;      // Role of the user, e.g., 'trainer' or 'member'
    private String email;     // Email of the user
    private String phone;     // Phone number of the user
    private String password;  // Password of the user

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
