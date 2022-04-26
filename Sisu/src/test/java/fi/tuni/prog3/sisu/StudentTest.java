/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

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
    public void testSetDegreeProgramme() {

        DegreeProgramme deg = new DegreeProgramme("Tijo", "tijo111", 180);
        DegreeProgramme prog = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.setDegreeProgramme(prog);
        
        assertEquals(instance.getDegreeProgramme(), prog);
      
    }

    /**
     * Test of getCourses method, of class Student.
     */
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

        CourseUnit tetapk = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        CourseUnit tijopk = new CourseUnit("tijopk", "zz3", "tijo123", 5, 
             "jee", "pöö", "1-5");
        Integer grade = 4;
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        instance.addCourse(tetapk, grade);
        
        HashMap map = instance.getCourses();
        
        HashMap<CourseUnit, Integer> expected = new HashMap<>();
        expected.put(tetapk, 4);
        
        assertThat(map, is(expected));
        assertThat(map.size(), is(1));
        assertThat(map, IsMapContaining.hasEntry(tetapk, 4));
        assertThat(map, not(IsMapContaining.hasEntry(tijopk, 5)));
        assertThat(map, IsMapContaining.hasKey(tetapk));
        assertThat(map, IsMapContaining.hasValue(4));

    }

 
    /**
     * Test of getProgression method, of class Student.
     */
    @Test
    public void testGetProgression() {
        
        DegreeProgramme deg = new DegreeProgramme("Tuta", "tuta111", 180);
        Student instance = new Student("Jaakko", "Q123", deg);
        Integer expResult = 5/180;
        CourseUnit tetapk = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        instance.addCourse(tetapk, 5);
        Integer result = instance.getProgression();
        
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
        CourseUnit tetapk = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        instance.addCourse(tetapk, 5);
        CourseUnit tijopk = new CourseUnit("tijopk", "zz3", "tijo123", 5, 
             "jee", "pöö", "1-5");
        instance.addCourse(tijopk, 4);
        double result = instance.getGPA();
        assertEquals(expResult, result, 0.00);
        
    }
    
}
