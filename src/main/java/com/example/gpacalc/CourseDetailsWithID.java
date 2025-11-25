package com.example.gpacalc;

public class CourseDetailsWithID extends CourseDetails {
    private int id;

    public CourseDetailsWithID(int id, String name, Double credit, String grade, String code, String teacher1, String teacher2) {
        super(name, credit, grade, code, teacher1, teacher2);
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
