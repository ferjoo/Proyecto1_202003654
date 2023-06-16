/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ferjo
 */
public class GenderReportRow {
        private final SimpleStringProperty gender;
    private final SimpleStringProperty percentage;

    public GenderReportRow(String gender, String percentage) {
        this.gender = new SimpleStringProperty(gender);
        this.percentage = new SimpleStringProperty(percentage);
    }

    public String getGender() {
        return gender.get();
    }

    public String getPercentage() {
        return percentage.get();
    }
}
