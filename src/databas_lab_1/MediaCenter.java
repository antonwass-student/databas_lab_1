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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Anton
 */
public class MediaCenter extends Application{
    DatabaseCommunication dbCom = null;
    User currentUser = null;
    
    private final TableView tv = new MediaTable();
    private final Button btn = new Button();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("Options");
    private final TextField searchKey = new TextField();
    private final Label tableTitle = new Label("Media");
    private final ComboBox cbMediaType = new ComboBox();
    private final ComboBox cbGenre = new ComboBox();
    
    public MediaCenter(){
        this.dbCom = new DBMySql();
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        updateGenreCombobox();
        updateMediaTypeCombobox();
        updateCreatorCombobox();
        
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
                        ArrayList<MediaEntity> result;
                        if((Genre)cbGenre.getValue() == null){
                             result = 
                                    dbCom.getMediaBySearch(searchKey.getText());
                        }
                        else{
                            result = 
                                    dbCom.getMediaBySearch(searchKey.getText(), (Genre)cbGenre.getValue());
                        }
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
        
        cbGenre.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                updateGenreCombobox();
            };
        });
        
        cbMediaType.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                updateMediaTypeCombobox();
            };
        });
        
        BorderPane root = new BorderPane();
        VBox leftBox = new VBox();
        VBox searchBox = new VBox();
        leftBox.setSpacing(15);
        leftBox.setPadding(new Insets(10,10,10,10));
        
        searchBox.getChildren().add(new Label("Keyword"));
        searchBox.getChildren().add(searchKey);
        leftBox.getChildren().add(searchBox);
        
        leftBox.getChildren().add(btn);
        
        leftBox.getChildren().add(new Label("Filters:"));

        HBox cbGenreBox = new HBox();
        Label lblGenreSelect = new Label("Genre:   ");
        cbGenreBox.getChildren().add(lblGenreSelect);
        cbGenreBox.getChildren().add(cbGenre);
        leftBox.getChildren().add(cbGenreBox);
        
        HBox mediaTypeBox = new HBox();
        Label lblTypeSelect = new Label("Media Type:    ");
        mediaTypeBox.getChildren().add(lblTypeSelect);
        mediaTypeBox.getChildren().add(cbMediaType);
        leftBox.getChildren().add(mediaTypeBox);
        
        VBox centerBox = new VBox();
        centerBox.getChildren().add(tableTitle);
        centerBox.getChildren().add(tv);
        
        tableTitle.setStyle("-fx-font-size:20px; -fx-border:5px solid black;");
        

       
        root.setCenter(centerBox);
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
    
    private void updateMediaTypeCombobox(){
        ArrayList<MediaType>types = new ArrayList();
        types.add(new MediaType(-1, "Any"));
        types.addAll(dbCom.getMediaTypes());
        cbMediaType.setItems(FXCollections.observableArrayList(types));
    }
    
    private void updateCreatorCombobox(){
        
    }
    
    private void updateGenreCombobox(){
        ArrayList<Genre>genres = new ArrayList();
        genres.add(new Genre(-1, "Any"));
        genres.addAll(dbCom.getGenres());
        cbGenre.setItems(FXCollections.observableArrayList(genres));
    }
    
    
    
    private void updateMediaList(ArrayList<MediaEntity> media){
        if(media == null)
            return;
        
        ObservableList<MediaEntity> data = 
                FXCollections.observableArrayList(media);
        tv.setItems(data);
        
    }
}
