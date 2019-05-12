
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.awt.*;
import java.applet.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import java.lang.ProcessBuilder;

public class mainClass {
    // method to download a jar file. 
    private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    // method to print the classes present in the jar file.

    public static int printClass(){
        String [] list1 = {"find" , ".", "-mindepth", "2" , "-name", "*.class"};
        Runtime r = Runtime.getRuntime();
        FileOutputStream ofs = null;
        PrintWriter printWriter;
        int lineCount = 0;
        try{
            ofs = new FileOutputStream("output.txt");
            //System.setOut(ofs);
            printWriter = new PrintWriter(ofs);
            printWriter.println("These are the classes present in present working directeory incluing their path.\n");
            
            Process p1 = r.exec(list1);
            BufferedReader is = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            String line;
            
            // reading the output
            while ((line = is.readLine()) != null){
                lineCount++;
                printWriter.println(line);
            }printWriter.println("\n");
            printWriter.close();
            ofs.close();
            
        }
        catch(Exception e){
            System.out.println("Error in strating Runtime Object");
        }finally{
            return lineCount;
        }
        

    }

    // public methods to extract the contents of jar files.

    public static int extract_jar(){
        List<String> list = new ArrayList<String>();
        list.add("jar");
        list.add("xf");
        list.add("jarfiles.jar");
        int lineCount = 0;
        try{
            ProcessBuilder pb = new ProcessBuilder(list);
            Process p = pb.start();
            lineCount = printClass();
        }
        catch(Exception e){
            System.out.println("Error in strating Process Builder");
        }finally{
            return lineCount;
        }

    }

    // methods to calculate the standard deviation.

    public static double std(int[] arr, float mean){
        double sum= 0;
        for (int i=0;i<arr.length;i++){
            double diff= arr[i]-mean;

            sum+=Math.pow(diff,2);
        }
        sum= sum/(arr.length);
        return Math.pow(sum,0.5);
    }

    // method to compute task 1 of the javap Instructions 

    public static void javapInst(int lineCount){
        BufferedReader ifr;
        BufferedReader reader;
        int[] constantpoolSize = new int[lineCount];
        //FileOutputStream fileOS = null;
        Runtime r = Runtime.getRuntime();
        //PrintWriter printWriter;
        try{
            //fileOS = new FileOutputStream("output.txt");
            ifr = new BufferedReader(new InputStreamReader(new FileInputStream("output.txt")));
            String[] processName = {"javap", "-v" , "default class"};
            String lineddd = ifr.readLine();
            lineddd = ifr.readLine();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("output.txt"),true));
            //output.append("\n\n\nThe constant pool size for these classes respectively is:- \n");

            printWriter.append("\n\n**************************** First Task *****************************\n\n");
            printWriter.append("These are the pool sizes of the classes present in the jar files.\n");
            for(int i=0;i<lineCount;i++){

                processName[2] = ifr.readLine();
                Process p1 = r.exec(processName);
                reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                String line= reader.readLine();
                int index;
                while( ( index= line.indexOf("Constant pool:") )== (-1) ) line = reader.readLine();
                
                int count = 0;
                line = reader.readLine();
                while( (index = line.indexOf("#")) != -1 ) {
                    count++;
                    line = reader.readLine();
                }

                constantpoolSize[i] = count;
                printWriter.append(constantpoolSize[i] + "\n");
            }
            float average = 0;
            for(int i=0;i<lineCount;i++){
                average = average + constantpoolSize[i];
            }average = average/lineCount;
            printWriter.append("\nThe average pool size of the classes is " + average);
            int min = 999999;
            for(int i=0;i<lineCount;i++){
                if (min > constantpoolSize[i] ){
                    min = constantpoolSize[i];
                }
            }
            printWriter.append("\nThe minimum pool size of the classes is " + min);

            int max = -1;
            for(int i=0;i<lineCount;i++){
                if (max < constantpoolSize[i] ){
                    max = constantpoolSize[i];
                }
            }
            printWriter.append("\nThe maximum pool size of the classes is " + max);

            double standardDev = std(constantpoolSize , average);

            printWriter.append("\nThe standard deviation of constant pool sizes of the classes is " + standardDev);

            printWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    // method to perform task 2 i.e. printing top 50 instructions in JVM

    public static void javapInst2(int lineCount) {
        BufferedReader reader;
        BufferedReader ifr;
        Runtime r = Runtime.getRuntime();
        try{
            ifr = new BufferedReader(new InputStreamReader(new FileInputStream("output.txt")));
            Map<String, Integer> hmap= new HashMap<String, Integer>();
            String[] processName = {"javap", "-c", "-p" , "default class"};
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("output.txt"),true));

            printWriter.append("\n\n*************************** Second Task ******************************* \n\n");
            printWriter.append("The top 50 JVM instruction and their frequencies are: \n\n");
            
            String path_class;
            path_class =  ifr.readLine();
            path_class =  ifr.readLine();
            for (int i=0;i<lineCount;i++){
                processName[3] = ifr.readLine();
                Process p = r.exec(processName);

                reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line_read = reader.readLine().trim();
                int check=0;
                int index;
                while (line_read!= null){
                    line_read = line_read.trim();
                    if (line_read.isEmpty()){
                        check=0;
                    }
                    int index2= line_read.indexOf(":");
                    if (check==1 && index2!=-1){
                        String arr[]= line_read.split(" ");
                        if (hmap.containsKey(arr[1])){
                            int count= hmap.get(arr[1]);
                            count++;
                            hmap.put(arr[1],count);
                        }
                        else{
                            hmap.put(arr[1],1);
                        }
                    }
                    index= line_read.indexOf("Code:");
                    if (index!=-1)
                        check=1;

                    line_read= reader.readLine();
                }
            }

            Set<Entry<String, Integer>> entries = hmap.entrySet();
            Comparator<Entry<String, Integer>> comp = new Comparator<Entry<String,Integer>>() {
                @Override
                public int compare(Entry<String, Integer> m1, Entry<String, Integer> m2) {
                int v1 = m1.getValue();
                int v2 = m2.getValue();
                return v2-v1;
                }
            };
            List<Entry<String, Integer>> entrylist= new ArrayList<Entry<String, Integer>>(entries);
            Collections.sort(entrylist, comp);
            
            
            int c=0;
            for(Entry<String, Integer> entry : entrylist){
                c++;
                printWriter.append(entry.getKey() + " : " + entry.getValue() + "\n");
                if (c==50)
                    break;
            }
            printWriter.close();
            
        }
        catch (IOException e){
            System.out.println(e.getMessage()); 
        }
    }

    // methods to perform task 3 ie. list the average number of instruction in jar file.

    public static void javapInst3(int lineCount) {
        BufferedReader ifr;
        BufferedReader reader;
        int[] numMethods = new int[lineCount];
        Runtime r = Runtime.getRuntime();
        try{
            ifr = new BufferedReader(new InputStreamReader(new FileInputStream("output.txt")));
            String[] processName = {"javap", "-p" , "default class"};
            String lineddd = ifr.readLine();
            lineddd = ifr.readLine();
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File("output.txt"),true));
            printWriter.append("\n\n*************************** Third Task ***********************************\n\n");
            for(int i=0;i<lineCount;i++){
                processName[2] = ifr.readLine();
                Process p1 = r.exec(processName);
                reader = new BufferedReader(new InputStreamReader(p1.getInputStream()));
                String line= reader.readLine();
                int index;
                int numberOFmethods = 0;
                while(line != null) {
                    if ( (index = line.indexOf("(")) == (-1) && ( index = line.indexOf(")") ) == (-1)) {
                        line = reader.readLine();
                        continue;
                    }
                    numberOFmethods++;
                    line = reader.readLine();
                }
                
                numMethods[i] = numberOFmethods;
                //System.out.println(numberOFmethods);
            }
            float average = 0;
            for(int i=0;i<lineCount;i++){
                average = average + numMethods[i];
            }
            average = average/lineCount;
            printWriter.append("\nThe average number of methods in all classes is " + average);
            printWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
        }
    }

    // Main function to invoke all the other functions

    public static void main(String[] args) {
        String url = args[0];
        try {
            downloadUsingNIO(url , "/home/rishabh/Desktop/Paradime assignment/2016csb1054_assingment3/jarfiles.jar");

            
        } catch (IOException e) {
            e.printStackTrace();
        }

        int lineCount = extract_jar();
        javapInst(lineCount );
        javapInst2(lineCount );
        javapInst3(lineCount );
    }

}