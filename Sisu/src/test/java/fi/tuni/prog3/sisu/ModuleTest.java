package fi.tuni.prog3.sisu;

import static org.junit.Assert.*;
import org.junit.Test;


public class ModuleTest {
    
    /**
     * Test of getGroupId method, of class Module.
     */
    @Test
    public void testGetGroupId() {
        Module instance = new Module("yhteinen", "q123");
        String expResult = "q123";
        String result = instance.getGroupId();
        assertEquals(expResult, result);

    }

    /**
     * Test of getName method, of class Module.
     */
    @Test
    public void testGetName() {

        Module instance = new Module("yhteinen", "q123");
        String expResult = "yhteinen";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
}
