/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Anton
 */
public class MediaCenter extends Application{
    DatabaseCommunication dbCom = null;
    
    private TableView tv = new MediaTable();
    private Button btn = new Button();
    private MenuBar menuBar = new MenuBar();
    private Menu menuFile = new Menu("File");
    private TextField searchKey = new TextField();
    
    public MediaCenter(){
        this.dbCom = new DBMySql();
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        btn.setText("Search");
        btn.setMinWidth(200);
        
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
                        ArrayList<MediaEntity> result = 
                                dbCom.getMediaBySearch(searchKey.getText());
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
        
        BorderPane root = new BorderPane();
        VBox leftBox = new VBox();
        
        leftBox.getChildren().add(btn);
        leftBox.getChildren().add(searchKey);
        
        
        root.setCenter(tv);
        root.setLeft(leftBox);
        menuBar.getMenus().add(menuFile);
        root.setTop(menuBar);
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
        
        ObservableList<MediaEntity> data = 
                FXCollections.observableArrayList(media);
        
        tv.setItems(data);
        
    }
}
