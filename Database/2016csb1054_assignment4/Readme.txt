Submitter name: Rishabh Singh
Roll No.:		2016csb1054
Course:			CSL-310
=================================

1. How to compile and run this program
	
	Sample Run : Assuming You are in the the Directory of the Java files

	Step 1: First Run the Sql Schema 
		Opening your mysql          ---------------------  mysql -u <username> -p<password>
		------------------------------------------------- source database.sql

	Step 2: Comping the code: 
				javac *.java
			Running the code with -i flag:
				java -cp .:./mysql-connector-java-5.1.46/mysql-connector-java-5.1.46.jar Main -i
			Running the code with -q flaG:
				java -cp .:./mysql-connector-java-5.1.46/mysql-connector-java-5.1.46.jar Main -q



2. What does this program do
	This programme first create a database named Bank.
	This database contain 4 tables named
		contact_info
		account_holder
		bank_account
		transaction

	Then this programme ask user to run with -i option, which insert random data in the database. 
	These random data are created with random function which are named as :
		getNames()
		generateRandDOB()
		generateRandOpeningDate()
		generateRandPanNumber()
		generateRandomCityStateCountry()

	Then the function named "insertInDatabase()" Runs which runs 4 Function each corresponding to populatig the four 
	tables in the database. 
		These four function are
			insertContactsInfo()			 Insert into contact_info Table
			insertAccountHolder()			 Insert into account_holder Table
			insertBankAccount()				 Insert into bank_account Table
			insertTransaction()				 Insert into transaction Table

	After inserting into database user runs the programme with -q option:
		With this he can see output of the three queries whicha are mentioned in the question.


Assumptions:
		1. The query 2nd and query 3rd do not assume a single year month. It assume that if month is 2 then it will only check for month 2nd in all the years.
		2. The user have provided the mysql username and password in the queries.properties File
		3. The account number for the users are from 1 to 50
		4. The City names are listed in the randomAdd.txt file. 
			Some of the sample city names are listed below 
				Varanasi
				Allhabad
				Jaunpur
				Gazibad
				Sunam
				Amritsar
				Ganganagar
				Dharmshala
				Brookings
				Aberdeen
				Charlottesville
				Norfolk

		5. The pan Number are randomy generated. 
				These looks something like this "AAAPL1C"

		6. I have inserted the data for transaction one month only so please enter date like (It may take time to run so please have patience)
			2018/04/01
			2018/04/10
			
		7. Please do not run the code with -i two times as it may result in exception, Due to same Pan number generation( As pan number are unique).



	
