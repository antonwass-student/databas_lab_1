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
public class User {
    private int id;
    private ObjectId mongoId;
    private String name;
    private String password;
    
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
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
}
