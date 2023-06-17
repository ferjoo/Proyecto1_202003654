package models;

import java.util.Arrays;
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
    private Student[] studentsArray;
    private CourseActivity[] courseActivitiesArray;

    public Course(int code, String name, int credits, int students, String teacher, int teacherCode) {
        this.code = new SimpleIntegerProperty(code);
        this.name = new SimpleStringProperty(name);
        this.credits = new SimpleIntegerProperty(credits);
        this.students = new SimpleIntegerProperty(students);
        this.teacher = new SimpleStringProperty(teacher);
        this.teacherCode = new SimpleIntegerProperty(teacherCode);
        this.studentsArray = new Student[0]; // Initialize an empty array of students
        this.courseActivitiesArray = new CourseActivity[0]; // Initialize an empty array of course activities
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
    
        public Student[] getStudentsArray() {
        return studentsArray;
    }

    public void setStudentsArray(Student[] studentsArray) {
        this.studentsArray = studentsArray;
    }

    public CourseActivity[] getCourseActivitiesArray() {
        return courseActivitiesArray;
    }

    public void setCourseActivitiesArray(CourseActivity[] courseActivitiesArray) {
        this.courseActivitiesArray = courseActivitiesArray;
    }
    
    public void addStudent(Student student) {
        Student[] newStudentsArray = new Student[studentsArray.length + 1];
        for (int i = 0; i < studentsArray.length; i++) {
            newStudentsArray[i] = studentsArray[i];
        }
        newStudentsArray[studentsArray.length] = student;
        studentsArray = newStudentsArray;
    }

    public void addCourseActivity(CourseActivity courseActivity) {
        CourseActivity[] newCourseActivitiesArray = new CourseActivity[courseActivitiesArray.length + 1];
        for (int i = 0; i < courseActivitiesArray.length; i++) {
            newCourseActivitiesArray[i] = courseActivitiesArray[i];
        }
        newCourseActivitiesArray[courseActivitiesArray.length] = courseActivity;
        courseActivitiesArray = newCourseActivitiesArray;
    }
    
    public void deleteStudent(Student student) {
        int index = -1;
        for (int i = 0; i < studentsArray.length; i++) {
            if (studentsArray[i].equals(student)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Student[] updatedStudentsArray = new Student[studentsArray.length - 1];
            int counter = 0;
            for (int i = 0; i < studentsArray.length; i++) {
                if (i != index) {
                    updatedStudentsArray[counter] = studentsArray[i];
                    counter++;
                }
            }
            studentsArray = updatedStudentsArray;
        }
    }


}
