
package fi.tuni.prog3.sisu;


public class StudyModule extends Module {
    private String groupId;
    private String name;
    
    public StudyModule(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
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
