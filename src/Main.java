
import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
HBox root=new  HBox();
  Scene scene=new Scene(root , 300,300);
  Button btn=new Button();
        
        btn.setOnAction(e-> {
            
            FileChooser fc=new FileChooser();
            
            fc.getExtensionFilters().addAll(new ExtensionFilter("PDF File", "*pdf"));
            
            File file= fc.showOpenDialog(primaryStage);
            
            System.out.println(file);
            
        });
        root.getChildren().add(btn);
        primaryStage.setScene(scene);
        primaryStage.show();    }
    
}
