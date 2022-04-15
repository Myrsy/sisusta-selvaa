/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import java.util.HashMap;


/**
 *
 * @author pinja
 */
public class Student {
    
    private String name;
    private String studentNumber;
    private String degreeProgramme;
    private HashMap<CourseUnit, Integer> courses;
    
    public Student(String name, String studentNumber, String degreeProgramme){
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.courses = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public HashMap<CourseUnit, Integer> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<CourseUnit, Integer> courses) {
        this.courses = courses;
    }
    
    
}
