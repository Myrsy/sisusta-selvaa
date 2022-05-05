/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pinja
 */
public class StudentDataTest {
    
    public StudentDataTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addStudent method, of class StudentData.
     */
    @Test
    public void testAddStudent() throws IOException {
        System.out.println("addStudent");
        String studentNumber = "";
        DegreeProgramme tuta = new DegreeProgramme("Tuta", "tuta111", 180);
        Student student = new Student("Jaakko", "H123", tuta);
        StudentData instance = new StudentData();
        instance.addStudent(student);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * Test of changeStudentProgramme method, of class StudentData.
     */
    @Test
    public void testChangeStudentProgramme() throws Exception {
        System.out.println("changeStudentProgramme");
        Student student = null;
        DegreeProgramme degree = null;
        StudentData.changeStudentProgramme(student, degree);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of studentsToFile method, of class StudentData.
     */
    @Test
    public void testStudentsToFile() throws Exception {
        System.out.println("studentsToFile");
        StudentData.studentsToFile();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStudent method, of class StudentData.
     */
    @Test
    public void testGetStudent() {
        System.out.println("getStudent");
        String studentNumber = "";
        Student expResult = null;
        Student result = StudentData.getStudent(studentNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
