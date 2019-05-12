// Creating an abstract class to get factories for different groups of similar Object.
package algo;
public abstract class abstractFactory{
	abstract sortInterface getAlgo(String algoname);
}