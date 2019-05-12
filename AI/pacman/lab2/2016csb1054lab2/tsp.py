'''  Import Statements '''
from math import sqrt
from copy import deepcopy
import sys

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

'''  Hill climb search function '''
def TravellingSaleman(coordinate):
	total_distance = 0 
	''' Loop to calculate Total distance between cities '''
	for i in range(0, len(coordinate) - 1):
		total_distance = total_distance + cal_dist(coordinate[i] , coordinate[i+1])
	total_distance += cal_dist(coordinate[len(coordinate) -1 ], coordinate[0])

	n = len(coordinate)
	print ('Path:')
	''' Loop through till we are not stuck at local minima '''
	while (1):
		print (coordinate)
		# print ("\n")
		print("Length = " + str(total_distance)+ "\n")
		min_distance = total_distance
		swap_index1 = -1
		swap_index2 = -1
		''' Swaping and then calculating new distance '''
		for i in range(n - 1):
			for j in range(i+1 , n):
				t_coordinate = deepcopy(coordinate)
				t_coordinate[i],t_coordinate[j] = t_coordinate[j],t_coordinate[i]
				
				if (i == j-1):
					temp_distance = total_distance - cal_dist(coordinate[i], coordinate[i-1]) - cal_dist(coordinate[j], coordinate[(j+1)%n])
					temp_distance += cal_dist(t_coordinate[i], t_coordinate[i-1]) + cal_dist(t_coordinate[j], t_coordinate[(j+1)%n])
				elif (i == 0 and j == n - 1):
					temp_distance = total_distance - cal_dist(coordinate[i], coordinate[i+1]) - cal_dist(coordinate[j], coordinate[(j-1)%n])
					temp_distance += cal_dist(t_coordinate[i], t_coordinate[i+1]) + cal_dist(t_coordinate[j], t_coordinate[(j-1)%n])
				else :
					temp_distance = total_distance - cal_dist(coordinate[i], coordinate[i-1]) - cal_dist(coordinate[j], coordinate[(j+1)%n])
					temp_distance -= (cal_dist(coordinate[i], coordinate[i+1]) + cal_dist(coordinate[j],coordinate[j-1]) )
					temp_distance += cal_dist(t_coordinate[i], t_coordinate[i-1]) + cal_dist(t_coordinate[j], t_coordinate[(j+1)%n])
					temp_distance += (cal_dist(t_coordinate[i], t_coordinate[i+1]) + cal_dist(t_coordinate[j],t_coordinate[j-1]) )
				
				if (min_distance > temp_distance):
					min_distance = temp_distance
					swap_index1 = i
					swap_index2 = j
		if (min_distance == total_distance):
			print( 'End of hill climbing')
			# print (min_distance)
			return
		print ("Swap node at posttion " + str(swap_index1 + 1) + " and " + str(swap_index2 + 1) + "\n")
		total_distance = min_distance
		coordinate[swap_index1],coordinate[swap_index2] = coordinate[swap_index2],coordinate[swap_index1]



if (len(sys.argv) != 2):
	print("Error !! Please Enter only the filename as command line arguments")
else :
	filename = sys.argv[1]	
	coordinate = read_coordinates(filename)
	TravellingSaleman(coordinate)
