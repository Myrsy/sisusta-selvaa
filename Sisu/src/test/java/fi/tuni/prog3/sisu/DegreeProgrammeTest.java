package fi.tuni.prog3.sisu;

import static org.junit.Assert.*;
import org.junit.Test;


public class DegreeProgrammeTest {
    
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
    
}
