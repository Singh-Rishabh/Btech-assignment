// Creating the factory classes that inherit AbstractFactory class to generate the object of concrete class based on given information.
package algo;
public class alogFactory extends abstractFactory{
	public sortInterface getAlgo(String algoname){
		if (algoname == null) {
			return null;
		}
		if (algoname.equalsIgnoreCase("BUBBLE-SORT")){
			return new BubbleSort();
		} 
		else if (algoname.equalsIgnoreCase("INSERTION-SORT")){
			return new InsertionSort();
		} 
		else if (algoname.equalsIgnoreCase("SELECTION-SORT")){
			return new SelectionSort();
		} 
		System.out.println("Please Enter Valid arguments\n");
		return null;
	}
}