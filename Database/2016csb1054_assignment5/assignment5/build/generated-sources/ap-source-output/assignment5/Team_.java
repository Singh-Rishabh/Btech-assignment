package assignment5;

import assignment5.ContactInfo;
import assignment5.TeamMember;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-18T07:04:12")
@StaticMetamodel(Team.class)
public class Team_ { 

    public static volatile CollectionAttribute<Team, TeamMember> teamMemberCollection;
    public static volatile SingularAttribute<Team, ContactInfo> officeID;
    public static volatile SingularAttribute<Team, Integer> teamID;
    public static volatile SingularAttribute<Team, String> name;
    public static volatile SingularAttribute<Team, String> creationDate;
    public static volatile SingularAttribute<Team, String> status;

}