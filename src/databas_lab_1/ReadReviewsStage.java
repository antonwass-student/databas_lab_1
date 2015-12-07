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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Joachim
 */
public class ReadReviewsStage extends Stage{
    private DatabaseCommunication dbCom = null;
    
    private Button btn_confirm = new Button("Done");
    
    public ReadReviewsStage(DatabaseCommunication dbCom, User user){
         this.dbCom = dbCom;
        super.initStyle(StageStyle.DECORATED);
        VBox vb = new VBox();
        super.setTitle("Read reviews");
        
        vb.getChildren().add(new Label("Read reviews"));
        vb.setPadding(new Insets(10,10,10,10));
        vb.setSpacing(10);
        
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
