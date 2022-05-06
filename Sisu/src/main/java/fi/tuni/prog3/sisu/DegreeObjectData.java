package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;


/**
 * A class for storing information about the degree programmes. Because all the 
 * methods are static, it is not necessary to instantiate the class as an object 
 * in order to get access to the methods.
 */
public class DegreeObjectData {
    
    private static final String ISO_STRING = "ISO-8859-15";
    private static HashMap<String, DegreeProgramme> 
            degreeProgrammes = new HashMap<>();

    /**
     * Reads degree programme data from the specified file
     * and adds the corresponding {@link DegreeProgramme} objects to the 
     * {@link #degreeProgrammes} map by calling 
     * {@link #addProgramme(fi.tuni.prog3.sisu.DegreeProgramme) 
     * addProgramme(DegreeProgramme)} method.
     * <p>
     * The file is excepted to contain the full nested degree trees in 
     * JSON format. The program doesn't handle erroneous fields. If the file is
     * empty, doesn't exist, or if some degree programme that a student has 
     * chosen is missing from the file when starting the program, all the 
     * missing degree programmes will be searched from the API. 
     * @param filename the file that will be read.
     */
    public static void jsonFileToObjects(String filename) {
                   
        try (Reader reader = new FileReader(filename, 
                Charset.forName(ISO_STRING))) {
           Gson gson = new GsonBuilder().disableHtmlEscaping()
                   .setPrettyPrinting().create();
           DegreeProgramme[] progs = gson.fromJson(reader, 
                   DegreeProgramme[].class);
            if (progs != null) {
                for (DegreeProgramme prog: progs) {
                    addProgramme(prog);
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Tiedostoa " + filename + 
                    " ei löytynyt, joten tutkinnot haetaan API:sta");
        } catch (IOException ex) {
            System.err.println("Error: " + ex);
        }
    }
    
    /**
     * Creates a {@link DegreeProgramme} object from the specified JsonArray and
     * adds the created object to {@link #degreeProgrammes} map by calling
     * {@link #addProgramme(fi.tuni.prog3.sisu.DegreeProgramme) 
     * addProgramme(DegreeProgramme)} method. 
     * <p>
     * The JsonArray is excpected to contain one or several 
     * {@link DegreeProgramme} objects in Json format. The program doesn't handle 
     * erroneous JsonArray. 
     * @param jsonArray JsonArray that will be converted to degree object.
     */
    public static void jsonArrayToObject(JsonArray jsonArray) {
        
        Gson gson = new GsonBuilder().disableHtmlEscaping()
                .setPrettyPrinting().create();
        DegreeProgramme prog = gson.fromJson(jsonArray, 
                DegreeProgramme[].class)[0];
        addProgramme(prog);               
    }
        
    /**
     * Adds a new degree programme to the {@link #degreeProgrammes} that keeps
     * track of all the degree programmes. The degree programme's groupId is
     * added as a key and the corresponding {@link DegreeProgramme} object is 
     * added as the value.
     * @param prog the degree programme that will be added to the 
     * {@link #degreeProgrammes} map.
     */
    public static void addProgramme(DegreeProgramme prog){
        degreeProgrammes.put(prog.getGroupId(), prog);
    }
        
    /**
     * Returns the {@link #degreeProgrammes} map.     *
     * @return the {@link #degreeProgrammes} map.
     */
    public static  HashMap<String, DegreeProgramme> getDegreeMap(){
        return degreeProgrammes;
    }
    
    
}
