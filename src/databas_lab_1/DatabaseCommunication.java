/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.util.ArrayList;

/**
 *
 * @author Anton
 */
public interface DatabaseCommunication {
    public void connect();
    public void disconnect();
    public void addMediaEntity(MediaEntity mediaEntity);
    public void addGenre(Genre genre);
    public void addMediaType(MediaType mediaType);
    public void addCreator(Creator creator);
    public void addReview(MediaEntity mediaEntity);
    public ArrayList<MediaEntity> getMediaByTitle(String title);
    public ArrayList<MediaEntity> getMediaByCreator(String creator);
    public ArrayList<MediaEntity> getMediaByGenre(String genre);
    public ArrayList<MediaEntity> getMediaByRating(float minRating);
}
