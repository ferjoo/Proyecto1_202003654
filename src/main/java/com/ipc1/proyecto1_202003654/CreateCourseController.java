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
import models.Teacher;

public class CreateCourseController implements Initializable {

    @FXML
    private TextField nameInput;
    @FXML
    private TextField creditsInput;
    @FXML
    private TextField studentsInput;
    @FXML
    private Label nameErrorLabel;
    @FXML
    private Label creditsErrorLabel;
    @FXML
    private Label studentsErrorLabel;
    @FXML
    private Label teacherErrorLabel;
    @FXML
    private ComboBox<String> teacherSelectedCombo;

    private Teacher[] teachers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate teacherSelectedCombo with teacher information
        teachers = App.getTeachers();
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                String teacherInfo = teacher.getFirstName() + " " + teacher.getLastName();
                teacherSelectedCombo.getItems().add(teacherInfo);
            }
        }
    }

    @FXML
    public void onSave(ActionEvent event) {
        // Clear previous error messages
        nameErrorLabel.setText("");
        creditsErrorLabel.setText("");
        studentsErrorLabel.setText("");
        teacherErrorLabel.setText("");

        // Get input values
        String name = nameInput.getText();
        String creditsText = creditsInput.getText();
        String studentsText = studentsInput.getText();
        String teacherInfo = teacherSelectedCombo.getValue();

        // Validate name
        if (name.isEmpty()) {
            nameErrorLabel.setText("El nombre es requerido");
            return;
        }

        // Validate credits
        int credits;
        try {
            credits = Integer.parseInt(creditsText);
            if (credits <= 0) {
                creditsErrorLabel.setText("Los créditos deben ser mayores a 0");
                return;
            }
        } catch (NumberFormatException e) {
            creditsErrorLabel.setText("Valor de créditos inválido");
            return;
        }

        // Validate students
        int students;
        try {
            students = Integer.parseInt(studentsText);
            if (students <= 0) {
                studentsErrorLabel.setText("El número de estudiantes debe ser mayor a 0");
                return;
            }
        } catch (NumberFormatException e) {
            studentsErrorLabel.setText("Valor de estudiantes inválido");
            return;
        }

        // Validate teacher
        if (teacherInfo == null) {
            teacherErrorLabel.setText("Debe seleccionar un profesor");
            return;
        }

        // Find the selected teacher object
        Teacher selectedTeacher = null;
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                String teacherFullName = teacher.getFirstName() + " " + teacher.getLastName();
                if (teacherFullName.equals(teacherInfo)) {
                    selectedTeacher = teacher;
                    break;
                }
            }
        }

        if (selectedTeacher == null) {
            teacherErrorLabel.setText("No se encontró el profesor seleccionado");
            return;
        }

        // Add the course
        App.addCourse(-1, name, credits, students, selectedTeacher.getFirstName() + " " + selectedTeacher.getLastName(), selectedTeacher.getCode());
        System.out.println("¡Curso agregado exitosamente!");

        // Show success message
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Curso Creado");
        alert.setHeaderText(null);
        alert.setContentText("¡Curso creado exitosamente!");
        alert.showAndWait();

        // Clear input fields
        nameInput.clear();
        creditsInput.clear();
        studentsInput.clear();
        teacherSelectedCombo.setValue(null);

        // Clear error labels
        nameErrorLabel.setText("");
        creditsErrorLabel.setText("");
        studentsErrorLabel.setText("");
        teacherErrorLabel.setText("");
    }


    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("coursesAdmin");
    }
}
