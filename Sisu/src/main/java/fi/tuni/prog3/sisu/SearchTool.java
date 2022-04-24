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
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Iterator;
import javafx.util.Pair;
import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;




        


/**
 *
 * @author pinja
 */
public class SearchTool {
    
    private static final String ALL_DEGREES_FILENAME = "degreeprogrammesfile.txt";
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.txt";
    private static final String ISO_STRING = "ISO-8859-15";
    private DegreeObjectData degreeData;
    
    public SearchTool(){
        degreeData = new DegreeObjectData();
    }
    
    private void writeArrayToFile(String filename, JsonArray array) 
            throws IOException {
        
        System.out.println("tulostetaan");
        
        String data = new String(Files.readAllBytes(Paths.get(filename)));
        byte[] bytes = StringUtils.getBytesUtf8(data);
        String utf8data = StringUtils.newStringUtf8(bytes);
        JsonElement element = (JsonElement) new JsonParser().parse(utf8data);
        
        if(!(element instanceof JsonNull)){
            JsonArray jsonArray = (JsonArray)element;
            
            jsonArray.addAll(array);
            
            try(FileWriter fw = new FileWriter(filename, Charset.forName(ISO_STRING))){
                    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                    gson.toJson(jsonArray, fw);
            }
            
        }else{
            try(FileWriter fw = new FileWriter(filename, Charset.forName(ISO_STRING))){
                    Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
                    gson.toJson(array, fw);
            }
        }
           
        
    }
    
    public void searchDegreeProgrammesURL() throws IOException {        
        
        try {
            File file = new File(ALL_DEGREES_FILENAME);
            file.createNewFile(); 
                        
            URL url = new URL("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000");
            String data = new String(url.openStream().readAllBytes()); 
            byte[] bytes = StringUtils.getBytesUtf8(data);
            String utf8data = StringUtils.newStringUtf8(bytes);
 
            
            JsonObject json = new JsonParser().parse(utf8data).getAsJsonObject();
            JsonArray searchResults = json.getAsJsonArray("searchResults");

            JsonArray fileRoot = new JsonArray();

            for(JsonElement x : searchResults){

               JsonObject result = x.getAsJsonObject();
               JsonPrimitive groupId = result.getAsJsonPrimitive("groupId");
               JsonPrimitive name = result.getAsJsonPrimitive("name");
               JsonObject credits = result.getAsJsonObject("credits");
               JsonPrimitive minCredits = credits.getAsJsonObject().getAsJsonPrimitive("min");

               JsonObject programme = new JsonObject();
               programme.addProperty("name",name.getAsString());
               programme.addProperty("groupId",groupId.getAsString());
               programme.addProperty("minCredits", minCredits.getAsString());
               
               fileRoot.add(programme);
            }

            writeArrayToFile(ALL_DEGREES_FILENAME, fileRoot);

            
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    
    }
    

    public void searchDegreeURL(String groupId) throws MalformedURLException, IOException{
        String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                + groupId + "&universityId=tuni-university-root-id";
        
        URL url = new URL(urlStr);
        String data = new String(url.openStream().readAllBytes());
        byte[] isoBytes = data.getBytes(Charset.forName(ISO_STRING));
        String isoData = new String (isoBytes, Charset.forName(ISO_STRING) );
        
        JsonArray array = new JsonArray();
        
        array = parseAndSaveModule(isoData, array);
        writeArrayToFile(FULL_DEGREES_FILENAME, array);

        
    }
    
    private Pair<JsonArray,String>parseRules(JsonObject rule) throws MalformedURLException, IOException{
        JsonArray rules = null;
        String desc = "";
        if(rule.getAsJsonPrimitive("type").getAsString().equals("CompositeRule")){
            JsonElement description= rule.get("description");
            JsonPrimitive descriptionFI = null;
            if(!(description instanceof JsonNull)){
                descriptionFI = description.getAsJsonObject().getAsJsonPrimitive("fi");
                if(descriptionFI != null){
                    desc = parseString(descriptionFI.getAsString());
                }
            }
        }
        if((rule.getAsJsonObject("rule") != null) 
                && (rule.getAsJsonArray("rules") == null)){
           rule = rule.getAsJsonObject("rule");
           Pair rulesPair = parseRules(rule);
           rules =(JsonArray) rulesPair.getKey();
        } else if ((rule.getAsJsonArray("rules") != null)){
            rules = rule.getAsJsonArray("rules");
            if (rules.get(0).getAsJsonObject().getAsJsonPrimitive("type").getAsString().equals("ModuleRule")
                    || rules.get(0).getAsJsonObject().getAsJsonPrimitive("type").getAsString().equals("CourseUnitRule")){
                Pair<JsonArray, String> pair = new Pair<>(rules, desc);
                return pair;
            } else {
                for(JsonElement r: rules){
                    
                    Pair rulesPair = parseRules(r.getAsJsonObject());
                    rules =(JsonArray) rulesPair.getKey();
                }
            }
        }
        Pair<JsonArray, String> pair = new Pair<>(rules, desc);
        return pair;
    }

    
    public JsonArray parseAndSaveModule(String data, JsonArray array) throws IOException{
        JsonArray json = new JsonParser().parse(data).getAsJsonArray();
        for(JsonElement x: json){
            JsonObject jsonObj = x.getAsJsonObject();  
            JsonObject module = new JsonObject();
            
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            module.addProperty("name", parseString(nameFI.getAsString()));
            JsonPrimitive type = jsonObj.getAsJsonPrimitive("type");
            module.addProperty("type", type.getAsString());
            JsonElement code = jsonObj.get("code");
            if(!(code instanceof JsonNull)){
               module.addProperty("code",code.getAsString());
            }
            
            JsonObject credits = jsonObj.getAsJsonObject("targetCredits");
            JsonPrimitive minCredits = null;
            if(credits != null){
                minCredits = credits.getAsJsonPrimitive("min");
                if(minCredits != null){
                    module.addProperty("minCredits", minCredits.getAsString());
                }
            }
            
            JsonObject learningOutcomes = jsonObj.getAsJsonObject("learningOutcomes");
            JsonPrimitive learningOutcomesFI = null;
            if(learningOutcomes != null){
                learningOutcomesFI = learningOutcomes.getAsJsonPrimitive("fi");
                if(learningOutcomesFI != null){
                    module.addProperty("learningOutcomes",parseString(learningOutcomesFI.getAsString()));
                }
            }
                       
 
            JsonObject rule = jsonObj.getAsJsonObject("rule");
            Pair rulesPair = parseRules(rule); 
            JsonArray rules = (JsonArray) rulesPair.getKey();
            module.addProperty("description", (String) rulesPair.getValue());
            JsonArray ruleArray = new JsonArray();
            module.add("modules", ruleArray);
            array.add(module);


            if (rules != null) {
                for (JsonElement y : rules) {
                    JsonObject ruleObj = y.getAsJsonObject();

                    if(ruleObj.getAsJsonPrimitive("type").getAsString().equals("ModuleRule")){
                        String moduleGroupId = ruleObj.getAsJsonPrimitive("moduleGroupId").getAsString();
                        String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                                + moduleGroupId + "&universityId=tuni-university-root-id";
                        URL url = new URL(urlStr);
                        String data2 = new String(url.openStream().readAllBytes());
                        byte[] isoBytes = data2.getBytes(Charset.forName(ISO_STRING));
                        String isoData = new String (isoBytes, Charset.forName(ISO_STRING) );
                        ruleArray = parseAndSaveModule(isoData, ruleArray);
                    }else if (ruleObj.getAsJsonPrimitive("type").getAsString().equals("CourseUnitRule")){
                        String courseUnitGroupId = ruleObj.getAsJsonPrimitive("courseUnitGroupId").getAsString();
                        String urlStr = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=" 
                                + courseUnitGroupId + "&universityId=tuni-university-root-id";
                        URL url = new URL(urlStr);
                        String data2 = new String(url.openStream().readAllBytes());
                        byte[] isoBytes = data2.getBytes(Charset.forName(ISO_STRING));
                        String isoData = new String (isoBytes, Charset.forName(ISO_STRING) );
        

                        ruleArray.add(parseCourseUnit(isoData));
                    } else {
                        return array;
                    }
                }         
            }
        }
        return array;         
    }
    
    public JsonObject parseCourseUnit(String data){
        JsonArray json = new JsonParser().parse(data).getAsJsonArray();
        JsonObject course = new JsonObject();
        
        for(JsonElement x : json){
            JsonObject jsonObj = x.getAsJsonObject();
            JsonObject credits = jsonObj.getAsJsonObject("credits");
            JsonPrimitive minCredits = credits.getAsJsonPrimitive("min");
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            JsonPrimitive code = jsonObj.getAsJsonPrimitive("code");
            JsonElement outcomes = jsonObj.get("outcomes");
            JsonPrimitive outcomesFI = null;
            if(!(outcomes instanceof JsonNull)){
                outcomesFI = outcomes.getAsJsonObject().getAsJsonPrimitive("fi");
            }          
            JsonElement content = jsonObj.get("content");
            JsonPrimitive contentFI = null;
            course.addProperty("type", "CourseUnitRule");
            if(!(content instanceof JsonNull)){
                contentFI = content.getAsJsonObject().getAsJsonPrimitive("fi");
            }
            if (nameFI != null){
                course.addProperty("name", parseString(nameFI.getAsString()));
            }
            course.addProperty("code", code.getAsString());
            course.addProperty("minCredits", minCredits.getAsString());
            
            if(contentFI != null){
                course.addProperty("content", parseString(contentFI.getAsString()));
            }else{
                if(!(content instanceof JsonNull)){
                    JsonPrimitive contentEN = content.getAsJsonObject().getAsJsonPrimitive("en");
                    if( contentEN != null){
                        course.addProperty("content", parseString(contentEN.getAsString()));
                    }
                }
                
            }     
            if(outcomesFI != null ){
               course.addProperty("outcomes",parseString(outcomesFI.getAsString())); 
            }else{
                if(!(outcomes instanceof JsonNull)){
                    JsonPrimitive outcomesEN = outcomes.getAsJsonObject().getAsJsonPrimitive("en");
                    if( outcomesEN != null){
                        course.addProperty("content", parseString(outcomesEN.getAsString()));
                    }
                }
            }
            
                      
            
        }
        
        return course;
    }
    
    private String parseString(String str) {
        String result = str.replaceAll("\\<.*?\\>", "\n");
        String previousResult = "";
        while(!previousResult.equals(result)){
            previousResult = result;
            result = result.replaceAll("\n\n","\n");
        }
        
        result = result.replace("Ã?", "ä");
        result = result.replace("Ã¶", "ö");
        result = result.replace("&#34;", "\"");
        

        
        return result;
    }
    
    
}


