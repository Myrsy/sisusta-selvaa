
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

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
        GridPane gridDegree = new GridPane();
        TabPane tabPane = new TabPane();
        Tab startPage = new Tab("Aloitus");
        tabPane.getTabs().add(startPage);
        startPage.setContent(gridStart);
        startPage.setClosable(false); 
        
        
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
        TreeItem rootItem = new TreeItem ();
        ObservableList<StudyModule> degreeOb = FXCollections.observableList(degrees.get(0).getModules());


        rootItem = getTreeItem(rootItem, degreeOb);
        TreeView tree = new TreeView (rootItem);  
 
        
        leftControl.getChildren().add(tree);
        
        Scene scene = new Scene(tabPane, 1000, 500);
        stage.setScene(scene);
        stage.show();
    
    
    }
    
    public TreeItem getTreeItem(TreeItem treeIt, ObservableList<StudyModule> obsList){
        
        for(var mod : obsList){
            if(mod.getType().equals("CourseUnitRule")){
               TreeItem item = new TreeItem (mod);
               treeIt.getChildren().add(item);
            }else{
                ObservableList<StudyModule> degreeObs = FXCollections.observableList(mod.getModules());
                if(mod.getName() == null){
                    return getTreeItem(treeIt, degreeObs);
                }else{
                    TreeItem item = new TreeItem (mod);
                    treeIt.getChildren().add(item);
                }
            } 
   
            
            
        }
  
        return treeIt;

  
    }

    
    
    
    public static void main(String args[]) {
       launch();
    }
}
