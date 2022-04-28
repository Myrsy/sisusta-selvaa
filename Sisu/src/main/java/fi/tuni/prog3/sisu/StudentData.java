
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Emma
 */
public class StudentData {
    
    private static final String STUDENTS_TO_JSON_FILENAME = "sudentsfile.txt";
    private HashMap<String, Student> students;
    
    public StudentData(){
        this.students = new HashMap<>();
    }
    
    public void addStudent(Student student) throws IOException{
        this.students.put(student.getStudentNumber(), student);
        studentsToFile();
    }
    
    public boolean searchStudent(String studentNumber){
        if(students.containsKey(studentNumber)){
            return true;
        }
        return false;
    }
    
    public void getOldStudents(){
        
    }
    
    public void studentsToFile() throws IOException {
        
        try (FileWriter fw = new FileWriter(STUDENTS_TO_JSON_FILENAME, Charset.forName("UTF-8"))){
            ArrayList<Student> studentObjs = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (String id: students.keySet()) {
                studentObjs.add(students.get(id));
            }
            gson.toJson(studentObjs, fw);
            
        }
    }
    
}
