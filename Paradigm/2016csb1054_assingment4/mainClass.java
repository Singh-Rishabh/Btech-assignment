// Normal imports Statement
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import java.awt.*;
import java.applet.*;

// Import package for Using date ad timer in code
import java.util.Timer;
import java.util.concurrent.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime; 

// Import package for Hash maps
import java.util.HashMap;
import java.util.Map;

// Import package for Process builder
import java.lang.ProcessBuilder;

// Import packages for sending mail
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*; 

public class mainClass {


	// user name (gmail) for sending mail.
	private static String userName = "abhishek23dawas"; 
	// The password of the above user name 
    private static String password = "abhi123@";

    public static void main(String [] args) {
    	

        // The name of the file 
        String fileName = "config.txt";
        int cpuUsusageLimit = 0;				// sustained.max.cpu.usage.limit
        int cpuDurationLimit = 0;				// sustained.max.cpu.usage.duration.limit
        int memUsusageLimit = 0;				// sustained.max.memory.usage.limit
        int memDurationLimit = 0;				// sustained.max.memory.usage.duration.limit
        int delay = 10;							// delay for each top command
        int de = 25;							// default delay time for clearing the commamds
        ArrayList<String> emailAdd = new ArrayList<String>(); 		// list of email address to send mails


        // Reading the config file(setting.properties) to extract above defined variables.
        try {
        	// A file reader object to open file and read through it.
            FileReader file = new FileReader("config.txt");

            // A buffer reader to store the lines of the fileReader.
            BufferedReader br   = new BufferedReader(file);
            

            // variable line to store the line of text file.
            String line;

            // while loop till the end of file
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");			// Spiting the line.

                // reading File and extracting the necessary details
                if (arr[0].equals("quota.window.minutes")) {
                	de = Integer.parseInt(arr[2]);
                }
                if (arr[0].equals("sustained.max.cpu.usage.limit")){
                    cpuUsusageLimit = Integer.parseInt(arr[2]);
                }
                if (arr[0].equals("sustained.max.cpu.usage.duration.limit")){
                    cpuDurationLimit = Integer.parseInt(arr[2]);
                }
                if (arr[0].equals("sustained.max.memory.usage.duration.limit")){
                    memDurationLimit = Integer.parseInt(arr[2]);
                }
                if (arr[0].equals("sustained.max.memory.usage.limit")){
                    memUsusageLimit = Integer.parseInt(arr[2]);
                }

                if (arr[0].equals("notify.emails")){
                    String[] temp = arr[2].split(",");
                    for(int i=0;i<temp.length; i++){
                        emailAdd.add(temp[i]);
                    }
                }

            }

            // Printing the detail extracted from the configuration file
            System.out.println(cpuDurationLimit + " " + cpuUsusageLimit + " " + memDurationLimit + " " + memUsusageLimit);
            for(String email : emailAdd){
                System.out.println(email);
            }


            /*	A hashmap to store all the information of the running programme pid is the Key and all other data is value.
				When a pid is updated it is updated in the hashmap. The time_use field in the data class is the total time 
				a process is violating the %cpu Ususage Or %mem Ususage of a process.
            */
            HashMap<String, data> map = new HashMap<String, data>();

            final int delay2 = de;			// Dealy for second runnable.

            // Starting a ScheduledExecutorService Object.
            ScheduledExecutorService scheduleTopcmd = Executors.newSingleThreadScheduledExecutor();

            // The command line command to run top command.
            String [] list = {"top" , "-n1", "-b"};

            // Process builder to run the command line command.
            ProcessBuilder pb = new ProcessBuilder(list);
            final float t1 = cpuUsusageLimit;
            final float t2 = memUsusageLimit;
            final int t3 = cpuDurationLimit;
            final int t4 = memDurationLimit;

            // making a new runnable object.
            Runnable task = new Runnable() {
                public void run() {

                    PrintWriter printWriter;
                    System.out.println("\nExecuting the first runnable\n");
                    try{
                        printWriter = new PrintWriter(new FileOutputStream(new File("topFile.txt"),true));
                        Process p1 = pb.start();

                        // getting the input stream of the Process builder.
                        BufferedReader is = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                        String line;
                        int count = 0;
                        ArrayList<data> procData = new ArrayList<data>();		// Array list to store the details of a top command.
                        ArrayList<data> violators = new ArrayList<data>();		// Array list to store the process which are violating the commands
                        ArrayList<String> pidArr = new ArrayList<String>();		// An array List to store the PID of each top command.


                        // Getting present the date and time.
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                        LocalDateTime now = LocalDateTime.now();

                        // Writing to the topData file with the details of the top -n1 -b command.
                        printWriter.append("NEWTOP\n");
                        printWriter.append(dtf.format(now)+ "\n");
                        while ((line = is.readLine()) != null){
                            data d = new data();
                            line = line.trim();
                            count++;
                            String[] temp2_list = line.split("\\s+");
                            if (count >= 8){
                                printWriter.format("%-8s%-12s%-8s%-8s%-12s%-8s",temp2_list[0],temp2_list[1],temp2_list[8],temp2_list[9],temp2_list[10],temp2_list[11]);
                                printWriter.printf("\n");
                                d.setData(temp2_list[0],temp2_list[1],temp2_list[8],temp2_list[9],temp2_list[10],temp2_list[11],0);
                                procData.add(d);		// Update procDAta Array
                                if (Float.parseFloat(temp2_list[8]) > t1 || Float.parseFloat(temp2_list[9]) > t2)
                                {
                                    if (map.get(temp2_list[0]) != null )
                                    {
                                    	procData.get(procData.size()-1).time_use = delay + map.get(temp2_list[0]).time_use; 
                                    } 
                                    else 
                                    {
                                    	procData.get(procData.size()-1).time_use = delay;
                                    }
                                }
                                pidArr.add(temp2_list[0]);		// adding the Pid to the PidArray
                            
                            }else if (count == 7){
                                printWriter.format("%-8s%-12s%-8s%-8s%-12s%-8s","PID","USER","%CPU","%MEM","TIME+","COMMAND");
                                printWriter.printf("\n");
                            }
                            else{
                                printWriter.append(line);
                                printWriter.append("\n");
                            }
                            
                        }

                        // // Printing the PidArray 
                        // System.out.println("PRinting PID ARRAY");
                        // System.out.println("Pid Array size " + pidArr.size());
                        // for(String d : pidArr){
                        //     System.out.println(d);
                        // }

                        // Updating the HashMap with new top commands
                        int j = 0;
                        for(String pi : pidArr){
                        	if(map.get(pi) != null){
                        		map.put(pi , procData.get(j));
                        	}else if (map.get(pi) == null){
                        		map.put(pi , procData.get(j));
                        	}
                        	j++;
                        }


                        // Remove the Pid Which are not currently working.
                        ArrayList<String> removePid = new ArrayList<String>();
                        for(Map.Entry m : map.entrySet()){
                        	int check = 0;
                        	for(String pi : pidArr){
                        		if (pi.equals( ((String)m.getKey())) ){
                        			check = 1;
                        			break;
                        		}
                        	}
                        	if (check == 0){
                        		removePid.add( (String)m.getKey() );
                        	}
                        }
                        for(String pi : removePid){
                        	map.remove(pi);
                        }

                        // Adding entries to violaters list.
                        for(Map.Entry m : map.entrySet()){
                        	if ( ((data)m.getValue()).time_use > t3 || ((data)m.getValue()).time_use > t4 ){
                        		violators.add((data)m.getValue());
                        	}
                        }

                        // Calling the sendMail Function send the mail to the user. 
                        String from = userName;
						String pass = password;
						String[] to = {emailAdd.get(0)}; // list of recipient email addresses only zeroth 
						String subject = "process using more Cpu";		// Subject of the Email address
						String textMessage = "These are the process using more %cpu or more %mem.\n";		// The text message Stored.
						for(data d : violators){
							textMessage = textMessage + "\tPID: " + d.pid + " USER: "+ d.user + "\n";
						}
                        if (violators.size() > 0){
                            sendMail(from, pass, to, subject, textMessage);
                            System.out.println("Mail Send Sucessfully");
                        }
						

                        // System.out.println("Printing violaters size " + violators.size());
                        // for(data d : violators){
                        //     System.out.println(d.pid + " " + d.user + " " + d.cpu + " " + d.mem);
                        // }

						// System.out.println("printing map " + map.size());
						// for(Map.Entry m : map.entrySet()){  
			   			// 	System.out.println(m.getKey()+" "+((data)m.getValue()).command + " "  + ((data)m.getValue()).time_use);  
			    		// }
                        printWriter.printf("ENDOFTOP\n");
                        printWriter.close();
                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("ArrayIndexOutOfBoundsException foumd");
                    }
                    catch(Exception exc){
                        System.out.println("Caught Exception");
                    }
                    

                }

            };

            
            scheduleTopcmd.scheduleAtFixedRate(task, 0,delay, TimeUnit.SECONDS);


            // the second ScheduledExecutorService For deleting entries from files after regular interval.
            ScheduledExecutorService scheduleDelFile = Executors.newSingleThreadScheduledExecutor();
            Runnable task2 = new Runnable() {
                public void run() {
                    PrintWriter printWriter;
                    System.out.println("\nExecuting the second runnable\n");
                    /* 
						Maing a temporary file and copying TopData file useful content to it and then reverse copy it to topData file 
						and delete the Temp File. 
                    */
                    try{
                        printWriter = new PrintWriter(new FileOutputStream("tempFile.txt"));
                        BufferedReader br = new BufferedReader(new FileReader("topFile.txt"));
                        String line = br.readLine();
                        int count = 0;  

                        // Get Curent date time to compare what portion to delete.
                        SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
                        Date currTime = new Date();
                        while (true){
                        	if (line == null) break;
                            if(line.equals("NEWTOP")){
                                String topCmdDate = br.readLine();
                                line = topCmdDate;
                                Date fileDate = dtf.parse(topCmdDate);
                                long diff = currTime.getTime() - fileDate.getTime(); // difference in two date object
                                long diffSeconds = diff/1000%60; // difference in the seconds
                                long diffMinutes = (diff/(60*1000)%60)*60;
                                long diffHours = (diff/(60*60*1000)%24)*60*60;
                                long diffDays = (diff/(24*60*60*1000))*24*60*60;
								// System.out.print(diffDays + " days, ");
								// System.out.print(diffHours + " hours, ");
								// System.out.print(diffMinutes + " minutes, ");
								System.out.println(diffSeconds + " seconds.");

								// if difference of seconds is greater than the delay then deleting that part.
                                if (diffSeconds > delay2){
                                	System.out.println("deleting a top command");
                                	while(line != null && !line.equals("NEWTOP")){
                                		line = br.readLine();
                                	}
                                }else{
                                	printWriter.append("NEWTOP\n");
                                	// printWriter.append(line);
                                	while(line != null && !line.equals("NEWTOP")){
                                		printWriter.append(line + "\n");
                                		line = br.readLine();
                                	}
                                }
                            }
                        }printWriter.close();
                        br.close();

                        // Updating agalin the top File.
                        PrintWriter pw = new PrintWriter(new FileOutputStream("topFile.txt"));
                        BufferedReader b = new BufferedReader(new FileReader("tempFile.txt"));
                        while((line = b.readLine()) != null ){
                        	pw.println(line);
                        }pw.close();
                        b.close();

                        // Deleting the temp file.
                        File file = new File("tempFile.txt");
				        if(file.delete()){
				            System.out.println("File deleted successfully");
				        }else{
				            System.out.println("Failed to delete the file");
				        }



                    }catch(ArrayIndexOutOfBoundsException e){
                        System.out.println("ArrayIndexOutOfBoundsException foumd");
                    }
                    catch(Exception exc){
                        System.out.println("Caught Exception");
                    }
                    
                }
            };
     
            // Running the second Runnable.
            scheduleDelFile.scheduleAtFixedRate(task2, 1 ,delay2, TimeUnit.SECONDS);


        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(Exception ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'"); 
        }
    }


    // Send Mail function taken online.
    private static void sendMail(String from, String pass, String[] to, String subject, String body) {
    	Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");
        

        //Session session = Session.getDefaultInstance(props);
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	    	}
	});

        try {
        	
        MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    } 

}
