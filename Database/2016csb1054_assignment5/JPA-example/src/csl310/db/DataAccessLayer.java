/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auser
 */
public class DataAccessLayer {

    private Connection conn = null;
    public static final String SQL_FIND_POSTS = "find.posts.sql";
    public static final String SQL_INSERT_POST = "post.insert.sql";
    public static final String SQL_INSERT_CODEPART = "codepart.insert.sql";
    private Properties queries = new Properties();

    public DataAccessLayer(String dbUrl) throws SQLException {
        conn = DriverManager.getConnection(dbUrl);
        try {            
            queries.load(DataAccessLayer.class.getResourceAsStream("/csl310/queries.properties"));
        } catch (IOException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<PostDto> findPostsByTag(String tag) {
        ArrayList<PostDto> posts = new ArrayList();
        try {
            
            // 2. Create a SQL stmt
            PreparedStatement ps = 
                    conn.prepareStatement(queries.getProperty(SQL_FIND_POSTS));
            ps.setString(1, tag);
            
            // 3. Fire the query and retrieve results
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PostDto p = new PostDto();
                p.id = rs.getInt("id");
                p.postText = rs.getString("ca_textpart");
                p.tags = rs.getString("tags");
                p.title = rs.getString("title");
                
                posts.add(p);
       
            }
            System.out.println("FOUND rows "+posts.size());
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return posts;
    }
    
    public List findCodeInPost(int postId) {
        return new ArrayList();
    }
    
    public void savePost(PostDto post, CodePartDto code) 
            throws SQLException {
        if (post == null || code == null) {
            throw new IllegalArgumentException(
                    "Both arguments are required ");
        }
        conn.setAutoCommit(false);
        try {
            PreparedStatement ps = conn.prepareStatement(
                    queries.getProperty(SQL_INSERT_POST));
            ps.setInt(1, post.id);
            ps.setString(2, post.postText);
            ps.setString(3, post.tags);
            ps.setString(4, post.title);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                System.out.println(">>>>> Insert post failed!!");
            }
            PreparedStatement ps2 = conn.prepareStatement(
                    queries.getProperty(SQL_INSERT_CODEPART));
            ps2.setInt(1, code.codeId);
            ps2.setInt(2, code.postId);
            ps2.setString(3, code.charFP);
            ps2.setString(4, code.code);
            
            rows += ps2.executeUpdate();
            if (rows != 2) {
                System.out.println(">>>>> Insert code failed!!");
            }
            
            conn.commit();
            
            System.out.println("DATA COMMITTED!");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            conn.rollback();
            System.out.println("!!!! TRANSACTION ROLLEDBACK.");
        } finally {
            conn.setAutoCommit(true);
        }
    }
    
}
