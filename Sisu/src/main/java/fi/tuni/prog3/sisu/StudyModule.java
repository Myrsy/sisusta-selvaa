package fi.tuni.prog3.sisu;

import java.util.ArrayList;

/**
 * A class for storing the degree programme's information about its submodules.
 * {@link StudyModule} objects will be instantiated by Gson by desrerializing
 * Json. A {@link StudyModule} object will store all its submodules and courses 
 * as a {@link StudyModule}. Since a {@link StudyModule} object represents all
 * the other types of modules, rules and courses, apart from degree programme,
 * that are found from the API, there might be unnecessary fields that are not
 * used when instantiating a object. Calling a getter on such field will return
 * null. 
 */
public class StudyModule {
    private String allMandatory;
    private String type;
    private String groupId;
    private String name;
    private String code;
    private String gradeScaleId;
    private String minCredits;
    private String maxCredits;
    private String content;
    private String outcomes;
    private String description;
    private String minRequire;
    private String maxRequire;
    private ArrayList<StudyModule> modules;
    
    /**
     * Construct a {@link StudyModule} object. Since Gson will deserialize
     * all the {@link StudyModule} objects without constructor, the constructor 
     * is specified only for creating instances for JUnit tests.
     * @param name the name of the module.
     * @param groupId the groupId of the module.
     */
    public StudyModule(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
        this.modules = new ArrayList<>();
    }
    
    /**
     * Returns the groupId of the module.
     * @return the groupId of the module.
     */
    public String getGroupId(){
        return groupId;
    }
    
    /**
     * Returns the name of the module or null if the module doesn't have a name.
     * @return the name of the module or null if the module doesn't have a name.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Overrides the toString method to return the name of the module. If there
     * is no name (as there won't be when the module represents a grouping 
     * module, credits rule, composite rule or module rule) the method checks
     * whether there is a value for {@link #allMandatory}. This will happen if 
     * the module represents a composite rule. If there is no name or value
     * for {@link #allMandatory} the method returns en empty string.
     * @return the name, instructions if all submodules are mandatory, or empty 
     * string based on what the module represents.
     */
    @Override
    public String toString(){
        if (name != null) {
            return name;  
        } else if (allMandatory != null && allMandatory.equals("true")) {
            return "Kaikki suoritettava";
        } else if (allMandatory != null && allMandatory.equals("false")) {
            return "Valitse osa";
        }
        
        return "";
    }

    /**
     * Returns the list of submodules of the module.
     * @return the list of submodules of the module.
     */
    public ArrayList<StudyModule> getModules() {
        return modules;
    }
    
    /**
     * Returns the boolean if all the submodules are mandatory as a string.
     * @return the boolean if all the submodules are mandatory as a string.
     */
    public String getAllMandatory() {
        return allMandatory;
    }

    /**
     * Returns the type of the module.
     * @return the type of the module.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the code of the module.
     * @return the code of the module.
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the grading scale of the module.
     * @return the grading scale of the module.
     */
    public String getGradeScaleId() {
        return gradeScaleId;
    }

    /**
     * Returns the minimum amount of credits that is required to complete the 
     * module.
     * @return the minimum amount of credits that is required to complete the
     * module.
     */
    public String getMinCredits() {
        return minCredits;
    }
    
    /**
     * Returns the maximum amount of credits that is required to complete the 
     * module. Returns 999 if there is no maximum value.
     * @return the maximum amount of credits that is required to complete the 
     * module or 999 if there is no maximum value.
     */
    public String getMaxCredits() {
        return maxCredits;
    }

    /**
     * Returns the content of the module.
     * @return the content of the module.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the outcomes of the module.
     * @return the outcomes of the module.
     */
    public String getOutcomes() {
        return outcomes;
    }
    
    /**
     * Returns the description of the module.
     * @return the description of the module.
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Returns the minimum amount of submodules that needs to be picked.
     * @return the minimum amount of submodules that needs to be picked.
     */
    public String getMinRequire() {
        return minRequire;
    }
    
    /**
     * Returns the maximum amount of submodules that needs to be picked.
     * @return Returns the maximum amount of submodules that needs to be picked.
     */
    public String getMaxRequire() {
        return maxRequire;
    }
    
}
