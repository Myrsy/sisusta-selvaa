package fi.tuni.prog3.sisu;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;


public class SearchToolTest {
    
    /**
     * Test of searchDegreeURL method, of class SearchTool.
     */
    @Test
    public void testSearchDegreeURL() throws Exception {

        String groupId = "otm-16bba6b3-0557-4ac8-b33a-6acdcf7d6e23";
        String filename = "testSearchDegreeURL.json";
        SearchTool.searchDegreeURL(groupId, filename);
        DegreeObjectData.jsonFileToObjects(filename);
        HashMap<String, DegreeProgramme> degMap = DegreeObjectData.getDegreeMap();
        
        Files.deleteIfExists(Paths.get(filename));

        assertEquals(groupId, degMap.get(groupId).getGroupId());
        
    }
    
}
