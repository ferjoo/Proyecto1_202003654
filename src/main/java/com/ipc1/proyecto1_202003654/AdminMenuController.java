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
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author ferjo
 */
public class AdminMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    @FXML 
    private Button adminCoursesButton;
    @FXML 
    private Button adminTeachersButton;
    @FXML 
    private Button logoutButton;
    
    public void onAdminCourses(ActionEvent event) throws IOException {
        App.setRoot("coursesAdmin"); 
    }
    
    public void onAdminTeachers(ActionEvent event) throws IOException {
        App.setRoot("teachersAdmin"); 
        
    }
    
    public void onLogout(ActionEvent event) throws IOException {
        App.setRoot("login"); 
    }
    
}
