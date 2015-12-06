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
public class User {
    private int id;
    private String name;
    private String password;
    
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    
    public String getName(){
        return name;
    }
}
