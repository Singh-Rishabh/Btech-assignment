# Import statements
import sys
import commands

# Give user options to choose query
print("Please enter 1 to run Query1 or 2 to run Query2 and 3 to relod the : ")
option = input();
# print option

# if user choose option 1 then run query 1
if(option == 1):
	print ("Enter the Software App id: ")
	id = input()
	x = commands.getoutput('echo "findall(M,query1(' + str(id) + ',M),Output_machineIDs)." | swipl -q -f facts_and_rules.pl')
	print x
# if user choose option 2 then run query 2
elif(option == 2):
	print ("Enter the Software App id: ")
	sid = input()
	print ("Enter the Machine id: ")
	mid = input()
	x = commands.getoutput('echo "query2(' + str(sid) + ',' + str(mid) + ')." | swipl -q -f facts_and_rules.pl')
	if (x[0] == 't'):
		print("Yes Software App with id "+ str(sid) + " can run on machine with id " + str(mid) + ".")
	else :
		print("No Software App with id "+ str(sid) + " cannot run on machine with id " + str(mid) + ".")	
