/*-----------------------

	Rishabh Singh
	2016csb1054
	Assignemnt 2
	CSL-333

-----------------------*/

Sample Run :

	1. Dowlnoad the tar file and then extact it in suitable folder.

	2. Compile the programme by 

		$ javac Main.java

	3. Run the programme by 

		$ java Main <Number-index> <Number-OF-Thread> <Device-Type> <Compute-Delay>* <Boot-Delay>*

		Example Run :  $ java Main 5000 100 real

					   $ java Main 5000 100 unreal

		* - Optional.


	Sample Output.

		Result Summary

		Target Count (n).............: 5000
		Number of Threads............: 100
		Usead real Device............: true
		Time taken ..................: 1.221
		Resulting Number.............: 1143957738
		Device invoked (approx)......: 18203

Working : 

	I simply made a queue to store the nth unique number and the set to disallow the
	repetition of the numbers. I kept pushing the numbers in queue. In the multi-threaded code
	The part of selecting the base and the part of inserting all the numbers in the queue map
	are the parts which we need to syncronized. As the number of threads increases the time 
	taken to find nth unique number decreases. But after some number of threads say 150 the 
	time increases becauses of additional overhead.

