// Class to implement Bubble Sort
package algo;
class SelectionSort implements sortInterface
{

     // Sort function to sort the given array. This function is an interface function defined in the interface sortInterface.
    public void sort(int arr[])
    {
        int n = arr.length;
        System.out.println("Performing SelectionSort on the given array");
 
        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min_idx])
                    min_idx = j;
 
            // Swap the found minimum element with the first
            // element
            int temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
    }
 
    // printArray function to print the given array. This function is also an interface function defined in the interface sortInterface.
    public void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
 
    
}