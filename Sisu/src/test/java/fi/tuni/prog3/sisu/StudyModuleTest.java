package fi.tuni.prog3.sisu;

import static org.junit.Assert.*;
import org.junit.Test;


public class StudyModuleTest {
    
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
