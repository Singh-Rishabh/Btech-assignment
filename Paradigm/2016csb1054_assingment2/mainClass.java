// main class written by user who have only ascess to different interface.

import java.io.*; 
public class mainClass{
	public static void main(String[] args) throws IOException{
		abstractFactory absfac = FactoryProducer.getFactory("SORT");
		int arr[] = {5,4,3,8,1,10,7,15,14,6};

		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
		System.out.print("Enter the name of sorting algorithm to sort an array : ");  
      	String sorting_algo = br.readLine();  

      	System.out.println("");

		sortInterface s1 = absfac.getAlgo(sorting_algo);
		s1.printArray(arr);
		s1.sort(arr);
		s1.printArray(arr);

	}
}