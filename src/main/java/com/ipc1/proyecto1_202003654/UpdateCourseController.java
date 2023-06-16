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
import models.Course;
import models.Teacher;

public class UpdateCourseController implements Initializable {

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
    private ComboBox<String> courseSelectedCombo; 
    @FXML
    private ComboBox<String> teacherSelectedCombo; 
    
    private Course selectedCourse; // Global variable to store the selected course
    private Teacher[] teachers; // Array to store the teachers' data

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate courseSelectedCombo with course information
        Course[] courses = App.getCourses();
        for (Course course : courses) {
            if (course != null) {
                String courseInfo = course.getName();
                courseSelectedCombo.getItems().add(courseInfo);
            }
        }

        // Populate teacherSelectedCombo with teacher information
        teachers = App.getTeachers();
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                String teacherInfo = teacher.getFirstName() + " " + teacher.getLastName();
                teacherSelectedCombo.getItems().add(teacherInfo);
            }
        }

        // Set listener for courseSelectedCombo selection change
        courseSelectedCombo.setOnAction(event -> {
            String selectedCourseName = courseSelectedCombo.getSelectionModel().getSelectedItem();
            if (selectedCourseName != null) {
                selectedCourse = findCourseByName(selectedCourseName);
                if (selectedCourse != null) {
                    // Set the data from selectedCourse onto the corresponding fields
                    nameInput.setText(selectedCourse.getName());
                    creditsInput.setText(String.valueOf(selectedCourse.getCredits()));
                    studentsInput.setText(String.valueOf(selectedCourse.getStudents()));

                    // Update the teacherSelectedCombo
                    if (selectedCourse.getTeacher() != null) {
                        teacherSelectedCombo.getSelectionModel().select(selectedCourse.getTeacher());
                    } else {
                        teacherSelectedCombo.getSelectionModel().clearSelection();
                    }
                }
            }
        });
    }

    @FXML
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("coursesAdmin");
    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    public void onUpdate(ActionEvent event) throws IOException {
        // Use the selectedCourse variable for further processing
        if (selectedCourse != null) {
            // Get updated input values
            String name = nameInput.getText();
            String creditsText = creditsInput.getText();
            String studentsText = studentsInput.getText();

            // Perform validation on updated inputs
            // ...

            // Update the course's information
            selectedCourse.setName(name);
            selectedCourse.setCredits(Integer.parseInt(creditsText));
            selectedCourse.setStudents(Integer.parseInt(studentsText));

            // Update the selected teacher
            String selectedTeacherFullName = teacherSelectedCombo.getSelectionModel().getSelectedItem();
            Teacher selectedTeacher = findTeacherByName(selectedTeacherFullName);
            if (selectedTeacher != null) {
                selectedCourse.setTeacher(selectedTeacher.getFirstName() + " " + selectedTeacher.getLastName());
            } else {
                selectedCourse.setTeacher(null);
            }

            // Invoke the updateCourse method in App.java
            App.updateCourse(selectedCourse);

            // Perform any additional operations or updates after updating

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Curso Actualizado");
            alert.setHeaderText(null);
            alert.setContentText("¡Curso actualizado exitosamente!");
            alert.showAndWait();

            // Clear the fields and reset the selectedCourse
            nameInput.clear();
            creditsInput.clear();
            studentsInput.clear();

            selectedCourse = null;

            // Clear the selection in courseSelectedCombo
            courseSelectedCombo.getSelectionModel().clearSelection();
            courseSelectedCombo.setPromptText("Select a course");

            // Clear the selection in teacherSelectedCombo
            teacherSelectedCombo.getSelectionModel().clearSelection();
        }
    }



    private Course findCourseByName(String name) {
        Course[] courses = App.getCourses();
        for (Course course : courses) {
            if (course != null && course.getName().equals(name)) {
                return course;
            }
        }
        return null;
    }

    private Teacher findTeacherByName(String fullName) {
        for (Teacher teacher : teachers) {
            if (teacher != null && (teacher.getFirstName() + " " + teacher.getLastName()).equals(fullName)) {
                return teacher;
            }
        }
        return null;
    }
}
