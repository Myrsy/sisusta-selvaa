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
    
    private String name;
    private String groupId;
    private String code;
    private int minCredits;
    private int maxCredits;
    private int grade;
    private String content;
    private String outcomes;
    private String gradeScaleId;

    public CourseUnit(String name, String groupId, String code, int minCredits, int maxCredits, 
             int grade, String content, String outcomes, String gradeScaleId) {
        this.name = name;
        this.groupId = groupId;
        this.code = code;
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
        this.grade = grade;
        this.content = content;
        this.outcomes = outcomes;
        this.gradeScaleId = gradeScaleId;
    }
    
    public CourseUnit(String name, String groupId, String code, int minCredits, int maxCredits, int grade) {
        this.name = name;
        this.groupId = groupId;
        this.code = code;
        this.minCredits = minCredits;
        this.maxCredits = maxCredits;
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

    public int getMinCredits() {
        return minCredits;
    }
    
    public int getMaxCredits() {
        return maxCredits;
    }
    
    public int getGrade() {
        return grade;
    }
    
    public String getContent() {
        return content;
    }

    public String getOutcomes() {
        return outcomes;
    }
    
    public String getGradeScaleId() {
        return gradeScaleId;
    }
}
