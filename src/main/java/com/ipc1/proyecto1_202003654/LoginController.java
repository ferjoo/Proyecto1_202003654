package com.ipc1.proyecto1_202003654;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.Teacher;

public class LoginController implements Initializable {

    @FXML
    private TextField codeInput;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void onLogin(ActionEvent event) throws IOException {
        String enteredCode = codeInput.getText();
        String enteredPassword = passwordInput.getText();

        if (enteredCode.equals("admin") && enteredPassword.equals("admin")) {
            System.out.println("Login as admin successful");
            App.setRoot("adminMenu");
            // Proceed with the desired actions after successful login
        } else {
            boolean validLogin = false;

            for (Teacher teacher : App.getTeachers()) {
                if (teacher != null && teacher.getCode() == Integer.parseInt(enteredCode)) {
                    if (teacher.getPassword().equals(enteredPassword)) {
                        validLogin = true;
                        // Set the current teacher session
                        App.setCurrentTeacherSession(teacher);
                        break;
                    }
                }
            }

            if (validLogin) {
                System.out.println("Login as teacher successful");
                App.setRoot("teacherMenu");
                // Proceed with the desired actions after successful login
            } else {
                // Invalid code or password
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error al iniciar sesion");
                alert.setHeaderText(null);
                alert.setContentText("Codigo o contraseña incorrectos");
                alert.showAndWait();
                // Clear the input fields
                codeInput.clear();
                passwordInput.clear();
            }
        }
    }
}
