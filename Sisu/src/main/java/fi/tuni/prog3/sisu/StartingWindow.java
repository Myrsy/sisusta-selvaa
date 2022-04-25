
package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

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
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 400, 200);
        stage.setScene(scene);
        stage.show();
    
    
    }
    public static void main(String args[]) {
       launch();
    }
}
