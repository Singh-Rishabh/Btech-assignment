Submitter name: Shreyanshu Shekhar

Roll No.: 2016CSB1060

Course: CSL-310

=================================


-----------------------------+
1. What does this program do |
-----------------------------+

This program selects data randomly and insert into tables.
It creates 20 teams and add team members and creates their contacts.
Team members are:
	-> Players (10 to 15)
	-> Owners   (2 to 4)
	-> Managers (2 to 5)
	-> Others   (0 to 4)s
Executes hard-coded queries.
	: All “Players” of a given Team whose salary is between a given range.
	: Role wise numbers of team members in a given team.
	: Average salary of players from a given state.

------------------------------------------------------------+
2. A description of how this program works (i.e. its logic) |
------------------------------------------------------------+

I have made ".txt" files, which contains the data of teams, team members and their contact information.
This code reads these ".txt" files and stores them string.
They are chosen randomly and stored in table.
First it creates a team and creates team members with their contact information.
Then it executes three queries as mentioned in the question, takes input from user and prints the output.

Functions:
	-> query1     : contains the execution part of query 1.
	-> query2     : contains the execution part of query 2.
	-> query3     : contains the execution part of query 3.
	-> saveEntity : inserts the set values in table.
	-> Read       : takes .txt files as argument and retruns a single string containing all the data of that .txt file.
	-> insert     : insert all the data into the tables.
	-> query      : takes input from user and calls query1, query2 and query3 functions.
	-> main       : according to argument -i or -q it calls the insert or query function respectively.

---------------------------------------+
3. How to compile and run this program |
---------------------------------------+

Before you compile you have to create database so run:

	mysql> source path_of_the_sql_file

TO COMPILE

	$ javac -cp .:./lib/* csl310_csb1060_assign_5/*.java


TO RUN

	$ java -cp .:./lib/* csl310_csb1060_assign_5.CSL310_csb1060_assign_5 -i


-------------------------

It is necessary to give class path and database URL to run otherwise it will show error or alert message.
After running it will ask the database user-name and password which must be provided by the user

-i for inserting data
-q for executing query

You have to give only one flag at a time.

---------------------------------------+
4. Provided a snapshot of a sample run |
---------------------------------------+

I have included sample run screen shot, you can check there.

