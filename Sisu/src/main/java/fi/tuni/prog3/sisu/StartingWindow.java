
package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Emma
 */
public class StartingWindow extends Application {

   
    private Student student;
    StartingWindow(Student student){
        this.student = student;
    }

    /*public GridPane createTab1(){
        
    }*/
    
    @Override
    public void start(Stage stage) {
                
        stage.setTitle("SISU");
        GridPane gridStart = new GridPane();
        TabPane tabPane = new TabPane();
        Tab startPage = new Tab("Aloitus");
        tabPane.getTabs().add(startPage);
        startPage.setClosable(false); 
        
        Label nameLabel = new Label("Hei " + student.getName() + "!");
        nameLabel.setFont(new Font("Arial", 30)); 
        Label studentNumberLabel = new Label(student.getStudentNumber());
        studentNumberLabel.setFont(new Font("Arial", 18));
        VBox statbox = new VBox();
        statbox.getChildren().add(nameLabel);
        statbox.getChildren().add(studentNumberLabel);
        gridStart.add(statbox, 0, 0);
        
        VBox progBox = new VBox();
        ProgressBar progression = new ProgressBar();
        progression.setProgress(student.getProgression());
        Label progLabel = new Label(student.getCompletedCredits() + "/" 
                + student.getDegreeProgramme().getMinCredits());
        progBox.getChildren().add(progression);
        progBox.getChildren().add(progLabel);
        
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(100);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(100);
        gridStart.getColumnConstraints().addAll(column1, column2); 
       
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(100);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(100);    
        gridStart.getRowConstraints().addAll(row1, row2);
        gridStart.setHgap(10);
        gridStart.setPadding(new Insets(10, 10, 0, 0));
        gridStart.add(progBox, 1, 0);
        
        startPage.setContent(gridStart);
        
        
        
        Tab degreePage = new Tab("Tutkinto");
        tabPane.getTabs().add(degreePage);
        degreePage.setClosable(false);
        
        SplitPane splitPane = new SplitPane();

        VBox leftControl  = new VBox();
        VBox rightControl = new VBox();

        splitPane.getItems().addAll(leftControl, rightControl);
        
        degreePage.setContent(splitPane);
        
        
        List<DegreeProgramme> values = new ArrayList<>(); 
        values.add(student.getDegreeProgramme());
        
        ObservableList<DegreeProgramme> degrees = FXCollections.observableList(values);
        TreeItem rootItem = new TreeItem (degrees.get(0));
        //Ottaa ensimmäisen degreen
        ObservableList<StudyModule> degreeOb = FXCollections.observableList(degrees.get(0).getModules());


        rootItem.getChildren().add(getTree(student.getDegreeProgramme().getModules().get(0)));
        TreeView tree = new TreeView (rootItem);
 
        
        leftControl.getChildren().add(tree);
        
        Scene scene = new Scene(tabPane, 500, 500);
        stage.setScene(scene);
        stage.show();
    
    
    }
    
        public TreeItem<StudyModule> getTree(StudyModule root) {
        TreeItem<StudyModule> result = new TreeItem<>(root);
                
        if (root.getModules() != null) {
            if (root.getName() != null) {
                for (StudyModule module: root.getModules()) {
                    result.getChildren().add(getTree(module));
                }
            } else if (root.getType().equals("CompositeRule")){
                if (root.getModules().size() > 1) {
                    for (StudyModule module: root.getModules()) {
                        result.getChildren().add(getTree(module));
                    }
                } else if (root.getModules().size() == 1){
                    result = getTree(root.getModules().get(0));
                }

            } else {
                result = getTree(root.getModules().get(0));
                
            }
        
        }
        
        return result;
    }
    
        
    
    
    public static void main(String args[]) {
       launch();
    }
}
