'''  Import Statements '''
from math import sqrt
from math import exp
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
	x_coordinate = []
	y_coordinate = []
	with open(filename) as fp:
		line = fp.readline().split()
		# print (line )
		# .
		x_coordinate = [float(i) for i in line]
		line = fp.readline().split()
		y_coordinate = [float(i) for i in line]
	coordinate = []
	for i in range(len(x_coordinate)):
		coordinate.append((x_coordinate[i],y_coordinate[i]))
	return coordinate

''' Function to calculate distance between two points '''
def cal_dist(point1 , point2):
	return sqrt( (point2[0] - point1[0])**2 + (point2[1] - point1[1])**2 ) 


''' Function to calculate total distance between cities '''
def cal_total_distance(coordinate):
	total_distance = 0
	for i in range(0, len(coordinate) - 1):
		total_distance = total_distance + cal_dist(coordinate[i] , coordinate[i+1])
	total_distance += cal_dist(coordinate[len(coordinate) -1 ], coordinate[0])
	return total_distance

''' Function to perform two opt swap. here i and j are the index between which we swap '''
def twoOptSwap(coordinate, i , j):
	t_coordinate = []
	n = len(coordinate)
	for t_i in range(i):
		t_coordinate.append(coordinate[t_i])
	for t_i in range(i,j+1):
		t_coordinate.append(coordinate[j - (t_i - i)])
	for t_i in range(j+1,n):
		t_coordinate.append(coordinate[t_i])
	return t_coordinate


'''  Simulated Anneling search function '''
def SimulatedAnneling(coordinate):
	''' Calculate Total distance between cities '''
	total_distance = cal_total_distance(coordinate)
	n = len(coordinate)
	print ('Path:')
	Temp = 1000
	''' Loop through till Temperature becomes zero '''
	while (1):
		print (coordinate)
		print("Length = " + str(total_distance)+ "\n")
		if (Temp <= 0):
			print( 'End of Simulated annealing')
			return
		i , j = 0,0
		while (i == j):
			i = random.randint(0,n-1)
			j = random.randint(0,n-1)
			if (i > j):
				i,j = j,i
		# print (i,j)
		t_coordinate = twoOptSwap(coordinate,i,j)
		temp_distance = cal_total_distance(t_coordinate)
		
		if (temp_distance < total_distance):
			total_distance = temp_distance
			coordinate = t_coordinate
		else :
			prob = exp((total_distance - temp_distance)/Temp)
			choose = random.random()
			# print (prob , choose)
			if (choose <= prob):
				total_distance = temp_distance
				coordinate = t_coordinate
		Temp = Temp - 50
		


if (len(sys.argv) != 2):
	print("Error !! Please Enter only the filename as command line arguments")
else :
	filename = sys.argv[1]	
	coordinate = read_coordinates(filename)
	SimulatedAnneling(coordinate)
