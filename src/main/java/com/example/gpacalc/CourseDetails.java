package com.example.gpacalc;

import javafx.beans.property.*;

public class CourseDetails {
    private final StringProperty name;
    private final DoubleProperty credit;
    private final StringProperty grade;
    private final StringProperty code;


    public CourseDetails(String name,Double credit,String grade,String code) {
        this.name = new SimpleStringProperty(name);
        this.credit =new SimpleDoubleProperty(credit);
        this.grade = new SimpleStringProperty(grade);
        this.code = new SimpleStringProperty(code);

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
}


