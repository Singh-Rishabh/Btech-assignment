package assignment5;

import assignment5.Person;
import assignment5.Team;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-18T07:04:12")
@StaticMetamodel(TeamMember.class)
public class TeamMember_ { 

    public static volatile SingularAttribute<TeamMember, String> hireDate;
    public static volatile SingularAttribute<TeamMember, String> role;
    public static volatile SingularAttribute<TeamMember, Team> teamID;
    public static volatile SingularAttribute<TeamMember, Person> personID;
    public static volatile SingularAttribute<TeamMember, BigDecimal> salary;
    public static volatile SingularAttribute<TeamMember, String> remarks;
    public static volatile SingularAttribute<TeamMember, Integer> memberID;

}