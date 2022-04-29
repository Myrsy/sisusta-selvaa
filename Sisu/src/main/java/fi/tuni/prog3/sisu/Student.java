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
    private transient DegreeProgramme degreeProgramme;
    private String degreeGroupId;
    private HashMap<CourseUnit, Integer> courses;
    
    public Student(String name, String studentNumber, DegreeProgramme degreeProgramme){
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.degreeGroupId = degreeProgramme.getGroupId();
        this.courses = new HashMap<>();
       
    }

    public String getName() {
        return name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
    
   public DegreeProgramme getDegreeProgramme() {
        return degreeProgramme;//new DegreeObjectData().getDegreeMap().get(degreeGroupId);
    }
    
    public void setDegreeProgramme(DegreeProgramme prog) {
        if(!this.degreeGroupId.equals(prog.getGroupId())){
            courses.clear();
        }
        this.degreeProgramme = prog;
        this.degreeGroupId = prog.getGroupId();
        
    }

    public HashMap<CourseUnit, Integer> getCourses() {
        return courses;
    }

    public void addCourse(CourseUnit course, Integer grade) {
        courses.put(course, grade);
    }
  
    
    public double getProgression(){
        double sumCredits = 0.0;
        for(var course : this.courses.keySet()){
            sumCredits += course.getMinCredits();
        }
        return sumCredits / this.degreeProgramme.getMinCredits();
    }
    
    public Integer getCompletedCredits(){
        int sumCredits = 0;
        for(var course : this.courses.keySet()){
            sumCredits += course.getMinCredits();
        }
        
        return sumCredits;
    }
    
    public double getGPA(){
        double sumCompleted = 0.00;
        double sumGrade = 0.00;
        for (Map.Entry<CourseUnit, Integer> course : courses.entrySet()){
            sumCompleted += course.getKey().getMinCredits();
            sumGrade += course.getValue()*course.getKey().getMinCredits();
        }
        
        return sumGrade/sumCompleted;
    
    }
    
    
}
