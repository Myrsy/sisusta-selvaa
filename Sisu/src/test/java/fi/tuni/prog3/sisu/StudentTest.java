/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.hamcrest.collection.IsMapContaining;
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
public class StudentTest {
    
    public StudentTest() {
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
     * Test of getName method, of class Student.
     */
    @Test
    public void testGetName() {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        String expResult = "Jaakko";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getStudentNumber method, of class Student.
     */
    @Test
    public void testGetStudentNumber() {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        String expResult = "Q123";
        String result = instance.getStudentNumber();
        assertEquals(expResult, result);
   
    }

    /**
     * Test of getDegreeProgramme method, of class Student.
     */
    @Test
    public void testGetDegreeProgramme() throws IOException {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        DegreeProgramme expResult = deg;
        DegreeProgramme result = instance.getDegreeProgramme();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setDegreeProgramme method, of class Student.
     */
    @Test
    public void testSetDegreeProgramme() throws IOException {

        DegreeProgramme deg = new DegreeProgramme("Tijo", "tijo111", 180);
        DegreeProgramme prog = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.changeDegreeProgramme(prog);
        
        assertEquals(instance.getDegreeProgramme(), prog);
      
    }

    /**
     * Test of getCourses method, of class Student.
     */
    /*
    @Test
    public void testGetCourses() {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        CourseUnit tetapk = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
            "jee", "pöö", "1-5");
        CourseUnit tijopk = new CourseUnit("tijopk", "zz3", "tijo123", 5, 
             "jee", "pöö", "1-5");
        instance.addCourse(tetapk, 4);
        instance.addCourse(tijopk, 5);
        HashMap<CourseUnit, Integer> expResult = new HashMap<>();
        expResult.put(tetapk, 4);
        expResult.put(tijopk, 5);
        HashMap<CourseUnit, Integer> result = instance.getCourses();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of addCourse method, of class Student.
     */
    @Test
    public void testAddCourse() {

        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 5,5);
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.addCourse(tetapk);
        instance.addCourse(tijopk);
        
        ArrayList result = instance.getCourses();
        
        ArrayList<CourseUnit> expResult = new ArrayList<>();
        expResult.add(tetapk);
        expResult.add(tijopk);
        
        assertArrayEquals(expResult.toArray(), result.toArray());


    }

 
    /**
     * Test of getProgression method, of class Student.
     */
    @Test
    public void testGetProgression() {
        
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        Integer expResult = 5/180;
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        instance.addCourse(tetapk);
        Double result = instance.getProgression();
        
        assertEquals(expResult, result);

    }

    /**
     * Test of getGPA method, of class Student.
     */
    @Test
    public void testGetGPA() {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        double expResult = 4.50;
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        instance.addCourse(tetapk);
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 5, 4);
        instance.addCourse(tijopk);
        double result = instance.getGPA();
        assertEquals(expResult, result, 0.00);
        
    }

    /**
     * Test of getDegreeGroupId method, of class Student.
     */
    @Test
    public void testGetDegreeGroupId() {
        System.out.println("getDegreeGroupId");
        Student instance = null;
        String expResult = "";
        String result = instance.getDegreeGroupId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeDegreeProgramme method, of class Student.
     */
    @Test
    public void testChangeDegreeProgramme() {
        System.out.println("changeDegreeProgramme");
        DegreeProgramme prog = null;
        Student instance = null;
        instance.changeDegreeProgramme(prog);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCourses method, of class Student.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        Student instance = null;
        ArrayList<CourseUnit> expResult = null;
        ArrayList<CourseUnit> result = instance.getCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCompletedCredits method, of class Student.
     */
    @Test
    public void testGetCompletedCredits() {
        System.out.println("getCompletedCredits");
        Student instance = null;
        Integer expResult = null;
        Integer result = instance.getCompletedCredits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
}
