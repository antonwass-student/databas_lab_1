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
public class Genre {
    private int id;
    private String name;
    
    public Genre(int id, String name){
        this.id = id;
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
