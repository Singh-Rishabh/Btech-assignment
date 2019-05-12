package assignment5;

import assignment5.Person;
import assignment5.Team;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-18T07:04:12")
@StaticMetamodel(ContactInfo.class)
public class ContactInfo_ { 

    public static volatile SingularAttribute<ContactInfo, String> country;
    public static volatile CollectionAttribute<ContactInfo, Team> teamCollection;
    public static volatile SingularAttribute<ContactInfo, Integer> contactID;
    public static volatile SingularAttribute<ContactInfo, String> streetAdd;
    public static volatile SingularAttribute<ContactInfo, String> city;
    public static volatile SingularAttribute<ContactInfo, String> phone;
    public static volatile SingularAttribute<ContactInfo, Integer> postalCode;
    public static volatile CollectionAttribute<ContactInfo, Person> personCollection;
    public static volatile SingularAttribute<ContactInfo, String> mobile;
    public static volatile SingularAttribute<ContactInfo, String> state;
    public static volatile SingularAttribute<ContactInfo, String> email;

}