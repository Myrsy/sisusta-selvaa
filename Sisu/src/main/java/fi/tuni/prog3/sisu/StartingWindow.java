
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
import javafx.scene.control.Spinner;
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

    //private StudentData studentData;
    private Student student;
    StartingWindow(Student student){
      //  this.studentData = new StudentData();
        this.student = student;
    }

    
    @Override
    public void start(Stage stage) throws IOException {
                
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
        
        Label gpaLabel = new Label("Keskiarvo: " + student.getGPA());
        gpaLabel.setFont(new Font("Arial", 18));
        gridStart.add(gpaLabel, 1, 0);
        
        Label degreeLabel = new Label("Tutkinnon vaihtaminen: ");
        degreeLabel.setFont(new Font("Arial", 18));
        Label warningLabel = new Label("HUOM! Tutkinnon vaihtaminen nollaa\n" +
                "kaikki suorittamaksi merkitsemäsi kurssit.");
        Button btnChangeDegree = new Button("Vaihda tutkintoa");
        degreeBox.getChildren().addAll(degreeLabel, degreeComboBox,
                warningLabel, btnChangeDegree);
        gridStart.add(degreeBox, 0, 2, 1, 2);
        GridPane.setValignment(degreeComboBox, VPos.TOP);
        
        Button saveExitBtn = new Button("Tallenna ja poistu");
        Button saveBackBtn = new Button("Tallenna ja palaa\nkirjautumissivulle");
        gridStart.add(saveExitBtn, 1, 1);
        gridStart.add(saveBackBtn, 1, 2);
        
        saveBackBtn.setOnAction((ActionEvent e) -> {
            //Tallennus tähän väliin  
            Sisu sisu = new Sisu();
            try {
                sisu.start(stage);
            } catch (IOException ex) {
                Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((Node)(e.getSource())).getScene().getWindow().hide();
        });
        
        saveExitBtn.setOnAction((ActionEvent e) -> {
            //Tallennus tähän väliin      
            ((Node)(e.getSource())).getScene().getWindow().hide();
        });
        
        btnChangeDegree.setOnAction((ActionEvent e) -> {
            DegreeProgramme degree = (DegreeProgramme) degreeComboBox.getValue();

            HashMap<String, DegreeProgramme> degrees1 = DegreeObjectData.getDegreeMap();
            if (!(degrees1.containsKey(degree.getGroupId()))) {
                try {
                    SearchTool.searchDegreeURL(degree.getGroupId());
                    DegreeObjectData.jsonFileToObjects();
                    degrees1 = DegreeObjectData.getDegreeMap();
                }catch (IOException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                StudentData.changeStudentProgramme(student, degrees1.get(degree.getGroupId()));
            } catch (IOException ex) {
                Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            StartingWindow startingWindow = new StartingWindow(student);
            Stage stage1 = new Stage();
            try {
                startingWindow.start(stage1);
            } catch (IOException ex) {
                Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((Node)(e.getSource())).getScene().getWindow().hide();
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
        upperControl.setPrefHeight(240);
        GridPane lowerControl = new GridPane();
        lowerControl.setPrefHeight(240);
        rightControl.getChildren().add(splitPaneR); 
        
        List<DegreeProgramme> degs = new ArrayList<>(); 
        degs.add(student.getDegreeProgramme());
        
        ObservableList<DegreeProgramme> deg = FXCollections.observableList(degs);
        TreeItem rootItem = new TreeItem (deg.get(0));
        if (student.getDegreeProgramme().getModules() == null) {
            System.out.println("null");
        } else {
            rootItem.getChildren().add(getTree(student.getDegreeProgramme().getModules().get(0)));
        }
        TreeView tree = new TreeView (rootItem);
        
        Label addGradeLabel = new Label("Syötä arvosana: ");
        addGradeLabel.setVisible(false);
        Spinner<Integer> addGradeSpinner = new Spinner<>(1, 5, 0, 1);
        addGradeSpinner.setVisible(false);
        lowerControl.add(addGradeLabel, 0, 1, 1, 1);
        lowerControl.add(addGradeSpinner, 1, 1, 1, 1);
        Label infoModule = new Label(); 
        ScrollPane scroll = new ScrollPane();
        Label courseInfo = new Label("");
        Button btnAddCourse= new Button("Lisää kurssi");
        btnAddCourse.setVisible(false);
        lowerControl.add(btnAddCourse, 0, 2, 1, 1);
        lowerControl.add(courseInfo, 0,0,2,1);
        upperControl.getChildren().add(scroll);
        
        
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
         
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
                TreeItem treeItem = (TreeItem)newValue;
                
                if (treeItem.getValue() instanceof StudyModule) {
                    StudyModule mod = (StudyModule) treeItem.getValue();
                    String name = mod.getName();
                    String code = mod.getCode();
                    String content = mod.getContent();
                    String outcome = mod.getOutcomes();
                    String desc = mod.getDescription();
                    String type = mod.getType();

                    if(content == null){
                        content = "";
                    }else{
                        content = "\nSisältö:\n" + mod.getContent();
                    }

                    if(outcome == null){
                        outcome = "";
                    }else{
                        outcome = "\nOppismistavoitteet:\n" + mod.getOutcomes();
                    }
                    String text = String.format("%s (%s)\n%s\n%s",
                            name, code, content, outcome);
                    if(name != null){
                        infoModule.setText(text);
                    }else if(desc != null){
                        infoModule.setText(desc);
                        infoModule.setMaxWidth(480);
                        infoModule.setWrapText(true);
                        scroll.setContent(infoModule);
                        scroll.setPrefHeight(240);
         //               upperControl.getChildren().add(scroll);
                    }


                    if(type.equals("CourseUnitRule")){
                        String textComplete = 
                                String.format("Merkitse kurssi suoritetuksi: %s", name);
                        courseInfo.setText(textComplete);
                        courseInfo.setVisible(true);
                        addGradeSpinner.setVisible(true);
                        addGradeLabel.setVisible(true);
                        btnAddCourse.setVisible(true);


                        addCourseBtnClicked(btnAddCourse, addGradeSpinner.getValue(), mod);

                    }else{
                        addGradeSpinner.setVisible(false);
                        addGradeLabel.setVisible(false);
                        courseInfo.setVisible(false);
                        btnAddCourse.setVisible(false);
                    }
                }
            }

            private void addCourseBtnClicked(Button btnAddCourse, Integer grade, StudyModule mod) {
                btnAddCourse.setOnAction(new EventHandler<ActionEvent>(){
            
                @Override
                public void handle(ActionEvent e){

                    CourseUnit course = new CourseUnit(mod.getName(),
                            mod.getGroupId(), Integer.valueOf(mod.getMinCredits()));
                    student.addCourse(course, grade);
                    try {
                        progLabel.setText(student.getCompletedCredits() + "/"
                                + student.getDegreeProgramme().getMinCredits());
                    } catch (IOException ex) {
                        Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    gpaLabel.setText("Keskiarvo: " + student.getGPA());
                    
                    progression.setProgress(student.getProgression());
                    //addGradeSpinner.
                }

            });
            }
            
            
        });
 
        
        leftControl.getChildren().add(tree);
        splitPaneR.getItems().addAll(upperControl, lowerControl);
        splitPane.getItems().addAll(leftControl, rightControl);
        degreePage.setContent(splitPane);
        Scene scene = new Scene(tabPane, 1000, 500);
        stage.setScene(scene);
        stage.show();
    
    
    }
    
    private TreeItem<StudyModule> getTree(StudyModule root) {
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
