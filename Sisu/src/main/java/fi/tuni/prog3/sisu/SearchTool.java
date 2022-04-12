package fi.tuni.prog3.sisu;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;



        


/**
 *
 * @author pinja
 */
public class SearchTool {
    
    public SearchTool(){
        
    }
    
    public void searchDegreeProgrammesURL() throws IOException {        
        
        try {
            File file = new File("degreeprogrammesfile.txt");
            file.createNewFile(); 
                        
            URL url = new URL("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000");
            String data = new String(url.openStream().readAllBytes());   
            System.out.println(data);
            
            JsonObject json = new JsonParser().parse(data).getAsJsonObject();
            JsonArray searchResults = json.getAsJsonArray("searchResults");

            JsonArray fileRoot = new JsonArray();

            for(JsonElement x : searchResults){

               JsonObject result = x.getAsJsonObject();
               JsonPrimitive groupId = result.getAsJsonPrimitive("groupId");
               JsonPrimitive name = result.getAsJsonPrimitive("name");
               JsonObject credits = result.getAsJsonObject("credits");
               JsonPrimitive minCredits = credits.getAsJsonObject().getAsJsonPrimitive("min");

               JsonObject programme = new JsonObject();
               programme.addProperty("name", name.getAsString());
               programme.addProperty("groupId", groupId.getAsString());
               programme.addProperty("minCredits", minCredits.getAsString());
               
                System.out.println(name.getAsString());

               fileRoot.add(programme);
            }

            try(FileWriter fw = new FileWriter("degreeprogrammesfile.txt", Charset.forName("UTF-8"))){
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(fileRoot, fw);
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

        
        
      
    }
    

   
    
}


