/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

/**
 *
 * @author pinja
 */
public class CourseUnit {
    
    private final String name;
    private final String groupId;
    private final String code;
    private final int credits;
    private final int grade;

    
    public CourseUnit(String name, String groupId, String code, int credits, int grade) {
        this.name = name;
        this.groupId = groupId;
        this.code = code;
        this.credits = credits;
        this.grade = grade;
    }
    


    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }
    
    
    public int getGrade() {
        return grade;
    }
    
}
