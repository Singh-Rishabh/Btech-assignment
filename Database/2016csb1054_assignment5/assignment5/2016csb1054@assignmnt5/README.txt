Submitter name: Rishabh singh

Entry No.: 2016CSB1060

Course: CSL-310

=================================
--------------+
1. Sample Run |
--------------+
	At first you need to create the database So open mysql and run the commands.
	
		Opening mysql ---------------------------  $ mysql -u <username> -p<password>
		
		Running the database schema -------------  $ source database.sql
		
	Then you need to compile the programme ie java files...
		
		To compile the java programme -----------  $ javac -cp .:./lib/* ./assignment5/*.java
		
		Now run the programme in insert mode ----  $ java -cp .:./lib/* assignment5.Assignment5 -i
		
		Now run the programme in the query mode -  $ java -cp .:./lib/* assignment5.Assignment5 -q
		
		Note *** Please chane the username and password in the Persistence file.
	
--------------+
2. Files	: |
--------------+
	
	-> Assignment5.java 	: Contains the main function and all the related function to produce random Data.
	-> ContactInfo.java		: Entity class From the Databse Team of table contact_info.
	-> Person.java			: Entity class From the Databse Team of table person.
	-> Team.java			: Entity class From the Databse Team of table team.
	-> TeamMember.java 		: Entity class From the Databse Team of table team_member.
	-> randDate.java		: Class to produce the random date.
	

-----------------------------+
3. What does this program do |
-----------------------------+

	At first this programme enter random but swnsible data in the Team schema.
	It creates 20 teams and insert members into these teams...
		The Role of the team member are among the following list
			Players			10-15
			Managers		2-5
			Owners			2-5
			Others			0-4
	When programme run in -q then it ask user to enter the queries detail and it will give the result of Query.
		The Three queries are : 
			: All “Players” of a given Team whose salary is between a given range.
			: Role wise numbers of team members in a given team.
			: Average salary of players from a given state.


--------------------------+
4. Logic of the programme |
--------------------------+

	Insertion PArt: 
		There are two files named randAdd.txt and randNames.txt which Provide the names of the random people and addess of them
		With the help of these two files it Generates the random data.
		For creating random date it used randDAte.java 
	
	Query Part: 
		I have used named query to do all the query from the database.
		

---------------+
4. Assumptions |
---------------+
	1. Please insert into database only once to get the correct output.
	
	2. The user have to provide the mysql username and password in the persistence.xml file in the "meta_inf" Folder
	
	3. The team names of the teams are 
		Team1, Team2, Team3 ... Team20
		
	4. The City State names are listed in the randomAdd.txt file. 
		Some of the sample state names are listed below 
		Hp
		Up
		Punjab
		Ap
		
	5. Salary of team member are in 1000 To 100000

	-i for inserting data
	-q for executing query

