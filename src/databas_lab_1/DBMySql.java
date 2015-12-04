/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.sql.Connection;
import java.sql.DriverManager;
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
    String user = "anton";
    String password = "test123";
    String database = "test";
    String server = "jdbc:mysql://localhost:3306/" + database +
            "?UseClientEnc=UTF8";
    Connection con = null;
    
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
        catch(Exception e){
            System.out.println("MySQL error.");
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
    public ArrayList<MediaEntity> getMediaByTitle(String title) {
        ArrayList<MediaEntity> mediaList = new ArrayList();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM testtable");
            
            while(rs.next()){
                mediaList.add(new MediaEntity(0, rs.getString(1),
                        null, null, null));
            }
            
        } catch (SQLException e) {
            System.out.println("SQL error : " + e.getMessage());
        }
        
        return mediaList;
    }

    @Override
    public ArrayList<MediaEntity> getMediaByCreator(String creator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<MediaEntity> getMediaByGenre(String genre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<MediaEntity> getMediaByRating(float minRating) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
