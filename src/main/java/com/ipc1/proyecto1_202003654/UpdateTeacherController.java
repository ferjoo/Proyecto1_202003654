package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Teacher;

public class UpdateTeacherController implements Initializable {
    @FXML
    private TextField codeInput;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField passwordInput;
    @FXML
    private ComboBox<String> genderCombo;
    @FXML
    private ComboBox<String> teacherSelectedCombo;

    private Teacher selectedTeacher; // Global variable to store the selected teacher

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate teacherSelectedCombo with teacher information
        Teacher[] teachers = App.getTeachers();
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                String teacherInfo = teacher.getCode() + ": " + teacher.getFirstName() + " " + teacher.getLastName();
                teacherSelectedCombo.getItems().add(teacherInfo);
            }
        }

        // Set listener for teacherSelectedCombo selection change
        teacherSelectedCombo.setOnAction(event -> {
            String selectedTeacherInfo = teacherSelectedCombo.getSelectionModel().getSelectedItem();
            if (selectedTeacherInfo != null) {
                String[] parts = selectedTeacherInfo.split(": ");
                int teacherCode = Integer.parseInt(parts[0]);
                selectedTeacher = findTeacherByCode(teacherCode);
                if (selectedTeacher != null) {
                    // Set the data from selectedTeacher onto the corresponding fields
                    codeInput.setText(String.valueOf(selectedTeacher.getCode()));
                    nameInput.setText(selectedTeacher.getFirstName());
                    lastNameInput.setText(selectedTeacher.getLastName());
                    emailInput.setText(selectedTeacher.getEmail());
                    passwordInput.setText(selectedTeacher.getPassword());
                    genderCombo.setValue(selectedTeacher.getGender());
                }
            }
        });
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("teachersAdmin");
    }
    
    @FXML
    public void onUpdate(ActionEvent event) throws IOException {
        // Use the selectedTeacher variable for further processing
        if (selectedTeacher != null) {
            // Get updated input values
            String name = nameInput.getText();
            String lastName = lastNameInput.getText();
            String email = emailInput.getText();
            String password = passwordInput.getText();
            String gender = genderCombo.getValue();

            // Perform validation on updated inputs
            // ...

            // Update the teacher's information
            selectedTeacher.setFirstName(name);
            selectedTeacher.setLastName(lastName);
            selectedTeacher.setEmail(email);
            selectedTeacher.setPassword(password); // Update the password
            selectedTeacher.setGender(gender);

            // Invoke the updateTeacher method in App.java
            App.updateTeacher(selectedTeacher);

            // Perform any additional operations or updates after updating

            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Profesor Actualizado");
            alert.setHeaderText(null);
            alert.setContentText("¡Profesor actualizado exitosamente!");
            alert.showAndWait();

            // Clear the fields and reset the selectedTeacher
            codeInput.clear();
            nameInput.clear();
            lastNameInput.clear();
            emailInput.clear();
            passwordInput.clear();

            // Repopulate the gender combo box with default values
            genderCombo.getItems().clear();
            genderCombo.getItems().addAll("Masculino", "Femenino", "Otro");
            genderCombo.getSelectionModel().clearSelection();

            // Set the default value for gender combo box
            genderCombo.setValue(gender);

            selectedTeacher = null;

            // Clear the selection in teacherSelectedCombo
            teacherSelectedCombo.getSelectionModel().clearSelection();
            teacherSelectedCombo.setPromptText("Seleccionar un profesor");
        }
    }

    private Teacher findTeacherByCode(int code) {
        Teacher[] teachers = App.getTeachers();
        for (Teacher teacher : teachers) {
            if (teacher != null && teacher.getCode() == code) {
                return teacher;
            }
        }
        return null;
    }
}
