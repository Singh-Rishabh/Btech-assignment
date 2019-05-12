// Class to implement Bubble Sort
class BubbleSort implements sortInterface
{   
    // Sort function to sort the given array. This function is an interface function defined in the interface sortInterface.
    public void sort(int arr[])
    {
        int n = arr.length;
        System.out.println("Performing BubbleSort on the given array");
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j] > arr[j+1])
                {
                    // swap temp and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }

    // printArray function to print the given array. This function is also an interface function defined in the interface sortInterface.
    public void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
 
    
}