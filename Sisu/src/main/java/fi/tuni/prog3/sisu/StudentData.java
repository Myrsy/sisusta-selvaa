
package fi.tuni.prog3.sisu;


import java.util.HashMap;


/**
 *
 * @author Emma
 */
public class StudentData {
    
    private HashMap<String, Student> students;
    
    public StudentData(){
        this.students = new HashMap<>();
    }
    
    public void addStudent(String studentNumber, Student student){
        this.students.put(studentNumber, student);
    }
    
    public boolean searchStudent(String studentNumber){
        if(students.containsKey(studentNumber)){
            return true;
        }
        return false;
    }
    
    public void getOldStudents(){
        
    }
    
}
