/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment5;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rishabh
 */
@Entity
@Table(name = "team_member")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamMember.findAll", query = "SELECT t FROM TeamMember t")
    , @NamedQuery(name = "TeamMember.findByMemberID", query = "SELECT t FROM TeamMember t WHERE t.memberID = :memberID")
    , @NamedQuery(name = "TeamMember.findBySalary", query = "SELECT t FROM TeamMember t WHERE t.salary = :salary")
    , @NamedQuery(name = "TeamMember.findByHireDate", query = "SELECT t FROM TeamMember t WHERE t.hireDate = :hireDate")
    , @NamedQuery(name = "TeamMember.findByRole", query = "SELECT t FROM TeamMember t WHERE t.role = :role")
    , @NamedQuery(name = "TeamMember.averageSalary", query = "SELECT avg(t.salary) FROM TeamMember t inner join t.personID p inner join p.contactId ci  where ci.state = :state and t.role = 'Player'")
    , @NamedQuery(name = "TeamMember.countMemberWise", query = "SELECT count(t.role),  t.role FROM TeamMember t WHERE t.teamID = :teamID GROUP BY t.role")})
public class TeamMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "memberID")
    private Integer memberID;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "salary")
    private BigDecimal salary;
    @Column(name = "hireDate")
    private String hireDate;
    @Column(name = "role")
    private String role;
    @Lob
    @Column(name = "remarks")
    private String remarks;
    @JoinColumn(name = "teamID", referencedColumnName = "teamID")
    @ManyToOne
    private Team teamID;
    @JoinColumn(name = "personID", referencedColumnName = "personID")
    @ManyToOne
    private Person personID;

    public TeamMember() {
    }

    public TeamMember(Integer memberID) {
        this.memberID = memberID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Team getTeamID() {
        return teamID;
    }

    public void setTeamID(Team teamID) {
        this.teamID = teamID;
    }

    public Person getPersonID() {
        return personID;
    }

    public void setPersonID(Person personID) {
        this.personID = personID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberID != null ? memberID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamMember)) {
            return false;
        }
        TeamMember other = (TeamMember) object;
        if ((this.memberID == null && other.memberID != null) || (this.memberID != null && !this.memberID.equals(other.memberID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "assignment5.TeamMember[ memberID=" + memberID + " ]";
    }
    
}
