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
public class GroupingModuleTest {
    
    public GroupingModuleTest() {
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
     * Test of getName method, of class GroupingModule.
     */
    @Test
    public void testGetName() {

        GroupingModule instance = new GroupingModule("yhteinen", "q123");
        String expResult = "yhteinen";
        String result = instance.getName();
        assertEquals(expResult, result);

    }

    /**
     * Test of getGroupId method, of class GroupingModule.
     */
    @Test
    public void testGetGroupId() {
        GroupingModule instance = new GroupingModule("yhteinen", "q123");
        String expResult = "q123";
        String result = instance.getGroupId();
        assertEquals(expResult, result);
    }
    
}
