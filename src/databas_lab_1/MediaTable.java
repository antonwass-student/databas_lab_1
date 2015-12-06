/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Anton
 */
public class MediaTable extends TableView {
    public MediaTable(){
        super.setEditable(true);
        TableColumn titleCol = new TableColumn("Title");
        TableColumn typeCol = new TableColumn("Type");
        TableColumn genreCol = new TableColumn("Genre");
        TableColumn creatorCol = new TableColumn("Creator");
        TableColumn addedbyCol = new TableColumn("Added By");
        
        titleCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("title"));
        
        creatorCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("creatorName"));
        
        typeCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("typeName"));
        
        super.getColumns().addAll(titleCol, typeCol, genreCol,
                creatorCol, addedbyCol);
        
    }
}
