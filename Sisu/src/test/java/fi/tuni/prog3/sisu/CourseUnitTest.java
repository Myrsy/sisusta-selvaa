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
public class CourseUnitTest {
    
    public CourseUnitTest() {
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
     * Test of getName method, of class CourseUnit.
     */
    @Test
    public void testGetName() {

        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "tetapk";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getGroupId method, of class CourseUnit.
     */
    @Test
    public void testGetGroupId() {

        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "zz2";
        String result = instance.getGroupId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCode method, of class CourseUnit.
     */
    @Test
    public void testGetCode() {
        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "tuta123";
        String result = instance.getCode();
        assertEquals(expResult, result);

    }

    /**
     * Test of getMinCredits method, of class CourseUnit.
     */
    @Test
    public void testGetMinCredits() {

        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        int expResult = 5;
        int result = instance.getMinCredits();
        assertEquals(expResult, result);

    }

    /**
     * Test of getContent method, of class CourseUnit.
     */
    @Test
    public void testGetContent() {

        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "jee";
        String result = instance.getContent();
        assertEquals(expResult, result);

    }

    /**
     * Test of getOutcomes method, of class CourseUnit.
     */
    @Test
    public void testGetOutcomes() {
        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "pöö";
        String result = instance.getOutcomes();
        assertEquals(expResult, result);

    }

    /**
     * Test of getGradeScaleId method, of class CourseUnit.
     */
    @Test
    public void testGetGradeScaleId() {
        CourseUnit instance = new CourseUnit("tetapk", "zz2", "tuta123", 5, 
             "jee", "pöö", "1-5");
        String expResult = "1-5";
        String result = instance.getGradeScaleId();
        assertEquals(expResult, result);

    }
    
}
