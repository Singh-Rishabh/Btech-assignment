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
@Table(name = "person")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPersonID", query = "SELECT p FROM Person p WHERE p.personID = :personID")
    , @NamedQuery(name = "Person.findByNameF", query = "SELECT p FROM Person p WHERE p.nameF = :nameF")
    , @NamedQuery(name = "Person.findByNameL", query = "SELECT p FROM Person p WHERE p.nameL = :nameL")
    , @NamedQuery(name = "Person.findByDob", query = "SELECT p FROM Person p WHERE p.dob = :dob")
    , @NamedQuery(name = "Person.findByCintactID", query = "SELECT p FROM Person p WHERE p.contactId = :contactID")
    , @NamedQuery(name = "Person.nameOfPlayersBwtweenSalary", query = "SELECT p FROM TeamMember t INNER JOIN t.personID p INNER JOIN t.teamID tm WHERE tm.name = :team_name and t.role = 'Player' and t.salary BETWEEN :min and :max")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "personID")
    private Integer personID;
    @Basic(optional = false)
    @Column(name = "nameF")
    private String nameF;
    @Basic(optional = false)
    @Column(name = "nameL")
    private String nameL;
    @Column(name = "dob")
    private String dob;
    @JoinColumn(name = "contactId", referencedColumnName = "contactID")
    @ManyToOne
    private ContactInfo contactId;
    @OneToMany(mappedBy = "personID")
    private Collection<TeamMember> teamMemberCollection;

    public Person() {
    }

    public Person(Integer personID) {
        this.personID = personID;
    }

    public Person(Integer personID, String nameF, String nameL) {
        this.personID = personID;
        this.nameF = nameF;
        this.nameL = nameL;
    }

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    public String getNameL() {
        return nameL;
    }

    public void setNameL(String nameL) {
        this.nameL = nameL;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public ContactInfo getContactId() {
        return contactId;
    }

    public void setContactId(ContactInfo contactId) {
        this.contactId = contactId;
    }

    @XmlTransient
    public Collection<TeamMember> getTeamMemberCollection() {
        return teamMemberCollection;
    }

    public void setTeamMemberCollection(Collection<TeamMember> teamMemberCollection) {
        this.teamMemberCollection = teamMemberCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personID != null ? personID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personID == null && other.personID != null) || (this.personID != null && !this.personID.equals(other.personID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "assignment5.Person[ personID=" + personID + " ]";
    }
    
}
