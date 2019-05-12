// Package of the file
package assignment5;

// Import necessary files
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import static java.lang.Math.abs;
import static java.lang.System.in;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author rishabh
 */

// class which holds the Main function
public class Assignment5 {

    private EntityManagerFactory emf = null;                            // Creating the emtity manager factory Variable
    public static int numPeople;                                        // Variable to store number of people
    public randDate randomDate;                                         // Variable of type randDate which returns a random date 
    public int numTeams ;                                               // Stores the numbers of teams (20)...
    // Constructor to initialise all the variables.
    public Assignment5() {  
        Random rand = new Random();
        randomDate = new randDate();
        numTeams = 20;
        numPeople = 580;
        emf = Persistence.createEntityManagerFactory("Assignment5-csl310");
    }
    // method that generates the random names by reading from file.
    public String[] generateRandNames(){
        try{
            FileReader fr = new FileReader("randNames.txt");            // Opening the file which contain the random names
            BufferedReader br = new BufferedReader(fr);
            String [] names = new String[numPeople];
            String line ;
            int i=0;
            while( (line = br.readLine()) != null && i<numPeople){
                names[i] = line;
                i++;
            }
            return names;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    // Method that generates the random Dob by calling the randomDate object
    public String[] generateRandDOB(){
        try{
            String [] DOB = new String[numPeople];
            int i=0;
            while(i<numPeople){
                DOB[i] = randomDate.getDate(1990, 2000);
                i++;
            }
            return DOB;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    // Method to genearate Random creation date.
    public String[] generateRandcreationDate(){
        try{
            String [] OpeningDAte = new String[numTeams];
            int i=0;
            while(i<numTeams){
                OpeningDAte[i] = randomDate.getDate(2000, 2005);
                i++;
            }
            return OpeningDAte;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    // Method to genearate Random Hire  date.
    public String[] generateRandHireDate(){
        try{
            String [] OpeningDAte = new String[numPeople];
            int i=0;
            while(i<numPeople){
                OpeningDAte[i] = randomDate.getDate(2005, 2010);
                i++;
            }
            return OpeningDAte;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to generate Random City State and country.
    public String[] generateRandomCityState(){
        try{
            FileReader fr = new FileReader("randAdd.txt");
            BufferedReader br = new BufferedReader(fr);
            ArrayList<Integer> randNumArr = new ArrayList<Integer>();
            String line;
            int lineNum = 0;
            String [] address = new String[numPeople];
            while((line = br.readLine()) != null){
                lineNum ++;
            }
            br.close();
            fr.close();
            Random rand = new Random();
            fr = new FileReader("randAdd.txt");
            br = new BufferedReader(fr);
            for (int i=0;i<numPeople; i++){
                int  n = rand.nextInt(lineNum) + 1;
                randNumArr.add(n);
                
            }
            Collections.sort(randNumArr);
            int countLine = 1;
            line = br.readLine();
            for(int i=0;i<randNumArr.size(); i++){
                while (randNumArr.get(i) != countLine ){
                    line = br.readLine();
                    countLine++;
                }
                address[i] = line;
            }

            return address;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    // Method to find by contact ID in contact_info table
    public List<ContactInfo> findByContactID(int contactId) {
    	List<ContactInfo> list = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("ContactInfo.findByContactID");
            q.setParameter("contactID", contactId);
            list = q.getResultList();
        } finally {
            em.clear();
            em.close();
        }
        return list;
    }
    // Method to find by Person ID in Person Table
    public List<Person> findByPersonID(int contactId) {
    	List<Person> list = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Person.findByPersonID");
            q.setParameter("personID", contactId);
            list = q.getResultList();
        } finally {
            em.clear();
            em.close();
        }
        return list;
    }
    // Method to find by Team ID in Team Table
    public List<Team> findByTeamID(int contactId) {
    	List<Team> list = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query q = em.createNamedQuery("Team.findByTeamID");
            q.setParameter("teamID", contactId);
            list = q.getResultList();
        } finally {
            em.clear();
            em.close();
        }
        return list;
    }
    
    // Method to insert into the Contact indo tables. It takes the address and the names arrays to do its work
    private void insertIntoContact_info(String[] names, String[] address){
        for(int i=0;i<names.length ; i++){
            String [] temparr = address[i].split(" ");
            String [] temparr2 = names[i].split(" ");
            ContactInfo ci = new ContactInfo();
            Random rand = new Random();
            ci.setStreetAdd("A-" + rand.nextInt(50));
            ci.setCity(temparr[0]);
            ci.setState(temparr[1]);
            ci.setCountry("India");
            ci.setPostalCode(Integer.parseInt(temparr[2]));
            ci.setPhone("9999999999");
            ci.setMobile("9999999999");
            ci.setEmail(temparr2[0] + temparr2[1]+ "@gmail.com");
            saveEntity(ci);
        }
        
    }
    
     // Method to insert into the Person tables. It takes the dob and the names arrays to do its work.
    private void insertIntoPerson(String[] names, String[] dob) {
        for(int i=0;i<names.length ; i++){
            String [] temparr2 = names[i].split(" ");
            Person p = new Person();
            p.setNameF(temparr2[0]);
            p.setNameL(temparr2[1]);
            p.setDob(dob[i]);
            List<ContactInfo> list = findByContactID(i+1);
            p.setContactId(list.get(0));
            saveEntity(p);
        }
    }
    
    // Method to insert into the Team tables. It takes the creationDate array to do its work
    private void insertIntoTeam(String[] creationDate) {
        Random rand = new Random();
        int t1 = rand.nextInt(numTeams) + 1;
        int t2 = rand.nextInt(numTeams) + 1;
        int t3 = rand.nextInt(numTeams) + 1;
        for(int i=0;i<numTeams ; i++){
            Team t = new Team();
            t.setName("Team" + (i+1));
            t.setCreationDate(creationDate[i]);
            
            if (i+1 == t1 || i+1 == t2 || i+1 == t3){
                t.setStatus("Retired");
            }else{
                t.setStatus("Active");
            } 
            List<ContactInfo> list = findByContactID(rand.nextInt(numPeople) + 1);
            t.setOfficeID(list.get(0));
            saveEntity(t);
        }
    }
    
     // Method to insert into the TeamMember table. It takes the hireDate array to do its work
    private void insertIntoTeamMember(String[] hireDate) {
        Random rand  = new Random();
        int temp = 1;
        String [] temparr = {"Owner", "Player", "Manager", "Other"};
        for (int i = 0; i< numTeams ; i++){
            int numPlayers = rand.nextInt(5) + 10;
            int numManagers = rand.nextInt(3) + 2;
            int numOwners = rand.nextInt(3) + 2;
            int numOthers = rand.nextInt(4);
            int sum = numPlayers + numManagers + numOwners + numOthers;
            
            for(int j=0;j<sum; j++){
                TeamMember tm = new TeamMember();
                List<Person> list = findByPersonID((temp + j)%numPeople);
                tm.setPersonID(list.get(0));
                List<Team> list1 = findByTeamID(i+1);
                tm.setTeamID(list1.get(0));
                tm.setHireDate(hireDate[ ((temp + j)%numPeople)]);
                BigDecimal b = new BigDecimal(rand.nextFloat()*100000 , new MathContext(3, RoundingMode.HALF_EVEN));
                tm.setSalary(b);
                tm.setRemarks("---");
                if (j<numPlayers){
                    tm.setRole("Player");
                }else if (j<numPlayers + numManagers){
                    tm.setRole("Managers");
                }else if (j<numPlayers + numManagers + numOwners){
                    tm.setRole("Owners");
                }
                else{
                    tm.setRole("Others");
                }    
                saveEntity(tm);
                
            }temp = (temp + sum)%numPeople;
        }
    }
    // A Templete Method to insert into any of the database table
    public <E> void saveEntity(E entity) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            em.close();
        } finally {
            em.close();
        }
    }
    
    // The main function to call all the other method.
    public static void main(String[] args) {
        System.out.println("Starting ");
        Assignment5 dao = new Assignment5();

        // If the command line argument is -i then load the database with random values.
        if (args[0].equals("-i")){
            String [] names = dao.generateRandNames();
            String [] address = dao.generateRandomCityState();
            String [] dob = dao.generateRandDOB();
            String [] creationDate = dao.generateRandcreationDate();
            String [] hireDate = dao.generateRandHireDate();
            dao.insertIntoContact_info(names,address);

            System.out.println("Inserted into contact info");
            dao.insertIntoPerson(names, dob);
            System.out.println("Inserted into Person");
            dao.insertIntoTeam(creationDate);
            System.out.println("Inserted into Team");
            dao.insertIntoTeamMember(hireDate);
            System.out.println("Inserted into Team MEMBER");
        }

        // If the command line argument is -q then run the queries by user.
        else if (args[0].equals("-q")){
            try{

                Scanner sc=new Scanner(System.in);                              // Initialising the Scanner class.
                EntityManager em = dao.emf.createEntityManager();
                System.out.println("Output of the three queris will be provided to you\n\tQuery 1: All “Players” of a given Team whose salary is between a given range.");
                System.out.println("\tQuery 2: Role wise numbers of team members in a given team.");
                System.out.println("\tQuery 3: Average salary of players from a given state.");
                
                int choose = -1;
                // Runs First query.
                System.out.println("Enter 1 or 2 or 3 to execute the respective query");
                choose = sc.nextInt();
                if (choose == 1){
                    System.out.println("Enter the team name : ");
                    String team_name = sc.next();
                    System.out.print("team name : " + team_name);
                    System.out.print("\nEnter The range of salary to get Players name \nMin Salary : "); 
                    float min = sc.nextFloat();
                    System.out.print("Max Salary: ");
                    float max = sc.nextFloat();
                    List<Person> list = null;
                    try {
                        Query q = em.createNamedQuery("Person.nameOfPlayersBwtweenSalary");
                        q.setParameter("team_name", team_name);
                        q.setParameter("min", min);
                        q.setParameter("max", max);
                        list = q.getResultList();
                    
                        for(Person p : list){
                            System.out.println("\t" + p.getNameF() + " " + p.getNameL());
                        }
                        if (list.size() == 0){
                            System.out.println("No result found");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                // Runs Second query.
                else if (choose == 2){
                    System.out.println("\nEnter the Team name: ");
                    String team_name = sc.next();
                    List<Team> list = null;
                    try {
                        Query q = em.createNamedQuery("Team.findByName");
                        q.setParameter("name", team_name);
                        list = q.getResultList();
                        Query q1 = em.createNamedQuery("TeamMember.countMemberWise");
                        q1.setParameter("teamID", list.get(0));
                        List<Object[]> pp = q1.getResultList();
                        for(Object[] o : pp){
                            System.out.println("\t" + o[0] + " " + (String)o[1]);
                        }
                        if (pp.size() == 0){
                            System.out.println("No result found");
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    
                }
                // Runs Third query.
                else if (choose == 3){
                    System.out.println("\nenter the name of the State: ");
                    String state = sc.next();
                    try {
                        Query q3 = em.createNamedQuery("TeamMember.averageSalary");
                        q3.setParameter("state", state);
                        Object pp = q3.getResultList();
                        System.out.println("\tThe average salary of the layers from the given state is " + pp);

                        
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("Please Enter correct input");
                }
                

               
            }catch(Exception e){
                e.printStackTrace();
            }
        
        }
        
    }

}