/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rishabh
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t")
    , @NamedQuery(name = "Team.findByTeamID", query = "SELECT t FROM Team t WHERE t.teamID = :teamID")
    , @NamedQuery(name = "Team.findByCreationDate", query = "SELECT t FROM Team t WHERE t.creationDate = :creationDate")
    , @NamedQuery(name = "Team.findByStatus", query = "SELECT t FROM Team t WHERE t.status = :status")
    , @NamedQuery(name = "Team.findByName", query = "SELECT t FROM Team t WHERE t.name = :name")
    })
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "teamID")
    private Integer teamID;
    @Column(name = "creationDate")
    private String creationDate;
    @Column(name = "status")
    private String status;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "teamID")
    private Collection<TeamMember> teamMemberCollection;
    @JoinColumn(name = "officeID", referencedColumnName = "contactID")
    @ManyToOne
    private ContactInfo officeID;

    public Team() {
    }

    public Team(Integer teamID) {
        this.teamID = teamID;
    }

    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(Integer teamID) {
        this.teamID = teamID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<TeamMember> getTeamMemberCollection() {
        return teamMemberCollection;
    }

    public void setTeamMemberCollection(Collection<TeamMember> teamMemberCollection) {
        this.teamMemberCollection = teamMemberCollection;
    }

    public ContactInfo getOfficeID() {
        return officeID;
    }

    public void setOfficeID(ContactInfo officeID) {
        this.officeID = officeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamID != null ? teamID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.teamID == null && other.teamID != null) || (this.teamID != null && !this.teamID.equals(other.teamID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "assignment5.Team[ teamID=" + teamID + " ]";
    }
    
}
