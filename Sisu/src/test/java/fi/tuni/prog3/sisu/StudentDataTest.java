/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

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
    public void testAddStudent() {
        System.out.println("addStudent");
        String studentNumber = "";
        Student student = null;
        StudentData instance = new StudentData();
        instance.addStudent(studentNumber, student);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchStudent method, of class StudentData.
     */
    @Test
    public void testSearchStudent() {
        System.out.println("searchStudent");
        String studentNumber = "";
        StudentData instance = new StudentData();
        boolean expResult = false;
        boolean result = instance.searchStudent(studentNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOldStudents method, of class StudentData.
     */
    @Test
    public void testGetOldStudents() {
        System.out.println("getOldStudents");
        StudentData instance = new StudentData();
        instance.getOldStudents();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
