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
public class MediaEntity {
    private int id;
    private String title;
    private User addedBy;
    private Creator creator;
    private Genre genre;
    
    public MediaEntity(int id, String title, User addedBy, Creator creator, Genre genre){
        this.id = id;
        this.title = title;
        this.addedBy = addedBy;
        this.creator = creator;
        this.genre = genre;
    }
    
    public String getTitle(){
        return title;
    }
}
