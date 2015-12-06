/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Anton
 */
public class MediaEntity {
    private int id;
    private String title;
    private User addedBy;
    private Creator creator;
    private Genre genre;
    private MediaType mediaType;
    
    public MediaEntity(int id, String title, MediaType mediaType, User addedBy, Creator creator, Genre genre){
        this.id = id;
        this.title = title;
        this.mediaType = mediaType;
        this.addedBy = addedBy;
        this.creator = creator;
        this.genre = genre;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getCreatorName(){
        return creator.getName();
    }
    
    public String getTypeName(){
        return mediaType.getType();
    }
}
