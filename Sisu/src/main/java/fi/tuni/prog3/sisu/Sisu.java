package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;



/**
 * A class for creating the GUI menu for logging and signing in.
 */
public class Sisu extends Application {
    
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.json";
    private static final String STUDENTS_JSON_FILENAME = "studentsfile.json";
    private static final String ALL_DEGREES_FILENAME = "alldegreesfile.json";
    /*public Sisu(){
        
    }*/
            
    private class NewStudent extends Application {
                
        /**
         * Sets up a new window for logging in based on the student number.
         * If the student number is not found from the student file a warning
         * message will appear.
         * User can create a new account by pressing "Uusi opiskelija"-button.
         * User needs to input the student's name and student number. If the 
         * student number is already used a warning message will appear. 
         * User also needs to select degree programme from the ComboBox. The 
         * degree programmes are searched from the {@link #ALL_DEGREES_FILE} so
         * the objects will be instantiated only with name, groupId and minimum 
         * credits.
         * @param stage the stage object.
         */
        @Override
        public void start(Stage stage) {
            
            GridPane grid = new GridPane();
            stage.setTitle("Lisää uusi opiskelija");
            Label nameLabel = new Label("Nimi:");
            Label numberLabel = new Label("  Opiskelijanumero:");
            Label degreeLabel = new Label("Valitse opintokokonaisuus:");
            grid.add(nameLabel, 0, 0, 1, 1);
            grid.add(numberLabel, 0, 1, 1, 1);
            grid.add(degreeLabel, 0, 2, 1, 1);
            GridPane.setHalignment(nameLabel, HPos.RIGHT);
            GridPane.setHalignment(numberLabel, HPos.RIGHT);
            GridPane.setHalignment(degreeLabel, HPos.RIGHT);
            GridPane.setValignment(degreeLabel, VPos.TOP);
            
            TextField addNameField = new TextField();
            TextField addNumberField = new TextField();
            grid.add(addNameField, 1, 0, 1, 1);
            grid.add(addNumberField, 1, 1, 1, 1);
            
            RowConstraints row1 = new RowConstraints();
            row1.setPrefHeight(30);
            RowConstraints row2 = new RowConstraints();
            row2.setPrefHeight(30);
            grid.getRowConstraints().addAll(row1, row2);
            grid.setHgap(10);
            grid.setVgap(10);            
           
            List<DegreeProgramme> values = new ArrayList<>();
            
            Gson gson = new Gson();
            try (Reader reader = new FileReader(ALL_DEGREES_FILENAME)) {
               DegreeProgramme[] progs = gson.fromJson(reader, DegreeProgramme[].class);
               for (DegreeProgramme prog: progs) {
                   values.add(prog);
               }
            } catch (FileNotFoundException ex) {
                System.err.println("Error: " + ex);
            } catch (IOException ex) {
                System.err.println("Error: " + ex);
            }
            
            ObservableList<DegreeProgramme> degrees = FXCollections.observableList(values);
            ComboBox degreeComboBox = new ComboBox(degrees);
            grid.add(degreeComboBox, 1, 2, 1, 1);
            GridPane.setValignment(degreeComboBox, VPos.TOP);
           
            RowConstraints row3 = new RowConstraints();
            row3.setPrefHeight(80);
            grid.getRowConstraints().addAll(row3);
            
            Button btnAddStudent = new Button("Lisää opiskelija");
            grid.add(btnAddStudent, 1, 3, 1, 1);
            
            btnAddStudent.setOnAction(new EventHandler<ActionEvent>(){
            
                /**
                 * Method that is called when "Lisää opiskelija"-button is 
                 * pressed. If the specified degree programme is not found from 
                 * the {@link DegreeObjectData#degreeProgrammes} map the
                 * method will call 
                 * {@link SearchTool#searchDegreeURL(java.lang.String) 
                 * SearchTool.searchDegreeUrl(groupId)} method in order to 
                 * search, parse and save the specified degree programme as an
                 * object. After that the new degree programme is included in 
                 * {@link DegreeObjectData#degreeProgrammes} map.
                 * @param e the ActionEvent.
                 */
                @Override
                public void handle(ActionEvent e){

                    String name = addNameField.getText();
                    String number = addNumberField.getText();
                    DegreeProgramme degree = (DegreeProgramme) degreeComboBox.getValue();
                    HashMap<String, DegreeProgramme> degrees = DegreeObjectData.getDegreeMap();

                    if (!(("").equals(name.strip())) && 
                            !(("").equals(number.strip())) && 
                            degree != null) {
                       
                        if (StudentData.getStudent(number) != null) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("Virhe");
                            alert.setHeaderText(null);
                            alert.setContentText("Opiskelijanumero on varattu!");

                            alert.showAndWait();
                        } else {
                                if (!(degrees.containsKey(degree.getGroupId()))) {
                                    try {
                                        SearchTool.searchDegreeURL(degree.getGroupId(), 
                                                FULL_DEGREES_FILENAME);
                                    } catch (IOException ex) {
                                        System.err.println("Error: " + ex);
                                    }
                                    degrees = DegreeObjectData.getDegreeMap();
                                }
                                Student student = new Student(name, number, 
                                        degrees.get(degree.getGroupId()));
                                StudentData.addStudent(student);

                                StartingWindow startingWindow = new StartingWindow(student);
                                Stage stage = new Stage();
                                try {
                                    startingWindow.start(stage);
                                } catch (IOException ex) {
                                    System.err.println("Error: " + ex);
                                }
                                 ((Node)(e.getSource())).getScene().getWindow().hide(); 
                        }

                    } else if (("").equals(name.strip())) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Virhe");
                        alert.setHeaderText(null);
                        alert.setContentText("Syötä nimi");
                        
                        alert.showAndWait();
                    } else if (("").equals(number.strip())) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Virhe");
                        alert.setHeaderText(null);
                        alert.setContentText("Syötä opiskelijanumero");
                        
                        alert.showAndWait();
                    } else if (degree == null) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Virhe");
                        alert.setHeaderText(null);
                        alert.setContentText("Valitse tutkinto");
                        
                        alert.showAndWait();
                    }
                    
                }

            });

            Scene scene = new Scene(grid, 800, 200);
            stage.setScene(scene);
            stage.show();
            
        }
    }
    
    /**
     * Sets up a GUI for logging in.
     * @param stage new Stage object.
     */
    @Override
    public void start(Stage stage)  {
              
        GridPane grid = new GridPane();
        
        Label logInLabel = new Label("  Kirjaudu sisään:");
        grid.add(logInLabel, 0, 0, 1, 1);       
        
        Label numberLabel = new Label("  Opiskelijanumero:");
        grid.add(numberLabel, 0, 1, 1, 1);
        TextField addNumberField = new TextField();
        grid.add(addNumberField, 1, 1, 1, 1);
        Label newStudentLabel = new Label("Lisää uusi opiskelija:");
        grid.add(newStudentLabel, 0, 2, 1, 1);

        Button btnLogIn = new Button("Kirjaudu sisään");
        Button btnNewStudent = new Button("Uusi opiskelija");
        grid.add(btnLogIn, 2, 1, 1, 1);
        grid.add(btnNewStudent, 0, 3, 1, 1);
        
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(20);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(50);
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(20);
        RowConstraints row4 = new RowConstraints();
        row4.setPrefHeight(40);
        grid.getRowConstraints().addAll(row1, row2, row3, row4);
        grid.setHgap(10);
        grid.setPadding(new Insets(10, 10, 0, 0));
        
        btnLogIn.setOnAction(new EventHandler<ActionEvent>(){
            
            /**
             * Method that is called when "Kirjaudu sisään"-button is 
             * pressed. It calls the 
             * {@link StudentData#getStudent(java.lang.String) 
             * StudentData.getStudent(studentNumber)} in order to find out 
             * whether the student is in the {@link StudentData#students} map.
             * If it isn't a warning message will be shown. If it is the 
             * {@link StartingWindow} is activated and the current window will be 
             * hidden.
             */
            @Override
            public void handle(ActionEvent e){
                
                String studentNumber = addNumberField.getText();
                if (StudentData.getStudent(studentNumber) == null) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Virhe!");
                    alert.setHeaderText(null);
                    alert.setContentText("Opiskelijanumerolla ei löytynyt "
                            + "opiskelijaa.");

                    alert.showAndWait();

                    
                } else {
                    Stage stage = new Stage();
                    StartingWindow start = 
                            new StartingWindow(StudentData.getStudent(studentNumber));
                    try {
                        start.start(stage);
                    } catch (IOException ex) {
                        System.err.println("Error: " + ex);
                    }

                    ((Node)(e.getSource())).getScene().getWindow().hide();
                }
                
            }

        });
        
        btnNewStudent.setOnAction(new EventHandler<ActionEvent>(){
            
            /**
             * Method that is called when "Uusi opiskelija"-button is pressed.
             * It instantiates a new subclass {@link NewStudent} and hides the
             * current window.
             * @param e ActionEvent object.
             */
            @Override
            public void handle(ActionEvent e){
                Stage stage = new Stage();
                NewStudent newStudent = new NewStudent();
                newStudent.start(stage);
                
                ((Node)(e.getSource())).getScene().getWindow().hide();
                
            }

        });
        
        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Before launching the program the method calls for 
     * {@link DegreeObjectData#jsonFileToObjects() 
     * DegreeObjectData.jsonFileToObjects()} in order to instantiate
     * the full nested degree programme objects from the 
     * {@link DegreeObjectData#FULL_DEGREES_FILENAME} file. 
     * The method also calls {@link StudentData#getOldStudents() 
     * StudentData.getOldStudents()}
     * method in order to instantiate all the student objects from the
     * student file.
     * The method finally calls {@link SearchTool#searchDegreeProgrammesURL() 
     * SearchTool.searchDegreeProgrammesURL()} in order to search and write 
     * all the degree programmes (reduced versions) to the file if they aren't
     * in the file already.
     * @param args args.
     */
    public static void main(String[] args) {
        
        try {
            DegreeObjectData.jsonFileToObjects(FULL_DEGREES_FILENAME);
            StudentData.getOldStudents(STUDENTS_JSON_FILENAME);
            SearchTool.searchDegreeProgrammesURL(ALL_DEGREES_FILENAME);
        } catch(IOException ex) {
            System.err.println("Error: " + ex);
        }
        
        launch();
    }

}



