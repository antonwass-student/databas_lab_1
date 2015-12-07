/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class AddGenreStage extends Stage{
    private DatabaseCommunication dbCom = null;
    private TextField tf_title = new TextField();
    private Button btn_confirm = new Button("Done");
    
    public AddGenreStage(DatabaseCommunication dbCom){
        this.dbCom = dbCom;
        super.initStyle(StageStyle.DECORATED);
        VBox vb = new VBox();
        super.setTitle("Create a new genre!");
        vb.setPadding(new Insets(10,10,10,10));
        
        vb.getChildren().add(new Label("Add a new genre"));
        vb.getChildren().add(new Label("Genre:"));
        vb.getChildren().add(tf_title);
        vb.getChildren().add(btn_confirm);
        
        btn_confirm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Thread t = new Thread(){
                    
                    public void run(){
                        Genre genre = new Genre(-1, tf_title.getText());

                        dbCom.addGenre(genre);
                        
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
        
        Scene scene_AddMedia = new Scene(vb, 300, 300);
        
        super.setScene(scene_AddMedia);
        super.show();
    }
        
    private void closeStage(){
        super.close();
    }
}
