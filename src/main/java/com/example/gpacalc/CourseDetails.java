package com.example.gpacalc;

import javafx.beans.property.*;
import javafx.scene.control.TextField;

public class CourseDetails {
    private final StringProperty name;
    private final DoubleProperty credit;
    private final StringProperty grade;
    private final StringProperty code;
    private final StringProperty teacher1;
    private final StringProperty teacher2;


    public CourseDetails(String name, Double credit, String grade, String code, String teacher1, String teacher2) {
        this.name = new SimpleStringProperty(name);
        this.credit =new SimpleDoubleProperty(credit);
        this.grade = new SimpleStringProperty(grade);
        this.code = new SimpleStringProperty(code);
        this.teacher1 = new SimpleStringProperty(teacher1);
        this.teacher2 = new SimpleStringProperty(teacher2);


    }



    public String getName() {
        return name.get();
    }
    public double getCredit() {
        return credit.get();
    }
    public String getGrade() {
        return grade.get();
    }
    public String getCode() {
        return code.get();
    }
    public String getTeacher1() {
        return teacher1.get();
    }
    public String getTeacher2(){
        return teacher2.get();
    }



    public StringProperty nameProperty() {
        return name;
    }
    public DoubleProperty creditProperty() {
        return credit;
    }
    public StringProperty gradeProperty() {
        return grade;
    }
    public StringProperty codeProperty() {
        return code;
    }
    public StringProperty T1Property() {
        return teacher1;
    }
    public StringProperty T2Property() {
        return teacher2;
    }

}


