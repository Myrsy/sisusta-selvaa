package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


public class DegreeObjectData {
    
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.txt";
    private static final String OBJECTS_TO_JSON_FILENAME = "objsJson.txt";
    private static final String ISO_STRING = "ISO-8859-15";
    private static HashMap<String, DegreeProgramme> degreeProgrammes = new HashMap<>();

    
    public static void jsonFileToObjects() throws FileNotFoundException, IOException {
                   
        try (Reader reader = new FileReader(FULL_DEGREES_FILENAME, Charset.forName(ISO_STRING))) {
           Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
           DegreeProgramme[] progs = gson.fromJson(reader, DegreeProgramme[].class);
            if (progs != null) {
                for (DegreeProgramme prog: progs) {
                    addProgramme(prog);
                }
            }
        }
    }
    
    public static void jsonArrayToObject(JsonArray jsonArray) {
        
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        DegreeProgramme prog = gson.fromJson(jsonArray, DegreeProgramme[].class)[0];
        addProgramme(prog);               
    }
    
    public static void objectsToJson() throws IOException {     
                
        try (FileWriter fw = new FileWriter(OBJECTS_TO_JSON_FILENAME, Charset.forName(ISO_STRING))){
            ArrayList<DegreeProgramme> degreeObjs = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (String id: degreeProgrammes.keySet()) {
                degreeObjs.add(degreeProgrammes.get(id));
            }
            gson.toJson(degreeObjs, fw);
            
        }
    }
    
    public static void addProgramme(DegreeProgramme prog){
        degreeProgrammes.put(prog.getGroupId(), prog);
    }
    
    public static void addStudyModuleToDegree(String groupIdParent, StudyModule mod){
        degreeProgrammes.get(groupIdParent).addStudyModule(mod);
    }
    
    public static  HashMap<String, DegreeProgramme> getDegreeMap(){
        return degreeProgrammes;
    }
            
}
