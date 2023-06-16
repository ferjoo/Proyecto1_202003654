package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateTeacherController implements Initializable {

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set default values for genderCombo
        genderCombo.getItems().addAll("Masculino", "Femenino", "Otro");
       // genderCombo.getSelectionModel().selectFirst();
    }
    
    @FXML
    public void onSave(ActionEvent event) {
        // Clear previous error messages
        nameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        genderErrorLabel.setText("");

        // Get input values
        String name = nameInput.getText();
        String lastName = lastNameInput.getText();
        String email = emailInput.getText();
        String password = passwordInput.getText();
        String gender = genderCombo.getValue();

        // Validate name
        if (name.isEmpty()) {
            nameErrorLabel.setText("El nombre es requerido");
            return;
        }

        // Validate last name
        if (lastName.isEmpty()) {
            lastNameErrorLabel.setText("El apellido es requerido");
            return;
        }

        // Validate email
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            emailErrorLabel.setText("Email inválido");
            return;
        }

        // Validate password
        if (password.isEmpty()) {
            passwordErrorLabel.setText("La contraseña es requerida");
            return;
        }

        // Validate gender
        if (gender == null) {
            genderErrorLabel.setText("El género es requerido");
            return;
        }

        // Add the teacher
        App.addTeacher(-1, name, lastName, email, password, gender);
        System.out.println("¡Profesor agregado exitosamente!");

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Profesor Creado");
        alert.setHeaderText(null);
        alert.setContentText("¡Profesor creado exitosamente!");
        alert.showAndWait();

        // Clear input fields
        nameInput.clear();
        lastNameInput.clear();
        emailInput.clear();
        passwordInput.clear();

        // Repopulate the gender combo box with default values
        genderCombo.getItems().clear();
        genderCombo.getSelectionModel().clearSelection();
                // Set default values for genderCombo
        genderCombo.getItems().addAll("Masculino", "Femenino", "Otro");
        genderCombo.setPromptText("Seleccionar género");

        // Clear error labels
        nameErrorLabel.setText("");
        lastNameErrorLabel.setText("");
        emailErrorLabel.setText("");
        passwordErrorLabel.setText("");
        genderErrorLabel.setText("");
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("teachersAdmin");
    }
}
