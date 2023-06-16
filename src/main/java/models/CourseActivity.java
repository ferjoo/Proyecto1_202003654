/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author ferjo
 */
public class CourseActivity {
    private int courseCode;
    private String activityName;
    private String activityDescription;
    private double activityValue;

    public CourseActivity(int courseCode, String activityName, String activityDescription, double activityValue) {
        this.courseCode = courseCode;
        this.activityName = activityName;
        this.activityDescription = activityDescription;
        this.activityValue = activityValue;
    }

    // Getters and setters

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public double getActivityValue() {
        return activityValue;
    }

    public void setActivityValue(double activityValue) {
        this.activityValue = activityValue;
    }
}
