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
 * A class for storing information of all the students. Because all the methods
 * are static, it is not necessary to instantiate the class as an object in order
 * to get access to the methods.
 */
public class StudentData {
    
    private static final String STUDENTS_TO_JSON_FILENAME = "studentsfile.json";
    private static HashMap<String, Student> students = new HashMap<>();
        
    /**
     * Adds a new student to the map {@link #students} that keeps track of all 
     * the students. The student's student number is added as a key and the 
     * corresponding {@link Student} object is added as the value.
     * @param student the student that will be added to the 
     * {@link #students} map.
     */
    public static void addStudent(Student student) {
        students.put(student.getStudentNumber(), student);        
    }
    
    /**
     * Changes student's degree programme. First the method checks if the 
     * desired student is in the {@link #students} map by comparing student 
     * numbers. If the student is in the map, the student's method 
     * {@link Student#changeDegreeProgramme(fi.tuni.prog3.sisu.DegreeProgramme) 
     * Student.chageDegreeProgramme(DegreeProgramme)} is called. 
     * Otherwise this method does nothing.
     * @param student the student whose degree is being changed.
     * @param degree student's new degree.
     */
    public static void changeStudentProgramme(Student student, DegreeProgramme degree) {
        if (students.keySet().contains(student.getStudentNumber())) {
            students.get(student.getStudentNumber()).changeDegreeProgramme(degree);
        } else {
            System.out.println("Opiskelijaa " + student.getStudentNumber() 
                    + " ei löytynyt");
        }
    }
    
    
    /**
     * Reads student data from the {@link #STUDENTS_TO_JSON_FILENAME} file and 
     * adds the students to the {@link #students} map by calling the 
     * {@link #addStudent(fi.tuni.prog3.sisu.Student) addStudent(Student)} 
     * method. Because the students' whole nested degree programmes are not 
     * stored in the {@link #STUDENTS_TO_JSON_FILENAME} file, the 
     * {@link Student#setDegreeProgramme(java.lang.String) 
     * Student.setDegreeProgramme(degreeGroupId)} is called to set the nested 
     * degree programme.
     * <p>
     * The student file is excepted to contain student's name, studentNumber, 
     * degreeGroupId and courses in Json format. The program doesn't handle 
     * erroneous fields. However, the file can be completely empty. 
     * If the file doesn't exist when starting the program, it will be created.
     * @throws IOException if there is an IO error.
     */
    public static void getOldStudents() throws IOException {

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
    
    /**
     * Writes student objects from {@link #students} map to the 
     * {@link #STUDENTS_TO_JSON_FILENAME} file. All the objects are written at 
     * once and the existing file will be overwritten.
     * @throws IOException if there is an IO error.
     */
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

    /**
     * Returns student object with specified student number if the 
     * {@link #students} map contains the specified student number. Otherwise 
     * returns null.
     * @param studentNumber student number of the student that is being searched.
     * @return {@link Student} object if the student is in the map, otherwise 
     * return null.
     */
    public static Student getStudent(String studentNumber){
        if (students.containsKey(studentNumber)) {
            return students.get(studentNumber);
        }
        return null;
    }
    
}
