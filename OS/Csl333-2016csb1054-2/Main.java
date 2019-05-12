// import Stateents

import dev.*;
import java.util.*; 


// Main class
public class Main{  

	// public Variables of Main class...
	public static HashMap<Long,Long>unique_number_set = new HashMap<Long,Long>();
	public static Queue<Long> unique_number_queue = new LinkedList<Long>() ;
	public static Queue<Long> unique_number_queue_copy = new LinkedList<Long>() ;
	public static int number_thread = 10;
	public static String device_name = "Real";
	public static int compute_delay = 3 ;
	public static int boot_delay = 250 ;
	public static boolean done = false;
	public static int n = 0;

	// Main function.......
	public static void main(String args[]){

		// unique number queue....
		unique_number_queue.add(Long.valueOf(2));
		unique_number_set.put(Long.valueOf(2),Long.valueOf(1) );
		if ( args.length <= 2){
			System.out.println("Invalid Usage");
			return;
		}
		
		// Nth unique number.
		n = Integer.parseInt(args[0]);
		
		// Number of Thread.
		int number_thread = Integer.parseInt(args[1]);
		
		device_name = args[2];
		// System.out.println(device_name);
		if ( !device_name.equals("real") && !device_name.equals("unreal") ){
			System.out.println("Wrong devide name !! Exiting");
			return;
		}
		
		if ( args.length >= 4 ){
			compute_delay = Integer.parseInt(args[3]);
		}
		
		if ( args.length >= 5 ){
			boot_delay = Integer.parseInt(args[4]);
		}
		
		long start_time = System.currentTimeMillis();
		
		// Creating the threads.
		myRunnable myThread[] = new myRunnable[number_thread];
		Thread thread[] = new Thread[number_thread];
		for (int i=0 ; i<number_thread ; i++){
			myThread[i] = new myRunnable();
			thread[i] = new Thread(myThread[i]);
			thread[i].start();
		}

		// Wating for joining the Thread.
		for (int i=0 ; i<number_thread ; i++){
			try{
				thread[i].join();
			}catch(Exception e){
				System.out.println("Exception in joining in Thread");
			}
		}

		boolean isreal = false;
		if (device_name.equals("real")){
			isreal = true;
		}

		System.out.println("Result Summary\n");
		System.out.println("Target Count (n).............: " + n);
		System.out.println("Number of Threads............: " + number_thread);
		System.out.println("Usead real Device............: " + isreal);
		System.out.println("Time taken ..................: " + (System.currentTimeMillis() - start_time)/2000.0);

		long number = -1;
		for (Map.Entry<Long,Long> entry : Main.unique_number_set.entrySet()){
			if( entry.getValue() == Main.n -1 ){
				number = entry.getKey();
				// System.out.println();
				break;
			}
		}

		System.out.println("Resulting Number.............: " + number);
		System.out.println("Device invoked (approx)......: " + myRunnable.num_of_times_device_invoked);
		


	}	
	
}