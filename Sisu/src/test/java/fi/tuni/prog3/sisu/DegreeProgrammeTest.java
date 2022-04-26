/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.tuni.prog3.sisu;

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
public class DegreeProgrammeTest {
    
    public DegreeProgrammeTest() {
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
     * Test of getName method, of class DegreeProgramme.
     */
    @Test
    public void testGetName() {

        DegreeProgramme instance = new DegreeProgramme("Tuta", "tuta111", 180);
        String expResult = "Tuta";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getGroupId method, of class DegreeProgramme.
     */
    @Test
    public void testGetGroupId() {
        DegreeProgramme instance = new DegreeProgramme("Tuta", "tuta111", 180);
        String expResult = "tuta111";
        String result = instance.getGroupId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getMinCredits method, of class DegreeProgramme.
     */
    @Test
    public void testGetMinCredits() {
        
        DegreeProgramme instance = new DegreeProgramme("Tuta", "tuta111", 180);
        int expResult = 180;
        int result = instance.getMinCredits();
        assertEquals(expResult, result);

    }

    /**
     * Test of addStudyModule method, of class DegreeProgramme.
     */
    @Test
    public void testAddStudyModule() {

        StudyModule module = new StudyModule("yhteinen", "q123");
        DegreeProgramme instance = new DegreeProgramme("Tuta", "tuta111", 180);
        instance.addStudyModule(module);
        
        ArrayList<StudyModule> array = instance.getModules();
        ArrayList<StudyModule> exp = new ArrayList<>();
        exp.add(module);
        
        assertArrayEquals(exp.toArray(), array.toArray());
        

    }

    /**
     * Test of toString method, of class DegreeProgramme.
     */
    @Test
    public void testToString() {
        DegreeProgramme instance = new DegreeProgramme("Tuta", "tuta111", 180);
        String expResult = "Tuta";;
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}
