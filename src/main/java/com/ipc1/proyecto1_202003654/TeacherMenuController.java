package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Course;
import models.Teacher;

public class TeacherMenuController implements Initializable {
    @FXML
    private TableView<Course> teacherCoursesTable;
    @FXML
    private TableColumn<Course, String> courseCodeColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Teacher, String> courseActionColumn;
    private static Course selectedCourse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the columns to display the appropriate properties of the Course class
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Get the teacher session courses from the App class
        Course[] teacherSessionCourses = App.getTeacherSessionCourses();

        // Set the items in the TableView
        teacherCoursesTable.getItems().clear(); // Clear existing items

        // Add non-null courses to the TableView
        for (Course course : teacherSessionCourses) {
            if (course != null) {
                teacherCoursesTable.getItems().add(course);
            }
        }

        // Add event handler to the TableView
        teacherCoursesTable.setOnMouseClicked(event -> {
            // Get the selected course
            Course newSelectedCourse = teacherCoursesTable.getSelectionModel().getSelectedItem();

            // Check if a course is selected
            if (newSelectedCourse != null) {
                // Perform your desired action with the selected course
                selectedCourse = newSelectedCourse;
                try {
                    App.setRoot("manageCourse");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    // Getter method to access the selected course from other controllers
    public static Course getSelectedCourse() {
        return selectedCourse;
    }
    
    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    public void onEditProfile(ActionEvent event) throws IOException {
        App.setRoot("editTeacherProfile");
    }
    
    @FXML 
    public void onCourseSelected(ActionEvent event) throws IOException {
        
    }
}