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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Student;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class ManageStudentController implements Initializable {
    @FXML
    private TextField codeInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private ComboBox<String> genderCombo;
    private Student selectedStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        selectedStudent = ManageCourseController.getStudentSelected();
        // Check if a student is selected
        if (selectedStudent != null) {
            // Populate the text fields with the student's information
            codeInput.setText(String.valueOf(selectedStudent.getCode()));
            nameInput.setText(selectedStudent.getFirstName());
            lastNameInput.setText(selectedStudent.getLastName());
            emailInput.setText(selectedStudent.getEmail());
            genderCombo.setValue(selectedStudent.getGender());
        }
    }
    
    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("manageCourse");
    }
    
    @FXML
    public void onDelete(ActionEvent event) throws IOException {
        ManageCourseController.deleteSelectedStudent();
        App.setRoot("manageCourse");
    }
    
    
}
