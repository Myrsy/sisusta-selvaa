package fi.tuni.prog3.sisu;

import java.util.ArrayList;


public class StudyModule extends Module {
    private String groupId;
    private String name;
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
}
