/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import javafx.beans.property.SimpleStringProperty;
import org.bson.types.ObjectId;

/**
 *
 * @author Anton
 */
public class MediaEntity {
    private int id;
    private ObjectId mongoId;
    private String title;
    private User addedBy;
    private Creator creator;
    private Genre genre;
    private MediaType mediaType;
    private float rating;
    
    public MediaEntity(int id, String title, MediaType mediaType, User addedBy, Creator creator, Genre genre, float rating){
        this.id = id;
        this.title = title;
        this.mediaType = mediaType;
        this.addedBy = addedBy;
        this.creator = creator;
        this.genre = genre;
        this.rating = rating;
    }
    
    public MediaEntity(ObjectId id, String title, MediaType mediaType, User addedBy, Creator creator, Genre genre, float rating){
        this.mongoId = id;
        this.title = title;
        this.mediaType = mediaType;
        this.addedBy = addedBy;
        this.creator = creator;
        this.genre = genre;
        this.rating = rating;
    }
    
    public ObjectId getMongoId(){
        return mongoId;
    }
    
    public void setMongoId(ObjectId mid){
        mongoId = mid;
    }
    
    public int getId(){
        return id;
    }
    
    public MediaType getMediaType(){
        return mediaType;
    }
    
    public User getAddedByUser(){
        return addedBy;
    }
    
    public Creator getCreator(){
        return creator;
    }
    
    public Genre getGenre(){
        return genre;
    }
    
    public String getRating(){
        return "" + rating;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getGenreName(){
        return genre.getName();
    }
    
    public String getAddedBy(){
        return addedBy.getName();
    }
    
    public String getCreatorName(){
        return creator.getName();
    }
    
    public String getTypeName(){
        return mediaType.getType();
    }
}
