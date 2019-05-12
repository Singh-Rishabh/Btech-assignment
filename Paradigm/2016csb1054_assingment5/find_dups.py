# 	Import Beautiful Soup
from bs4 import BeautifulSoup			# Using beautiful soup for parsing html files
import requests				
import sys								# Taking arguments form command line

# Store File name from command line arguments and check 

if (len(sys.argv)<=1):
	print("Usuage: python find_dups.py <filename>")
	exit()
filename = sys.argv[1]

# opening a file to perform parsing
with open(filename) as fp:
	line_no=1					# variable to store line numbers for a link
	string=[]					# store the necessaty details for printing in console
	a_list = []					# list to store the Link fields
	href_list = []				# stores the href of the link
	name_list = []				# list to store the Data in the link
	check_list = []				# list to check if a link a dublicate
	lineNoOfLink = []			# store the line number of link
	for line in fp:
		soup = BeautifulSoup(line,'html.parser')		# open the soup for parsing the file line by line
		for link in soup.find_all('a'):
			check_link = 0
			index = 0
			store_indeex = -1
			for abcd in href_list:						
				if (abcd == link.get('href')):
					check_link = 1
					store_indeex = index
					break
				index = index +1
			if (check_link == 0):						# populate the chech_list to check for dublicates
				check_list.append(0)
				
			else:										
				check_list.append(1)
				check_list[store_indeex] = 2
			a_list.append(link)							# populating the a_list to store the link field
			lineNoOfLink.append(line_no)				# store the line number of the link
			href_list.append(link.get('href'))			# populate the href_list
			name_list.append(link.get_text())			# populate the name _list
			
		line_no=line_no+1
	
fp.close()

num_dup = 0
inc = 0
for check in check_list:								# for loop to store the details to be printed on the console related to code
	if (check == 1 or check == 2):
		num_dup = num_dup +1
		data = str(num_dup) + ". " +  href_list[inc] + " \"" +  name_list[inc] + "\" at line " + str(lineNoOfLink[inc])
		string.append(data)
	inc = inc + 1

print("Found " + str(num_dup) +  " duplicates:")		# printing the duplicates
for line in string:
	print("\t" + line)

# printing the necessary details
print("Select hyperlinks that you want to keep.")		
print("\tEnter A to keep all, OR")
print("\tEnter F to keep the first one in a set of duplicates, OR")
print("\tEnter the serial numbers (separated by commas) of the links to keep.")
print("\n")
selection = str()
selection = raw_input('Your Selection: ')				# take user argument to perform the delte options

if (selection == "A"):									# if selection is A then it simply copy and paste the html file in output.html.dedup
	with open(filename) as fp:
	    with open("output.html.dedup", "w") as f1:
	        for line in fp:
	            f1.write(line)
	print("Removed 0 hyperlinks. Output file written to chrome_bookmarks.html.dedup")            
	exit()
elif (selection == "F"):								# if selection is F then it leave the first occurences of the duplicate link and delete all the leftover
	with open(filename) as fp:
		soup = BeautifulSoup(fp,'html.parser')
		# href_list = []
		reoved_links = 0
		inc = 0
		for link in soup.find_all('a'):
			if (check_list[inc] == 1):
				link.decompose()
				reoved_links = reoved_links + 1
			inc = inc + 1
		with open("output.html.dedup", "wb") as file:
			file.write(str(soup.prettify("utf-8")))
		file.close()
	print("Removed "+ str(reoved_links) + " hyperlinks. Output file written to chrome_bookmarks.html.dedup")       
	fp.close()
	exit()
else:													# if selection is list seperated by comma then it leave those links and delete all the left
	data = selection.split(",") 						# split string into a list
	if(len(data) == 0):
		print("No links removed, Please enter a valid output")
	for temp in data:									# checking the user input 
		if(int(temp) > num_dup):
			print("please enter valid numbers\n")
			exit()
	with open(filename) as fp:							# open the file to delete the the links
		soup = BeautifulSoup(fp,'html.parser')
		count = 0
		removed_links = 0
		inc = 0
		for link in soup.find_all('a'):
			check = 0
			if(check_list[inc] != 0):
				count = count + 1
				for linknotToDelete in data:
					if (count == int(linknotToDelete)):
						check = 1
						a_list[count-1] = "x"
						break
		inc = 0
		for check in check_list:
			if (check == 0):
				a_list[inc] = "x"
			inc = inc + 1
	fp.close()
	with open(filename) as fp:							# writing the utput in the file
		reoved_links = 0
		soup = BeautifulSoup(fp,'html.parser')
		index = 0
		for link in soup.find_all('a'):
			if (a_list[index] != "x"):
				link.decompose()
				reoved_links  = reoved_links + 1
			index = index +1
		with open("output.html.dedup", "wb") as file:
			file.write(str(soup.prettify("utf-8")))
		file.close()
	fp.close()
	print("Removed "+ str(reoved_links) + " hyperlinks. Output file written to chrome_bookmarks.html.dedup")       
	exit()
		
