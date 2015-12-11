/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import org.bson.types.ObjectId;

/**
 *
 * @author Anton
 */
public class Review {
    private int id;
    private ObjectId mongoId;
    private float rating;
    private String text;
    private User author;
    private MediaEntity media;
    
    public Review(int id, float rating, String text, User author, MediaEntity media){
        this.id = id;
        this.rating = MathUtility.clamp(rating, 0f, 5f);
        this.text = text;
        this.author = author;
        this.media = media;
    }
    
    public ObjectId getMongoId(){
        return mongoId;
    }
    
    public void setMongoId(ObjectId mid){
        mongoId = mid;
    }
}
