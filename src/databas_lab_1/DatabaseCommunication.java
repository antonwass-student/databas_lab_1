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
    public void addCreator(Creator creator, User user);
    public void addReview(MediaEntity mediaEntity, String text, User user);
    public ArrayList<MediaEntity> getMediaBySearch(String keyword);
    public ArrayList<MediaEntity> getMediaBySearch(String keyword, Genre genre);
    public ArrayList<Genre> getGenres();
    public ArrayList<MediaType> getMediaTypes();
    public ArrayList<Creator> getCreators();
    public ArrayList<String> getReviews(MediaEntity me);
    public User loginWithUser(String username, String pwd);
    public void rateMediaEntity(MediaEntity me, User user, float rating);
    public boolean checkIfReviewed(User user, MediaEntity me);
    public boolean checkIfRated(MediaEntity me, User user);
}
