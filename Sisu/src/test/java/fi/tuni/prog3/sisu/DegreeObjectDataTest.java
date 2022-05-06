package fi.tuni.prog3.sisu;

import java.util.HashMap;
import static org.junit.Assert.*;
import org.junit.Test;

public class DegreeObjectDataTest {
    
    
    /**
     * Test of jsonFileToObjects and getDegreeMap methods,
     * of class DegreeObjectData.
     */
    @Test
    public void testAddProgrammeGetDegreeMap() {
        
        String id1 = "tuta111";
        String id2 = "tuto222";
        DegreeProgramme degree1 = new DegreeProgramme("Tuta", id1, 180);
        DegreeProgramme degree2 = new DegreeProgramme("Tuto", id2, 180);
        DegreeObjectData.addProgramme(degree1);
        DegreeObjectData.addProgramme(degree2);
        HashMap<String, DegreeProgramme> degMap = DegreeObjectData.getDegreeMap();
        
        assertEquals(degree1, degMap.get(id1));
        assertEquals(degree2, degMap.get(id2));
    }

}
