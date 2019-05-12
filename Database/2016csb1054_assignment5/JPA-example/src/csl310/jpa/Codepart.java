/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csl310.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author auser
 */
@Entity
@Table(name = "codepart")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codepart.findAll", query = "SELECT c FROM Codepart c")
    , @NamedQuery(name = "Codepart.findByCodeid", query = "SELECT c FROM Codepart c WHERE c.codeid = :codeid")
    , @NamedQuery(name = "Codepart.findByCharfp", query = "SELECT c FROM Codepart c WHERE c.charfp = :charfp")
    , @NamedQuery(name = "Codepart.findByCharfpsize", query = "SELECT c FROM Codepart c WHERE c.charfpsize = :charfpsize")
    , @NamedQuery(name = "Codepart.findByCode", query = "SELECT c FROM Codepart c WHERE c.code = :code")
    , @NamedQuery(name = "Codepart.findByCodesize", query = "SELECT c FROM Codepart c WHERE c.codesize BETWEEN :min AND :max")
    , @NamedQuery(name = "Codepart.findByTags", query = "SELECT c FROM Codepart c WHERE c.tags = :tags")})
public class Codepart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codeid")
    private Long codeid;
    @Column(name = "charfp")
    private String charfp;
    @Column(name = "charfpsize")
    private Integer charfpsize;
    @Column(name = "code")
    private String code;
    @Column(name = "codesize")
    private Integer codesize;
    @Column(name = "tags")
    private String tags;
    @JoinColumn(name = "postid", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post postid;

    public Codepart() {
    }

    public Codepart(Long codeid) {
        this.codeid = codeid;
    }

    public Long getCodeid() {
        return codeid;
    }

    public void setCodeid(Long codeid) {
        this.codeid = codeid;
    }

    public String getCharfp() {
        return charfp;
    }

    public void setCharfp(String charfp) {
        this.charfp = charfp;
    }

    public Integer getCharfpsize() {
        return charfpsize;
    }

    public void setCharfpsize(Integer charfpsize) {
        this.charfpsize = charfpsize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodesize() {
        return codesize;
    }

    public void setCodesize(Integer codesize) {
        this.codesize = codesize;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Post getPostid() {
        return postid;
    }

    public void setPostid(Post postid) {
        this.postid = postid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeid != null ? codeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codepart)) {
            return false;
        }
        Codepart other = (Codepart) object;
        if ((this.codeid == null && other.codeid != null) || (this.codeid != null && !this.codeid.equals(other.codeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "csl310.jpa.Codepart[ codeid=" + codeid + " ]";
    }
    
}
