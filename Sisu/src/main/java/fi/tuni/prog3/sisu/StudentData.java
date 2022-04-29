
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Emma
 */
public class StudentData {
    
    private static final String STUDENTS_TO_JSON_FILENAME = "sudentsfile.txt";
    private static HashMap<String, Student> students;
    
    public StudentData(){
        students = new HashMap<>();
    }
    
    public static void addStudent(Student student) throws IOException{
        students.put(student.getStudentNumber(), student);
        System.out.println(students + " " + students.keySet());
        studentsToFile();
    }
    
    public boolean searchStudent(String studentNumber){
        if(students.containsKey(studentNumber)){
            return true;
        }
        return false;
    }
    
    public static void getOldStudents() throws FileNotFoundException, IOException{
        // HUOM! jokaisen lisäyksen jälkeen kirjoitetaan tiedosto uusiksi
        // pitäisiköhän tehdä paremmin?
        Gson gson = new Gson();
        try (Reader reader = new FileReader(STUDENTS_TO_JSON_FILENAME)) {
           Student[] studentsList = gson.fromJson(reader, Student[].class);
            if (studentsList != null) {
                for (Student student: studentsList) {
                    addStudent(student);
                }
            }
        }
        
    }
    
    private static void studentsToFile() throws IOException {
        
        try (FileWriter fw = new FileWriter(STUDENTS_TO_JSON_FILENAME, Charset.forName("UTF-8"))){
            ArrayList<Student> studentObjs = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
