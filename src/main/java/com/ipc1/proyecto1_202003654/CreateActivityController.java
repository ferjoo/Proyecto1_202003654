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
import javafx.scene.control.TextField;
import models.CourseActivity;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class CreateActivityController implements Initializable {
    
    @FXML
    private TextField nameInput;
    @FXML
    private TextField descriptionInput;
    @FXML
    private TextField valueInput;

    private Double totalPoints = ManageCourseController.getTotalActivitiesPoints();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    public void onSave(ActionEvent event) throws IOException {

        // Get the values from the text fields
        String name = nameInput.getText();
        String description = descriptionInput.getText();
        double value = 0.0;

        // Validate and parse the value from the text field
        try {
            value = Double.parseDouble(valueInput.getText());
        } catch (NumberFormatException e) {
            // Show an error alert if the value is not a valid number
            showAlert("Valor inválido", "Por favor, ingrese un número válido para el valor.");
            return;
        }

        // Calculate the updated total points if the course is added
        double tempTotalPoints = totalPoints + value;

        if (tempTotalPoints <= 100.00) {
            // Create a new CourseActivity object
            CourseActivity activity = new CourseActivity(-1, name, description, value);

            // Add the activity using the CourseActivityManager
            ManageCourseController.addCourseActivity(activity);

            // Update the totalPoints variable
            totalPoints = tempTotalPoints;

            // Show a success alert
            showAlert("Actividad creada", "La actividad se ha creado exitosamente.");

            // Clear the values from the text fields
            nameInput.clear();
            descriptionInput.clear();
            valueInput.clear();
        } else {
            // Show an alert when adding the new activity would exceed the total points limit
            showAlert("Límite de puntos excedido", "Agregar esta actividad excedería el límite máximo de 100.00 puntos.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
