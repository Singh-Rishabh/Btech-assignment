# Import statements
import yaml
import sys
import time
from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from subprocess import call

# Function to call after there is change in machine_info.yaml file
def fun1():
	print ("starting")
	with open("machine_info_yaml/machine_info.yaml", 'r') as stream:
		s = stream.read().replace('\t', '    ')		# replacing the \t with four spaces
		# print s
		try:	
			data = yaml.load(s)						# loding the yaml file to make list of dictionary

			# print(yaml.load(s))
		except yaml.YAMLError as exc:
			print(exc)
	stream.close()


	# print data[0];
	# if 'OS' in data[0]:
	# 	print data[0]	['OS']


	with open("facts_and_rules.pl", 'wb') as stream:
		# for loop to enter the facts in .pl file
		for i in range(len(data)):
			temp = data[i]
			if 'OS' in temp:

				arch = temp['OS']['arch'].split(" ")
				arch = int(arch[0])
				string = "isOS([" + str(temp['OS']['id']) + "], '" + str(temp['OS']['name']) + "', "  + str(temp['OS']['version']) + ", "  + str(arch) + ", "  + str(temp['OS']['limits']['max_open_files']) + ", "  + str(temp['OS']['limits']['max_connections']) + ", "  + str(temp['OS']['provides_libs']) + ")."
				# print "OS :\t" + str(temp['OS']['id']) + ", " + str(temp['OS']['name']) + ", "  + str(temp['OS']['version']) + ", "  + str(arch) + ", "  + str(temp['OS']['limits']['max_open_files']) + ", "  + str(temp['OS']['limits']['max_connections']) + ", "  + str(temp['OS']['provides_libs'])
			elif 'Machine' in temp:	
				ram = temp['Machine']['RAM'].split(" ")			# converting ram units in MB
				if (ram[1] == "GB"):
					ram = int(ram[0])*1024
				else:
					ram = int(ram[0])
				# ram = str(ram) + " MB"
				disk = temp['Machine']['disk'].split(" ")		# converting disk units in GB
				if (disk[1] == "TB"):
					disk = int(disk[0])*1024
				else:
					disk = int(disk[0])
				cores = temp['Machine']['CPU'].split(" ")
				cores = int(cores[0])
				# disk = str(disk) + " GB"
				string = "isMachine(" + str(temp['Machine']['id']) + ", '" + str(temp['Machine']['type']) + "', [" + str(temp['Machine']['OS']) + "], " + str(ram) + ", " + str(disk) + ", " + str(cores) + ")."
				# print "Machine :\t" + str(temp['Machine']['id']) + ", " + str(temp['Machine']['type']) + ", " + str(temp['Machine']['OS']) + ", " + str(ram) + ", " + str(disk) + ", " + str(cores) 
			elif 'SoftwareApp' in temp:
				ram = temp['SoftwareApp']['requires_hardware']['min_RAM'].split(" ")
				if (ram[1] == "GB"):
					ram = int(ram[0])*1024
				else:
					ram = int(ram[0])
				# ram = str(ram) + " MB"
				disk = temp['SoftwareApp']['requires_hardware']['min_disk'].split(" ")
				if (disk[1] == "TB"):
					disk = int(disk[0])*1024
				else:
					disk = int(disk[0])
				# disk = str(disk) + " GB"
				cores = temp['SoftwareApp']['requires_hardware']['min_CPU'].split(" ")
				cores = int(cores[0])
				string = "isSoftwareApp(" + str(temp['SoftwareApp']['id']) + ", '" + str(temp['SoftwareApp']['name']) + "', " + str(ram) + ", " + str(disk) + ", " + str(cores) + ", " +  str(temp['SoftwareApp']['requires_software']['OS']) + ", " + str(temp['SoftwareApp']['requires_software']['libs']) + ")." 
				# print "SoftwareApp :\t" + str(temp['SoftwareApp']['id']) + ", " + str(temp['SoftwareApp']['name']) + ", " + str(ram) + ", " + str(ram) + ", " + str(cores) + ", " +  str(temp['SoftwareApp']['requires_software']['OS']) + ", " + str(temp['SoftwareApp']['requires_software']['libs'])
			# print string
			stream.write(string + "\n")
		# writing the rules in .pl file
		stream.write("\n")
		stream.write("contained_in(L1, L2) :- maplist(contains(L2), L1). \ncontains(L, X) :- member(X, L).\n\n")
		stream.write('''query1(Id,M) :-
		isSoftwareApp(Id,_,B,C,D,E,F),
		isMachine(M,_,O,P,Q,R),
		P>=B,
		Q>=C,
		R>=D,
		contained_in(O,E),
		isOS(O,_,_,_,_,_,Y),
		contained_in(F,Y).\n\n''')
		stream.write('''query2(Id1,Id2) :- 
		isSoftwareApp(Id1,_,B,C,D,E,F),
		isMachine(Id2,_,O,P,Q,R),
		P>=B,
		Q>=C,
		R>=D,
		contained_in(O,E),
		isOS(O,_,_,_,_,_,Y),
		contained_in(F,Y).''')
		stream.close()
# from subprocess import call
# call(["swipl", "-s" , "facts_and_rules.pl"])
	

# making the warchDog handler class
class MyHandler(FileSystemEventHandler):
    def on_modified(self, event):
    	fun1()
        print "machine_info.yaml changed. Reloding the facts_and_rules.pl file."


# Running and calling the watchdogHandler class
if __name__ == "__main__":
	print ("Running the facts_gen.py")
	fun1()
	event_handler = MyHandler()
	observer = Observer()
	observer.schedule(event_handler, path='./machine_info_yaml', recursive=False)
	observer.start()

	try:
		while True:
			time.sleep(1)
	except KeyboardInterrupt:
		observer.stop()
	observer.join()
