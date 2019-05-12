'''  Import Statements '''
from math import sqrt
from copy import deepcopy
import sys
import random

'''  Function to open file and read inputs
	 The file should be of format 
	 x1 x2 ..... xn 
	 y1 y2 ..... yn 
	 where x1,x2,...xn are the x cordinates and 
	 y1,y2 ... yn are the y coordinates  
'''
def read_coordinates(filename):
	coordinate = []
	max_gen = 50
	rand_seed = 10
	with open(filename) as fp:
		line = fp.readline().split()
		max_gen = int(line[1])
		line = fp.readline().split()
		rand_seed = int(line[2])
		line = fp.readline().split()
		line = fp.readline().split()
		while (line):
			temp = [float(i) for i in line]
			coordinate.append((temp[1],temp[2]))
			line = fp.readline().split()
		return (max_gen, rand_seed, coordinate)

def create_population(NUM_POPULATION,coordinate,number_of_truck):
	population_list = []
	for i in range(NUM_POPULATION):
		random.shuffle(coordinate)
		for j in range(len(coordinate)):
			temp = random.randint(0,len(coordinate)-1)
			

		print ((coordinate))
		index = random.sample(range(len(coordinate)), number_of_truck-1)
		index.sort()
		population_list.append((coordinate,index))
	return population_list


filename = 'data1.txt'
NUM_POPULATION = 5
# print(read_coordinates(filename))
max_gen, rand_seed, coordinate = read_coordinates(filename)
# print max_gen
# print rand_seed
# print coordinate
random.seed(rand_seed)
number_of_truck = int(input("Enter the number of Truck : "))

population_list = create_population(NUM_POPULATION,coordinate,number_of_truck)
# print (population_list)
print ("\n\n")
for i in population_list:
	print (i)





