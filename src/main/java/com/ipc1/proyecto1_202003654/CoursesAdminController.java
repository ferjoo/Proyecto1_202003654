/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ipc1.proyecto1_202003654;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.Course;
import models.TopStudentsRow;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class CoursesAdminController implements Initializable {
    private Map<String, Integer> courseStudentsMap;

    @FXML
    private TableView<Course> coursesTable;
    @FXML
    private TableColumn<Course, Integer> codeColumn;
    @FXML
    private TableColumn<Course, String> nameColumn;
    @FXML
    private TableColumn<Course, Integer> creditsColumn;
    @FXML
    private TableColumn<Course, Integer> studentsColumn;
    @FXML
    private TableColumn<Course, String> teacherColumn;
    @FXML
    private TableView<TopStudentsRow> topStudentsTable;
    @FXML
    private TableColumn<TopStudentsRow, Integer> positionColumn;
    @FXML
    private TableColumn<TopStudentsRow, String> courseNameColumn;
    @FXML
    private TableColumn<TopStudentsRow, Integer> amountOfStudentsColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up the table columns
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        studentsColumn.setCellValueFactory(new PropertyValueFactory<>("students"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacher"));

        // Configure the topStudentsTable columns
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        amountOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("amountOfStudents"));

        // Clear any existing items in the coursesTable
        coursesTable.getItems().clear();

        // Retrieve the courses from App.java
        Course[] courses = App.getCourses();

        // Check if the courses array is null or empty
        if (courses != null && courses.length > 0) {
            // Populate the coursesTable with the courses array
            for (Course course : courses) {
                if (course != null) {
                    coursesTable.getItems().add(course);
                }
            }
        }
        // Update the topStudentsTable with the latest data
        updateTopStudentsTable();
    }

    private Course[] getTopCoursesWithMostStudents(int count) {
        // Retrieve the courses from App.java
        Course[] courses = App.getCourses();

        if (courses == null || count <= 0) {
            return new Course[0]; // Return an empty array if courses is null or count is less than or equal to 0
        }

        // Get the number of populated courses in the array
        int populatedCount = 0;
        for (Course course : courses) {
            if (course != null) {
                populatedCount++;
            } else {
                break; // Stop counting when a null element is encountered
            }
        }

        // Sort the populated courses array based on the number of students in descending order using bubble sort
        for (int i = 0; i < populatedCount - 1; i++) {
            for (int j = 0; j < populatedCount - i - 1; j++) {
                if (courses[j].getStudents() < courses[j + 1].getStudents()) {
                    // Swap the courses if they are out of order
                    Course temp = courses[j];
                    courses[j] = courses[j + 1];
                    courses[j + 1] = temp;
                }
            }
        }

        // Get the top courses with the most students
        int topCount = Math.min(count, populatedCount);
        Course[] topCourses = new Course[topCount];
        for (int i = 0; i < topCount; i++) {
            topCourses[i] = courses[i];
        }

        return topCourses;
    }
   
    private void updateTopStudentsTable() {
        // Get the top courses with the most students
        Course[] topCourses = getTopCoursesWithMostStudents(3); // Example: Get top 10 courses

        // Clear the existing data in the table
        topStudentsTable.getItems().clear();

        // Populate the table with the top courses data
        int position = 1;
        for (Course course : topCourses) {
            if (course != null) {
                int amountOfStudents = course.getStudents();
                TopStudentsRow topStudentsRow = new TopStudentsRow(position, course.getName(), amountOfStudents);
                topStudentsTable.getItems().add(topStudentsRow);
                position++;
            }
        }

        // Print the contents of topStudentsTable for debugging
        System.out.println("Top Students Table:");
        for (TopStudentsRow row : topStudentsTable.getItems()) {
            System.out.println(row.getPosition() + " - " + row.getCourseName() + " - " + row.getAmountOfStudents());
        }
    }
    
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("adminMenu"); 
    }
    
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login"); 
    }

    public void onAddCourse(ActionEvent event) throws IOException {
        App.setRoot("createCourse"); 
    }

    public void onDeleteCourse(ActionEvent event) {
        // TODO: Handle delete course button action
    }

    public void onEditCourse(ActionEvent event) throws IOException {
        App.setRoot("updateCourse"); 
    }

    public void onExportCourses(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
    fileChooser.setInitialFileName("courses.html");
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
        String filePath = file.getAbsolutePath();
        if (!filePath.endsWith(".html")) {
            filePath += ".html";
        }

        File outputFile = new File(filePath);
        if (outputFile.exists()) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("File Already Exists");
            alert.setHeaderText(null);
            alert.setContentText("The file already exists. Do you want to overwrite it?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                return;
            }
        }

        App.exportCoursesToHTML(filePath);
    }
}
    
    public void uploadCoursesFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int successfulUploads = 0;
                int failedUploads = 0;

                // Skip the header line
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length == 6) {  // Updated data length to 6
                        int code;
                        String name = data[1];
                        int credits;
                        int students;
                        String teacher = data[4];
                        int teacherCode;

                        try {
                            code = Integer.parseInt(data[0]);
                            credits = Integer.parseInt(data[2]);
                            students = Integer.parseInt(data[3]);
                            teacherCode = Integer.parseInt(data[5]);  // Parse teacherCode from data[5]
                        } catch (NumberFormatException e) {
                            // Handle invalid code, credits, or students format
                            failedUploads++;
                            continue;
                        }

                        // Add the course to the courses array
                        App.addCourse(code, name, credits, students, teacher, teacherCode);
                        successfulUploads++;
                    } else {
                        // Handle invalid data format
                        failedUploads++;
                    }
                }

                // Update the TableView and show the alert
                updateCoursesTableViewAndShowAlert(successfulUploads, failedUploads);
            } catch (IOException e) {
                // Show error alert
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Se produjo un error al cargar los cursos: " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }


    private void updateCoursesTableViewAndShowAlert(int successfulUploads, int failedUploads) {
        // Retrieve the courses from App.java
        Course[] courses = App.getCourses();

        // Clear any existing items in the coursesTable
        coursesTable.getItems().clear();

        // Populate the coursesTable with the courses array
        if (courses != null && courses.length > 0) {
            for (Course course : courses) {
                if (course != null) {
                    coursesTable.getItems().add(course);
                }
            }
        }

        // Show the alert with upload summary
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Carga de Cursos");
        alert.setHeaderText(null);
        alert.setContentText("Se cargaron exitosamente " + successfulUploads + " cursos.\n"
                + "No se pudieron cargar " + failedUploads + " cursos debido a un formato incorrecto.");
        alert.showAndWait();
        updateTopStudentsTable();
    }

}
