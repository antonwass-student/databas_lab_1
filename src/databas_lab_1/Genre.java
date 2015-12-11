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
public class Genre {
    private int id;
    private ObjectId mongoId;
    private String name;
    
    public Genre(int id, String name){
        this.id = id;
        this.name = name;
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
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
