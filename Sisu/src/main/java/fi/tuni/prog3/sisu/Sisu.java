package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;




public class Sisu extends Application {
    
    public class NewStudent extends Application {
        @Override
        public void start(Stage stage) {
            
            
            GridPane grid = new GridPane();
            stage.setTitle("Lisää uusi opiskelija");
            Label nameLabel = new Label("Nimi:");
            Label numberLabel = new Label("  Opiskelijanumero:");
            grid.add(nameLabel, 0, 0, 1, 1);
            grid.add(numberLabel, 0, 1, 1, 1);
            GridPane.setHalignment(nameLabel, HPos.RIGHT);
            GridPane.setHalignment(numberLabel, HPos.RIGHT);
            
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
        
            Scene scene = new Scene(grid, 400, 200);
            stage.setScene(scene);
            stage.show();
            
        }
    }
    
    public StudentData findStudentData(){
        StudentData studentData = new StudentData();
        studentData.getOldStudents();
        return studentData;
    }

    @Override
    public void start(Stage stage) {
        
        StudentData studentData = findStudentData();
        
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
        
        launch();
    }

}



