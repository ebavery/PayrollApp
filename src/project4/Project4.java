/*****************************************************************************
 * Program Name:        Project 4 -- Project4.java
 * Program Description: This program creates a payroll application.
 * Program Author:      Elizabeth Avery
 * Date Created:        12/09/2016
 * Change#      Change Date     Programmer Name     Description
 * -------      -----------     ---------------     ----------------------
*****************************************************************************/
package project4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Project4 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root, 1000, 900);
        scene.getStylesheets().add(Project4.class.getResource("PayrollAdministration.css").toExternalForm()); 
        stage.setScene(scene);
        stage.setTitle("Payroll Administration");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
