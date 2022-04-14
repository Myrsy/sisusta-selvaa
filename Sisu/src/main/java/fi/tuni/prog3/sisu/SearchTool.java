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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;




        


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
    

    public void searchDegreeURL(String groupId) throws MalformedURLException, IOException{
        String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                + groupId + "&universityId=tuni-university-root-id";
        
        URL url = new URL(urlStr);
        String data = new String(url.openStream().readAllBytes());
        
        JsonArray array = new JsonArray();
        
        parseAndSaveModule(data, array);
        
    }
    
    public void parseAndSaveModule(String data, JsonArray array) throws IOException{
        JsonArray json = new JsonParser().parse(data).getAsJsonArray();
        for(JsonElement x: json){
            JsonObject jsonObj = x.getAsJsonObject();  
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            JsonPrimitive code = jsonObj.getAsJsonPrimitive("code");
            JsonObject credits = jsonObj.getAsJsonObject("targetCredits");
            JsonPrimitive minCredits = credits.getAsJsonPrimitive("min");
            JsonObject learningOutcomes = jsonObj.getAsJsonObject("learningOutcomes");
            JsonPrimitive learningOutcomesFI = null;
            if(learningOutcomes != null){
                learningOutcomesFI = learningOutcomes.getAsJsonPrimitive("fi");
            }
            
            JsonObject rule = jsonObj.getAsJsonObject("rule");
            JsonArray rules = null;
            
            /*
            while(true){
                
                if(rule != null && rule.getAsJsonArray("rules") == null){
                    rule = rule.getAsJsonObject("rule");
                }else if(rule != null && rule.getAsJsonObject().getAsJsonPrimitive("type").getAsString().equals("CompositeRule")){
                    rules = rule.getAsJsonArray("rules");
                    String type = rules.get(0).getAsJsonObject().getAsJsonPrimitive("type").getAsString();
                    String localId = rules.get(0).getAsJsonObject().getAsJsonPrimitive("localId").getAsString();
                    System.out.println(localId);
                    System.out.println(type);
                    if(type.equals("ModuleRule") || type.equals("CourseUnitRule")){
                        break;
                    }
                   
                }else if(rules.get(0).getAsJsonObject().getAsJsonPrimitive("type").getAsString().equals("CompositeRule")){
                    rules =rules.get(0).getAsJsonObject().getAsJsonArray("rules");
                    
                    String type = rules.get(0).getAsJsonObject().getAsJsonPrimitive("type").getAsString();
                    System.out.println(type);
                    if(type.equals("ModuleRule") || type.equals("CourseUnitRule")){
                        break;
                    }
                }
                
                */
            
            while(rules == null){
                if(rule.getAsJsonArray("rules") == null ){
                    rule = rule.getAsJsonObject("rule");
                }else{
                   rules = rule.getAsJsonArray("rules"); 
                }
                
            }
            
                    
            
            JsonObject module = new JsonObject();
            module.addProperty("name", nameFI.getAsString());
            module.addProperty("code", code.getAsString());
            module.addProperty("minCredits", minCredits.getAsString());
            
            System.out.println(nameFI.getAsString());
            if(learningOutcomesFI != null){
                module.addProperty("learningOutcomes", learningOutcomesFI.getAsString());
            }
            

            JsonArray ruleArray = new JsonArray();
            module.add("modules", ruleArray);
            array.add(module);

            for (JsonElement y : rules) {
                JsonObject ruleObj = y.getAsJsonObject();
                
                if(ruleObj.getAsJsonPrimitive("type").getAsString().equals("ModuleRule")){
                    String moduleGroupId = ruleObj.getAsJsonPrimitive("moduleGroupId").getAsString();
                    String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                            + moduleGroupId + "&universityId=tuni-university-root-id";
                    URL url = new URL(urlStr);
                    String data2 = new String(url.openStream().readAllBytes());
                    parseAndSaveModule(data2, ruleArray );
                }else if (ruleObj.getAsJsonPrimitive("type").getAsString().equals("CourseUnitRule")){
                    String courseUnitGroupId = ruleObj.getAsJsonPrimitive("moduleGroupId").getAsString();
                    String urlStr = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId" 
                            + courseUnitGroupId + "&universityId=tuni-university-root-id";
                    URL url = new URL(urlStr);
                    String data2 = new String(url.openStream().readAllBytes());
                    
                    ruleArray.add(parseCourseUnit(data2));
                    
                }
            }


            try(FileWriter fw = new FileWriter("fulldegreesfile.txt", Charset.forName("UTF-8"))){
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    gson.toJson(array, fw);
            }
            
            
        }
        
        
    }
    
    public JsonObject parseCourseUnit(String data){
        JsonArray json = new JsonParser().parse(data).getAsJsonArray();
        JsonObject course = new JsonObject();
        
        for(JsonElement x : json){
            JsonObject jsonObj = x.getAsJsonObject();
            JsonObject credits = jsonObj.getAsJsonObject("targetCredits");
            JsonPrimitive minCredits = credits.getAsJsonPrimitive("min");
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            JsonPrimitive code = jsonObj.getAsJsonPrimitive("code");
            JsonObject outcomes = jsonObj.getAsJsonObject("outcomes");
            JsonPrimitive outcomesFI = outcomes.getAsJsonPrimitive("fi");
            JsonObject content = jsonObj.getAsJsonObject("content");
            JsonPrimitive contentFI = content.getAsJsonPrimitive("fi");
            
            course.addProperty("name", nameFI.getAsString());
            course.addProperty("code", code.getAsString());
            course.addProperty("minCredits", minCredits.getAsString());
            course.addProperty("outcomes", outcomesFI.getAsString());
            course.addProperty("content", contentFI.getAsString());          
            
        }
        
        return course;
    }
    
}


