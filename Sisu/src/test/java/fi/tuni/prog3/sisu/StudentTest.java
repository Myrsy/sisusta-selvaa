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
    public void testGetDegreeProgramme() {

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

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        String groupId = "otm-16bba6b3-0557-4ac8-b33a-6acdcf7d6e23";
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.setDegreeProgramme(groupId);
                
        assertEquals(instance.getDegreeProgramme().getGroupId(), groupId);
      
    }

    /**
     * Test of getCourses method, of class Student.
     *
     */
    @Test
    public void testGetCourses() {

        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 10, 3);
        instance.addCourse(tetapk);
        instance.addCourse(tijopk);
        ArrayList<CourseUnit> expResult = new ArrayList<>();
        expResult.add(tetapk);
        expResult.add(tijopk);
        ArrayList<CourseUnit> result = instance.getCourses();
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
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 5, 4);
        CourseUnit tetapk2 = new CourseUnit("tijopk2", "tuta321", 5, 1);
        CourseUnit tijopk2 = new CourseUnit("tijopk2", "tijo321", 5, 3);
        instance.addCourse(tetapk);
        instance.addCourse(tijopk);
        instance.addCourse(tetapk2);
        instance.addCourse(tijopk2);
        Double expResult = 0.1111111;
        Double result = instance.getProgression();
        
        assertEquals(expResult, result, 0.00001);

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
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 5, 4);
        instance.addCourse(tetapk);
        instance.addCourse(tijopk);
        double result = instance.getGPA();
        
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of getDegreeGroupId method, of class Student.
     */
    @Test
    public void testGetDegreeGroupId() {
        
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        String expResult = "tuta111";
        String result = instance.getDegreeGroupId();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of changeDegreeProgramme method, of class Student.
     */
    @Test
    public void testChangeDegreeProgramme() {
        
        DegreeProgramme deg = new DegreeProgramme("Tijo", "tijo111", 180);
        DegreeProgramme prog = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.changeDegreeProgramme(prog);
        DegreeProgramme result = instance.getDegreeProgramme();
        
        assertEquals(result, prog);
    }


    /**
     * Test of getCompletedCredits method, of class Student.
     */
    @Test
    public void testGetCompletedCredits() {
        
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        CourseUnit tetapk = new CourseUnit("tetapk", "tuta123", 5, 5);
        CourseUnit tijopk = new CourseUnit("tijopk", "tijo123", 5, 4);
        instance.addCourse(tetapk);
        instance.addCourse(tijopk);
        int expResult = 10;
        int result = instance.getCompletedCredits();
        
        assertEquals(result, expResult);
    }
    
    
}
