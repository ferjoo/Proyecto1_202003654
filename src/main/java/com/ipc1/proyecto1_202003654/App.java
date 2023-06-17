package com.ipc1.proyecto1_202003654;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import models.Course;
import models.Teacher;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Teacher[] teachers = new Teacher[100];
    private static int teacherCount = 0;
    private static Course[] courses = new Course[100];
    private static int courseCount = 0;
    private static Teacher currentTeacherSession;
    private static Course[] teacherSessionCourses = new Course[100];

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 900, 700);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    // Current Teacher methods
    
    public static Teacher getCurrentTeacherSession() {
        return currentTeacherSession;
    }

    public static void setCurrentTeacherSession(Teacher teacher) {
        currentTeacherSession = teacher;
        System.out.println("Current Teacher Session: " + teacher.getFirstName() + " " + teacher.getLastName());

        // Reset the teacherSessionCourses array
        teacherSessionCourses = new Course[100];

        // Populate teacherSessionCourses with courses associated with the current teacher session
        int teacherCode = teacher.getCode();
        int courseIndex = 0;

        for (Course course : courses) {
            if (course != null && course.getTeacherCode() == teacherCode) {
                teacherSessionCourses[courseIndex] = course;
                courseIndex++;
            }
        }
    }

    
    public static Course[] getTeacherSessionCourses() {
        return teacherSessionCourses;
    }
    
    // Teacher methods

    public static void addTeacher(int code, String firstName, String lastName, String email, String password, String gender) {
        if (code == -1) {
            code = teacherCount + 1; // Auto-increment the code based on the current teacher count
        }

        Teacher teacher = new Teacher(code, firstName, lastName, email, password, gender);

        // Check if there is enough space in the teachers array
        if (teacherCount == teachers.length) {
            // Resize the array
            Teacher[] newTeachers = new Teacher[teachers.length * 2];

            // Copy the existing teachers to the new array
            System.arraycopy(teachers, 0, newTeachers, 0, teacherCount);

            // Update the teachers reference to point to the new array
            teachers = newTeachers;
        }

        // Add the teacher to the teachers array
        teachers[teacherCount] = teacher;
        teacherCount++;
    }

    public static Teacher[] getTeachers() {
        return teachers;
    }    
  
    public static void deleteTeacher(Teacher teacher) {
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i] != null && teachers[i].getCode() == teacher.getCode()) {
                teachers[i] = null; // Set the element to null to delete it
                break; // Exit the loop once the teacher is found and deleted
            }
        }
    }
    
    public static void updateTeacher(Teacher teacher) {
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i] != null && teachers[i].getCode() == teacher.getCode()) {
                // Update the teacher's information
                teachers[i].setFirstName(teacher.getFirstName());
                teachers[i].setLastName(teacher.getLastName());
                teachers[i].setEmail(teacher.getEmail());
                teachers[i].setPassword(teacher.getPassword());
                teachers[i].setGender(teacher.getGender());
                break; // Exit the loop once the teacher is found and updated
            }
        }
    }
    
    public static void uploadTeachersFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip the header row
            reader.readLine();

            String line;
            int successfulUploads = 0;
            int failedUploads = 0;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 5) {
                    int code;
                    String name = data[0];
                    String lastName = data[1];
                    String email = data[2];
                    String gender = data[3];

                    try {
                        code = Integer.parseInt(data[0]);
                    } catch (NumberFormatException e) {
                        // Handle invalid code format
                        failedUploads++;
                        continue;
                    }

                    // Convert gender value to "Femenino", "Masculino", or "Otro"
                    if (gender.equalsIgnoreCase("F")) {
                        gender = "Femenino";
                    } else if (gender.equalsIgnoreCase("M")) {
                        gender = "Masculino";
                    } else {
                        gender = "Otro";
                    }

                    // Create a new teacher object
                    Teacher teacher = new Teacher(code, name, lastName, email, "1234", gender);

                    // Check if there is enough space in the teachers array
                    if (teacherCount == teachers.length) {
                        // Resize the array
                        Teacher[] newTeachers = new Teacher[teachers.length * 2];

                        // Copy the existing teachers to the new array
                        System.arraycopy(teachers, 0, newTeachers, 0, teacherCount);

                        // Update the teachers reference to point to the new array
                        teachers = newTeachers;
                    }

                    // Add the teacher to the teachers array
                    teachers[teacherCount] = teacher;
                    teacherCount++;

                    successfulUploads++;
                } else {
                    // Handle invalid data format
                    failedUploads++;
                }
            }

            // Show success and error alerts
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Carga Exitosa");
            successAlert.setHeaderText(null);
            successAlert.setContentText("¡Carga de profesores exitosa!\n"
                    + "Cargas exitosas: " + successfulUploads + "\n"
                    + "Cargas fallidas: " + failedUploads);
            successAlert.showAndWait();
        } catch (IOException e) {
            // Show error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Se produjo un error al cargar los profesores: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public static void exportTeachersToHTML(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the HTML file header
            writer.write("<html>");
            writer.newLine();
            writer.write("<head><title>Lista de Profesores</title></head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>Lista de Profesores</h1>");
            writer.newLine();
            writer.write("<table>");
            writer.newLine();
            writer.write("<tr><th>Código</th><th>Nombre</th><th>Apellido</th><th>Email</th><th>Género</th></tr>");
            writer.newLine();

            // Get the teachers from the teachers array
            Teacher[] teachers = getTeachers();

            // Write the teacher data to the HTML file
            for (Teacher teacher : teachers) {
                if (teacher != null) {
                    writer.write("<tr>");
                    writer.newLine();
                    writer.write("<td>" + teacher.getCode() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + teacher.getFirstName() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + teacher.getLastName() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + teacher.getEmail() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + teacher.getGender() + "</td>");
                    writer.newLine();
                    writer.write("</tr>");
                    writer.newLine();
                }
            }

            // Write the HTML file footer
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
            writer.newLine();

            System.out.println("¡Profesores exportados a HTML exitosamente!");

        } catch (IOException e) {
            System.out.println("Error al exportar profesores a HTML: " + e.getMessage());
        }
    }
    
    // Course methods

    public static void addCourse(int code, String name, int credits, int students, String teacher, int teacherCode) {
        if (code == -1) {
            code = courseCount + 1; // Auto-increment the code based on the current course count
        }

        Course course = new Course(code, name, credits, students, teacher, teacherCode);

        // Check if there is enough space in the courses array
        if (courseCount == courses.length) {
            // Resize the array
            Course[] newCourses = new Course[courses.length * 2];

            // Copy the existing courses to the new array
            System.arraycopy(courses, 0, newCourses, 0, courseCount);

            // Update the courses reference to point to the new array
            courses = newCourses;
        }

        // Add the course to the courses array
        courses[courseCount] = course;
        courseCount++;
    }

    public static Course[] getCourses() {
        return courses;
    }

    public static void deleteCourse(Course course) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null && courses[i].getCode() == course.getCode()) {
                courses[i] = null; // Set the element to null to delete it
                break; // Exit the loop once the course is found and deleted
            }
        }
    }

    public static void updateCourse(Course course) {
        for (int i = 0; i < courses.length; i++) {
            if (courses[i] != null && courses[i].getCode() == course.getCode()) {
                // Update the course's information
                courses[i].setName(course.getName());
                courses[i].setCredits(course.getCredits());
                courses[i].setStudents(course.getStudents());
                courses[i].setTeacher(course.getTeacher());
                break; // Exit the loop once the course is found and updated
            }
        }
    }

    public static void uploadCoursesFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip the header row
            reader.readLine();

            String line;
            int successfulUploads = 0;
            int failedUploads = 0;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 6) {  // Updated data length to 6
                    int code;
                    String name = data[0];
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
                        // Handle invalid data format
                        failedUploads++;
                        continue;
                    }

                    // Create a new course object
                    Course course = new Course(code, name, credits, students, teacher, teacherCode);

                    // Check if there is enough space in the courses array
                    if (courseCount == courses.length) {
                        // Resize the array
                        Course[] newCourses = new Course[courses.length * 2];

                        // Copy the existing courses to the new array
                        System.arraycopy(courses, 0, newCourses, 0, courseCount);

                        // Update the courses reference to point to the new array
                        courses = newCourses;
                    }

                    // Add the course to the courses array
                    courses[courseCount] = course;
                    courseCount++;

                    successfulUploads++;
                } else {
                    // Handle invalid data format
                    failedUploads++;
                }
            }

            // Show success and error alerts
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Carga Exitosa");
            successAlert.setHeaderText(null);
            successAlert.setContentText("¡Carga de cursos exitosa!\n"
                    + "Cargas exitosas: " + successfulUploads + "\n"
                    + "Cargas fallidas: " + failedUploads);
            successAlert.showAndWait();
        } catch (IOException e) {
            // Show error alert
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Se produjo un error al cargar los cursos: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    public static void exportCoursesToHTML(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the HTML file header
            writer.write("<html>");
            writer.newLine();
            writer.write("<head><title>Lista de Cursos</title></head>");
            writer.newLine();
            writer.write("<body>");
            writer.newLine();
            writer.write("<h1>Lista de Cursos</h1>");
            writer.newLine();
            writer.write("<table>");
            writer.newLine();
            writer.write("<tr><th>Código</th><th>Nombre</th><th>Créditos</th><th>Estudiantes</th><th>Profesor</th></tr>");
            writer.newLine();

            // Get the courses from the courses array
            Course[] courses = getCourses();

            // Write the course data to the HTML file
            for (Course course : courses) {
                if (course != null) {
                    writer.write("<tr>");
                    writer.newLine();
                    writer.write("<td>" + course.getCode() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + course.getName() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + course.getCredits() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + course.getStudents() + "</td>");
                    writer.newLine();
                    writer.write("<td>" + course.getTeacher() + "</td>");
                    writer.newLine();
                    writer.write("</tr>");
                    writer.newLine();
                }
            }

            // Write the HTML file footer
            writer.write("</table>");
            writer.newLine();
            writer.write("</body>");
            writer.newLine();
            writer.write("</html>");
            writer.newLine();

            System.out.println("¡Cursos exportados a HTML exitosamente!");

        } catch (IOException e) {
            System.out.println("Error al exportar cursos a HTML: " + e.getMessage());
        }
    }
}
