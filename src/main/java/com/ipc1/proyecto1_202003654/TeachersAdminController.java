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
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import models.GenderReportRow;
import models.Teacher;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class TeachersAdminController implements Initializable {
    
    private double malePercentage;
    private double femalePercentage;
    private double otherPercentage;
    
    @FXML
    private TableView<Teacher> teachersTable;
    @FXML
    private TableColumn<Teacher, String> codeColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, String> lastNameColumn;
    @FXML
    private TableColumn<Teacher, String> emailColumn;
    @FXML
    private TableColumn<Teacher, String> genderColumn;
    @FXML
    private TableView<GenderReportRow> gendersTable;
    @FXML
    private TableColumn<GenderReportRow, String> genderReportColumn;
    @FXML
    private TableColumn<GenderReportRow, String> percentageColumn;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the table columns
        codeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCode())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        genderColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        genderReportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGender()));
        percentageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPercentage()));

    
        // Get the teachers from the App class
        Teacher[] teachers = App.getTeachers();

        // Populate the TableView with teacher data
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                teachersTable.getItems().add(teacher);
            }
        }
        for (String gender : new String[] { "Femenino", "Masculino", "Otro" }) {
            GenderReportRow row = new GenderReportRow(gender, "");
            gendersTable.getItems().add(row);
        }
        calculateGenderPercentages();
    }

    private String getPercentageForGender(String gender) {
        if (gender.equalsIgnoreCase("Femenino")) {
            return String.format("%.2f", femalePercentage) + "%";
        } else if (gender.equalsIgnoreCase("Masculino")) {
            return String.format("%.2f", malePercentage) + "%";
        } else {
            return String.format("%.2f", otherPercentage) + "%";
        }
    }
  
    public void onBack(ActionEvent event) throws IOException {
        App.setRoot("adminMenu"); 
    }
    
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login"); 
    }
    
    public void onAddTeacher(ActionEvent event) throws IOException {
        App.setRoot("createTeacher"); 
    }
     
             
    public void onDeleteTeacher(ActionEvent event) throws IOException {
        App.setRoot("deleteTeacher"); 
    }
     
    public void onEditTeacher(ActionEvent event) throws IOException {
        App.setRoot("updateTeacher"); 
    }

    public void onExportTeacher(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));
        fileChooser.setInitialFileName("teachers.html");
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

            App.exportTeachersToHTML(filePath);
        }
    }
    
    public void uploadTeachersFile(ActionEvent event) {
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
                    if (data.length == 5) {
                        int code;
                        String name = data[1];
                        String lastName = data[2];
                        String email = data[3];
                        String gender = data[4];

                        try {
                            code = Integer.parseInt(data[0]);
                        } catch (NumberFormatException e) {
                            // Handle invalid code format
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

                        // Add the teacher to the teachers array
                        App.addTeacher(code, name, lastName, email, "1234", gender);
                        successfulUploads++;
                    } else {
                        // Handle invalid data format
                        failedUploads++;
                    }
                }

                // Update the TableView and show the alert
                updateTableViewAndShowAlert(successfulUploads, failedUploads);
            // Refresh the gender report table
            calculateGenderPercentages(); // Recalculate percentages
            refreshGenderReportTable(); // Update the gender report table
            } catch (IOException e) {
                // Show error alert
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Se produjo un error al cargar los profesores: " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
    
    private void updateTableViewAndShowAlert(int successfulUploads, int failedUploads) {
        // Clear the current items in the TableView
        teachersTable.getItems().clear();

        // Get the updated teachers from the App class
        Teacher[] teachers = App.getTeachers();

        // Populate the TableView with teacher data
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                teachersTable.getItems().add(teacher);
            }
        }

        // Show success alert
        Alert successAlert = new Alert(AlertType.INFORMATION);
        successAlert.setTitle("Carga Exitosa");
        successAlert.setHeaderText(null);
        successAlert.setContentText("¡Profesores cargados exitosamente!\n"
                + "Cargas exitosas: " + successfulUploads + "\n"
                + "Cargas fallidas: " + failedUploads);
        successAlert.showAndWait();
    }
    
        private void calculateGenderPercentages() {
        Teacher[] teachers = App.getTeachers();
        int totalTeachers = 0;
        int maleCount = 0;
        int femaleCount = 0;
        int otherCount = 0;
        
        // Count the number of teachers for each gender
        for (Teacher teacher : teachers) {
            if (teacher != null) {
                totalTeachers++;
                
                String gender = teacher.getGender();
                if (gender.equalsIgnoreCase("Masculino")) {
                    maleCount++;
                } else if (gender.equalsIgnoreCase("Femenino")) {
                    femaleCount++;
                } else {
                    otherCount++;
                }
            }
        }
        
        // Calculate the percentages
        malePercentage = calculatePercentage(maleCount, totalTeachers);
        femalePercentage = calculatePercentage(femaleCount, totalTeachers);
        otherPercentage = calculatePercentage(otherCount, totalTeachers);
        
            // Print the percentages
    System.out.println("Male Percentage: " + malePercentage + "%");
    System.out.println("Female Percentage: " + femalePercentage + "%");
    System.out.println("Other Percentage: " + otherPercentage + "%");
    refreshGenderReportTable();
       
    }
        
    private double calculatePercentage(int count, int total) {
        if (total == 0) {
            return 0.0;
        } else {
            return (count * 100.0) / total;
        }
    }

    private void refreshGenderReportTable() {
        gendersTable.getItems().clear();

        String[] genders = { "Femenino", "Masculino", "Otro" };
        double[] percentages = { femalePercentage, malePercentage, otherPercentage };

        for (int i = 0; i < genders.length; i++) {
            String gender = genders[i];
            double percentage = percentages[i];

            GenderReportRow row = new GenderReportRow(gender, String.format("%.2f%%", percentage));
            gendersTable.getItems().add(row);
        }
    }


}