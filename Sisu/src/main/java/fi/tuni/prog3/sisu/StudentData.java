
package fi.tuni.prog3.sisu;

import java.util.ArrayList;


/**
 *
 * @author Emma
 */
public class StudentData {
    
    private ArrayList<String> students;
    
    public StudentData(){
        this.students = new ArrayList<>();
    }
    
    public void addStudent(String studentNumber){
        this.students.add(studentNumber);
    }
    
    public boolean searchStudent(String studentNumber){
        for(String student : students){
            if(student.equals(studentNumber)){
               return true; 
            }
        }
        return false;
    }
    
}
