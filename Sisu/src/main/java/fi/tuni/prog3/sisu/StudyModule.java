package fi.tuni.prog3.sisu;

import java.util.ArrayList;


public class StudyModule extends Module {
    private String allMandatory;
    private String type;
    private String groupId;
    private String name;
    private String code;
    private String gradeScaleId;
    private String minCredits;
    private String content;
    private String outcomes;
    private ArrayList<CourseUnit> courses;
    private ArrayList<StudyModule> modules;
    
    public StudyModule(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
        this.courses = new ArrayList<>();
        this.modules = new ArrayList<>();
    }
    
    @Override
    public String getGroupId(){
        return this.groupId;
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    @Override
    public String toString(){
        return this.name;
    }

    public ArrayList<CourseUnit> getCourses() {
        return courses;
    }

    public ArrayList<StudyModule> getModules() {
        return modules;
    }
    
    public void addCourse(CourseUnit course){
        courses.add(course);
    }
    
    public void addModule(StudyModule mod){
        this.modules.add(mod);
    }
    
    
}
