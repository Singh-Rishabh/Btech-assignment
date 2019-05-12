/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310.db;

/**
 *
 * @author auser
 */
public class PostDto {
    public Integer id;
    public String tags;
    public String title;
    public String postText;

    @Override
    public String toString() {
        return "PostDto{" + "id=" + id + ", tags=" + tags + ", title=" + title + ", postText= some text I removed}";
    }
}
