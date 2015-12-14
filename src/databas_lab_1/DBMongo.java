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
import org.bson.types.ObjectId;

/**
 *
 * @author anton
 */
public class DBMongo implements DatabaseCommunication {
    private MongoClient mongoClient = null;
    private DB db = null;
    
    private DBCollection coll_mediaEntity = null;
    private DBCollection coll_review = null;
    private DBCollection coll_mediaType = null;
    private DBCollection coll_genre = null;
    private DBCollection coll_creator = null;
    private DBCollection coll_user = null;
    private DBCollection coll_rating = null;
    
    public DBMongo(){
        connect();
    }

    @Override
    public void connect() {
        try {
            mongoClient = new MongoClient("localhost", 27017);
            db = mongoClient.getDB("MediaCenter");
            
            initializeCollections();

            //Call this method to reset database.
            //resetDB();
            
            System.out.println("Connected to database.");
            
            
        } catch (UnknownHostException e) {
            System.out.println("MongoDB error: " + e.getMessage());
        }
    }
    
    private void resetDB(){
        
        db.dropDatabase();
        db = mongoClient.getDB("MediaCenter");
        
        initializeCollections();
        
        addUser("anton", "1234");
        addUser("joachim", "1234");
    }
    
    private void initializeCollections(){
        coll_mediaEntity = db.getCollection("mediaEntity");
        coll_review = db.getCollection("review");
        coll_mediaType = db.getCollection("mediaType");
        coll_genre = db.getCollection("genre");
        coll_creator = db.getCollection("creator");
        coll_user = db.getCollection("user");
        coll_rating = db.getCollection("rating");

    }
    
    private void addUser(String name, String pwd){
        BasicDBObject doc = new BasicDBObject("username", name)
                    .append("password", pwd);
        coll_user.insert(doc);
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
        BasicDBObject doc = new BasicDBObject("name", genre.getName());
        
        coll_genre.insert(doc);
    }

    @Override
    public void addMediaType(MediaType mediaType) {
        BasicDBObject doc = new BasicDBObject("type", mediaType.getType());
        
        coll_mediaType.insert(doc);
    }

    @Override
    public void addCreator(Creator creator, User user) {
        BasicDBObject doc = new BasicDBObject("name", creator.getName())
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
    
    private MediaType getMediaTypeByObjectId(ObjectId id){
        
        BasicDBObject type_query = new BasicDBObject("_id", id);
        DBObject type_doc = coll_mediaType.find(type_query).next();
        MediaType type = new MediaType((ObjectId)type_doc.get("_id"), (String)type_doc.get("type"));
        
        return type;
    }
    
    private User getUserByObjectId(ObjectId id){
        BasicDBObject user_query = new BasicDBObject("_id", id);
        DBObject user_doc = coll_user.find(user_query).next();
        User user = new User((ObjectId)user_doc.get("_id"),
                        (String)user_doc.get("username"),
                        (String)user_doc.get("password"));
        return user;
    }
    
    private Creator getCreatorByObjectId(ObjectId id){
        BasicDBObject creator_query = new BasicDBObject("_id", id);
        DBObject creator_doc = coll_creator.find(creator_query).next();
        Creator creator = new Creator((ObjectId)creator_doc.get("_id"), (String)creator_doc.get("name"));
        
        return creator;
    }
    
    private Genre getGenreByObjectId(ObjectId id){
        BasicDBObject genre_query = new BasicDBObject("_id", id);
        DBObject genre_doc = coll_genre.find(genre_query).next();
        Genre genre = new Genre((ObjectId)genre_doc.get("_id"), (String)genre_doc.get("name"));
        
        return genre;
    }

    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword) {
        ArrayList<MediaEntity> mes = new ArrayList();

        ArrayList<BasicDBObject> conditions = new ArrayList();
        conditions.add(new BasicDBObject("title", new BasicDBObject("$regex", keyword)));
        
        // Searches for subdocuments containing the specified keyword.
        // Then it adds the objectId of the matched genres to the conditions list.
        // This allows for search on strings in the sub documents to MediaEntity
        for(DBObject doc : coll_genre.find(new BasicDBObject("name", new BasicDBObject("$regex", keyword))))
            conditions.add(new BasicDBObject("genre_id", (ObjectId)doc.get("_id")));
        for(DBObject doc : coll_creator.find(new BasicDBObject("name", new BasicDBObject("$regex", keyword))))
            conditions.add(new BasicDBObject("creator_id", (ObjectId)doc.get("_id")));
        for(DBObject doc : coll_mediaType.find(new BasicDBObject("type", new BasicDBObject("$regex", keyword))))
            conditions.add(new BasicDBObject("mediaType_id", (ObjectId)doc.get("_id")));
        for(DBObject doc : coll_user.find(new BasicDBObject("username", new BasicDBObject("$regex", keyword))))
            conditions.add(new BasicDBObject("user_id", (ObjectId)doc.get("_id")));
        // Don't mind this...
        // ------------------
        
        BasicDBObject query = new BasicDBObject("$or", conditions);
        DBCursor cursor_title = coll_mediaEntity.find(query);
        
        try{
            while(cursor_title.hasNext()){
                System.out.println("found match");
                DBObject doc = cursor_title.next();
                
                MediaType type = getMediaTypeByObjectId((ObjectId)doc.get("mediaType_id"));
                User user = getUserByObjectId((ObjectId)doc.get("user_id"));
                Creator creator = getCreatorByObjectId((ObjectId)doc.get("creator_id"));
                Genre genre = getGenreByObjectId((ObjectId)doc.get("genre_id"));
                
                mes.add(new MediaEntity((ObjectId)doc.get("_id"), (String)doc.get("title"),
                        type,
                        user,
                        creator,
                        genre,
                        getAvgRating((ObjectId)doc.get("_id"))));
            }
        }
        finally{
            cursor_title.close();
        }
        return mes;
    }

    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword, Genre genre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Genre> getGenres() {
        ArrayList<Genre> genres = new ArrayList();
        DBCursor cursor = coll_genre.find();
        
        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            Genre genre = new Genre((ObjectId)doc.get("_id"), (String)doc.get("name"));
            genres.add(genre);
        }
        
        cursor.close();
        
        return genres;
    }

    @Override
    public ArrayList<MediaType> getMediaTypes() {
        ArrayList<MediaType> types = new ArrayList();
        DBCursor cursor = coll_mediaType.find();
        
        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            MediaType type = new MediaType((ObjectId)doc.get("_id"), (String)doc.get("type"));
            types.add(type);
        }
        
        cursor.close();
        
        return types;
    }

    @Override
    public ArrayList<Creator> getCreators() {
        ArrayList<Creator> creators = new ArrayList();
        DBCursor cursor = coll_creator.find();
        
        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            Creator creator = new Creator((ObjectId)doc.get("_id"), (String)doc.get("name"));
            creators.add(creator);
        }
        
        cursor.close();
        
        return creators;
    }

    @Override
    public ArrayList<String> getReviews(MediaEntity me) {
        ArrayList<String> reviews = new ArrayList();
        BasicDBObject query = new BasicDBObject("mediaEntity_id", me.getMongoId());
        DBCursor cursor = coll_review.find(query);
        
        while(cursor.hasNext()){
            DBObject doc = cursor.next();
            User user = getUserByObjectId((ObjectId)doc.get("user_id"));
            String review = "\n " + doc.get("text");
            review += "\n - " + user.getName();
            review += "\n____________________";
            reviews.add(review);
        }
        
        cursor.close();
        
        return reviews;
    }

    /**
     * Method that checks if there is a user in the database with matching
     * name and password.
     * @param username
     * @param pwd
     * @return User the user if a match is found, null if not
     */
    @Override
    public User loginWithUser(String username, String pwd) {
        User user = null;
        BasicDBObject login_query = new BasicDBObject("username", username)
                .append("password", pwd);
          
        DBCursor cursor = coll_user.find(login_query);
        
        if(cursor.hasNext()){
            DBObject doc = cursor.next();
            user = new User((ObjectId)doc.get("_id"), (String)doc.get("username"), (String)doc.get("password"));
        }
        cursor.close();
        
        return user;
    }

    @Override
    public void rateMediaEntity(MediaEntity me, User user, float rating) {
        if(checkIfRated(me, user))
            return;
        
        BasicDBObject doc = new BasicDBObject("mediaEntity_id", me.getMongoId())
                .append("user_id", user.getMongoId())
                .append("rating", rating);
        
        coll_rating.insert(doc);
        System.out.println("Media rated!");
    }
    
    @Override
    public boolean checkIfRated(MediaEntity me, User user){
        BasicDBObject query = new BasicDBObject("mediaEntity_id", me.getMongoId())
                .append("user_id", user.getMongoId());
        DBCursor cursor = coll_rating.find(query);
        
        if(cursor.hasNext())
            return true;
        else{
            return false;
        }
    }
    
    private float getAvgRating(ObjectId id){
        BasicDBObject query = new BasicDBObject("mediaEntity_id", id);
        DBCursor cursor = coll_rating.find(query);
        double sum = 0;
        double count = 0;
        while(cursor.hasNext()){
            System.out.println("rating#"+count);
            sum += (double)cursor.next().get("rating");
            count++;
        }
        if(count > 0)
            return (float)(sum/count);
        else 
            return 0;
    }

    @Override
    public boolean checkIfReviewed(User user, MediaEntity me) {
        BasicDBObject query = new BasicDBObject("mediaEntity_id", me.getMongoId())
                .append("user_id", user.getMongoId());
        DBCursor cursor = coll_review.find(query);
        
        if(cursor.hasNext())
            return true;
        else{
            return false;
        }
    }
    
}
