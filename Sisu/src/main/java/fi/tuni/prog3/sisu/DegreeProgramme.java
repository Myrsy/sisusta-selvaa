package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for saving degree programme's information. {@lin DegreeProgramme}
 * objects will be created by Gson by deserializing Json. 
 * A {@link DegreeProgramme} object will store all the submodules as a 
 * {@link StudyModule}. 
 */
public class DegreeProgramme {
    
    private String name;
    private String groupId;
    private String code;
    private int minCredits; 
    private int maxCredits;
    private String learningOutcomes;
    private ArrayList<StudyModule> modules;

    /**
     * Construct a {@link DegreeProgramme} object. Since Gson will deserialize
     * all the {@link DegreeProgramme} objects, the constructor is only for
     * JUnit tests.
     * @param name the name of the degree programme.
     * @param groupId the gourpId of the degree programme.
     * @param minCredits the minimum amount of credits that is required to 
     * complete the degree programme.
     */
    public DegreeProgramme(String name, String groupId, int minCredits){
        this.name = name;
        this.groupId = groupId;
        this.minCredits = minCredits;
        this.modules = new ArrayList<>();
    }

    /**
     * Returns the name of the degree programme.
     * @return the name of the degree programme.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the groupId of the degree programme.
     * @return the groupId of the degree programme.
     */
    public String getGroupId() {
        return groupId;
    }
    
    /**
     * Returns the code of the degree programme.
     * @return the code of the degree programme.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the minimum amount of credits that is required to complete the 
     * degree programme.
     * @return the minimum amount of credits that is required to complete the
     * degree programme.
     */
    public int getMinCredits() {
        return minCredits;
    }
    
    /**
     * Returns the maximum amount of credits that is required to complete the 
     * degree programme. Returns 999 if there is no maximum value.
     * @return the maximum amount of credits that is required to complete the 
     * degree programme or 999 if there is no maximum value.
     */
    public int getMaxCredits() {
        return maxCredits;
    }
    
    /**
     * Overrides the toString method to return the name of the degree programme.
     * @return the name of the degree programme.
     */
    @Override
    public String toString(){
        return this.name;
    }
    
    /**
     * Returns the learning outcomes of the degree programme.
     * @return the learning outcomes of the degree programme.
     */
    public String getLearningOutcomes() {
        return learningOutcomes;
    }

    /**
     * Returns the list of submodules of the degree programme.
     * @return the list of submodules of the degree programme.
     */
    public ArrayList<StudyModule> getModules() {
        return modules;
    }

    
    
        
}
