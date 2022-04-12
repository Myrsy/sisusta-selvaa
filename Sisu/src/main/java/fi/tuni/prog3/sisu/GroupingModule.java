package fi.tuni.prog3.sisu;

public class GroupingModule extends DegreeProgramme {
    
    private String name;
    private String groupId; 

    public GroupingModule(String name, String groupId) {
        this.name = name;
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public String getGroupId() {
        return groupId;
    }
    
    
}
