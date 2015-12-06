/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class AddMediaStage extends Stage{
    DatabaseCommunication dbCom = null;
    
    public AddMediaStage(DatabaseCommunication dbCom){
        this.dbCom = dbCom;
        super.initStyle(StageStyle.DECORATED);
        
        VBox vb = new VBox();
        vb.getChildren().add(new Label("Add a new media entity"));
        Scene scene_AddMedia = new Scene(vb, 300, 300);

        
        super.setScene(scene_AddMedia);
    }
}
