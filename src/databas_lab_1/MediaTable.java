/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
        TableColumn ratingCol = new TableColumn("Rating");
        TableColumn rateCol = new TableColumn("Rate");
        
        titleCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("title"));
        
        creatorCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("creatorName"));
        
        typeCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("typeName"));
        
        genreCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("genreName"));
        
        addedbyCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("addedBy"));
        
        ratingCol.setCellValueFactory
        (new PropertyValueFactory<MediaEntity, String>("rating"));
        
        super.getColumns().addAll(titleCol, typeCol, genreCol,
                creatorCol, addedbyCol, ratingCol);
        
        
    }
}
