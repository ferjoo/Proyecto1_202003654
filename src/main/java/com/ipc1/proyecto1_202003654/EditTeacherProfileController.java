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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.Teacher;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class EditTeacherProfileController implements Initializable {
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
    private Label nameErrorLabel;
    @FXML
    private Label lastNameErrorLabel;
    @FXML
    private Label emailErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label genderErrorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Access the currentTeacherSession from App.java
        Teacher currentTeacher = App.getCurrentTeacherSession();

        // Fill the text fields with teacher's information
        nameInput.setText(currentTeacher.getFirstName());
        lastNameInput.setText(currentTeacher.getLastName());
        emailInput.setText(currentTeacher.getEmail());
        passwordInput.setText(currentTeacher.getPassword());

        // Fill the gender combo box with options and set the selected value
        genderCombo.getItems().addAll("Male", "Female", "Other");
        genderCombo.setValue(currentTeacher.getGender());
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("teacherMenu");
    }

    @FXML
    public void onUpdate(ActionEvent event) throws IOException {
        // Access the currentTeacherSession from App.java
        Teacher currentTeacher = App.getCurrentTeacherSession();

        // Get the values from the input fields
        String name = nameInput.getText();
        String lastName = lastNameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText();
        String gender = genderCombo.getValue();

        // Check if any of the fields are empty
        boolean isInvalid = false;

        if (name.isEmpty()) {
            nameErrorLabel.setText("El nombre es requerido");
            isInvalid = true;
        }

        if (lastName.isEmpty()) {
            lastNameErrorLabel.setText("El apellido es requerido");
            isInvalid = true;
        }

        // Validate email using regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email.isEmpty()) {
            emailErrorLabel.setText("El correo electrónico es requerido");
            isInvalid = true;
        } else if (!email.matches(emailRegex)) {
            emailErrorLabel.setText("El correo electrónico no es válido");
            isInvalid = true;
        }

        if (password.isEmpty()) {
            passwordErrorLabel.setText("La contraseña es requerida");
            isInvalid = true;
        }

        if (gender == null || gender.isEmpty()) {
            genderErrorLabel.setText("El género es requerido");
            isInvalid = true;
        }

        if (isInvalid) {
            return; // Don't proceed if any field is empty or email is invalid
        }

        // All fields are filled and email is valid, update the teacher's information
        currentTeacher.setFirstName(name);
        currentTeacher.setLastName(lastName);
        currentTeacher.setEmail(email);
        currentTeacher.setPassword(password);
        currentTeacher.setGender(gender);

        // Invoke the updateTeacher method in App.java to update the teacher in the array
        App.updateTeacher(currentTeacher);

        // Show success message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Actualización de Perfil");
        alert.setHeaderText(null);
        alert.setContentText("¡Perfil actualizado exitosamente!");
        alert.showAndWait();

        // Repopulate the gender combo box with default values
        genderCombo.getItems().clear();
        genderCombo.getItems().addAll("Masculino", "Femenino", "Otro");

        // Set the default value for gender combo box
        genderCombo.setValue(gender);
    }



}
