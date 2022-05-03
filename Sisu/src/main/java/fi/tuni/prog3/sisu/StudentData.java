
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Emma
 */
public class StudentData {
    
    private static final String STUDENTS_TO_JSON_FILENAME = "studentsfile.txt";
    private static HashMap<String, Student> students = new HashMap<>();
    
    public StudentData(){
    }
    
    public static void addStudent(Student student) throws IOException{
        students.put(student.getStudentNumber(), student);        
    }
    
    public static void changeStudentProgramme(Student student, DegreeProgramme degree) throws IOException {
        if (students.keySet().contains(student.getStudentNumber())) {
            students.get(student.getStudentNumber()).changeDegreeProgramme(degree);
           // studentsToFile();
        } else {
            System.out.println("Opiskelijaa " + student.getStudentNumber() 
                    + " ei löytynyt");
        }
    }
    
    public static boolean searchStudent(String studentNumber){
        if(students.containsKey(studentNumber)){
            return true;
        }
        return false;
    }
    
    public static void getOldStudents() throws FileNotFoundException, IOException{

        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        
        try (Reader reader = new FileReader(STUDENTS_TO_JSON_FILENAME, Charset.forName("UTF-8"))) {
           Student[] studentsList = gson.fromJson(reader, Student[].class);
            if (studentsList != null) {
                for (Student student: studentsList) {
                    student.setDegreeProgramme(student.getDegreeGroupId());
                    addStudent(student);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Tiedostoa " + STUDENTS_TO_JSON_FILENAME + 
                    " ei löytynyt, joten se luodaan.");
        }
        
    }
    
    public static void studentsToFile() throws IOException {
        
        try (FileWriter fw = new FileWriter(STUDENTS_TO_JSON_FILENAME, Charset.forName("UTF-8"))){
            ArrayList<Student> studentObjs = new ArrayList<>();
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            for (String id: students.keySet()) {
                studentObjs.add(students.get(id));
            }            
            gson.toJson(studentObjs, fw);
           
        }
    }

    public static Student getStudent(String studentNumber){
        return students.get(studentNumber);
    }
    
}
