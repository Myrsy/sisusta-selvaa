/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author pinja
 */
public class Student {
    
    private final String name;
    private final String studentNumber;
    private DegreeProgramme degreeProgramme;
    private int minCredits;
    private HashMap<CourseUnit, Integer> courses;
    
    public Student(String name, String studentNumber, DegreeProgramme degreeProgramme){
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.courses = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
    
    public DegreeProgramme getDegreeProgramme() {
        return degreeProgramme;
    }
    
    public void setDegreeProgramme(DegreeProgramme prog) {
        this.degreeProgramme = prog;
    }

    public HashMap<CourseUnit, Integer> getCourses() {
        return courses;
    }

    public void addCourse(CourseUnit course, Integer grade) {
        courses.put(course, grade);
    }

    public int getMinCredits() {
        return minCredits;
    }

    public void setMinCredits(int minCredits) {
        this.minCredits = minCredits;
    }
    
    
    
    public Integer getProgression(){
        int sumCredits = 0;
        for(var course : courses.keySet()){
            sumCredits += course.getMinCredits();
        }
        return sumCredits / this.degreeProgramme.getMinCredits();
    }
    
    public double getGPA(){
        double sumCompleted = 0.0;
        double sumGrade = 0.0;
        for (Map.Entry<CourseUnit, Integer> course : courses.entrySet()){
            sumCompleted += course.getKey().getMinCredits();
            sumGrade += course.getValue()*course.getKey().getMinCredits();
        }
        
        return sumGrade/sumCompleted;
    
    }
    
    
}
