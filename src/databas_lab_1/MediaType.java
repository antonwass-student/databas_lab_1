/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

/**
 *
 * @author Anton
 */
public class MediaType {
    private int id;
    private String type;
    
    public MediaType(int id, String type){
        this.id = id;
        this.type = type;
    }
    
    public String getType(){
        return type;
    }
    
    public String toString(){
        return type;
    }
}
