/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databas_lab_1;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 *
 * @author Anton
 */
public class QueryWorker extends Thread {
    Connection con = null;
    Statement stmt = null;
    String query = null;
    
    public QueryWorker(Connection con, String query) throws SQLException{
        this.con = con;
        this.stmt = con.createStatement();
        this.query = query;
    }
    
    @Override
    public void run(){
        
    }
}
