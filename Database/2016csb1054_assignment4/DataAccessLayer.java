// Import Statements
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;  
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.text.DateFormat;  
import java.util.Date;  
// import java.sql.Date;

// The DataAccessLayer Class.
public class DataAccessLayer {
    
    private Connection conn = null;                 // Connection object to connect to the database.


                        // Important variables from the properties File.


    public static final String SQL_INSERT_BANK_ACCOUNT = "bank_account.insert.sql";
    public static final String SQL_INSERT_ACCOUNT_HOLDER = "account_holder.insert.sql";
    public static final String SQL_INSERT_CONTACT_INFO = "contact_info.insert.sql";
    public static final String SQL_INSERT_TRANSACTION = "transaction.insert.sql";
    public static final String SQL_UPDATE_BANK_ACCOUNT = "bank_account.update.sql";
    public static final String SQL_SELECT_BANK_ACCOUNT = "bank_account.select.sql";
    public static final String SQL_QUERY1 = "query1.sql";
    public static final String SQL_QUERY2 = "query2.sql";
    public static final String SQL_QUERY3 = "query3.sql";
    public static final String SQL_SELECT_STATUS_BANK_ACCOUNT = "bank_account.status.select.sql";


    
    public static final Integer numberDays = 30;                 // Stores the difference of the current date and the date till which we want our programme to go back.
    public static final int numPeople = 50;                     // Store the count of number of people
    
    
    private Properties queries = new Properties();
    private RandomDate randomDate;


    // Constructor To initiaise the vatiables and setup connection.
    public DataAccessLayer(String dbUrl,String username , String password) throws SQLException {
    
        try {  
            Class.forName("com.mysql.jdbc.Driver");  
            conn = DriverManager.getConnection(dbUrl,username,password);        
            System.out.println("Connected to the network");
            queries.load(DataAccessLayer.class.getResourceAsStream("queries.properties"));
            this.randomDate = new RandomDate();
            
        } catch (IOException ex) {
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    // Function to get random names fromm the file.
    public String[] getNames(){
        try{
            FileReader fr = new FileReader("details.txt");
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


    // Function to geneate Random DOB.
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


    // Function to genearate Ranfom opening date.
    public String[] generateRandOpeningDate(){
        try{
            String [] OpeningDAte = new String[numPeople];
            int i=0;
            while(i<numPeople){
                OpeningDAte[i] = randomDate.getDate(2000, 2001);
                i++;
            }
            return OpeningDAte;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // Function to genearate Ranfom Pan Number.
    public String[] generateRandPanNumber(){
        try{
            String [] panNumber = new String[numPeople];
            int i=0;
            while(i<numPeople){
                String line = "AAAPL" + Integer.toString(i+1) + "C";
                panNumber[i] = line;
                i++;
            }
            return panNumber;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    // Function to generate Random City State and country.
    public String[] generateRandomCityStateCountry(){
        try{
            FileReader fr = new FileReader("randomAdd.txt");
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
            fr = new FileReader("randomAdd.txt");
            br = new BufferedReader(fr);
            Random rand = new Random();
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


    // Function to Insert into contact_info table.
    public void insertContactsInfo(String[] contacts, String[] names){
        try {

            for (int i=0;i<contacts.length; i++){
                String line = contacts[i];
                String[] info_contacts = line.split(" ");
                String[] namearr = names[i].split(" ");
                String tempEmailID = namearr[0] + namearr[1] + "@gmail.com";
                PreparedStatement ps = 
                        conn.prepareStatement(queries.getProperty(SQL_INSERT_CONTACT_INFO));
                ps.setString(1,"Street NO : " + (i+1) );
                ps.setString(2,info_contacts[0]);
                ps.setString(3,info_contacts[1]);
                ps.setString(4,info_contacts[2]);
                ps.setString(5,info_contacts[3]);
                ps.setString(6,"9999999999");
                ps.setString(7,"1234567890");
                ps.setString(8,tempEmailID);

                int rows = ps.executeUpdate();
                if (rows == 0){
                    System.out.println("Insert failed : Contact_info");
                }
            }
        }catch (SQLException ex) {
            System.out.println("Cannot insert into database: contact_info");
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }


    // Function to Insert into Account_holder table.
    public void insertAccountHolder(String[] details){
        try {
            for (int i=0;i<details.length; i++){
                String line = details[i];
                
                String[] info_details = line.split(" ");
                // System.out.println("\n");
                // for (int j = 0 ;j<info_details.length; j++){
                //     System.out.println(info_details[j]);
                // }
                
                PreparedStatement ps = 
                        conn.prepareStatement(queries.getProperty(SQL_INSERT_ACCOUNT_HOLDER));
                ps.setString(1,info_details[3]);
                ps.setString(2,info_details[0]);
                ps.setString(3,info_details[1]);
                // DateFormat formatter = new SimpleDateFormat("yyyy/MM/DD"); 
                // Date date = (Date)formatter.parse(info_details[2]);
                ps.setString(4,info_details[2]);
                ps.setInt(5,i+1);

                int rows = ps.executeUpdate();
                if (rows == 0){
                    System.out.println("Insert failed : account_holder");
                }
                
            }
        }catch (SQLException ex) {
            System.out.println("Cannot insert into database: account_holder");
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println("Insertion failed");
            e.printStackTrace();
        }
        
    }


    // Function to Insert into Bank_Account table.
    private void insertBankAccount(String[] openingDate){
        try {
            Random random = new Random();
            int t1 = random.nextInt(numPeople-1) + 0;
            int t2 = random.nextInt(numPeople-1) + 0;
            int t3 = random.nextInt(numPeople-1) + 0;
            int t4 = random.nextInt(numPeople-1) + 0;
            int t5 = random.nextInt(numPeople-1) + 0;
            int t6 = random.nextInt(numPeople-1) + 0;
            for (int i=0;i<openingDate.length; i++){
                String line = openingDate[i];
                
                PreparedStatement ps = 
                        conn.prepareStatement(queries.getProperty(SQL_INSERT_BANK_ACCOUNT));
                
                ps.setString(1,line);

                if (t1 == i || t2 == i){
                    ps.setString(2,randomDate.getDate(2013, 2015));
                    ps.setString(3,"Dormant");
                }else{
                    ps.setString(2,null);
                    if(i == t3 || i == t4 || i==t5 || i==t6){
                        ps.setString(3,"Dormant");
                    }
                    else{
                        ps.setString(3,"Active");
                    }
                }

                ps.setString(4,"Saving");

                float tempBalance = random.nextFloat()*1000000;
                ps.setFloat(5,tempBalance);
                ps.setString(6,randomDate.getDate(2010, 2013));
                ps.setInt(7,i+1);

                int rows = ps.executeUpdate();
                if (rows == 0){
                    System.out.println("Insert failed : Bank_Account");
                }
                
            }
        }catch (SQLException ex) {
            System.out.println("Cannot insert into database: Bank_Account");
            Logger.getLogger(DataAccessLayer.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception e){
            System.out.println("Insertion failed");
            e.printStackTrace();
        }
        
    }


    // Function to Insert the daily Transactions.
    private void insertTransaction(){
        Date date = new Date(); 
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, - numberDays);
        Random random = new Random();
        Date dateBefore2Days = cal.getTime();
        String[] randCategory = {"Tax" , "Grocery", "Salary", "Medical" , "Bill", "Dining" , "Money Transfer"};
        try{

            for (int i=0;i<numberDays;i++){
                for (int j=0;j<numPeople;j++){
                    PreparedStatement ps = conn.prepareStatement(queries.getProperty(SQL_SELECT_STATUS_BANK_ACCOUNT));
                    ps.setInt(1,j+1);
                    ResultSet rs = ps.executeQuery();
                    String status1 = null;
                    String openingDate1 = null;
                    while(rs.next()) {
                        status1 = rs.getString("status");
                        openingDate1 = rs.getString("openingDate");
                    }
                    int to = random.nextInt(numPeople) + 1;
                    ps = conn.prepareStatement(queries.getProperty(SQL_SELECT_STATUS_BANK_ACCOUNT));
                    ps.setInt(1,to);

                    rs = ps.executeQuery();
                    String status2 = null;
                    String openingDate2 = null;
                    while(rs.next()) {
                        status2 = rs.getString("status");
                        openingDate2 = rs.getString("openingDate");
                    }
                    if ( status1.equals("Dormant") ){
                        continue;
                    }
                    if ( status2.equals("Dormant") ){
                        continue;
                    }
                

                    conn.setAutoCommit(false);
                    float amount = random.nextFloat()*200;
                    int randIndex = random.nextInt(randCategory.length-1) + 0;
                    TransactionDto from_accNo = new TransactionDto();
                    from_accNo.set("Debit" , cal.getTime(), amount,to,j+1,randCategory[randIndex],"----");
                    ps = 
                        conn.prepareStatement(queries.getProperty(SQL_INSERT_TRANSACTION));

                    ps.setString(1,from_accNo.type);
                    ps.setDate(2,new java.sql.Date( from_accNo.tDate.getTime() ));
                    ps.setFloat(3,from_accNo.amount);
                    ps.setInt(4,from_accNo.accountIDTo);
                    ps.setInt(5,from_accNo.accountIDFrom);
                    ps.setString(6,from_accNo.category);
                    ps.setString(7,from_accNo.remarks);
                    int rows = ps.executeUpdate();

                    if (rows != 1) {
                        System.out.println(">>>>> Insert in Transaction Debit part failed!!");
                    }

                    TransactionDto to_accNo = new TransactionDto();
                    to_accNo.set("Credit" , cal.getTime(), amount,j+1,to,randCategory[randIndex],"----");

                    ps = 
                        conn.prepareStatement(queries.getProperty(SQL_INSERT_TRANSACTION));


                    ps.setString(1,to_accNo.type);
                    ps.setDate(2,new java.sql.Date( from_accNo.tDate.getTime() ));
                    ps.setFloat(3,to_accNo.amount);
                    ps.setInt(4,to_accNo.accountIDTo);
                    ps.setInt(5,to_accNo.accountIDFrom);
                    ps.setString(6,to_accNo.category);
                    ps.setString(7,to_accNo.remarks);

                    rows = ps.executeUpdate();
                    if (rows != 1) {
                        System.out.println(">>>>> Insert in Transaction Credit part failed!!");
                    }

                    ps = conn.prepareStatement(queries.getProperty(SQL_SELECT_BANK_ACCOUNT));

                    rs = ps.executeQuery();
                    float balance = 0;
                    while(rs.next()) {
                        balance = rs.getInt("cuurBalance");
                    }


                    ps = 
                        conn.prepareStatement(queries.getProperty(SQL_UPDATE_BANK_ACCOUNT));
                    ps.setFloat(1,balance-amount);
                    DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                    Date d = cal.getTime();
                    // System.out.println( );
                    String strDate = formatter.format(d);  
                    ps.setString(2,strDate);
                    ps.setInt(3,j+1);
                    rows = ps.executeUpdate();
                    if (rows != 1) {
                        System.out.println(">>>>> Update in Bank_Account failed!!");
                    }


                    ps = 
                        conn.prepareStatement(queries.getProperty(SQL_UPDATE_BANK_ACCOUNT));
                    ps.setFloat(1,balance+amount);
                    ps.setString(2,strDate);
                    ps.setInt(3,to);
                    rows = ps.executeUpdate();
                    if (rows != 1) {
                        System.out.println(">>>>> Update in Bank_Account failed!!");
                    }

                    conn.commit();
                    conn.setAutoCommit(true);
                    
                }cal.add(Calendar.DATE, +1);
            }
        }
        catch(Exception e){
            System.out.println("ERROR in TRANSACTION");
            e.printStackTrace();
        }
        finally{

        }
        
    }


    // Method to Insert overall thigs into database.
    public void insertInDatabase(String[] contacts , String[] details, String[] openingDate, String[] names){
        this.insertContactsInfo(contacts,names);
        System.out.println( "Inserted into the database: contact_info");
        this.insertAccountHolder(details);
        System.out.println( "Inserted into the database: account_holder");
        this.insertBankAccount(openingDate);
        System.out.println( "Inserted into the database: Bank_Account");
        this.insertTransaction();
        System.out.println( "Inserted into the database: transaction");
    }


    // Method to Execute the first Query.
    public void executeQuery(int id, Date d1, Date d2){
        try{
            PreparedStatement ps = conn.prepareStatement(queries.getProperty(SQL_QUERY1));
            ps.setFloat(1,id);

            ps.setDate(2, new java.sql.Date(d1.getTime()) );
            ps.setDate(3,new java.sql.Date(d2.getTime()));

            ResultSet rs = ps.executeQuery();
            System.out.println("\nExecuting 1st query");
            int check = 0;
            while(rs.next()) {
                System.out.println("TransactionID: "+ rs.getInt("transactionID") +  "\ttype "+ rs.getString("type") +  "\ttype "+ rs.getDate("tDate") +  "\tAmount "+ rs.getFloat("amount") +  "\tTO "+ rs.getInt("accountIDTo") +  "\tcategory "+ rs.getString("category")) ;
                check = 1;
            }
            if (check == 0){
                System.out.println("No result");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // Method to Execute the Second Query.
    public void executeQuery2(int id, int month){
        try{
            PreparedStatement ps = conn.prepareStatement(queries.getProperty(SQL_QUERY2));
            ps.setFloat(1,id);

            ps.setInt(2, month);

            ResultSet rs = ps.executeQuery();
            System.out.println("\nExecuting 2nd query");
            int check = 0;
            while(rs.next()) {
                System.out.println("Sum : " + rs.getFloat("totalAmount") + "\tCategory : "+ rs.getString("category")) ;
                check = 1;
            }
            if (check == 0){
                System.out.println("No result");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // Method to Execute the Third Query.
    public void executeQuery3(String city, int month){
        try{
            PreparedStatement ps = conn.prepareStatement(queries.getProperty(SQL_QUERY3));
            ps.setInt(1,month);

            ps.setString(2, city);

            ResultSet rs = ps.executeQuery();
            System.out.println("\nExecuting 3rd query");
            int check = 0;
            while(rs.next()) {
                System.out.println("Sum : " + rs.getFloat("totalAmount") + "\tCategory : "+ rs.getString("category")) ;
                check = 1;
            }
            if (check == 0){
                System.out.println("No result");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
