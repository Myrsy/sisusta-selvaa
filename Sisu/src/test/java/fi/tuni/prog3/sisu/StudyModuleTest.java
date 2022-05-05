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


    
}
