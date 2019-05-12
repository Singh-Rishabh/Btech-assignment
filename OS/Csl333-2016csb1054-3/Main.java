// import Stateents

import java.io.*;
import java.util.*; 


// Main class
public class Main{  

	// Utility function to print array 
	public static void print_Arr(String[] a){
		for (int i=0;i<a.length; i++){
			System.out.print(a[i]);
			System.out.print(", ");

		}
		System.out.print("\n");
	}

	// Utility function to check if avaliable is greater or smaler than requested function.
	public static boolean is_small(Vector<Integer> available, Vector<Integer> requested){
		boolean out = true;
		for (int i=0;i<available.size(); i++){
			if (available.elementAt(i) < requested.elementAt(i + available.size() ) ){
				out = false;
				break;
			}
		}
		return out;
	}
	
	// Main function.......
	public static void main(String args[])  throws IOException{	

		BufferedReader reader;
		// System.out.println("args " + args[0]);
		if (args.length == 0 ){
			System.out.println("Invalid Arguments... Returning ");
			return;
		}
		System.out.println("\nUsing input from " + args[0] );

		Vector<Integer> available  = new Vector<Integer>(); 
		Vector<Vector<Integer>> process_data  = new Vector<Vector<Integer>>(); 

		try {
			reader = new BufferedReader(new FileReader(
					args[0]));
			String line = reader.readLine();
			int count = 0;

			//  Reading line by line of csv file
			while (line != null) {
				line = line.replace("{", "");
				line = line.replace("}", "");

				String[] temp = line.split(",");
				
				count += 1;
				if (count == 1){
					for (String ele : temp){
						available.add(Integer.parseInt(ele));
					}

				}else {
					Vector<Integer> v = new Vector<Integer>();
					for (String ele : temp){
						v.add(Integer.parseInt(ele));
					}
					process_data.add(v);
				}
				
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("Cannont Find the File. Exiting\n");
			return;
		}

		HashSet<Integer> safe_process = new HashSet<Integer>();
		boolean deadLock;

		while(true){
			int count = 0;
			for (int i=0;i<process_data.size(); i++){
				if (!safe_process.contains(i) && is_small(available,process_data.elementAt(i))){
					safe_process.add(i);
					for (int k = 0; k< available.size(); k++){
						available.set(k ,available.elementAt(k) + process_data.elementAt(i).elementAt(k) ) ;
					}
					count = count + 1;
					
				}
			}
			if (count == 0){
				deadLock = true;
				break;
			}
			if (safe_process.size() == process_data.size() ){
				deadLock = false;
				break;
			}
		}

		if (deadLock){

			HashSet<String> resources = new HashSet<String>();
			Vector<String> processes = new Vector<String>();

			for (int i = 0; i<process_data.size() ; i++){
				if (!safe_process.contains(i) ){
					processes.add("P" + Integer.toString(i+1) );

					for (int k=0;k<available.size(); k++){
						int t = available.elementAt(k) - process_data.elementAt(i).elementAt(k + available.size());
						if (t < 0 ){
							resources.add("R" + Integer.toString(k+1) );
						}
					}

				}
				
			}

			System.out.print("System State: Deadlocked. Processses: ");

			Iterator<String> j = processes.iterator();
	        while (j.hasNext()) 
	            System.out.print(j.next() + " ");

	        System.out.print("Resources: ");

			Iterator<String> i = resources.iterator(); 
	        while (i.hasNext()) 
	            System.out.print(i.next() + " "); 

	        System.out.print("\n\n");
		}
		else {
			System.out.println("System State: Safe\n");
		}

	}	
}