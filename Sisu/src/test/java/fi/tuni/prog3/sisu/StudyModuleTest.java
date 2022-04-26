/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

import java.util.ArrayList;
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
public class StudyModuleTest {
    
    public StudyModuleTest() {
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
     * Test of getGroupId method, of class StudyModule.
     */
    @Test
    public void testGetGroupId() {
        StudyModule instance = new StudyModule("yhteinen", "q123");
        String expResult = "q123";
        String result = instance.getGroupId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class StudyModule.
     */
    @Test
    public void testGetName() {

        StudyModule instance = new StudyModule("yhteinen", "q123");
        String expResult = "yhteinen";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCourses method, of class StudyModule.
     */
    @Test
    public void testGetCourses() {

        StudyModule instance = new StudyModule("yhteinen", "q123");
        ArrayList<CourseUnit> expResult = new ArrayList<>();
        CourseUnit course = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        instance.addCourse(course);
        expResult.add(course);
        ArrayList<CourseUnit> result = instance.getCourses();
        assertArrayEquals(expResult.toArray(), result.toArray());

    }

    /**
     * Test of getModules method, of class StudyModule.
     */
    @Test
    public void testGetModules() {

        StudyModule instance = new StudyModule("yhteinen", "q123");
        ArrayList<StudyModule> expResult = new ArrayList<>();
        StudyModule mod = new StudyModule("omat", "c123");
        expResult.add(mod);
        instance.addModule(mod);        
        ArrayList<StudyModule> result = instance.getModules();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of addCourse method, of class StudyModule.
     */
    @Test
    public void testAddCourse() {
        StudyModule instance = new StudyModule("yhteinen", "q123");
        ArrayList<CourseUnit> expResult = new ArrayList<>();
        CourseUnit course = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        expResult.add(course);
        instance.addCourse(course);
        ArrayList<CourseUnit> result = instance.getCourses();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of addModule method, of class StudyModule.
     */
    @Test
    public void testAddModule() {
        StudyModule instance = new StudyModule("yhteinen", "q123");
        ArrayList<StudyModule> expResult = new ArrayList<>();
        StudyModule mod = new StudyModule("omat", "c123");
        expResult.add(mod);
        instance.addModule(mod);        
        ArrayList<StudyModule> result = instance.getModules();
        assertArrayEquals(expResult.toArray(), result.toArray());
    }
    
}
