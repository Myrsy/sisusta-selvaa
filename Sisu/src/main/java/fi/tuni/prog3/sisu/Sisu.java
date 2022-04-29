package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;




public class Sisu extends Application {
    
    private StudentData studentData = new StudentData();

    public class NewStudent extends Application {
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
            /*RowConstraints row3 = new RowConstraints();
            row3.setPrefHeight(20);
            RowConstraints row4 = new RowConstraints();
            row4.setPrefHeight(40);*/
            grid.getRowConstraints().addAll(row1, row2);
            grid.setHgap(10);
            grid.setVgap(10);
            
            /*
            DegreeObjectData degreeObjectData = new DegreeObjectData();
            try{
                degreeObjectData.jsonToObjects();

            }catch(Exception e){

            }
            */
            
           
            List<DegreeProgramme> values = new ArrayList<>();
            
            Gson gson = new Gson();
                try (Reader reader = new FileReader("degreeprogrammesfile.txt")) {
                   DegreeProgramme[] progs = gson.fromJson(reader, DegreeProgramme[].class);
                   for (DegreeProgramme prog: progs) {
                       values.add(prog);
                   }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            ObservableList<DegreeProgramme> degrees = FXCollections.observableList(values);
            ComboBox degreeComboBox = new ComboBox(degrees);
            grid.add(degreeComboBox, 1, 2, 1, 1);
            GridPane.setValignment(degreeComboBox, VPos.TOP);
           
            RowConstraints row3 = new RowConstraints();
            row3.setPrefHeight(80);
            grid.getRowConstraints().addAll(row3);
            
            
            
            //grid.add(degreeComboBox, 1, 2, 1, 1);
            
            Button btnAddStudent = new Button("Lisää opiskelija");
            grid.add(btnAddStudent, 1, 3, 1, 1);
            
            //Label errorLabel = new Label("");
            //errorLabel.setTextFill(Color.RED);
            //grid.add(errorLabel, )
            btnAddStudent.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e){
                                
               String name = addNameField.getText();
               String number = addNumberField.getText();
               DegreeProgramme degree =(DegreeProgramme) degreeComboBox.getValue();
               
               DegreeObjectData data = new DegreeObjectData();
                try {
                    data.jsonFileToObjects();
                } catch (IOException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                HashMap<String, DegreeProgramme> degrees = data.getDegreeMap();
               
                if(!(degrees.containsKey(degree.getGroupId()))){
                   try {
                       SearchTool tool = new SearchTool();
                       tool.searchDegreeURL(degree.getGroupId());
                       data.jsonFileToObjects();
                       degrees = data.getDegreeMap();
                   } catch (IOException ex) {
                       Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                   }

                }

                if(name == null){

                }
                Student student = new Student(name, number, degrees.get(degree.getGroupId()));
                
                if(studentData.searchStudent(number) == true){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Virhe");
                    alert.setHeaderText(null);
                    alert.setContentText("Opiskelijanumero on varattu!");

                    alert.showAndWait();
                }else{
                    
                   try {
                       studentData.addStudent(student);
                   } catch (IOException ex) {
                       Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                   }
                    
                    StartingWindow startingWindow = new StartingWindow(student);
                    Stage stage = new Stage();
                    startingWindow.start(stage);
                    ((Node)(e.getSource())).getScene().getWindow().hide(); 
                    }
                
            }

        });
            
            
            
            
   
            Scene scene = new Scene(grid, 400, 200);
            stage.setScene(scene);
            stage.show();
            
        }
    }
    

    
    
    /*public StudentData findStudentData() throws IOException{
        StudentData studentData = new StudentData();
        studentData.getOldStudents();
        return studentData;
    }*/

    @Override
    public void start(Stage stage) throws IOException {
       
        
        //StudentData studentData = findStudentData();
        //StudentData studentData = new StudentData();
        studentData.getOldStudents();
        
        GridPane grid = new GridPane();

        

        Label logInLabel = new Label("  Kirjaudu sisään:");
        Label logInErrorLabel = new Label("");
        grid.add(logInLabel, 0, 0, 1, 1);
        grid.add(logInErrorLabel, 1, 0, 2, 1);
        
        
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
            
            @Override
            public void handle(ActionEvent e){
                
                String studentNumber = addNumberField.getText();
                if(!studentData.searchStudent(studentNumber)){
                    logInErrorLabel.setText("Virhe! Opiskelijanumerolla ei löytynyt opiskelijaa.");
                    
                }else{
                    Stage stage = new Stage();
                    StartingWindow start = 
                            new StartingWindow(studentData.getStudent(studentNumber));
                    start.start(stage);

                    ((Node)(e.getSource())).getScene().getWindow().hide();
                }
                
                
            }

        });
        btnNewStudent.setOnAction(new EventHandler<ActionEvent>(){
            
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
    

    public static void main(String[] args) throws IOException {
        
        SearchTool tool = new SearchTool();
        try{
            tool.searchDegreeProgrammesURL();
        }catch(IOException e){
                
        }
        
        
        launch();
    }

}



