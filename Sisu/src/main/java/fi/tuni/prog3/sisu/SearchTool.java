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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
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
   // private DegreeObjectData degreeData;
    
    public SearchTool() throws IOException{
        DegreeObjectData.jsonFileToObjects();

    }
    
    /**
     * 
     * @param filename
     * @param array
     * @throws IOException 
     */
    private static void writeArrayToFile(String filename, JsonArray array) 
            throws IOException {
                
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
    
    /**
     * 
     * @throws IOException 
     */
    public static void searchDegreeProgrammesURL() throws IOException {        
        
        try {
            File file = new File(ALL_DEGREES_FILENAME);
            file.createNewFile(); 
            
            BufferedReader br = new BufferedReader(new FileReader(ALL_DEGREES_FILENAME));
            if (br.readLine() != null) {
                System.out.println("Kaikki tutkinnot ovat jo tiedostossa " + ALL_DEGREES_FILENAME);
            } else {
                URL url = new URL("https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-2021&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000");
                String data = new String(url.openStream().readAllBytes());
                byte[] isoBytes = data.getBytes(Charset.forName(ISO_STRING));
                String isoData = new String(isoBytes, Charset.forName(ISO_STRING));          

                JsonObject json = new JsonParser().parse(isoData).getAsJsonObject();
                JsonArray searchResults = json.getAsJsonArray("searchResults");

                JsonArray fileRoot = new JsonArray();

                for(JsonElement x : searchResults){

                   JsonObject result = x.getAsJsonObject();
                   JsonPrimitive groupId = result.getAsJsonPrimitive("groupId");
                   JsonPrimitive name = result.getAsJsonPrimitive("name");
                   JsonObject credits = result.getAsJsonObject("credits");
                   JsonPrimitive minCredits = credits.getAsJsonObject().getAsJsonPrimitive("min");

                   JsonObject programme = new JsonObject();
                   programme.addProperty("name", parseString(name.getAsString()));
                   programme.addProperty("groupId",groupId.getAsString());
                   programme.addProperty("minCredits", minCredits.getAsString());

                   fileRoot.add(programme);
                }

                writeArrayToFile(ALL_DEGREES_FILENAME, fileRoot);
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    
    }
    
    /**
     * 
     * @param groupId
     * @throws MalformedURLException
     * @throws IOException 
     */
    public static void searchDegreeURL(String newGroupId) throws MalformedURLException, IOException{
        
        Set<String> savedGroupIds = DegreeObjectData.getDegreeMap().keySet();
        
        if (savedGroupIds.contains(newGroupId)) {
            System.out.println("tutkinto " + newGroupId + " on jo tiedostossa");
        } else {
            String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                    + newGroupId + "&universityId=tuni-university-root-id";

            URL url = new URL(urlStr);
            String data = new String(url.openStream().readAllBytes());
            byte[] isoBytes = data.getBytes(Charset.forName(ISO_STRING));
            String isoData = new String(isoBytes, Charset.forName(ISO_STRING));

            JsonArray json = new JsonParser().parse(isoData).getAsJsonArray();
            JsonObject jsonObj = json.get(0).getAsJsonObject();

            JsonArray array = new JsonArray();

            array = parseAndSaveModule(jsonObj, array);
            
            DegreeObjectData.jsonArrayToObject(array);
            writeArrayToFile(FULL_DEGREES_FILENAME, array);
            
        }
        
    }
    
    /**
     * 
     * @param jsonObj
     * @param array
     * @return
     * @throws IOException 
     */
    private static JsonArray parseAndSaveModule(JsonObject jsonObj, JsonArray array) throws IOException{ 
        
        JsonObject module = new JsonObject();
        JsonArray moduleArray = new JsonArray();

        JsonPrimitive type = jsonObj.getAsJsonPrimitive("type");

        if (type.getAsString().equals("CompositeRule")) {
            JsonPrimitive allMandatory = jsonObj.getAsJsonPrimitive("allMandatory");
            module.addProperty("allMandatory", allMandatory.getAsString());
            module.addProperty("type", type.getAsString());
            String desc = "";
            JsonElement description = jsonObj.get("description");
            JsonPrimitive descriptionFI = null;
            if(!(description instanceof JsonNull)) {
                descriptionFI = description.getAsJsonObject().getAsJsonPrimitive("fi");
                    if(descriptionFI != null){
                        desc = parseString(descriptionFI.getAsString());
                    }
            }
            module.addProperty("description", desc);
            JsonArray ruleArray = jsonObj.getAsJsonArray("rules");
            for (JsonElement subElement: ruleArray) {
                JsonObject subObj = subElement.getAsJsonObject();
                JsonPrimitive subType = subObj.getAsJsonPrimitive("type");
                if (subType.getAsString().equals("CourseUnitRule")) {
                    moduleArray.add(parseCourseUnit(subObj));
                } else {
                    moduleArray = parseAndSaveModule(subObj, moduleArray);
                }
            }       
        } else if (type.getAsString().equals("GroupingModule")) {
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            if(nameFI == null){
                JsonPrimitive nameEN = name.getAsJsonPrimitive("en");
                module.addProperty("name", parseString(nameEN.getAsString()));
            }else{
                module.addProperty("name", parseString(nameFI.getAsString()));
            }
            JsonObject subObject = jsonObj.getAsJsonObject("rule");
            module.addProperty("type", type.getAsString());
            moduleArray = parseAndSaveModule(subObject, moduleArray);
        } else if (type.getAsString().equals("CreditsRule")) {
            JsonObject subObject = jsonObj.getAsJsonObject("rule");
            module.addProperty("type", type.getAsString());
            moduleArray = parseAndSaveModule(subObject, moduleArray);
        } else if (type.getAsString().equals("CourseUnitRule")) {
            moduleArray.add(parseCourseUnit(jsonObj));                    
        } else if (type.getAsString().equals("ModuleRule")) {
            module.addProperty("type", type.getAsString());
            String moduleGroupId = jsonObj.getAsJsonPrimitive("moduleGroupId").getAsString();
            String urlStr = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId="
                    + moduleGroupId + "&universityId=tuni-university-root-id";
            URL url = new URL(urlStr);
            String moduleData = new String(url.openStream().readAllBytes());
            byte[] isoBytes = moduleData.getBytes(Charset.forName(ISO_STRING));
            String isoData = new String (isoBytes, Charset.forName(ISO_STRING) );
            JsonArray json = new JsonParser().parse(isoData).getAsJsonArray();
            JsonArray subObjArray = json.getAsJsonArray();
            for (JsonElement subObj: subObjArray) {
                moduleArray = parseAndSaveModule(subObj.getAsJsonObject(), moduleArray);
            }
        } else if (type.getAsString().equals("DegreeProgramme") || type.getAsString().equals("StudyModule")){
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            if(nameFI == null){
                JsonPrimitive nameEN = name.getAsJsonPrimitive("en");
                module.addProperty("name", parseString(nameEN.getAsString()));
            }else{
                module.addProperty("name", parseString(nameFI.getAsString()));
            }

            JsonPrimitive groupId = jsonObj.getAsJsonPrimitive("groupId");
            module.addProperty("groupId", groupId.getAsString());
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
            JsonElement learningOutcomes = jsonObj.get("learningOutcomes");
            JsonPrimitive learningOutcomesFI = null;
            if(!(learningOutcomes instanceof JsonNull) && learningOutcomes != null){
                learningOutcomesFI = learningOutcomes.getAsJsonObject().getAsJsonPrimitive("fi");
                if(learningOutcomesFI != null){
                    module.addProperty("learningOutcomes",parseString(learningOutcomesFI.getAsString()));
                }
            }
            JsonObject subObj = jsonObj.getAsJsonObject("rule");
            moduleArray = parseAndSaveModule(subObj, moduleArray);        

        } else {
            return array;
        }
            
        module.add("modules", moduleArray);
        array.add(module);
        
        return array;         
    }
    
    /**
     * 
     * @param jsonObj1
     * @return
     * @throws MalformedURLException
     * @throws IOException 
     */
    private static JsonObject parseCourseUnit(JsonObject courseObj) throws MalformedURLException, IOException{
        String courseUnitGroupId = courseObj.getAsJsonPrimitive("courseUnitGroupId").getAsString();
        String urlStr = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=" 
                + courseUnitGroupId + "&universityId=tuni-university-root-id";
        URL url = new URL(urlStr);
        String data2 = new String(url.openStream().readAllBytes());
        byte[] isoBytes = data2.getBytes(Charset.forName(ISO_STRING));
        String isoData = new String (isoBytes, Charset.forName(ISO_STRING));
        JsonArray json = new JsonParser().parse(isoData).getAsJsonArray();
        JsonObject course = new JsonObject();
        
        JsonObject jsonObj = json.get(0).getAsJsonObject();
        JsonObject credits = jsonObj.getAsJsonObject("credits");
        JsonPrimitive minCredits = credits.getAsJsonPrimitive("min");
        JsonPrimitive gradeScaleId = jsonObj.getAsJsonPrimitive("gradeScaleId");
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
        course.addProperty("gradeScaleId", gradeScaleId.getAsString());
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
             
        return course;
    }
    
    
    private static String parseString(String str) {
        String result = str.replaceAll("\\<.*?\\>", "\n");
        String previousResult = "";
        while(!previousResult.equals(result)){
            previousResult = result;
            result = result.replaceAll("\n\n","\n");
        }
        
        result = result.replace("Ã?", "ä");
        result = result.replace("Ã¶", "ö");
        result = result.replace("&#34;", "\"");
        result = result.replace("&#43", "+");
        result = result.replace("&#64", "@");
        
        return result;
    }
    
    
}


