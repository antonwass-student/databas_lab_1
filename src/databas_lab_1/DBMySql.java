/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author Anton
 */
public class DBMySql implements DatabaseCommunication{
    private String user = "mediacenter";
    private String password = "asdfgh11";
    private String database = "MediaCenter";
    private String server = "jdbc:mysql://localhost:3306/" + database +
            "?UseClientEnc=UTF8";
    private Connection con = null;
    
    //Prepared statements
    private PreparedStatement getCreatorSQL = null;
    private PreparedStatement getAllMediaByTitleSQL = null;
    private PreparedStatement getAllMediaByTitleAndGenreSQL = null;
    private PreparedStatement getAllGenresSQL = null;
    private PreparedStatement getAllMediaTypesSQL = null;
    private PreparedStatement getAllCreatorsSQL = null;
    private PreparedStatement addNewMediaEntitySQL = null;
    private PreparedStatement addNewMediaTypeSQL = null;
    private PreparedStatement addNewGenreSQL = null;
    private PreparedStatement addNewCreatorSQL = null;
    private PreparedStatement rateMediaEntitySQL = null;
    private PreparedStatement checkIfRatedSQL = null;
    private PreparedStatement addReviewSQL = null;
    private PreparedStatement getReviewsSQL = null;
    private PreparedStatement checkIfReviewedSQL = null;
    
    public DBMySql(){
        connect();
    }
    
    /**
     * Call once to establish connection to database.
     */
    @Override
    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(server, user, password);
            System.out.println("Connected!");
        }
        catch(SQLException e){
            System.out.println("SQL Error: " + e.getMessage());
        }catch(Exception e){
            System.out.println("Error");
        }
    }
    
    /**
     * Call once to close the connecton with the database.
     */
    public void disconnect(){
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
    /**
     * Sends a query to the server requesting all media types from the database.
     * @return ArrayList<MediaType> containing objects collected from the database.
     */
    @Override
    public ArrayList<MediaType> getMediaTypes(){
        ArrayList<MediaType> types = new ArrayList();
        try {
            if(getAllMediaTypesSQL == null){
                String sql = "SELECT * FROM T_MediaType";
                getAllMediaTypesSQL = con.prepareStatement(sql);
            }
            
            ResultSet rs = getAllMediaTypesSQL.executeQuery();
            
            while(rs.next()){
                types.add(new MediaType(rs.getInt(1), rs.getString(2)));
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    
        
        return types;
    }
    
    /**
     * Sends a query to the server asking for all creators in T_Creator table.
     * @return ArrayList<Creator> with all the creators found.
     */
    public ArrayList<Creator> getCreators(){
        ArrayList<Creator> creators = new ArrayList();
        try {
            if(getAllCreatorsSQL == null){
                String sql = "SELECT * FROM T_Creator";
                getAllCreatorsSQL = con.prepareStatement(sql);
            }
            
            ResultSet rs = getAllCreatorsSQL.executeQuery();
            
            while(rs.next()){
                creators.add(new Creator(rs.getInt(1), rs.getString(2)));
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        return creators;
    }
    
    /**
     * Sends a query to the server requesting all genres.
     * @return ArrayList<Genre> containing the collected objects from the database.
     */
    @Override
    public ArrayList<Genre> getGenres(){
        ArrayList<Genre> genres = new ArrayList();
        try {
            if(getAllGenresSQL == null){
                String sql = "SELECT * FROM T_Genre";
                getAllGenresSQL = con.prepareStatement(sql);
            }
            
            ResultSet rs = getAllGenresSQL.executeQuery();
            
            while(rs.next()){
                genres.add(new Genre(rs.getInt(1), rs.getString(2)));
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        return genres;
    }
    
    /**
     * Sends a login query to the database. If a row is matched with your username and password, the method will return with a user.
     * @param username The username to login with.
     * @param pwd The password to login with.
     * @return Returns your user.
     */
    @Override
    public User loginWithUser(String username, String pwd){
        try {
            String sql = "SELECT * FROM T_User WHERE username = '" + username + "' AND"
                    + " password = '" + pwd + "'";
           
            Statement loginStmt = con.createStatement();
            ResultSet rs = loginStmt.executeQuery(sql);
            
            if(rs.next()){
                User user = 
                        new User(rs.getInt(1), rs.getString(2), rs.getString(3));
                return user;
            }
            else{
                return null;
            }
            
            
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Adds a new media entity to the database.
     * @param mediaEntity The entity to be added.
     */
    @Override
    public void addMediaEntity(MediaEntity mediaEntity) {
        
        try {
            if(addNewMediaEntitySQL == null){
                String sql = "INSERT INTO T_MediaEntity(title, mediatypeid, genreid, creatorid, userid)" +
                            "values(?, ?, ?, ?, ?);";
                addNewMediaEntitySQL = con.prepareStatement(sql);
            }
            
            addNewMediaEntitySQL.setString(1, mediaEntity.getTitle());
            addNewMediaEntitySQL.setInt(2, mediaEntity.getMediaType().getId());
            addNewMediaEntitySQL.setInt(3, mediaEntity.getGenre().getId());
            addNewMediaEntitySQL.setInt(4, mediaEntity.getCreator().getId());
            addNewMediaEntitySQL.setInt(5, mediaEntity.getAddedByUser().getId());
            int res = addNewMediaEntitySQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }

    /**
     * Adds a new genre to the database.
     * @param genre The new genre to be added.
     */
    @Override
    public void addGenre(Genre genre) {
        try {
            if(addNewGenreSQL == null){
                String sql = "INSERT INTO T_Genre(name) values(?);";
                addNewGenreSQL = con.prepareStatement(sql);
            }
            
            addNewGenreSQL.setString(1, genre.getName());
            int res = addNewGenreSQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }

    /**
     * Adds a new type of media to the database.
     * @param mediaType The type of media to be added.
     */
    @Override
    public void addMediaType(MediaType mediaType) {
        try {
            if(addNewMediaTypeSQL == null){
                String sql = "INSERT INTO T_MediaType(type) values(?);";
                addNewMediaTypeSQL = con.prepareStatement(sql);
            }
            
            addNewMediaTypeSQL.setString(1, mediaType.getType());
            int res = addNewMediaTypeSQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }

    /**
     * Adds a crator to the database.
     * @param creator The creator to be added.
     * @param user The user adding the creator.
     */
    @Override
    public void addCreator(Creator creator, User user) {
        try {
            if(addNewCreatorSQL == null){
                String sql = "INSERT INTO T_Creator(name, userid) values(?, ?);";
                addNewCreatorSQL = con.prepareStatement(sql);
            }
            
            addNewCreatorSQL.setString(1, creator.getName());
            addNewCreatorSQL.setInt(2, user.getId());
            int res = addNewCreatorSQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }
    
    @Override
    public ArrayList<String> getReviews(MediaEntity me){
        ArrayList<String> reviews = new ArrayList();
        
        try {
            if(getReviewsSQL == null){
                String sql = "SELECT Text, Username FROM T_Review "
                        + " LEFT JOIN T_User "
                        + " ON T_Review.UserID = T_User.UserID "
                        + " WHERE MediaEntityID = ?";
                getReviewsSQL = con.prepareStatement(sql);
            }
            
            getReviewsSQL.setInt(1, me.getId());
            
            ResultSet rs = getReviewsSQL.executeQuery();
            
            while(rs.next()){
                String review = "\n " + rs.getString(1);
                review += "\n - " + rs.getString(2);
                review += "\n____________________";
                
                reviews.add(review);
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return reviews;
    }
    
    public boolean checkIfReviewed(User user, MediaEntity me){
        try {
            if(checkIfReviewedSQL == null){
                String sql = "SELECT * FROM T_Review WHERE userid = ? AND mediaentityid = ?";
                checkIfReviewedSQL = con.prepareStatement(sql);
            }
           
            checkIfReviewedSQL.setInt(1, user.getId());
            checkIfReviewedSQL.setInt(2, me.getId());
            
            ResultSet res = checkIfReviewedSQL.executeQuery();
            
            if(res.next())
            {
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        return true;
    }

    @Override
    public void addReview(MediaEntity mediaEntity, String text, User user) {
        if(checkIfReviewed(user, mediaEntity))
            return;
        try {
            if(addReviewSQL == null){
                String sql = "INSERT INTO T_Review(Text, userid, mediaentityid) values(?, ?, ?);";
                addReviewSQL = con.prepareStatement(sql);
            }
            
            addReviewSQL.setString(1, text);
            addReviewSQL.setInt(2, user.getId());
            addReviewSQL.setInt(3, mediaEntity.getId());
            int res = addReviewSQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }
    
    /**
     * Searches the database for all mediaentities that matches with the keyword.
     * @param keyword the keyword to search for.
     * @return Returns all entities matching the keyword.
     */
    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword) {
        ArrayList<MediaEntity> mediaList = new ArrayList();
        keyword = "%" + keyword + "%";
        
        try {
            if(getAllMediaByTitleSQL == null){
                String sql = "SELECT * FROM t_mediaentity"
                        + " LEFT JOIN t_genre ON t_mediaEntity.GenreID = t_genre.GenreID "
                        + " LEFT JOIN t_mediatype ON t_mediaEntity.mediatypeid = t_mediatype.mediatypeid "
                        + " LEFT JOIN t_creator ON t_mediaEntity.creatorid = t_creator.CreatorID "
                        + " LEFT JOIN t_user ON t_mediaentity.userid = t_user.userid"
                        + " LEFT JOIN avg_rating ON t_mediaentity.mediaentityid = avg_rating.MediaEntityID "
                        + " WHERE t_mediaentity.title LIKE ? "
                        + " OR t_genre.name LIKE ? "
                        + " OR t_creator.name LIKE ? "
                        + " OR t_mediatype.type LIKE ?"
                        + " OR t_user.username LIKE ?"
                        + " OR avg_rating.rating LIKE ?";
                
                getAllMediaByTitleSQL = con.prepareStatement(sql);
            }
            
            getAllMediaByTitleSQL.setString(1, keyword);
            getAllMediaByTitleSQL.setString(2, keyword);
            getAllMediaByTitleSQL.setString(3, keyword);
            getAllMediaByTitleSQL.setString(4, keyword);
            getAllMediaByTitleSQL.setString(5, keyword);
            getAllMediaByTitleSQL.setString(6, keyword);

            
            ResultSet rs = getAllMediaByTitleSQL.executeQuery();
            
            while(rs.next()){
                MediaType type = new MediaType(rs.getInt(9), rs.getString(10));
                Genre genre = new Genre(rs.getInt(7), rs.getString(8));
                Creator creator = new Creator(rs.getInt(11), rs.getString(12));
                User user = new User(rs.getInt(14), rs.getString(15), null);
                mediaList.add(new MediaEntity(rs.getInt(1), rs.getString(2),
                        type, user, creator, genre, rs.getFloat(18)));
            }
            
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return mediaList;
    }

    /**
     * Searches the database for all mediaentities that matches with the keyword.
     * @param keyword the keyword to search for.
     * @param genreFilter only take matches with the specified genre.
     * @return Returns all entities matching the keyword.
     */
    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword, Genre genreFilter) {
        ArrayList<MediaEntity> mediaList = new ArrayList();
        keyword = "%" + keyword + "%";
        
        try {
            if(getAllMediaByTitleAndGenreSQL == null){
                String sql = "SELECT * FROM t_mediaentity"
                        + " left join t_genre on t_mediaEntity.GenreID = t_genre.GenreID "
                        + " left join t_mediatype on t_mediaEntity.mediatypeid = t_mediatype.mediatypeid "
                        + " left join t_creator on t_mediaEntity.creatorid = t_creator.CreatorID "
                        + " left join t_user on t_mediaentity.userid = t_user.userid"
                        + " left join avg_rating on t_mediaentity.mediaentityid = avg_rating.MediaEntityID "
                        + " where (t_mediaentity.title like ? "
                        + " or t_genre.name like ? "
                        + " or t_creator.name like ? "
                        + " or t_mediatype.type like ?"
                        + " or t_user.username like ?"
                        + " or avg_rating.rating like ?)"
                        + " and t_mediaentity.genreid = ?";
                
                getAllMediaByTitleAndGenreSQL = con.prepareStatement(sql);
            }
            
            getAllMediaByTitleAndGenreSQL.setString(1, keyword);
            getAllMediaByTitleAndGenreSQL.setString(2, keyword);
            getAllMediaByTitleAndGenreSQL.setString(3, keyword);
            getAllMediaByTitleAndGenreSQL.setString(4, keyword);
            getAllMediaByTitleAndGenreSQL.setString(5, keyword);
            getAllMediaByTitleAndGenreSQL.setString(6, keyword);
            getAllMediaByTitleAndGenreSQL.setInt(7, genreFilter.getId());
            
            ResultSet rs = getAllMediaByTitleAndGenreSQL.executeQuery();
            
            while(rs.next()){
                MediaType type = new MediaType(rs.getInt(9), rs.getString(10));
                Genre genre = new Genre(rs.getInt(7), rs.getString(8));
                Creator creator = new Creator(rs.getInt(11), rs.getString(12));
                User user = new User(rs.getInt(14), rs.getString(15), null);
                mediaList.add(new MediaEntity(rs.getInt(1), rs.getString(2),
                        type, user, creator, genre, rs.getFloat(18)));
            }
            
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return mediaList;
    }
    
    /**
     * Sends a query to the database asking for a creator with specified ID.
     * @param id the id of the creator being looked for.
     * @return The creator if found with specified id, null if not found.
     */
    public Creator getCreatorByID(int id){
        Creator creator = null;
        try {
            if(getCreatorSQL == null){
                String sql = "SELECT * FROM T_Creator WHERE CreatorID = ?";
                getCreatorSQL = con.prepareStatement(sql);
            }
            
            getCreatorSQL.setInt(1, id);
            ResultSet rs = getCreatorSQL.executeQuery();
            rs.next();
            creator = new Creator(rs.getInt(1), rs.getString(2));
            
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return creator;
    }
    
    /**
     * Sends a query to the server asking for a media type with specified ID.
     * @param id The id of the type being requested
     * @return The media type if found with specified ID, null if not.
     */
    public MediaType getMediaTypeByID(int id){
        MediaType mediaType = null;
        
        try {
            String sql = "SELECT * FROM T_MediaType WHERE MediaTypeID = ?";
            PreparedStatement getMediaType = con.prepareStatement(sql);
            getMediaType.setInt(1, id);
            
            ResultSet rs = getMediaType.executeQuery();
            rs.next();
            mediaType = new MediaType(rs.getInt(1), rs.getString(2));
            System.out.println("mediatype = " + mediaType.getType());
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return mediaType;
    }
    
    /**
     * A SQL query that checks wether a user has already rated the media entity.
     * @param me the media entity to check if rated.
     * @param user the user that wants to rate.
     * @return True if user already has rated, false if not.
     */
    public boolean checkIfRated(MediaEntity me, User user){
        try {
            System.out.println("me id " + me.getId());
            System.out.println("user id " + user.getId());
            if(checkIfRatedSQL == null){
                String sql = "SELECT * FROM T_Rating WHERE userid = ? AND mediaentityid = ?";
                checkIfRatedSQL = con.prepareStatement(sql);
            }
           
            checkIfRatedSQL.setInt(1, user.getId());
            checkIfRatedSQL.setInt(2, me.getId());
            
            ResultSet res = checkIfRatedSQL.executeQuery();
            
            if(res.next())
            {
                return true;
            }
            else{
                return false;
            }
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        return true;
    }
    
    /**
     * Used to rate a media. Will check if the user already has rated the media.
     * @param me the media entity to rate.
     * @param user the user rating the entity.
     * @param rating the score given by the user.
     */
    public void rateMediaEntity(MediaEntity me, User user, float rating){
        
        if(checkIfRated(me, user))
            return;
        
        System.out.println("not rated yet");
        try {
            if(rateMediaEntitySQL == null){
                String sql = "INSERT INTO T_Rating(rating, userid, mediaentityid) values(?, ?, ?);";
                rateMediaEntitySQL = con.prepareStatement(sql);
            }
            
            rateMediaEntitySQL.setFloat(1, rating);
            rateMediaEntitySQL.setInt(2, user.getId());
            rateMediaEntitySQL.setInt(3, me.getId());
            
            int res = rateMediaEntitySQL.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
    }
}
