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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class AddReviewStage extends Stage{
    private DatabaseCommunication dbCom = null;
    
    private ComboBox mediaEntity = new ComboBox(null);  //Null should be list of all media entities
    
    private TextArea ta_review = new TextArea();
    private Button btn_confirm = new Button("Done");
    
    
    public AddReviewStage(DatabaseCommunication dbCom, User user){
        this.dbCom = dbCom;
        super.initStyle(StageStyle.DECORATED);
        VBox vb = new VBox();
        super.setTitle("Add a new review!");
        
        vb.getChildren().add(new Label("Add a new review!"));
        vb.getChildren().add(new Label("Select media entity:"));
        vb.getChildren().add(mediaEntity);
        vb.getChildren().add(new Label("Text:"));
        vb.getChildren().add(ta_review);
        vb.getChildren().add(btn_confirm);
        vb.setPadding(new Insets(10,10,10,10));
        
        btn_confirm.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
               //temp
               closeStage();
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
