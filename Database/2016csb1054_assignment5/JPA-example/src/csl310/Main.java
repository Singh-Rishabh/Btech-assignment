/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310;

import csl310.db.CodePartDto;
import csl310.db.DataAccessLayer;
import csl310.db.PostDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author auser
 */
public class Main {

    private DataAccessLayer dal;

    public Main() {
        String url = "jdbc:postgresql://localhost/cruso?user=cruso&password=secret";
        try {
            this.dal = new DataAccessLayer(url);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Starting.");
        String tagToQuery = "linux";
        
        Main app = new Main();
        app.showPosts(tagToQuery);
        System.out.println("Trying to add a new post...");
        app.addNewRandomPost();
    }
    
    private void showPosts(String tag) {
        List<PostDto> posts = dal.findPostsByTag("%"+tag+"%");
        System.out.println("POSTS: ");
        for (PostDto post : posts) {
            if (post.tags.toLowerCase().contains(tag)) {
                System.out.println(post);
            }
        }
    }
    
    private void showCodeInPost(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    private void addNewRandomPost() {
        Random rnd = new Random();
        PostDto p = new PostDto();
        p.id = 998000+rnd.nextInt(200);
        p.title = "TEST-CSL310-"+rnd.nextInt(200);
        p.postText = "SOMETEXT - "+rnd.nextGaussian();
        p.tags = "CSL310";
        
        CodePartDto cp = new CodePartDto();
        cp.codeId = 88900 + rnd.nextInt(200);
        cp.postId = p.id;
        cp.charFP = "FINGERPRINT-"+rnd.nextInt();
        cp.code = "THE CODE PART - "+rnd.nextInt();
        
        System.out.println("Adding post id = "+p.id + " and codeid = "+cp.codeId);
        try {
            dal.savePost(p, cp);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
