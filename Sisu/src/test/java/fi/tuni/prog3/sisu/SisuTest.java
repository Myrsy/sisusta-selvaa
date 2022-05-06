
package fi.tuni.prog3.sisu;


import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.junit.Test;

import org.testfx.framework.junit.ApplicationTest;

/**
 *
 * @author PC
 */
public class SisuTest extends ApplicationTest {

    
    
    /**
     * Test of Buttons, of class Sisu.
     */
    @Test
    public void checkButtons() {
        String[] queries = {"#btnAddStudent", "#btnLogIn", "#btnNewStudent"};
        String[] texts = {"Lisää opiskelija", "Kirjaudu sisään", "Uusi opiskelija"};
        for(int i = 0; i < queries.length; i++) {
        Button node = fromAll().lookup(queries[i]).query();
        assertTextsEqual(node.getText(), texts[i], queries[i].substring(1));
        }
    }
    
    

    /**
     * Test of start method, of class Sisu.
     */
    @Test
    public void testStart() throws Exception {
        System.out.println("start");
        Stage stage = null;
        Sisu instance = new Sisu();
        instance.start(stage);
        
    }

    

}
