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
public class AddCreatorStage extends Stage{
    private DatabaseCommunication dbCom = null;
    private TextField tf_title = new TextField();
    private Button btn_confirm = new Button("Done");
    
    public AddCreatorStage(DatabaseCommunication dbCom, User user){
        this.dbCom = dbCom;
        super.initStyle(StageStyle.DECORATED);
        VBox vb = new VBox();
        super.setTitle("Add a new creator!");
        
        vb.getChildren().add(new Label("Add a new creator!"));
        vb.getChildren().add(new Label("Name:"));
        vb.getChildren().add(tf_title);
        vb.getChildren().add(btn_confirm);
        vb.setPadding(new Insets(10,10,10,10));
        
        btn_confirm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Thread t = new Thread(){
                    
                    public void run(){
                        Creator creator = new Creator(-1, tf_title.getText());

                        dbCom.addCreator(creator, user);
                        
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
