package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;


public class DegreeObjectData {
    
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.txt";
    private static final String OBJECTS_TO_JSON_FILENAME = "objsJson.txt";
    private static HashMap<String, DegreeProgramme> degreeProgrammes = new HashMap<>();

    
    public void jsonFileToObjects() throws FileNotFoundException, IOException {
        
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FULL_DEGREES_FILENAME)) {
           DegreeProgramme[] progs = gson.fromJson(reader, DegreeProgramme[].class);
            if (progs != null) {
                for (DegreeProgramme prog: progs) {
                    addProgramme(prog);
                }
            }
        }
    }
    
    public void jsonArrayToObject(JsonArray jsonArray) {
        
        Gson gson = new Gson();
        DegreeProgramme prog = gson.fromJson(jsonArray, DegreeProgramme[].class)[0];
        addProgramme(prog);               
    }
    
    public void objectsToJson() throws IOException {     
                
        try (FileWriter fw = new FileWriter(OBJECTS_TO_JSON_FILENAME, Charset.forName("UTF-8"))){
            ArrayList<DegreeProgramme> degreeObjs = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            for (String id: degreeProgrammes.keySet()) {
                System.out.println(id);
                degreeObjs.add(degreeProgrammes.get(id));
            }
            gson.toJson(degreeObjs, fw);
            
        }
    }
    
    public void addProgramme(DegreeProgramme prog){
        degreeProgrammes.put(prog.getGroupId(), prog);
    }
    
    public void addStudyModuleToDegree(String groupIdParent, StudyModule mod){
        degreeProgrammes.get(groupIdParent).addStudyModule(mod);
    }
    
    public static  HashMap<String, DegreeProgramme> getDegreeMap(){
        return degreeProgrammes;
    }
            
}
