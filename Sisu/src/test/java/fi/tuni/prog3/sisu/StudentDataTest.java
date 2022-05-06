package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;
import org.junit.Test;


public class StudentDataTest {

    /**
     * Test of addStudent method, of class StudentData.
     */
    @Test
    public void testAddAndGetStudent() throws IOException {

        DegreeProgramme degree = new DegreeProgramme("Tuta", "tuta111", 180);
        Student student = new Student("Jaakko", "H123", degree);
        StudentData.addStudent(student);
        Student result = StudentData.getStudent("H123");
        
        assertEquals(result, student);
    }


    /**
     * Test of changeStudentProgramme method, of class StudentData.
     * @throws java.lang.Exception
     */
    @Test
    public void testChangeStudentProgramme() throws Exception {
        
        DegreeProgramme degree1 = new DegreeProgramme("Tuta", "tuta111", 180);
        DegreeProgramme degree2 = new DegreeProgramme("Tuto", "tuto222", 180);
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        Student student = new Student("Jaakko", "H123", degree1);
        student.addCourse(tetapk);
        StudentData.addStudent(student);
        
        StudentData.changeStudentProgramme(student, degree2);
        String expResult = "tuto222";
        String result = student.getDegreeGroupId();
        
        // Student's courses reset after changing the degree.
        assertFalse(student.getCourses().size() == 1);
        assertEquals(expResult, result);
    }

    /**
     * Test of studentsToFile method, of class StudentData.
     * @throws java.lang.Exception
     */
    @Test
    public void testStudentsToFileToObjects() throws Exception {

        DegreeProgramme degree1 = new DegreeProgramme(
                "Teatterityön kandidaattiohjelma", "uta-tohjelma-1748", 180);
        DegreeProgramme degree2 = new DegreeProgramme(
                "Tekniikan ja luonnontieteiden kandidaattiohjelma", 
                "otm-16bba6b3-0557-4ac8-b33a-6acdcf7d6e23", 180);
        Student student1 = new Student("Jaakko", "H123", degree1);
        Student student2 = new Student("Jaana", "K432", degree2);
        StudentData.addStudent(student1);
        StudentData.addStudent(student2);
        String filename = "testStudentsToFileToObjects.json";
        StudentData.studentsToFile(filename);
        StudentData.getOldStudents(filename);
        Student result1 = StudentData.getStudent("H123");
        Student result2 = StudentData.getStudent("K432");
        
        Files.deleteIfExists(Paths.get(filename));
        
        assertEquals(student1.getName(), result1.getName());
        assertEquals(student2.getName(), result2.getName());
    }

    
}
