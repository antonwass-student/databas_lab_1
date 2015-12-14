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
public class MediaType {
    private int id;
    private ObjectId mongoId;
    private String type;
    
    public MediaType(int id, String type){
        this.id = id;
        this.type = type;
    }
    
    public MediaType(ObjectId id, String type){
        this.mongoId = id;
        this.type = type;
    }
    
    public ObjectId getMongoId(){
        return mongoId;
    }
    
    public void setMongoId(ObjectId mid){
        mongoId = mid;
    }
    
    public String getType(){
        return type;
    }
    
    public String toString(){
        return type;
    }
    
    public int getId(){
        return id;
    }
}
