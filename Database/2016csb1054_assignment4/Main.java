// Some Important Import Statements to import proper packages
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.DateFormat;  
import java.util.Date;  
import java.util.Scanner;  


//  Main class to call all other class
public class Main {
    

    public static final String username = "main.mysql.username";            // username holder variable;
    public static final String password = "main.mysql.password";            // Password holder variable;
    private static DataAccessLayer dal;

    private static int numPeople ;                                          // Variable to store the number of people (50);
    private RandomDate randomDate;                                          // Creating the Variable of type RandomDate to call Random Date class 
    private Properties queries = new Properties();                          // Initialising the Properties file
    public Main() {
        try {

            queries.load(DataAccessLayer.class.getResourceAsStream("queries.properties")); // Loding the queries.properties file.
            this.randomDate = new RandomDate();                                            // Initialising the random date class.
            
            // Initialising the DataAccessLayer Class.
            this.dal = new DataAccessLayer("jdbc:mysql://localhost:3306/Bank?useSSL=false",queries.getProperty(username),queries.getProperty(password));
            numPeople = dal.numPeople;
            
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e ){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        // TODO code application logic here

        System.out.println("Starting.");
        Main app = new Main();

        // If the command line argument is -i then load the database with random values.
        if (args[0].equals("-i")){
            String[] randAddress = dal.generateRandomCityStateCountry();                    // Stores the random Address of the people.
            String[] randNames = dal.getNames();                                            // Stores the random Names of people.
            String[] randDOB = dal.generateRandDOB();                                       // Stores the random date Of birth of people.
            String[] randPanNo = dal.generateRandPanNumber();                               // Stores the random pan number of each people.
            String[] randDetails = new String[numPeople];                                   // Store the random details of the people.
            String[] randOpeningDate = dal.generateRandOpeningDate();                       // Stores the random opening date of the bank account.
            for(int i=0;i<numPeople; i++){
                randDetails[i] = randNames[i] + " " + randDOB[i] + " " + randPanNo[i];
            }
            dal.insertInDatabase(randAddress,randDetails,randOpeningDate,randNames);        // Calling the function to load the database.
        }

        // If the command line argument is -q then run the queries by user.
        else if (args[0].equals("-q")){
            try{

                Scanner sc=new Scanner(System.in);                              // Initialising the Scanner class.
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH); // making the DAte format.

                System.out.println("Output of the three queris will be provided to you\n\tQuery 1: A transaction statement for a given account. It will print the transactions recorded for a given account and between given two dates.");
                System.out.println("\tQuery 2: Category wise spending of a given person in a given month.");
                System.out.println("\tQuery 3: Category wise spending in a given month by all persons (taken together) who live in a given city.");
                
                int choose = -1;
                // Runs First query.
                System.out.println("Enter 1 or 2 or 3 to execute the respective query");
                choose = sc.nextInt();
                if (choose == 1){
                    System.out.println("\nEnter Account Id to get the Transaction details: "); 
                    
                    int id = sc.nextInt();

                    System.out.println("Enter startDate and End Date to get the transactions Statement(Please enter date in format yyyy/MM/dd)");
                    System.out.println("Note *** Please Enter the recent date");  

                    System.out.print("Start Date : ");
                    String sDate1=sc.next();
                    Date d1=formatter.parse(sDate1);  
                    System.out.print("End Date: ")  ;
                    String sDate2=sc.next();
                    Date d2=formatter.parse(sDate2);
                    dal.executeQuery(id, d1,d2);
                }
                // Runs Second query.
                else if (choose == 2){
                    System.out.println("\nEnter the account number to get Your category wise spending: ");
                    int id = sc.nextInt();
                    System.out.println("Enter the month in Integer to know Your category wise spending(4 to get good details): ");
                    int month = sc.nextInt();
                    dal.executeQuery2(id,month);
                }
                // Runs Third query.
                else if (choose == 3){
                     System.out.println("\nEnter name of city to get the Total Money Spend: ");
                    String city = sc.next();
                    System.out.println("Enter the month(4 to get good details): ");
                    int month = sc.nextInt();
                    dal.executeQuery3(city,month);
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
