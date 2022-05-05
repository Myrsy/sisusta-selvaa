package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import org.apache.commons.codec.binary.StringUtils;


/**
 * A class for getting the data from the Sisu API and writing. Because all the 
 * methods are static, it is not necessary to instantiate the class as an object 
 * in order to get access to the methods. 
 */
public class SearchTool {
    
    private static final String ALL_DEGREES_FILENAME = "alldegreesfile.json";
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.json";
    private static final String ISO_STRING = "ISO-8859-15";
    
    /**
     * Writes JsonArray to the specified file. If the JsonElement that is created
     * from the file is not JsonNull (i.e. the file is not empty) the file's
     * content will be saved as a JsonArray and combined with the JsonArray
     * that is wanted to be written. The combined JsonArray will be written in
     * the file and the existing information will be overwritten. However, if
     * the file is empty the new JsonArray is written to the file directly.
     * There is no need to check whether the specified file exists since
     * this method will called from methods that checks this already.
     * @param filename name of the file where the JsonArray will be written.
     * @param array JsonArray that will be written in a file.
     * @throws IOException if there is an IO error.
     */
    private static void writeArrayToFile(String filename, JsonArray array) 
            throws IOException {
                
        String data = new String(Files.readAllBytes(Paths.get(filename)));
        byte[] bytes = StringUtils.getBytesUtf8(data);
        String utf8data = StringUtils.newStringUtf8(bytes);
        JsonElement element = (JsonElement) new JsonParser().parse(utf8data);
        Gson gson = new GsonBuilder().disableHtmlEscaping()
                .setPrettyPrinting().create();
        
        if (!(element instanceof JsonNull)) {
            JsonArray jsonArray = (JsonArray)element;
            jsonArray.addAll(array);
            
            try (FileWriter fw = new FileWriter(filename, 
                    Charset.forName(ISO_STRING))){
                    gson.toJson(jsonArray, fw);
            }
        } else {
            try(FileWriter fw = new FileWriter(filename, 
                    Charset.forName(ISO_STRING))){
                    gson.toJson(array, fw);
            }
        }
    }
    
    /**
     * Searches and parses all the degree programmes from API and saves the name, groupId 
     * and minimum credits of each degree programme and calls 
     * {@link #writeArrayToFile(java.lang.String, com.google.gson.JsonArray) 
     * writeArrayToFile(filename, JsonArray)} in order to write the information
     * to the {@link #ALL_DEGREES_FILENAME} file if it doesn't already contain
     * this information. If the file is empty or doesn't exist the method will 
     * create a new file and write the information in it. The file is expected 
     * to contain all the degree programmes if it is not empty and the file is 
     * assumed to not be empty if it contains at least one row.
     * @throws IOException if there is an IO error.
     */
    public static void searchDegreeProgrammesURL() 
            throws IOException {        
        
        try {
            File file = new File(ALL_DEGREES_FILENAME);
            file.createNewFile(); 
            
            BufferedReader br = new BufferedReader(new FileReader(ALL_DEGREES_FILENAME));
            if (br.readLine() != null) {
                System.out.println("Kaikki tutkinnot ovat jo tiedostossa " + 
                        ALL_DEGREES_FILENAME);
            } else {
                URL url = new URL(
                        "https://sis-tuni.funidata.fi/kori/api/module-search?"
                                + "curriculumPeriodId=uta-lvv-2021&universityId"
                                + "=tuni-university-root-id&moduleType"
                                + "=DegreeProgramme&limit=1000");
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
                   JsonPrimitive minCredits = credits.getAsJsonObject()
                           .getAsJsonPrimitive("min");

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
     * Searches the degree programme specified by the groupId from the API if 
     * it isn't in the {@link #FULL_DEGREES_FILENAME} file already. Because all
     * the degree programmes from the {@link #FULL_DEGREES_FILENAME} file are 
     * instantiated ????when??? the degree programme is concluded to be in the 
     * file if it's groupId is found from 
     * {@link DegreeObjectData#getDegreeMap() DegreeObjectData.getDegreeMap()}.
     * If the degree programme is not found from the degree map 
     * (thus is not in the file) the method calls 
     * {@link #parseNestedDegree(com.google.gson.JsonObject, com.google.gson.JsonArray) 
     * parseNestedDegree(JsonObject, JsonArray)} in
     * order to parse the full nested degree programme. After that the method 
     * calls {@link DegreeObjectData#jsonArrayToObject(com.google.gson.JsonArray) 
     * DegreeObjectData.jsonArrayToObject(JsonArray)} in order to instantiate an
     * {@link DegreeProgramme} object from the Json array. Lastly, the 
     * {@link #writeArrayToFile(java.lang.String, com.google.gson.JsonArray) 
     * writeArrayToFIle(JsonArray)} method is called in order to write the new
     * degree programme to the {@link #FULL_DEGREES_FILENAME} file.
     * @param newGroupId groupId of the searched degree programme.
     * @throws MalformedURLException if the API url is erroneous.
     * @throws IOException if there is an IO error.
     */
    public static void searchDegreeURL(String newGroupId) 
            throws MalformedURLException, IOException{
        
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

            array = parseNestedDegree(jsonObj, array);
            
            DegreeObjectData.jsonArrayToObject(array);
            writeArrayToFile(FULL_DEGREES_FILENAME, array);
            
        }
        
    }
    
    /**
     * Parses the submodules of a nested degree programme from given JsonObject. 
     * The method works recursively by calling itself and searching the 
     * information about modules from API. When the method faces a course it 
     * will call {@link #parseCourseUnit(com.google.gson.JsonObject) 
     * parseCourseUnit(JsonObject)} in order to search and parse the information
     * about the course. The method reaches it's base case when there's no 
     * "type" in the specified JsonObject. The method then returns the JsonArray
     * that contains the submodules and courses from the corresponding 
     * iteration. Sometimes the method calls {@link #parseString(java.lang.String) 
     * parseString(String)} in order to show certain characters correctly.
     * The methos excpects the Json to be in format that is stated in
     * the API documentation ({@see 
     * <a href = "https://sis-tuni.funidata.fi/kori/swagger-ui/index.html?configUrl=/kori/v3/api-docs/swagger-config&filter=true">
     * https://sis-tuni.funidata.fi/kori/swagger-ui/index.html</a>}).
     * @param jsonObj JsonObject that represents a submodule or a course.
     * @param array JsonArray that contains all the modules and courses so far 
     * (i.e. the current nested Json tree).
     * @return JsonArray that contains all the modules and courses so far 
     * (i.e. the current nested Json tree).
     * @throws IOException if there is an IO error.
     */
    private static JsonArray parseNestedDegree(JsonObject jsonObj, JsonArray array) 
            throws IOException { 
        
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
            
            JsonObject require = null;
            JsonPrimitive minRequire = null;
            JsonPrimitive maxRequire = null;
            if (!(jsonObj.get("require") instanceof JsonNull)) {
                require = jsonObj.getAsJsonObject("require");
                
                if (!(require.get("min") instanceof JsonNull)) {
                    minRequire = require.getAsJsonPrimitive("min");
                    module.addProperty("minRequire", minRequire.getAsString());

                }
                if (!(require.get("max") instanceof JsonNull)) {
                    maxRequire = require.getAsJsonPrimitive("max");
                    module.addProperty("maxRequire", maxRequire.getAsString());

                }
            }

            JsonArray ruleArray = jsonObj.getAsJsonArray("rules");
            for (JsonElement subElement: ruleArray) {
                JsonObject subObj = subElement.getAsJsonObject();
                JsonPrimitive subType = subObj.getAsJsonPrimitive("type");
                if (subType.getAsString().equals("CourseUnitRule")) {
                    moduleArray.add(parseCourseUnit(subObj));
                } else {
                    moduleArray = parseNestedDegree(subObj, moduleArray);
                }
            }       
            
        } else if (type.getAsString().equals("GroupingModule")) {
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            if (nameFI == null) {
                JsonPrimitive nameEN = name.getAsJsonPrimitive("en");
                module.addProperty("name", parseString(nameEN.getAsString()));
            } else {
                module.addProperty("name", parseString(nameFI.getAsString()));
            }
            JsonObject subObject = jsonObj.getAsJsonObject("rule");
            module.addProperty("type", type.getAsString());
            moduleArray = parseNestedDegree(subObject, moduleArray);
            
        } else if (type.getAsString().equals("CreditsRule")) {
            JsonObject subObject = jsonObj.getAsJsonObject("rule");
            module.addProperty("type", type.getAsString());
            moduleArray = parseNestedDegree(subObject, moduleArray);
            
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
                moduleArray = parseNestedDegree(subObj.getAsJsonObject(), moduleArray);
            }
            
        } else if (type.getAsString().equals("DegreeProgramme") 
                || type.getAsString().equals("StudyModule")) {
            JsonObject name = jsonObj.getAsJsonObject("name");
            JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
            if (nameFI == null) {
                JsonPrimitive nameEN = name.getAsJsonPrimitive("en");
                module.addProperty("name", parseString(nameEN.getAsString()));
            } else {
                module.addProperty("name", parseString(nameFI.getAsString()));
            }

            JsonPrimitive groupId = jsonObj.getAsJsonPrimitive("groupId");
            module.addProperty("groupId", groupId.getAsString());
            module.addProperty("type", type.getAsString());
            JsonElement code = jsonObj.get("code");
            if (!(code instanceof JsonNull)) {
               module.addProperty("code",code.getAsString());
            }
            
            JsonObject credits = jsonObj.getAsJsonObject("targetCredits");
            JsonPrimitive minCredits = new JsonPrimitive("0");
            JsonPrimitive maxCredits = new JsonPrimitive("999");

            if (credits != null) {
                if (!(credits.get("min") instanceof JsonNull)) {
                    minCredits = credits.getAsJsonPrimitive("min");               
                }
                if (!(credits.get("max") instanceof JsonNull)) {
                    maxCredits = credits.getAsJsonPrimitive("max");
                }
                module.addProperty("minCredits", minCredits.getAsString());               
                module.addProperty("maxCredits", maxCredits.getAsString());
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
            moduleArray = parseNestedDegree(subObj, moduleArray);        

        } else {
            return array;
        }
            
        module.add("modules", moduleArray);
        array.add(module);
        
        return array;         
    }
    
    /**
     * Searches and parses the information about the course.
     * Sometimes the method calls {@link #parseString(java.lang.String) 
     * parseString(String)} in order to show certain characters correctly.
     * @param courseObj JsonObject that represents a course.
     * @return JsonObject that contains the wanted information about the course.
     * @throws IOException if there is an IO error.
     */
    private static JsonObject parseCourseUnit(JsonObject courseObj) 
            throws IOException{
        String courseUnitGroupId = courseObj.getAsJsonPrimitive("courseUnitGroupId").getAsString();
        String urlStr = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=" 
                + courseUnitGroupId + "&universityId=tuni-university-root-id";
        URL url = new URL(urlStr);
        String data = new String(url.openStream().readAllBytes());
        byte[] isoBytes = data.getBytes(Charset.forName(ISO_STRING));
        String isoData = new String (isoBytes, Charset.forName(ISO_STRING));
        JsonArray json = new JsonParser().parse(isoData).getAsJsonArray();
        JsonObject course = new JsonObject();

        JsonObject jsonObj = json.get(0).getAsJsonObject();
        JsonObject credits = jsonObj.getAsJsonObject("credits");
        JsonPrimitive minCredits = credits.getAsJsonPrimitive("min");
        JsonPrimitive maxCredits = new JsonPrimitive("999");
        JsonPrimitive gradeScaleId = jsonObj.getAsJsonPrimitive("gradeScaleId");
        JsonObject name = jsonObj.getAsJsonObject("name");
        JsonPrimitive nameFI = name.getAsJsonPrimitive("fi");
        JsonPrimitive code = jsonObj.getAsJsonPrimitive("code");
        JsonElement outcomes = jsonObj.get("outcomes");
        JsonPrimitive outcomesFI = null;
        if (!(outcomes instanceof JsonNull)) {
            outcomesFI = outcomes.getAsJsonObject().getAsJsonPrimitive("fi");
        }          
        JsonElement content = jsonObj.get("content");
        JsonPrimitive contentFI = null;
        course.addProperty("type", "CourseUnitRule");
        if (!(content instanceof JsonNull)) {
            contentFI = content.getAsJsonObject().getAsJsonPrimitive("fi");
        }
        if (nameFI != null) {
            course.addProperty("name", parseString(nameFI.getAsString()));
        }
        course.addProperty("code", parseString(code.getAsString()));
        course.addProperty("gradeScaleId", gradeScaleId.getAsString());
        course.addProperty("minCredits", minCredits.getAsString());
        if (!(credits.get("max") instanceof JsonNull)) {
            maxCredits = credits.getAsJsonPrimitive("max");
        }
        course.addProperty("maxCredits", maxCredits.getAsString());

        if (contentFI != null) {
            course.addProperty("content", parseString(contentFI.getAsString()));
        } else {
            if (!(content instanceof JsonNull)) {
                JsonPrimitive contentEN = content.getAsJsonObject().getAsJsonPrimitive("en");
                if (contentEN != null) {
                    course.addProperty("content", parseString(contentEN.getAsString()));
                }
            }
        }     
        if (outcomesFI != null) {
           course.addProperty("outcomes",parseString(outcomesFI.getAsString())); 
        } else {
            if (!(outcomes instanceof JsonNull)) {
                JsonPrimitive outcomesEN = outcomes.getAsJsonObject().getAsJsonPrimitive("en");
                if (outcomesEN != null) {
                    course.addProperty("content", parseString(outcomesEN.getAsString()));
                }
            }
        }

        return course;
    }
    
    /**
     * Parses the string in order to get rid of the HTML formatting and incorrect 
     * characters.
     * @param str string that will be parsed.
     * @return parsed string.
     */
    private static String parseString(String str) {
        String result = str.replaceAll("\\<.*?\\>", "\n");
        String previousResult = "";
        while (!previousResult.equals(result)) {
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


