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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Anton
 */
public class DBMySql implements DatabaseCommunication{
    String user = "mediacenter";
    String password = "asdfgh11";
    String database = "MediaCenter";
    String server = "jdbc:mysql://localhost:3306/" + database +
            "?UseClientEnc=UTF8";
    Connection con = null;
    
    //Prepared statements
    PreparedStatement getCreatorSQL = null;
    PreparedStatement getAllMediaByTitleSQL = null;
    PreparedStatement getAllGenresSQL = null;
    PreparedStatement getAllMediaTypesSQL = null;
    
    public DBMySql(){
        connect();
    }
    
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
    
    public void disconnect(){
        try {
            if(con != null)
                con.close();
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    
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

    @Override
    public void addMediaEntity(MediaEntity mediaEntity) {
    }

    @Override
    public void addGenre(Genre genre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addMediaType(MediaType mediaType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCreator(Creator creator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addReview(MediaEntity mediaEntity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<MediaEntity> getMediaBySearch(String keyword) {
        ArrayList<MediaEntity> mediaList = new ArrayList();
        keyword = "%" + keyword + "%";
        
        try {
            if(getAllMediaByTitleSQL == null){
                String sql = "SELECT * FROM t_mediaentity"
                        + " left join t_genre on t_mediaEntity.GenreID = t_genre.GenreID "
                        + " left join t_mediatype on t_mediaEntity.mediatypeid = t_mediatype.mediatypeid "
                        + " left join t_creator on t_mediaEntity.creatorid = t_creator.CreatorID "
                        + " left join t_user on t_mediaentity.userid = t_user.userid"
                        + " left join avg_rating on t_mediaentity.mediaentityid = avg_rating.MediaEntityID "
                        + " where t_mediaentity.title like ? "
                        + " or t_genre.name like ? "
                        + " or t_creator.name like ? "
                        + " or t_mediatype.type like ?"
                        + " or t_user.username like ?"
                        + " or avg_rating.rating like ?";
                
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
}
