/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.Expose;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author pinja
 */
public class Student {
    
    private String name;
    private String studentNumber;
    private transient DegreeProgramme degreeProgramme;
    private String degreeGroupId;
    private ArrayList<CourseUnit> courses;
    
    
    public Student(String name, String studentNumber, DegreeProgramme degreeProgramme){
        System.out.println("Opiskelijan " + name + " ensimmäinen rakentaja");
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.degreeGroupId = degreeProgramme.getGroupId();
        this.courses = new ArrayList<>();
       
    }

 
    public void setDegreeProgramme(String groupId) {
        this.degreeProgramme = DegreeObjectData.getDegreeMap().get(groupId);
    }

    public String getName() {
        return name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }
    
   public DegreeProgramme getDegreeProgramme() throws IOException {
        return degreeProgramme;//new DegreeObjectData().getDegreeMap().get(degreeGroupId);
    }
   
    public String getDegreeGroupId(){
        return degreeGroupId;
    }
    
    public void changeDegreeProgramme(DegreeProgramme prog) {
        if(!this.degreeGroupId.equals(prog.getGroupId())){
            courses.clear();
        }
        this.degreeProgramme = prog;
        this.degreeGroupId = prog.getGroupId();
        
    }

    public ArrayList<CourseUnit> getCourses() {
        return courses;
    }

    public void addCourse(CourseUnit newCourse) {//, Integer grade) {

        Boolean alreadyCompleted = false;
        for (CourseUnit completedCourse: courses) {
            if (completedCourse.getName().equals(newCourse.getName())) {
                alreadyCompleted = true;
            }
        }
        
        if (alreadyCompleted) {
            System.out.println("kurssi " + newCourse.toString() + " on jo käyty");
        } else {
            courses.add(newCourse);
            System.out.println("kurssi " + newCourse.toString() + " lisätty");
        }
      
        
    }
  
    
    public double getProgression(){
        double sumCredits = 0.0;
        for(var course : this.courses){
            sumCredits += course.getMinCredits();
        }
        return sumCredits / this.degreeProgramme.getMinCredits();
    }
    
    public Integer getCompletedCredits(){
        int sumCredits = 0;
        for(var course : this.courses){
            sumCredits += course.getMinCredits();
        }
        
        return sumCredits;
    }
    
    public double getGPA(){
        double sumCompleted = 0.00;
        double sumGrade = 0.00;

        for (CourseUnit course: courses) {
            sumCompleted += course.getMinCredits();
            sumGrade += course.getGrade()*course.getMinCredits();
        }
        
        return sumGrade/sumCompleted;
    
    }
    
    
}
