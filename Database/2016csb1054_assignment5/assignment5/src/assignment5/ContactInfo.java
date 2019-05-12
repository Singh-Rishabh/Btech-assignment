package assignment5;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "contact_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactInfo.findAll", query = "SELECT c FROM ContactInfo c")
    , @NamedQuery(name = "ContactInfo.findByContactID", query = "SELECT c FROM ContactInfo c WHERE c.contactID = :contactID")
    , @NamedQuery(name = "ContactInfo.findByStreetAdd", query = "SELECT c FROM ContactInfo c WHERE c.streetAdd = :streetAdd")
    , @NamedQuery(name = "ContactInfo.findByCity", query = "SELECT c FROM ContactInfo c WHERE c.city = :city")
    , @NamedQuery(name = "ContactInfo.findByState", query = "SELECT c FROM ContactInfo c WHERE c.state = :state")
    , @NamedQuery(name = "ContactInfo.findByCountry", query = "SELECT c FROM ContactInfo c WHERE c.country = :country")
    , @NamedQuery(name = "ContactInfo.findByPostalCode", query = "SELECT c FROM ContactInfo c WHERE c.postalCode = :postalCode")
    , @NamedQuery(name = "ContactInfo.findByPhone", query = "SELECT c FROM ContactInfo c WHERE c.phone = :phone")
    , @NamedQuery(name = "ContactInfo.findByMobile", query = "SELECT c FROM ContactInfo c WHERE c.mobile = :mobile")    
    , @NamedQuery(name = "ContactInfo.findByEmail", query = "SELECT c FROM ContactInfo c WHERE c.email = :email")})
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contactID")
    private Integer contactID;
    @Column(name = "streetAdd")
    private String streetAdd;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "state")
    private String state;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "postalCode")
    private int postalCode;
    @Column(name = "phone")
    private String phone;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "contactId")
    private Collection<Person> personCollection;
    @OneToMany(mappedBy = "officeID")
    private Collection<Team> teamCollection;

    public ContactInfo() {
    }

//    public ContactInfo(Integer contactID) {
//        this.contactID = contactID;
//    }

    public ContactInfo(String streetAdd, String city, String state, String country, int postalCode, String phone, String mobile,String email) {
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.streetAdd = streetAdd;
        
    }

    public Integer getContactID() {
        return contactID;
    }

    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    public String getStreetAdd() {
        return streetAdd;
    }

    public void setStreetAdd(String streetAdd) {
        this.streetAdd = streetAdd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }

    @XmlTransient
    public Collection<Team> getTeamCollection() {
        return teamCollection;
    }

    public void setTeamCollection(Collection<Team> teamCollection) {
        this.teamCollection = teamCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactID != null ? contactID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactInfo)) {
            return false;
        }
        ContactInfo other = (ContactInfo) object;
        if ((this.contactID == null && other.contactID != null) || (this.contactID != null && !this.contactID.equals(other.contactID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "assignment5.ContactInfo[ contactID=" + contactID + " ]";
    }
    
    
    
    
}
