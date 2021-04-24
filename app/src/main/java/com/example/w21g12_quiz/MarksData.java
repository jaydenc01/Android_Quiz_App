package com.example.w21g12_quiz;

public class MarksData {

    String className;
    double grade;

    public MarksData(String className, double grade) {
        this.className = className;
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
