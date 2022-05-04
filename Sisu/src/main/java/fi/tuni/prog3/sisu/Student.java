package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.util.ArrayList;


/**
 * A class for saving student's information.
 */
public class Student {
    
    private String name;
    private String studentNumber;
    private transient DegreeProgramme degreeProgramme;
    private String degreeGroupId;
    private ArrayList<CourseUnit> courses;
    
    /**
     * Constructs a Student object. Courses will be added by calling 
     * {@link #addCourse(fi.tuni.prog3.sisu.CourseUnit) addCourse(CourseUnit)}.
     * @param name student's name
     * @param studentNumber student's studentnumber
     * @param degreeProgramme student's degree programme
     */
    public Student(String name, String studentNumber, DegreeProgramme 
            degreeProgramme) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.degreeProgramme = degreeProgramme;
        this.degreeGroupId = degreeProgramme.getGroupId();
        this.courses = new ArrayList<>();
       
    }

    /**
     * Sets student's degree programme. The correct degree programme is fetched 
     * by calling {@link DegreeObjectData#getDegreeMap() 
     * DegreeObjectData.getDegreeMap()}.
     * @param groupId the groupId of desired degree programme.
     * @throws IOException if there is an IO error.
     */
    public void setDegreeProgramme(String groupId) throws IOException {
        if (DegreeObjectData.getDegreeMap().get(groupId) == null) {
            SearchTool.searchDegreeURL(groupId);
        }
        this.degreeProgramme = DegreeObjectData.getDegreeMap().get(groupId);
    }

    /**
     * Returns the name of the student.
     * @return the name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the student number of the student.
     * @return the student number of the student.
     */
    public String getStudentNumber() {
        return studentNumber;
    }
    
    /**
     * Returns the degree programme of the student.
     * @return the degree programme of the student.
     */
    public DegreeProgramme getDegreeProgramme() {
        return degreeProgramme;
    }
   
    /**
     * Returns the groupId of the student's degree programme.
     * @return the groupId of the student's degree programme.
     */
    public String getDegreeGroupId(){
        return degreeGroupId;
    }
    
    /**
     * Changes the student's degree programme. If the new degree programme is 
     * different than the old one, ie. the groupId of the new degree differs
     * from the old degree's groupId, all the student's courses are cleared and
     * the new {@link DegreeProgramme} will be set as the student's 
     * degreeProgramme. Also student's degreeGroupId will be changed accordingly.
     * @param prog student's new degree programme.
     */
    public void changeDegreeProgramme(DegreeProgramme prog) {
        if (!this.degreeGroupId.equals(prog.getGroupId())) {
            courses.clear();
        }
        this.degreeProgramme = prog;
        this.degreeGroupId = prog.getGroupId();
        
    }

    /**
     * Returns the courses that the student has completed.
     * @return the courses that the student has completed.
     */
    public ArrayList<CourseUnit> getCourses() {
        return courses;
    }

    /**
     * Adds a new course to the list of courses that the student has completed.
     * Before adding the course to the list, it is checked that the new course 
     * if not already in the list by calling {@link CourseUnit#getGroupId() 
     * CourseUnit.getGroupId()} and comparing the groupId of the new course to
     * the groupIds of the completed courses.
     * @param newCourse course that student has completed.
     */
    public void addCourse(CourseUnit newCourse) {
        Boolean alreadyCompleted = false;
        
        for (CourseUnit completedCourse: this.courses) {
            if (completedCourse.getGroupId().equals(newCourse.getGroupId())) {
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
  
    /**
     * Retuns the student's progression in terms of percentages. The progression 
     * is calculated by summing all the credits of the courses that the student 
     * has completed and dividing the sum by the minimum credit requirement of 
     * the student's degree programme.
     * @return studen'ts progression in terms of percentages.
     */
    public double getProgression(){
        double sumCredits = 0.0;
        for (CourseUnit course : this.courses){
            sumCredits += course.getCredits();
        }
        
        return sumCredits / this.degreeProgramme.getMinCredits();
    }
    
    /**
     * Returns the sum of the completed credits.
     * @return the sum of the completed credits.
     */
    public Integer getCompletedCredits(){
        int sumCredits = 0;
        for(CourseUnit course : this.courses){
            sumCredits += course.getCredits();
        }
        
        return sumCredits;
    }
    
    /**
     * Returns the student's GPA. The courses that have fail-pass-grading 
     * (ie. the method {@link CourseUnit#getCredits() CourseUnit.getCredits()} 
     * returns -1) are ignored.
     * @return the student's GPA
     */
    public double getGPA(){
        double sumCompleted = 0.00;
        double sumGrade = 0.00;

        for (CourseUnit course: courses) {
            if (course.getGrade() != -1) {
                sumCompleted += course.getCredits();
                sumGrade += course.getGrade()*course.getCredits();
            }
        }
        
        return sumGrade/sumCompleted;
    
    }
    
    
}
