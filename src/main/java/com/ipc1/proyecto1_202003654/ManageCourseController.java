/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Course;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class ManageCourseController implements Initializable {

    @FXML
    private Label courseNameLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the selected course from TeacherMenuController
        Course selectedCourse = TeacherMenuController.getSelectedCourse();
        System.out.println("Selected course: " + selectedCourse.getCode() + " - " + selectedCourse.getName());

        // Set the course name on the label
        courseNameLabel.setText(selectedCourse.getName());
    }

    
    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("teacherMenu");
    }
}
