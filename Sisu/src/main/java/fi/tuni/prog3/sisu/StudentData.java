
package fi.tuni.prog3.sisu;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
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
    private static HashMap<String, Student> students;
    
    public StudentData(){
        students = new HashMap<>();
    }
    
    public static void addStudent(Student student) throws IOException{
        students.put(student.getStudentNumber(), student);
//        System.out.println(student.getDegreeProgramme().toString());
        studentsToFile();
    }
    
    public static void updateStudent(Student student) throws IOException {
        String studentNum = student.getStudentNumber();
        if(students.get(studentNum) != null) {
            students.replace(studentNum, student);
            System.out.println("Päivitetty");
            studentsToFile();

        }else{
            System.out.println("Opiskelijaa " + student.getStudentNumber() 
                    + " ei löytynyt");
        }
    }
    
    public boolean searchStudent(String studentNumber){
        if(students.containsKey(studentNumber)){
            return true;
        }
        return false;
    }
    
    public void getOldStudents() throws FileNotFoundException, IOException{
        // HUOM! jokaisen lisäyksen jälkeen kirjoitetaan tiedosto uusiksi
        // pitäisiköhän tehdä paremmin?
        //Gson gson = new Gson();
        
        
        //gsonBuilder.registerTypeAdapter(Student.class, new StudentCreator());
        Gson gson = new Gson(); //gsonBuilder.create();
        
        try (Reader reader = new FileReader(STUDENTS_TO_JSON_FILENAME)) {
           Student[] studentsList = gson.fromJson(reader, Student[].class);
            if (studentsList != null) {
                for (Student student: studentsList) {
                    student.setDegreeProgramme(student.getDegreeGroupId());
                    addStudent(student);
                    System.out.println(student.getName() + 
                            "\n groupId " + student.getDegreeGroupId() +
                            "\n tutkinto " + student.getDegreeProgramme());
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Tiedostoa " + STUDENTS_TO_JSON_FILENAME + 
                    " ei löytynyt, joten se luodaan.");
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
