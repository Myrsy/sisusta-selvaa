
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
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
        
        VBox degreeBox = new VBox();
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
       
        
        Label degreeLabel = new Label("Tutkinnon vaihtaminen: ");
        degreeLabel.setFont(new Font("Arial", 18));
        Label warningLabel = new Label("HUOM! Tutkinnon vaihtaminen nollaa\n" +
                "kaikki suorittamaksi merkitsemäsi kurssit.");
        Button btnChangeDegree = new Button("Vaihda tutkintoa");
        degreeBox.getChildren().addAll(degreeLabel, degreeComboBox,
                warningLabel, btnChangeDegree);
        gridStart.add(degreeBox, 0, 2, 1, 1);
        GridPane.setValignment(degreeComboBox, VPos.TOP);
        
        btnChangeDegree.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent e){
               
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
                student.setDegreeProgramme(degrees.get(degree.getGroupId()));

                StartingWindow startingWindow = new StartingWindow(student);
                Stage stage = new Stage();
                startingWindow.start(stage);
                ((Node)(e.getSource())).getScene().getWindow().hide();
               
            }

        });
        
        
        
        
        startPage.setContent(gridStart);
        
        
        
        Tab degreePage = new Tab("Tutkinto");
        tabPane.getTabs().add(degreePage);
        degreePage.setClosable(false);
        
        SplitPane splitPane = new SplitPane();

        VBox leftControl  = new VBox();
        VBox rightControl = new VBox();
        
        SplitPane splitPaneR = new SplitPane();
        splitPaneR.setOrientation(Orientation.VERTICAL);
        
        VBox upperControl  = new VBox();
        VBox lowerControl = new VBox();
        splitPaneR.getItems().addAll(upperControl, lowerControl);
        rightControl.getChildren().add(splitPaneR);

        splitPane.getItems().addAll(leftControl, rightControl);
        
        degreePage.setContent(splitPane);
        
        
        List<DegreeProgramme> degs = new ArrayList<>(); 
        degs.add(student.getDegreeProgramme());
        
        ObservableList<DegreeProgramme> deg = FXCollections.observableList(degs);
        TreeItem rootItem = new TreeItem (deg.get(0));
        rootItem.getChildren().add(getTree(student.getDegreeProgramme().getModules().get(0)));
        TreeView tree = new TreeView (rootItem);
        
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        
            
        private Label info = new Label(); 
        private ScrollPane scroll = new ScrollPane();
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
                TreeItem treeItem = (TreeItem)newValue;
                StudyModule mod = (StudyModule) treeItem.getValue();
                String name = mod.getName();
                String code = mod.getCode();
                String content = mod.getContent();
                String outcome = mod.getOutcomes();
                String desc = mod.getDescription();
                
                if(content == null){
                    content = "";
                }
                
                if(outcome == null){
                    outcome = "";
                }
                String text = String.format("%s (%s)\n%s\n%s",
                        name, code, content, outcome);
                if(name != null){
                    info.setText(text);
                }else if(desc != null){
                    info.setText(desc);
                    info.setMaxWidth(350);
                    info.setWrapText(true);
                    scroll.setContent(info);
                    upperControl.getChildren().add(scroll);
                }
                
               
                
   
            }
            
            
        });
 
        
        leftControl.getChildren().add(tree);
        
        
        
        
        Scene scene = new Scene(tabPane, 1000, 500);
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
