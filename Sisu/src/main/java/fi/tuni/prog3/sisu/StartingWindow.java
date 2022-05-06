
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import java.io.File;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * A class for creating the main window and the degree programme tree.
 */
public class StartingWindow extends Application {

    private static final String STUDENTS_JSON_FILENAME = "studentsfile.json";
    private static final String ALL_DEGREES_FILENAME = "alldegreesfile.json";
    private static final String FULL_DEGREES_FILENAME = "fulldegreesfile.json";
    private final Image CHECK_MARK = new Image(new File("check.png").toURI().toString());
    private final Student student;
    
    /**
     * Constructs the object.
     * @param student Student who has logged in.
     */
    StartingWindow(Student student){
        this.student = student;
    }

    /**
     * Creates two tabs: the student's information tab and the degree 
     * programme tree tab. 
     * <p>
     * The student's information tab shows student's name, 
     * student number, progression (credits the student have completed and the 
     * degree's minimum credit requirement) and GPA. The user can save and quit
     * in which case the program writes student's information to the JSON-file
     * and closes the windows. The user can also save and return to menu for 
     * logging and signing in. If the user quits the program without saving
     * first, the changes the user have made won't be saved. The user can also
     * change the student's degree by selecting a new degree from the ComboBox.
     * The degree programme will be changed by calling 
     * {@link StudentData#changeStudentProgramme(fi.tuni.prog3.sisu.Student, 
     * fi.tuni.prog3.sisu.DegreeProgramme) 
     * StudentData.changeStudentProgramme(DegreeProgramme)} method.
     * <p>
     * The degree programme tab shows the student's degree programme in a tree
     * view. User can view additional information about the modules and courses
     * by clicking it in the tree view. For example some modules show the amount
     * of credits that is required to complete the module. Other modules might
     * instruct the user to choose certain amount of submodules or to complete
     * certain amount of credits from submodules. The program doesn't keep track
     * of for example if the student has completed enough credits for the module or
     * whether the student has completed correct amount of submodules for the
     * module. The user has to keep track of these.
     * <p>
     * Information about the course is shown when the course is clicked. 
     * Courses name, course code, credits, grading scale, contents and possibly
     * outcomes are shown. If the student hasn't completed the selected course
     * yet, the user can mark the course as completed. If the course have 
     * 1-5 grading the user can select the grade for the course. Otherwise the
     * course is marked as passed. If the course can be completed with varying
     * amounts of credits the user can select the amount of credits. When the
     * user has marked the course as completed, the student can't complete the 
     * course again. The "Lisää kurssi"-button will be removed and the grade and
     * credits that the student has got from course will be shown. Also, a check
     * mark will appear in front of the course name in the tree view. 
     * <p>
     * Free-choice modules and courses are not listed and the student can't add
     * or mark those as completed. User can't mark courses as failed.
     * @param stage Stage object.
     * @throws IOException if there is an IO error.
     */
    @Override
    public void start(Stage stage) 
            throws IOException {
                
        stage.setTitle("SISU");
        GridPane gridStart = new GridPane();
        TabPane tabPane = new TabPane();
        Tab startPage = new Tab("Opiskelijan tiedot");
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
        try (Reader reader = new FileReader(ALL_DEGREES_FILENAME)) {
           DegreeProgramme[] progs = gson.fromJson(reader, DegreeProgramme[].class);
           for (DegreeProgramme prog: progs) {
               values.add(prog);
           }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
        }

        ObservableList<DegreeProgramme> degreesObservable = FXCollections.observableList(values);
        ComboBox degreeComboBox = new ComboBox(degreesObservable);
        
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
            try {
                StudentData.studentsToFile(STUDENTS_JSON_FILENAME);
            } catch (IOException ex) {
                Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            Sisu sisu = new Sisu();
            sisu.start(stage);
        });
        
        saveExitBtn.setOnAction((ActionEvent e) -> {
            try {
                StudentData.studentsToFile(STUDENTS_JSON_FILENAME);
            } catch (IOException ex) {
                Logger.getLogger(StartingWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
            ((Node)(e.getSource())).getScene().getWindow().hide();
        });
        
        btnChangeDegree.setOnAction((ActionEvent e) -> {
            DegreeProgramme degree = (DegreeProgramme) degreeComboBox.getValue();

            HashMap<String, DegreeProgramme> degrees = DegreeObjectData.getDegreeMap();
            if (!(degrees.containsKey(degree.getGroupId()))) {
                try {
                    SearchTool.searchDegreeURL(degree.getGroupId());
                    DegreeObjectData.jsonFileToObjects(FULL_DEGREES_FILENAME);
                    degrees = DegreeObjectData.getDegreeMap();
                }catch (IOException ex) {
                    Logger.getLogger(Sisu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            StudentData.changeStudentProgramme(student, degrees.get(degree.getGroupId()));
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
        if (student.getDegreeProgramme().getModules() != null) {
            rootItem.getChildren().add(getTree(student.getDegreeProgramme().getModules().get(0)));
        }
        TreeView tree = new TreeView (rootItem);
        
        Label addGradeLabel = new Label("Syötä arvosana: ");
        addGradeLabel.setVisible(false);
        Spinner<Integer> addGradeSpinner = new Spinner<>(1, 5, 1, 1);
        addGradeSpinner.setVisible(false);
        lowerControl.add(addGradeLabel, 0, 1, 1, 1);
        lowerControl.add(addGradeSpinner, 1, 1, 1, 1);

        
        Label addCreditsLabel = new Label("Syötä opintopisteet: ");
        addCreditsLabel.setVisible(false);
        Spinner<Integer> addCreditsSpinner = new Spinner<>();
        addCreditsSpinner.setVisible(false);
        lowerControl.add(addCreditsLabel, 0, 2, 1, 1);
        lowerControl.add(addCreditsSpinner, 1, 2, 1, 1);
        
        Label infoModule = new Label(); 
        infoModule.setMaxWidth(480);
        infoModule.setWrapText(true);
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(infoModule);
        scroll.setPrefHeight(240);
        Label courseInfo = new Label("");
        Button btnAddCourse= new Button("Lisää kurssi");
        btnAddCourse.setVisible(false);
        lowerControl.add(btnAddCourse, 0, 3, 1, 1);
        lowerControl.add(courseInfo, 0, 0, 2, 1);
        upperControl.getChildren().add(scroll);
        
        
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
         
            /**
             * Method that is called when a tree item is clicked in the degree
             * tree view. The method shows information based on what type of 
             * tree item was clicked.
             * @param observable the ObservableValue which value was changed.
             * @param oldValue old tree item.
             * @param newValue tree item that was clicked.
             */
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
                TreeItem treeItem = (TreeItem) newValue;
                String infoText = "";
                ArrayList<CourseUnit> courses = student.getCourses();
                HashMap<String, CourseUnit> completedCourses = new HashMap<>();
                for (CourseUnit course: courses) {
                    completedCourses.put(course.getCode(), course);
                }
                
                if (treeItem.getValue() instanceof DegreeProgramme) {
                    DegreeProgramme mod = (DegreeProgramme) treeItem.getValue();
                    String name = mod.getName();
                    String code = mod.getCode();
                    int minCredits = mod.getMinCredits();
                    String outcomes = mod.getLearningOutcomes();
                    
                    infoText = String.format("%s (%s)\nVäh. %d op\n%s",
                            name, code, minCredits, outcomes);
                }
                
                if (treeItem.getValue() instanceof StudyModule) {
                    StudyModule mod = (StudyModule) treeItem.getValue();
                    String name = mod.getName();
                    String code = mod.getCode();
                    String content = mod.getContent();
                    String outcome = mod.getOutcomes();
                    String desc = mod.getDescription();
                    String type = mod.getType();
                    String gradeScale = mod.getGradeScaleId();
                    String minCredits = mod.getMinCredits();
                    String maxCredits = mod.getMaxCredits();
                    String minRequire = mod.getMinRequire();
                    String maxRequire = mod.getMaxRequire();
                    String credits = "";

                    if (content == null) {
                        content = "";
                    } else {
                        content = "\nSisältö:\n" + mod.getContent();
                    }

                    if (outcome == null) {
                        outcome = "";
                    } else {
                        outcome = "\nOppismistavoitteet:\n" + mod.getOutcomes();
                    }
                    
                    if (gradeScale == null) {
                        gradeScale = "";
                    } else {
                        gradeScale = "Arviointi: " + gradeScale.replace("sis-", "");
                    }
                    
                    if (minCredits != null && maxCredits != null) {
                        if (!minCredits.equals(maxCredits) && !maxCredits.equals("999")) {
                            credits = minCredits + "-" + maxCredits + " op";
                        } else if (maxCredits.equals("999")) {
                            credits = "väh. " + minCredits + " op";
                        } else {
                            credits = minCredits + " op";
                        }
                    }
                    
                    if (name != null) {
                        infoText = String.format("%s (%s)\n%s\n%s\n%s\n%s",
                            name, code, credits, gradeScale, content, outcome);
                    } else if ((desc != null) && (minRequire != null) && 
                            (maxRequire != null)) {
                        infoText = String.format("Valittava vähintään %s ja "
                                + "enintään %s\n%s", minRequire, maxRequire, desc);
         //               upperControl.getChildren().add(scroll);
                    } else if((desc != null) && (minRequire != null)) {
                        infoText = String.format("Valittava vähintään %s\n%s", 
                                minRequire, desc);
                    } else if ((desc != null)) {
                        infoText = desc;
                    }

                    if (type.equals("CourseUnitRule")) {
                        String textComplete = "";
                        
                        if (completedCourses.keySet().contains(code)) {
                            btnAddCourse.setVisible(false);
                            addGradeSpinner.setVisible(false);
                            addGradeLabel.setVisible(false);
                            addCreditsLabel.setVisible(false);
                            addCreditsSpinner.setVisible(false);
                            
                            if (gradeScale.contains("hyl-hyv")) {
                                textComplete = String.format("Kurssi %s suoritettu "
                                        + "hyväksytysti\nOpintopisteitä saatu: %d", name, 
                                        completedCourses.get(code).getCredits());
                            } else if (gradeScale.contains("0-5")) {
                                textComplete = String.format("Kurssista %s saatu "
                                        + "arvosanaksi %d\nOpintopisteitä saatu: %d", name, 
                                        completedCourses.get(code).getGrade(), 
                                        completedCourses.get(code).getCredits());
                            }
                        } else {
                            addGradeSpinner.setVisible(true);
                            addGradeLabel.setVisible(true);
                            btnAddCourse.setVisible(true);
                            textComplete = String.format("Merkitse kurssi suoritetuksi: %s", name);
                            
                            if (!minCredits.equals(maxCredits)) {
                                addCreditsLabel.setVisible(true);
                                addCreditsSpinner.setValueFactory(
                                        new IntegerSpinnerValueFactory(
                                                Integer.parseInt(minCredits), 
                                                Integer.parseInt(maxCredits), 
                                                Integer.parseInt(minCredits), 1));
                                addCreditsSpinner.setVisible(true);
                            } else {
                                addCreditsLabel.setVisible(false);
                                addCreditsSpinner.setVisible(false); 
                            }

                            if (gradeScale.equals("Arviointi: 0-5")) {
                                addGradeLabel.setVisible(true);
                                addGradeSpinner.setVisible(true);
                            } else {
                                addGradeLabel.setVisible(false);
                                addGradeSpinner.setVisible(false);
                            }

                            addCourseBtnClicked(btnAddCourse, mod, treeItem);
                        }
                        courseInfo.setText(textComplete);
                        courseInfo.setWrapText(true);
                        courseInfo.setVisible(true);
                           
                        
                    } else {
                        addGradeSpinner.setVisible(false);
                        addGradeLabel.setVisible(false);
                        courseInfo.setVisible(false);
                        btnAddCourse.setVisible(false);
                        addCreditsLabel.setVisible(false);
                        addCreditsSpinner.setVisible(false);
                    }
                }
                
                infoModule.setText(infoText);

            }

            /**
             * Method for handling the "Lisää kurssi"-button.
             * @param btnAddCourse "Lisää kurssi"-button.
             * @param mod selected course as a StudyModule object.
             * @param treeItem selected course as a TreeItem object.
             */
            private void addCourseBtnClicked(Button btnAddCourse, StudyModule 
                    mod, TreeItem treeItem) {
                btnAddCourse.setOnAction(new EventHandler<ActionEvent>(){
            
                    /**
                     * Method that is called when "Lisää kurssi"-button is 
                     * clicked. Instantiates new {@link CourseUnit} objects
                     * based on the grade and credits that student got.
                     * The completed course is assigned to the student by
                     * calling {@link Student#addCourse(fi.tuni.prog3.sisu.CourseUnit) 
                     * Student.addCourse(CourseUnit)}.
                     * <p> 
                     * The method checks if spinners are visible in order to 
                     * determine whether a course's grading is 0-5 or fail-pass
                     * and to determine how many credits the course is worth.
                     * A visible spinner indicates that the user has had the 
                     * option to choose the grade and/or credits. If a spinner
                     * is hidden, a default value will be used. Default value
                     * for grade is -1 (this means course is passed) and for
                     * credits is the courses minimum credits (which will be 
                     * the same as the maximum credits).
                     * @param e ActionEvent object.
                     */
                    @Override
                    public void handle(ActionEvent e){
                        btnAddCourse.setVisible(false);
                        
                        int grade = -1;
                        int credits = Integer.parseInt(mod.getMinCredits());

                        if (addCreditsSpinner.isVisible()) {
                            credits = addCreditsSpinner.getValue();
                        }
                        String textComplete = String.format("Kurssi %s suoritettu "
                                            + "hyväksytysti\nOpintopisteitä saatu: "
                                + "%d", mod.getName(), credits);

                        if (addGradeSpinner.isVisible()) {
                            grade = addGradeSpinner.getValue();
                            textComplete = String.format("Kurssista %s saatu "
                                            + "arvosanaksi %d\nOpintopisteitä saatu: "
                                    + "%d", mod.getName(), grade, credits);
                        }
                        courseInfo.setText(textComplete);


                        CourseUnit course = new CourseUnit(mod.getName(),
                                mod.getCode(), credits, grade);
                        student.addCourse(course);

                        addGradeSpinner.setVisible(false);
                        addGradeLabel.setVisible(false);
                        addCreditsLabel.setVisible(false);
                        addCreditsSpinner.setVisible(false);

                        progLabel.setText(student.getCompletedCredits() + "/"
                                + student.getDegreeProgramme().getMinCredits());
                        String gpaStr = String.format("Keskiarvo: %.2f", student.getGPA());
                        gpaLabel.setText(gpaStr);
                        treeItem.setGraphic(new ImageView(CHECK_MARK));

                        progression.setProgress(student.getProgression());
                        addGradeSpinner.getValueFactory().setValue(1);
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
    
    /**
     * Creates the TreeItem that will show the tree view. The method works 
     * recursively based on the {@link StudyModule} object's hierarchy. 
     * The method reaches it's base case when the StudyModule 
     * object doesn't have any submodules.
     * @param root a {@link StudyModule} object that represents a submodule 
     * or a course. 
     * @return TreeItem that contains all the modules and courses so far 
     * (i.e. the current nested tree)
     */
    private TreeItem<StudyModule> getTree(StudyModule root) {
        TreeItem<StudyModule> result = new TreeItem<>(root);
        
        if (root.getType().equals("CourseUnitRule")) {
            for (CourseUnit course: student.getCourses()) {
                if (root.getCode().equals(course.getCode())) {
                    result.setGraphic(new ImageView(CHECK_MARK)); 
                }
            }
        } 

        if (root.getModules() != null) {
            if (root.getName() != null) {
                for (StudyModule module: root.getModules()) {
                    result.getChildren().add(getTree(module));
                }
                
            } else if (root.getType().equals("CompositeRule")) {
                if (root.getModules().size() > 1) {
                    for (StudyModule module: root.getModules()) {
                        result.getChildren().add(getTree(module));
                    }
                } else if (root.getModules().size() == 1) {
                    result = getTree(root.getModules().get(0));
                }

            } else {
                result = getTree(root.getModules().get(0));

            }

        }

        return result;
    }
   
    
}
