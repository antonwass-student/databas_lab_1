/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class AddMediaStage extends Stage{
    private DatabaseCommunication dbCom = null;
    private User currentUser = null;
    private TextField tf_title = new TextField();
    private ComboBox cb_type = new ComboBox();
    private ComboBox cb_genre = new ComboBox();
    private ComboBox cb_creator = new ComboBox();
    private Button btn_confirm = new Button("Confirm");
    private Button btn_newType = new Button("+");
    private Button btn_newCreator = new Button("+");
    private Button btn_newGenre = new Button("+");
    
    public AddMediaStage(DatabaseCommunication dbCom, User user){
        this.dbCom = dbCom;
        this.currentUser = user;
        super.initStyle(StageStyle.DECORATED);
        
        HBox typeBox = new HBox();
        typeBox.getChildren().add(cb_type);
        typeBox.getChildren().add(btn_newType);
        HBox genreBox = new HBox();
        genreBox.getChildren().add(cb_genre);
        genreBox.getChildren().add(btn_newGenre);
        HBox creatorBox = new HBox();
        creatorBox.getChildren().add(cb_creator);
        creatorBox.getChildren().add(btn_newCreator);
        
        btn_newCreator.setMinWidth(50);
        btn_newGenre.setMinWidth(50);
        btn_newType.setMinWidth(50);
        creatorBox.setSpacing(20);
        genreBox.setSpacing(20);
        typeBox.setSpacing(20);
        
        VBox vb = new VBox();
        vb.getChildren().add(new Label("Add a new media entity"));
        vb.getChildren().add(new Label("Title:"));
        vb.getChildren().add(tf_title);
        vb.getChildren().add(new Label("Type of media:"));
        vb.getChildren().add(typeBox);
        vb.getChildren().add(new Label("Genre:"));
        vb.getChildren().add(genreBox);
        vb.getChildren().add(new Label("Creator (artist/director etc):"));
        vb.getChildren().add(creatorBox);
        vb.getChildren().add(btn_confirm);
        vb.setPadding(new Insets(10,10,10,10));
        Scene scene_AddMedia = new Scene(vb, 300, 300);
        
        btn_newType.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                AddMediaTypeStage newTypeStage = new AddMediaTypeStage(dbCom);
            }
        });
        
        btn_newGenre.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                AddGenreStage newGenreStage = new AddGenreStage(dbCom);
            }
        });
        
        btn_newCreator.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                AddCreatorStage newCreatorStage = new AddCreatorStage(dbCom, currentUser);
            }
        });
        
        btn_confirm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Thread t = new Thread(){
                    
                    public void run(){
                        MediaEntity media = new MediaEntity(-1,
                         tf_title.getText(),
                        (MediaType)cb_type.getValue(),
                        currentUser,
                        (Creator)cb_creator.getValue(),
                        (Genre)cb_genre.getValue(), 0f);
                        
                        dbCom.addMediaEntity(media);
                        
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    closeStage();
                                }
                            }
                        );
                    }
                };
                t.start();
            }
        });
        
        cb_genre.setOnMouseClicked(new EventHandler<MouseEvent>(){
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
        
        cb_type.setOnMouseClicked(new EventHandler<MouseEvent>(){
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
        
        cb_creator.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Thread t  = new Thread(){
                    public void run(){
                        ArrayList<Creator> result = dbCom.getCreators();
                        
                        javafx.application.Platform.runLater(
                            new Runnable(){
                                public void run(){
                                    updateCreatorCombobox(result);
                                }
                            }
                        );
                    }
                };
                t.start();
            };
        });
        
        
        
        super.setScene(scene_AddMedia);
    }
    
    private void closeStage(){
        super.close();
    }
    
    private void updateMediaTypeCombobox(ArrayList<MediaType> types){
        cb_type.setItems(FXCollections.observableArrayList(types));
    }
    
    private void updateCreatorCombobox(ArrayList<Creator> creators){
        cb_creator.setItems(FXCollections.observableArrayList(creators));
    }
    
    private void updateGenreCombobox(ArrayList<Genre> genres){
        cb_genre.setItems(FXCollections.observableArrayList(genres));
    }
}
