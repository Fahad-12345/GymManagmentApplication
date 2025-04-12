
package models;
public class memberClass {
    private int userID;
    private String date;
    private String time;
    private String classType;
    private String bookID;
    private String trainer;
    private String classStatus;
    private String planList;
    private int classID;

    // Default constructor
    public memberClass() {}

    // Getters and Setters
    public int getuserID() {
        return userID;
    }

    public void setuserID(int userID) {
        this.userID = userID;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }
    public String getBookedID(){
        return bookID;
    }
    public void setBookedID(String bookID){
        this.bookID = bookID;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(String classStatus) {
        this.classStatus = classStatus;
    }
    public String getplanList() {
        return planList;
    }

    public void setplanList(String planList) {
        this.planList = planList;
    }
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }
}
