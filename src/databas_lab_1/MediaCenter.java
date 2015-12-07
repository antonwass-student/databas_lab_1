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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private DatabaseCommunication dbCom = null;
    private User currentUser = null;
    
    private final TableView tv = new MediaTable();
    private final Button btn = new Button();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("New");
    private final TextField searchKey = new TextField();
    private final Label tableTitle = new Label("Media");
    private final ComboBox cbMediaType = new ComboBox();
    private final ComboBox cbGenre = new ComboBox();
    
    public MediaCenter(){
        this.dbCom = new DBMySql();
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        //Login stage. program wont continue until login window is closed.
        LoginStage loginStage = new LoginStage(dbCom, this);
        
        AddMediaStage addMediaStage = new AddMediaStage(dbCom, currentUser);
        MenuItem menuAddMedia = new MenuItem("Media");
        menuAddMedia.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                addMediaStage.show();
            }
        });
        
        MenuItem menuAddMediaType = new MenuItem("Media Type");
        menuAddMediaType.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                AddMediaTypeStage amts = new AddMediaTypeStage(dbCom);
                amts.show();
            }
        });
        
        MenuItem menuAddGenre = new MenuItem("Genre");
        menuAddGenre.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                AddGenreStage amts = new AddGenreStage(dbCom);
                amts.show();
            }
        });
        
        MenuItem menuAddCreator = new MenuItem("Creator");
        menuAddCreator.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                AddCreatorStage amts = new AddCreatorStage(dbCom, currentUser);
                amts.show();
            }
        });
           
        menuBar.getMenus().add(menuFile);
        menuFile.getItems().add(menuAddMedia);
        menuFile.getItems().add(menuAddMediaType);
        menuFile.getItems().add(menuAddGenre);
        menuFile.getItems().add(menuAddCreator);
        
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
                Thread t  = new Thread(){
                    public void run(){
                        ArrayList<Genre> result = dbCom.getGenres();
                        
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    updateGenreCombobox(result);
                                }
                            }
                        );
                    }
                };
                t.start();
            };
        });
        
        cbMediaType.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Thread t  = new Thread(){
                    public void run(){
                        ArrayList<MediaType> result = dbCom.getMediaTypes();
                        
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    updateMediaTypeCombobox(result);
                                }
                            }
                        );
                    }
                };
                t.start();
            };
        });
        
        BorderPane root = new BorderPane();
        VBox leftBox = new VBox();
        VBox rightBox = new VBox();
        VBox searchBox = new VBox();
        leftBox.setSpacing(15);
        leftBox.setPadding(new Insets(10,10,10,10));
        
        searchBox.getChildren().add(new Label("Keyword"));
        searchBox.getChildren().add(searchKey);
        leftBox.getChildren().add(searchBox);
        leftBox.getChildren().add(btn);
        leftBox.getChildren().add(new Label("Filters:"));

        VBox cbGenreBox = new VBox();
        Label lblGenreSelect = new Label("Genre:   ");
        cbGenreBox.getChildren().add(lblGenreSelect);
        cbGenreBox.getChildren().add(cbGenre);
        leftBox.getChildren().add(cbGenreBox);
        
        VBox mediaTypeBox = new VBox();
        Label lblTypeSelect = new Label("Media Type:    ");
        mediaTypeBox.getChildren().add(lblTypeSelect);
        mediaTypeBox.getChildren().add(cbMediaType);
        leftBox.getChildren().add(mediaTypeBox);
        
        VBox centerBoxMedia = new VBox();
        centerBoxMedia.getChildren().add(tableTitle);
        centerBoxMedia.getChildren().add(tv);
        
        tableTitle.setStyle("-fx-font-size:20px;");
      
        //RIGHT BOX
        HBox rateBox = new HBox();
        TextField tf_rate = new TextField();
        Button btn_rate = new Button("Rate!");
        tf_rate.setMaxWidth(50);
        rateBox.getChildren().add(tf_rate);
        rateBox.getChildren().add(btn_rate);
        
        HBox addReviewBox = new HBox();
        Button btn_addReview = new Button("Add review");
        addReviewBox.getChildren().add(btn_addReview);
        
        HBox readReviewBox = new HBox();
        Button btn_readReview = new Button("Read reviews");
        readReviewBox.getChildren().add(btn_readReview);
        
        rightBox.getChildren().addAll(rateBox, addReviewBox, readReviewBox);    
        rightBox.setPadding(new Insets(10,10,10,10));
        rightBox.setSpacing(10);
        root.setRight(rightBox);
        
        btn_rate.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                MediaEntity me = (MediaEntity)tv.getSelectionModel().getSelectedItem();
                dbCom.rateMediaEntity(me, currentUser, MathUtility.clamp(Float.parseFloat(tf_rate.getText()), 0f, 5f));
            }
        });
        
        btn_addReview.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                MediaEntity me = (MediaEntity)tv.getSelectionModel().getSelectedItem();
                AddReviewStage amts = new AddReviewStage(dbCom, currentUser, me);
                amts.show();
            }
        });
        
        btn_readReview.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent evt){
                MediaEntity me = (MediaEntity)tv.getSelectionModel().getSelectedItem();
                ReadReviewsStage amts = new ReadReviewsStage(dbCom, currentUser, me);
                amts.show();
            }
        });
        
        
        root.setCenter(centerBoxMedia);
        root.setLeft(leftBox);
        root.setTop(menuBar);
        Scene scene = new Scene(root, 800, 640);
        
        primaryStage.setTitle("Media Center by Anton Wass & Joachim Zetterman");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public void setUser(User user){
        this.currentUser = user;
    }
    
    private void updateMediaTypeCombobox(ArrayList<MediaType> types){
        cbMediaType.setItems(FXCollections.observableArrayList(types));
    }
    
    private void updateCreatorCombobox(ArrayList<Creator> creators){
        //cbCreator.setItems(FXCollections.observableArrayList(creators));
    }
    
    private void updateGenreCombobox(ArrayList<Genre> genres){
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
