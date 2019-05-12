/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author auser
 */
@Entity
@Table(name = "post")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Post.findAll", query = "SELECT p FROM Post p")
    , @NamedQuery(name = "Post.findById", query = "SELECT p FROM Post p WHERE p.id = :id")
    , @NamedQuery(name = "Post.findByTags", query = "SELECT p FROM Post p WHERE p.tags = :tags")
    , @NamedQuery(name = "Post.findByTitle", query = "SELECT p FROM Post p WHERE p.title = :title")
    , @NamedQuery(name = "Post.findByViewcount", query = "SELECT p FROM Post p WHERE p.viewcount = :viewcount")})
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "acceptedanswerid")
    private Integer acceptedanswerid;
    @Column(name = "answercount")
    private Integer answercount;
    @Column(name = "ca_sentiment")
    private Integer caSentiment;
    @Column(name = "ca_textpart")
    private String caTextpart;
    @Column(name = "closeddate")
    private String closeddate;
    @Column(name = "commentcount")
    private Integer commentcount;
    @Column(name = "communityowneddate")
    private String communityowneddate;
    @Column(name = "corenlpscore")
    private Integer corenlpscore;
    @Column(name = "creationdate")
    private String creationdate;
    @Column(name = "favoritecount")
    private Integer favoritecount;
    @Column(name = "lastactivitydate")
    private String lastactivitydate;
    @Column(name = "lasteditdate")
    private String lasteditdate;
    @Column(name = "lasteditordisplayname")
    private String lasteditordisplayname;
    @Column(name = "lasteditoruserid")
    private Integer lasteditoruserid;
    @Column(name = "ownerdisplayname")
    private String ownerdisplayname;
    @Column(name = "owneruserid")
    private Integer owneruserid;
    @Column(name = "parentid")
    private Integer parentid;
    @Column(name = "posttypeid")
    private Integer posttypeid;
    @Column(name = "score")
    private Integer score;
    @Column(name = "tags")
    private String tags;
    @Column(name = "title")
    private String title;
    @Column(name = "vaderscore")
    private Integer vaderscore;
    @Column(name = "viewcount")
    private Integer viewcount;
    @OneToMany(mappedBy = "postid", fetch = FetchType.LAZY)
    private Collection<Codepart> codepartCollection;

    public Post() {
    }

    public Post(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcceptedanswerid() {
        return acceptedanswerid;
    }

    public void setAcceptedanswerid(Integer acceptedanswerid) {
        this.acceptedanswerid = acceptedanswerid;
    }

    public Integer getAnswercount() {
        return answercount;
    }

    public void setAnswercount(Integer answercount) {
        this.answercount = answercount;
    }

    public Integer getCaSentiment() {
        return caSentiment;
    }

    public void setCaSentiment(Integer caSentiment) {
        this.caSentiment = caSentiment;
    }

    public String getCaTextpart() {
        return caTextpart;
    }

    public void setCaTextpart(String caTextpart) {
        this.caTextpart = caTextpart;
    }

    public String getCloseddate() {
        return closeddate;
    }

    public void setCloseddate(String closeddate) {
        this.closeddate = closeddate;
    }

    public Integer getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(Integer commentcount) {
        this.commentcount = commentcount;
    }

    public String getCommunityowneddate() {
        return communityowneddate;
    }

    public void setCommunityowneddate(String communityowneddate) {
        this.communityowneddate = communityowneddate;
    }

    public Integer getCorenlpscore() {
        return corenlpscore;
    }

    public void setCorenlpscore(Integer corenlpscore) {
        this.corenlpscore = corenlpscore;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public Integer getFavoritecount() {
        return favoritecount;
    }

    public void setFavoritecount(Integer favoritecount) {
        this.favoritecount = favoritecount;
    }

    public String getLastactivitydate() {
        return lastactivitydate;
    }

    public void setLastactivitydate(String lastactivitydate) {
        this.lastactivitydate = lastactivitydate;
    }

    public String getLasteditdate() {
        return lasteditdate;
    }

    public void setLasteditdate(String lasteditdate) {
        this.lasteditdate = lasteditdate;
    }

    public String getLasteditordisplayname() {
        return lasteditordisplayname;
    }

    public void setLasteditordisplayname(String lasteditordisplayname) {
        this.lasteditordisplayname = lasteditordisplayname;
    }

    public Integer getLasteditoruserid() {
        return lasteditoruserid;
    }

    public void setLasteditoruserid(Integer lasteditoruserid) {
        this.lasteditoruserid = lasteditoruserid;
    }

    public String getOwnerdisplayname() {
        return ownerdisplayname;
    }

    public void setOwnerdisplayname(String ownerdisplayname) {
        this.ownerdisplayname = ownerdisplayname;
    }

    public Integer getOwneruserid() {
        return owneruserid;
    }

    public void setOwneruserid(Integer owneruserid) {
        this.owneruserid = owneruserid;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getPosttypeid() {
        return posttypeid;
    }

    public void setPosttypeid(Integer posttypeid) {
        this.posttypeid = posttypeid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVaderscore() {
        return vaderscore;
    }

    public void setVaderscore(Integer vaderscore) {
        this.vaderscore = vaderscore;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }

    @XmlTransient
    public Collection<Codepart> getCodepartCollection() {
        return codepartCollection;
    }

    public void setCodepartCollection(Collection<Codepart> codepartCollection) {
        this.codepartCollection = codepartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Post)) {
            return false;
        }
        Post other = (Post) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "csl310.jpa.Post[ id=" + id + " ]";
    }
    
}
