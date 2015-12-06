/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Anton
 */
public class AddMediaTypeStage extends Stage {
    private DatabaseCommunication dbCom = null;
    private TextField tf_title = new TextField();
    
    public AddMediaTypeStage(){
        super.initStyle(StageStyle.DECORATED);
        VBox vb = new VBox();
        
        vb.getChildren().add(new Label("Add creator:"));
        vb.getChildren().add(new Label("Add creator:"));
        
        Scene scene_AddMedia = new Scene(vb, 300, 300);
        
        super.setScene(scene_AddMedia);
    }
}
