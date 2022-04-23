package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;


public class DegreeObjectData {
    
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.txt";
    private DegreeProgramme[] degreeObjs;
    private HashMap<String, DegreeProgramme> degreeProgrammes;

    public DegreeObjectData() {
        this.degreeProgrammes = new HashMap<>();
    }
    
    public void JsonToObjects() throws FileNotFoundException, IOException {
        
        Gson gson = new Gson();
        try (Reader reader = new FileReader(FULL_DEGREES_FILENAME)) {
           degreeObjs = gson.fromJson(reader, DegreeProgramme[].class);
        }   
    }
    
    public void writeObjsToJson() throws IOException {     
                
        try (FileWriter fw = new FileWriter("objsJson.txt", Charset.forName("UTF-8"))){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(degreeObjs, fw);
            
        }
    }
    
    public void addProgramme(DegreeProgramme prog){
        degreeProgrammes.put(prog.getGroupId(), prog);
    }
    
    public void addStudyModuleToDegree(String groupIdParent, StudyModule mod){
        this.degreeProgrammes.get(groupIdParent).addStudyModule(mod);
    }
        
}
