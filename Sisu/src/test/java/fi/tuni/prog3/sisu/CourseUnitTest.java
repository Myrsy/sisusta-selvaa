package fi.tuni.prog3.sisu;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CourseUnitTest {
    
    /**
     * Test of getName method, of class CourseUnit.
     */
    @Test
    public void testGetName() {

        CourseUnit instance = new CourseUnit("tetapk", "tuta123", 5, 5);
        String expResult = "tetapk";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getCode method, of class CourseUnit.
     */
    @Test
    public void testGetCode() {
        CourseUnit instance = new CourseUnit("tetapk", "tuta123", 5, 5);
        String expResult = "tuta123";
        String result = instance.getCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCredits method, of class CourseUnit.
     */
    @Test
    public void testGetCredits() {
        CourseUnit instance = new CourseUnit("tetapk", "tuta123", 5, 5);
        int expResult = 5;
        int result = instance.getCredits();
        assertEquals(expResult, result);
    }

    /**
     * Test of getGrade method, of class CourseUnit.
     */
    @Test
    public void testGetGrade() {
        CourseUnit instance = new CourseUnit("tetapk", "tuta123", 5, 5);
        int expResult = 5;
        int result = instance.getGrade();
        assertEquals(expResult, result);
    }
    
}
