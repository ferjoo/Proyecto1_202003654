/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ipc1.proyecto1_202003654;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.Course;
import models.Student;
import models.Teacher;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class ManageCourseController implements Initializable {
    
    @FXML
        private TableView<Student> studentsTable;
    @FXML
    private TableColumn<Student, Integer> codeColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, Double> gradesColumn;
    @FXML
    private Label courseNameLabel;
    public Course selectedCourse;
    private static Student[] students = new Student[100];
    private static Student studentSelected;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Get the selected course from TeacherMenuController
        selectedCourse = TeacherMenuController.getSelectedCourse();
        System.out.println("Selected course: " + selectedCourse.getCode() + " - " + selectedCourse.getName());

        // Set the course name on the label
        courseNameLabel.setText(selectedCourse.getName());

        // Configure the cell value factories for each column
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradesColumn.setCellValueFactory(new PropertyValueFactory<>("grades"));

        // Populate the table with the students array
        updateStudentsTable();

        // Add action listener to select student from the table
        studentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ManageCourseController.studentSelected = newSelection;
                try {
                    App.setRoot("manageStudent");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }
        });
    }
    
    private void updateStudentsTable() {
        studentsTable.getItems().clear();

        for (Student student : students) {
            if (student != null) {
                studentsTable.getItems().add(student);
            }
        }
    }
    
    public static Student getStudentSelected() {
        return studentSelected;
    }
    
        // Method to delete a selected student from the students array
    public static void deleteSelectedStudent() {
        if (studentSelected != null) {
            for (int i = 0; i < students.length; i++) {
                if (students[i] == studentSelected) {
                    students[i] = null;
                    break;
                }
            }
            studentSelected = null;
        }
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
    public void onCreateActivity(ActionEvent event) throws IOException {
    }

    @FXML
    public void onUploadStudents(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int successfulUploads = 0;
                int failedUploads = 0;

                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length == 6) {
                        int code;
                        String firstName = data[1];
                        String lastName = data[2];
                        String email = data[3];
                        String gender = data[4];
                        double grades;

                        try {
                            code = Integer.parseInt(data[0]);
                            grades = Double.parseDouble(data[5]);
                        } catch (NumberFormatException e) {
                            // Handle invalid code or grades format
                            failedUploads++;
                            continue;
                        }

                        // Map gender values
                        if (gender.equalsIgnoreCase("M")) {
                            gender = "Masculino";
                        } else if (gender.equalsIgnoreCase("F")) {
                            gender = "Femenino";
                        } else {
                            gender = "Otro";
                        }

                        // Create a new Student object and add it to the students array
                        Student student = new Student(code, firstName, lastName, email, gender, grades);
                        addStudent(student);

                        successfulUploads++;
                    } else {
                        // Handle invalid data format
                        failedUploads++;
                    }
                }

                // Show the alert
                showAlert(successfulUploads, failedUploads);
            } catch (IOException e) {
                // Show error alert
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Se produjo un error al cargar los estudiantes: " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }

    private void addStudent(Student student) {
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                students[i] = student;
                break;
            }
        }
    }

    private void showAlert(int successfulUploads, int failedUploads) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Carga de estudiantes");
        alert.setHeaderText(null);
        alert.setContentText("Se cargaron exitosamente " + successfulUploads + " estudiantes.\n"
                + "Fallaron " + failedUploads + " cargas.");

        alert.showAndWait();
        updateStudentsTable();
    }

    @FXML
public void downloadTopStudents(ActionEvent event) throws IOException {
    // Get the top 3 students with the best grades
    Student[] topStudents = new Student[3];
    int topStudentsCount = 0;

    for (Student student : students) {
        if (student != null) {
            if (topStudentsCount < 3) {
                topStudents[topStudentsCount] = student;
                topStudentsCount++;
            } else {
                // Sort the top students based on grades
                for (int i = 0; i < 3; i++) {
                    if (student.getGrades() > topStudents[i].getGrades()) {
                        // Shift the students to make room for the new student
                        for (int j = 2; j > i; j--) {
                            topStudents[j] = topStudents[j - 1];
                        }
                        // Insert the new student at the appropriate position
                        topStudents[i] = student;
                        break;
                    }
                }
            }
        }
    }

    // Print the top students with the highest grades
    System.out.println("Top Students:");
    for (Student student : topStudents) {
        if (student != null) {
            System.out.println(student.getFirstName() + " " + student.getLastName() + " - Grades: " + student.getGrades());
        }
    }

    // Generate the HTML content for the top students
    StringBuilder htmlContent = new StringBuilder();
    htmlContent.append("<html><body><h1>Top 3 Students</h1><ol>");

    for (Student student : topStudents) {
        if (student != null) {
            htmlContent.append("<li>")
                    .append(student.getFirstName())
                    .append(" ")
                    .append(student.getLastName())
                    .append(" - Grades: ")
                    .append(student.getGrades())
                    .append("</li>");
        }
    }

    htmlContent.append("</ol></body></html>");

    // Prompt the user to choose the file location
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Top Students");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
        // Write the HTML content to the selected file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(htmlContent.toString());
        }

        // Show success message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Download Successful");
        alert.setHeaderText(null);
        alert.setContentText("Top students exported to '" + file.getName() + "'");
        alert.showAndWait();
    }
}



    @FXML
    public void downloadWorstStudents(ActionEvent event) throws IOException {
        // Get the top 3 students with the worst grades
        Student[] worstStudents = new Student[3];
        int maxStudents = 3;

        for (Student student : students) {
            if (student != null) {
                if (worstStudents[0] == null) {
                    worstStudents[0] = student;
                } else {
                    // Find the position to insert the student based on grades
                    int insertIndex = 0;
                    while (insertIndex < maxStudents && worstStudents[insertIndex] != null && student.getGrades() >= worstStudents[insertIndex].getGrades()) {
                        insertIndex++;
                    }

                    if (insertIndex < maxStudents) {
                        // Shift the students to make room for the new student
                        for (int i = maxStudents - 1; i > insertIndex; i--) {
                            worstStudents[i] = worstStudents[i - 1];
                        }

                        // Insert the new student at the appropriate position
                        worstStudents[insertIndex] = student;
                    }
                }
            }
        }

        // Print the sorted values
        System.out.println("Sorted Worst Students:");
        for (Student student : worstStudents) {
            if (student != null) {
                System.out.println(student.getFirstName() + " " + student.getLastName() + " - Grades: " + student.getGrades());
            }
        }

        // Generate the HTML content for the worst students
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<html><body><h1>Worst 3 Students</h1><ol>");

        for (Student student : worstStudents) {
            if (student != null) {
                htmlContent.append("<li>")
                        .append(student.getFirstName())
                        .append(" ")
                        .append(student.getLastName())
                        .append(" - Grades: ")
                        .append(student.getGrades())
                        .append("</li>");
            }
        }

        htmlContent.append("</ol></body></html>");

        // Prompt the user to choose the file location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Worst Students");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Write the HTML content to the selected file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(htmlContent.toString());
            }

            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Download Successful");
            alert.setHeaderText(null);
            alert.setContentText("Worst students exported to '" + file.getName() + "'");
            alert.showAndWait();
        }
    }

}
