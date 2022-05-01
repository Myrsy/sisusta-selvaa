/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import com.google.gson.InstanceCreator;
import com.google.gson.annotations.Expose;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author pinja
 */
public class Student {
    
    private String name;
    private String studentNumber;
    // Tätä ei taideta luoda automaattisesti tiedostosta, 
    // ongelmia ainakin sisun getOldStudentsissa
    // transient
    private transient DegreeProgramme degreeProgramme;
    private String degreeGroupId;
    private String degreeName;
    private HashMap<CourseUnit, Integer> courses;
    private transient DegreeObjectData degreeData = new DegreeObjectData();
    
    
    public Student(String name, String studentNumber, DegreeProgramme degreeProgramme){
        System.out.println("Opiskelijan " + name + " ensimmäinen rakentaja");
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.degreeGroupId = degreeProgramme.getGroupId();
        this.degreeName = degreeProgramme.getName();
        this.courses = new HashMap<>();
       
    }

 
    public void setDegreeProgramme(String groupId) {
        this.degreeProgramme = new DegreeObjectData().getDegreeMap().get(groupId);
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

    public HashMap<CourseUnit, Integer> getCourses() {
        return courses;
    }

    public void addCourse(CourseUnit course, Integer grade) {
        if(courses.isEmpty()){
            courses.put(course, grade);
        }
        for(var course1 : this.courses.keySet()){
            if(!(course1.getName().equals(course.getName()))){
                courses.put(course, grade);
            }
        }
       
        
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
