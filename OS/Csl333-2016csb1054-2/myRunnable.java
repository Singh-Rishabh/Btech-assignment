// Import Statement
import dev.*;
import java.util.*; 

// Implementation of the class Runnable.
public class myRunnable implements Runnable{

	public static long num_of_times_device_invoked = 0;

	// implementation of the run variable.
	@Override
	public void run(){
		int endLoop = Device.MULTIPLIERS.length;

		// initialise the device variable.
		Device dev ;
		if (Main.device_name.equals("real")){
			RealDevice.DeviceConfig dc = new RealDevice.DeviceConfig( Main.compute_delay, Main.boot_delay, 1);
			dev = new RealDevice(dc);
		}
		else {
			dev = new UnrealDevice(1234);
		}
		long nxtNumber = 0;

		// while loop till the execution of the threads.
		while (Main.done == false){
			long tmp_base = check_top();

			while(tmp_base == 0){
				tmp_base = check_top();
			}
			long base = tmp_base;

			if (Main.unique_number_set.size() >= Main.n){
				Main.done = true;
				break;
			}

			long[] tmp_arr = new long[endLoop];
			for (int i=0;i<endLoop ; i++){
				try{
					num_of_times_device_invoked = num_of_times_device_invoked + 1;
					nxtNumber = dev.f(base, i);
					tmp_arr[i] = nxtNumber;
				}catch(Exception e){
					System.out.println("HEloooldac");
				}
				
			}

			int tmp = remove_and_add(base,tmp_arr);
			while ( tmp == 0){
				tmp = remove_and_add(base,tmp_arr);
			}
			
			if (Main.unique_number_set.size() >= Main.n){
				Main.done = true;
				break;
			}
		}

		// num_of_times_device_invoked = dev.operationCount;

	}

	// function to synchronise the peeking of the queue.
	public synchronized long check_top(){
		long base = 0;
		synchronized(Main.unique_number_queue){
			if( Main.unique_number_queue.size() != 0){
				base = Main.unique_number_queue.peek();
				Main.unique_number_queue_copy.add(base);
				Main.unique_number_queue.remove();
				return base;
			}else {
				return 0;
			}
		}
	}

	// function to synchronise the emoval of numbers from the queue.
	public synchronized int remove_and_add(long base, long arr[]) {
		synchronized(Main.unique_number_queue){
			if (base == Main.unique_number_queue_copy.peek()){
	        	Main.unique_number_queue_copy.remove();
	        	for (int i = 0; i < Device.MULTIPLIERS.length; i++){
	        		if (Main.unique_number_set.containsKey( arr[i] ) == false){
	        			Main.unique_number_set.put(arr[i] , Long.valueOf(Main.unique_number_set.size()) );
	        			Main.unique_number_queue.add(arr[i]);
	        		}
	        	}
	        	Main.unique_number_queue.notifyAll();
	        	return 1;

	        }else {
				try{
					Main.unique_number_queue.wait();
				}catch(Exception e){

				}
				return 0;
			}
		}  
    }
}