/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Anton
 */
public class MediaCenter extends Application{
    DatabaseCommunication dbCom = null;
    
    public MediaCenter(){
        this.dbCom = new DBMySql();
    }
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("SELECT * FROM testtable");
        
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              System.out.println("Stage is closing");
              dbCom.disconnect();
          }
        }); 
        
        //Test button to send a query to the server and present the result.
        btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                Thread t  = new Thread(){
                    public void run(){
                        ArrayList<MediaEntity> result = dbCom.getMediaByTitle("");
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    updateMediaList(result);
                                }
                            }
                        
                        );
                    }
                };
                t.start();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 800, 640);
        
        primaryStage.setTitle("Media Center by Anton Wass & Joachim Zetterman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void updateMediaList(ArrayList<MediaEntity> media){
        if(media == null)
            return;
        for(MediaEntity m : media)
            System.out.println(m.getTitle());
    }
}
