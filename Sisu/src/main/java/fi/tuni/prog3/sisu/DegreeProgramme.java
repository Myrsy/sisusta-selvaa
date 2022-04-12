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
public class DegreeProgramme extends Module{
    
    private String name;
    private String groupId;
    private int minCredits;
    
    public DegreeProgramme(String name, String groupId, int minCredits){
        this.name = name;
        this.groupId = groupId;
        this.minCredits = minCredits;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    public int getMinCredits() {
        return minCredits;
    }
    
    
        
}
