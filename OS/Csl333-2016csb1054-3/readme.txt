/*-----------------------

	Rishabh Singh
	2016csb1054
	Assignemnt 3
	CSL-333

-----------------------*/

Sample Run :

	1. Compile the programme by 

		$ javac Main.java

	3. Run the programme by 

		$ java Main <input_csv_file>

		Example Run :  $ java Main test.csv

					   $ java Main input.csv


	Sample Output.

		1. 
			Using input from test.csv
			System State: Deadlocked. Processses: P1 P2 Resources: R5

		2.
			Using input from input.csv
			System State: Deadlocked. Processses: P1 P3 Resources: R4



	Sample csv file:

		In csv file first line should show the availabe resources only, and then
		next line shows the details of each processes. The details contains allocated
		and requested resources seperated by comma.

		A sample reference is shown below.

			1,3,2,3,2,1								 --->> Available Resources
			0,2,1,0,2,1,2,0,1,0,6,4					|
			1,1,2,1,0,0,3,2,1,2,5,0					|
			2,0,0,0,2,0,0,1,0,0,1,0					|--->> Processes Details.
			1,0,0,2,0,2,0,3,0,0,2,1					|
			1,0,1,0,0,2,1,0,2,0,0,2 				|

Working : 

	I simply implemented the banker's algorithm, a resource allocation and deadlocak 
	detection algorithm. I used the following data structures.
		An avaliabe vector.
		A requested matrix.
		An allocated matrix.
		A HashSet to store the processes which are safe i.e. they can be completed without deadlock of resources.
