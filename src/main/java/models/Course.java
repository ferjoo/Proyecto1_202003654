package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    private final IntegerProperty code;
    private final StringProperty name;
    private final IntegerProperty credits;
    private final IntegerProperty students;
    private final StringProperty teacher;
    private final IntegerProperty teacherCode;

    public Course(int code, String name, int credits, int students, String teacher, int teacherCode) {
        this.code = new SimpleIntegerProperty(code);
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleIntegerProperty(credits);
        this.students = new SimpleIntegerProperty(students);
        this.teacher = new SimpleStringProperty(teacher);
        this.teacherCode = new SimpleIntegerProperty(teacherCode);
    }

    public int getCode() {
        return code.get();
    }

    public IntegerProperty codeProperty() {
        return code;
    }

    public void setCode(int code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getCredits() {
        return credits.get();
    }

    public IntegerProperty creditsProperty() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits.set(credits);
    }

    public int getStudents() {
        return students.get();
    }

    public IntegerProperty studentsProperty() {
        return students;
    }

    public void setStudents(int students) {
        this.students.set(students);
    }

    public String getTeacher() {
        return teacher.get();
    }

    public StringProperty teacherProperty() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher.set(teacher);
    }
    
    public int getTeacherCode() {
        return teacherCode.get();
    }

    public IntegerProperty teacherCodeProperty() {
        return teacherCode;
    }

    public void setTeacherCode(int teacherCode) {
        this.teacherCode.set(teacherCode);
    }
}
