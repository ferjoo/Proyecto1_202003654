/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ferjo
 */
public class TopStudentsRow {
    private final SimpleIntegerProperty position;
    private final SimpleStringProperty courseName;
    private final SimpleIntegerProperty amountOfStudents;

    public TopStudentsRow(int position, String courseName, int amountOfStudents) {
        this.position = new SimpleIntegerProperty(position);
        this.courseName = new SimpleStringProperty(courseName);
        this.amountOfStudents = new SimpleIntegerProperty(amountOfStudents);
    }

    public int getPosition() {
        return position.get();
    }

    public String getCourseName() {
        return courseName.get();
    }

    public int getAmountOfStudents() {
        return amountOfStudents.get();
    }
}