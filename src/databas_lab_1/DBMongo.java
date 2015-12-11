/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author anton
 */
public class DBMongo implements DatabaseCommunication {
    MongoClient mongoClient = null;
    DB db = null;
    
    DBCollection coll_mediaEntity = null;
    DBCollection coll_review = null;
    DBCollection coll_mediaType = null;
    DBCollection coll_genre = null;
    DBCollection coll_creator = null;
    DBCollection coll_user = null;
    DBCollection coll_rating = null;
    
    public DBMongo(){
        
    }

    @Override
    public void connect() {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("MediaCenter");
            
            
            coll_mediaEntity = db.getCollection("mediaEntity");
            coll_review = db.getCollection("review");
            coll_mediaType = db.getCollection("mediaType");
            coll_genre = db.getCollection("genre");
            coll_creator = db.getCollection("creator");
            coll_user = db.getCollection("user");
            coll_user = db.getCollection("rating");
            
            
        } catch (UnknownHostException e) {
            System.out.println("MongoDB error: " + e.getMessage());
        }
    }

    @Override
    public void disconnect() {
        mongoClient.close();
    }

    @Override
    public void addMediaEntity(MediaEntity me) {
        BasicDBObject doc = new BasicDBObject("title", me.getTitle())
                .append("user_id", me.getAddedByUser().getMongoId())
                .append("creator_id", me.getCreator().getMongoId())
                .append("mediaType_id", me.getMediaType().getMongoId())
                .append("genre_id", me.getGenre().getMongoId());
        
        coll_mediaEntity.insert(doc);
    }

    @Override
    public void addGenre(Genre genre) {
         BasicDBObject doc = new BasicDBObject("title", genre.getName());
        
        coll_genre.insert(doc);
    }

    @Override
    public void addMediaType(MediaType mediaType) {
        BasicDBObject doc = new BasicDBObject("title", mediaType.getType());
        
        coll_mediaType.insert(doc);
    }

    @Override
    public void addCreator(Creator creator, User user) {
        BasicDBObject doc = new BasicDBObject("title", creator.getName())
                .append("user_id", user.getMongoId());
        
        coll_creator.insert(doc);
    }

    @Override
    public void addReview(MediaEntity mediaEntity, String text, User user) {
        BasicDBObject doc = new BasicDBObject("mediaEntity_id", mediaEntity.getMongoId())
                .append("text", text)
                .append("user_id", user.getMongoId());
        
        coll_review.insert(doc);
    }

    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword) {
        ArrayList<MediaEntity> mes = new ArrayList();
        
        BasicDBObject query = new BasicDBObject("title", keyword);
        DBCursor cursor = coll_mediaEntity.find();
        DBObject doc = cursor.next();
        
        return mes;
    }

    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword, Genre genre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Genre> getGenres() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<MediaType> getMediaTypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Creator> getCreators() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getReviews(MediaEntity me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User loginWithUser(String username, String pwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rateMediaEntity(MediaEntity me, User user, float rating) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkIfReviewed(User user, MediaEntity me) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
