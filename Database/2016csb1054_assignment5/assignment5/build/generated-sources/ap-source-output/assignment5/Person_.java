package assignment5;

import assignment5.ContactInfo;
import assignment5.TeamMember;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-18T07:04:12")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile SingularAttribute<Person, String> nameF;
    public static volatile CollectionAttribute<Person, TeamMember> teamMemberCollection;
    public static volatile SingularAttribute<Person, ContactInfo> contactId;
    public static volatile SingularAttribute<Person, String> dob;
    public static volatile SingularAttribute<Person, Integer> personID;
    public static volatile SingularAttribute<Person, String> nameL;

}