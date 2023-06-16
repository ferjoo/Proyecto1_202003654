package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import models.Teacher;

public class DeleteTeacherController implements Initializable {
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
    public void onDelete(ActionEvent event) throws IOException {
        // Use the selectedTeacher variable for further processing
        if (selectedTeacher != null) {
            App.deleteTeacher(selectedTeacher); // Call the deleteTeacher method in App class

            // Perform any additional operations or updates after deletion

            // Clear the fields and reset the selectedTeacher
            codeInput.clear();
            nameInput.clear();
            lastNameInput.clear();
            emailInput.clear();
            genderCombo.getSelectionModel().clearSelection();
            selectedTeacher = null;

            // Clear the selection in teacherSelectedCombo
            teacherSelectedCombo.getSelectionModel().clearSelection();
            teacherSelectedCombo.setPromptText("Seleccionar un profesor");

            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Profesor Eliminado");
            alert.setHeaderText(null);
            alert.setContentText("¡Profesor eliminado exitosamente!");
            alert.showAndWait();
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
