
# ------------ IMPORT STATEMENTS -----------
import numpy as np
import scipy.ndimage
from scipy import misc
import glob
import os
import re
from copy import deepcopy
import random
import math
import pickle
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties
import matplotlib.patheffects as path_effects
from matplotlib import rcParams
import sys

# ------------- Some Parameters for ploting graphs ----------

reload(sys)  
sys.setdefaultencoding('utf8')
# Font properties Change
rcParams['font.family'] = 'serif'
rcParams['font.sans-serif'] = ['Tahoma']
rcParams.update({'font.size':10})


#Function to do the numerical sorting of the input images in the directory
numbers = re.compile(r'(\d+)')
def numericalSort(value):
    parts = numbers.split(value)
    parts[1::2] = map(int, parts[1::2])
    return parts


# Neural network class 
class Neural_Network:
	alpha = 0.01						# Learning rate
	batch_size = 64						# Batch size
	max_num_itteration = 5000			# number of Iteration
	dropout_percentage = 0.0			# droupout percentage
	
	# constructor of the Neural Network class
	def __init__(self,a,bs,ni,dp ):
		self.alpha = a
		self.batch_size = bs
		self.max_num_itteration = ni
		self.dropout_percentage = dp
		self.initialise_weights()
		print ("\n*******************************\n")
		print ("Learning Rate = " + str (a) + " \nbatch_size = "+ str(bs) + " \nNumber of Itteration = " + str(ni) + "\ndropout_percentage = " + str(dp))
		print ("\n*******************************\n")
	
	# function to initialise the weights of the neural network
	def initialise_weights(self):
		self.W1 = np.random.random_sample((1025,512))* 0.2 - 0.1
		self.W2 = np.random.random_sample((513,64) )* 0.2 - 0.1
		self.W3 = np.random.random_sample((65,1) )*0.2 - 0.1
		for i in range(len(self.W1[0])):
			self.W1[0][i] = 0
		for i in range(len(self.W2[0])):
			self.W2[0][i] = 0
		for i in range(len(self.W3[0])):
			self.W3[0][i] = 0

		
		
	# function to calculate the sigmoid of any x
	def sigmoid(self, x):
		return scipy.special.expit(x)

	# function to remove the droup nodes.
	def mask(self,X,percentage):
		(n,m) = X.shape
		zeros = int(percentage*m/100)
		rand_list = random.sample(range(0,m), int(zeros))
		mask = np.ones((n,m))
		for i in rand_list:
			mask[:,i] = 0
		return np.multiply(X,mask)

	# function to perform forward pass
	def forward_pass(self,X):
		if (self.dropout_percentage >= 0.1):

			X = self.mask(X,self.dropout_percentage)

			self.A1 = self.sigmoid(np.dot(X , self.W1))
			A1= self.mask(self.A1,self.dropout_percentage)
			self.P1 = np.multiply(self.A1, 1- self.A1).T
			self.A1 = np.append(np.ones((len(self.A1),1)) , self.A1 , axis = 1)

			self.A2 = self.sigmoid(np.dot(self.A1,self.W2))
			A2 = self.mask(self.A2,self.dropout_percentage)
			self.P2 = np.multiply(self.A2, 1- self.A2).T
			self.A2 = np.append(np.ones((len(self.A2),1)) , self.A2 , axis = 1)
			self.A3 = np.dot(self.A2,self.W3)
		else :
			self.A1 = self.sigmoid(np.dot(X , self.W1))
			self.P1 = np.multiply(self.A1, 1- self.A1).T
			self.A1 = np.append(np.ones((len(self.A1),1)) , self.A1 , axis = 1)

			self.A2 = self.sigmoid(np.dot(self.A1,self.W2))
			self.P2 = np.multiply(self.A2, 1- self.A2).T
			self.A2 = np.append(np.ones((len(self.A2),1)) , self.A2 , axis = 1)

			self.A3 = np.dot(self.A2,self.W3)
			

	# function to perform backkward pass.
	def backward_pass(self, X, Y):
		dw3 = (self.A3 - Y).T
		dw2 = np.multiply( np.dot(self.W3[1:,:] , (self.A3 - Y).T ), self.P2 )
		dw1 = np.multiply((np.dot(self.W2[1:,:] , dw2)) , self.P1)
		self.W3 -= self.alpha*(np.dot(dw3, self.A2)).T
		self.W2 -= self.alpha*(np.dot(dw2, self.A1)).T
		self.W1 -= self.alpha*(np.dot(dw1, X)).T

	# function to calculate the error on the dataset.
	def error(self,Y,X):
		A1 = self.sigmoid(np.dot(X , self.W1))
		A1 = np.append(np.ones((len(A1),1)) ,A1 , axis = 1)
		A2 = self.sigmoid(np.dot(A1,self.W2))
		A2 = np.append(np.ones((len(A2),1)) , A2 , axis = 1)
		A3 = np.dot(A2,self.W3)
		return np.sum ( np.square( np.subtract ( A3 , Y ) ) )/(float(len(Y)))
	
	# function to do the traning of the neural network	
	def train_network(self,X,Y,X_test,Y_test):
		num_batches = math.floor( len(X)/self.batch_size)
		num_itter = self.max_num_itteration
		traning_error_arr = []
		test_error_arr = []
		for i in range(num_itter):
			for j in range(int(num_batches) ):
				batch_training_set = X[ j*self.batch_size : j*self.batch_size + self.batch_size ]
				batch_output_set = Y[j*self.batch_size : j*self.batch_size + self.batch_size]
				self.forward_pass(batch_training_set)
				self.backward_pass(batch_training_set,batch_output_set)

			traning_error_arr.append(self.error(Y,X))
			test_error_arr.append(self.error( Y_test, X_test ))
			if (i%10 == 0):
				print("itteration number " + str(i) + " error = " + str(traning_error_arr[i]) + " " + str(test_error_arr[i]))
		
		print(traning_error_arr)
		print(len(traning_error_arr))

		fig, ax = plt.subplots()
		ax.yaxis.grid(True)
		ax.set_axisbelow(True)
		number_itter_arr = range(num_itter)
		
		# ............... Ploting the graphs.......
		ax.minorticks_on()
		ax.tick_params(axis='x',which='minor',bottom='off')
		ax.yaxis.grid(True)
		ax.set_axisbelow(True)
		ax.plot(number_itter_arr,traning_error_arr,label = "Traning Error")
		ax.plot(number_itter_arr,test_error_arr,label = "Test Error")
		ax.set_xlabel(r"Number of Itteration",fontsize=15)
		ax.set_ylabel("Error",fontsize=15)
		ax.legend(loc="best")
		ax.set_title(r'Error vs Number of Itteration',fontweight= 'bold',fontsize=15)
		# plt.tight_layout()
		plt.savefig("e_vs_ni_" + str(self.alpha) + '_' + str(num_itter) + '_' + str(self.batch_size) + '_' + str(self.dropout_percentage) +".jpg", bbox_inches='tight')



###################### Pre Processing #########################

# function to read output from file
def read_file(file_name):
	output_arr = []
	with open(file_name) as fp:
		line = fp.readline()
		while (line):
			line = line.split()
			angle = float(line[1])
			output_arr.append(angle)
			line = fp.readline()

	output = np.asarray(output_arr,dtype = np.float32)
	output = output.reshape((len(output_arr) , 1))
	return output

# function to read the images and create the dataset
def read_images(path):
	dataset_carr = []
	arr = np.empty((), int)
	for filename in sorted(glob.glob(os.path.join(path, '*.jpg') ), key=numericalSort):
		i2 = misc.imread(filename, mode = 'L')
		i2 = i2.flatten()
		i2 = i2.reshape((1,len(i2)))
		dataset_carr.append(i2[0])

	dataset = np.array(dataset_carr)
	return dataset

# function to partision the dataset.
def partition_dataset(dataset,output_dataset,percentage):
	len_dataset = len(dataset)
	len_new_arr = int(percentage*float(len_dataset)/100)
	new_dataset = []
	new_dataset_output = []
	dataset_1 = []
	output_dataset_1 = []
	tmp_arr = []
	for i in range(len_new_arr):
		temp = random.randint(1,len_dataset-1)
		if temp in tmp_arr:
			while temp in tmp_arr:
				temp = random.randint(1,len_dataset-1)
		tmp_arr.append(temp)
	tmp_arr.sort()
	j = 0

	for i in range(len(dataset)):
		if (j>= len(tmp_arr)):
			dataset_1.append(deepcopy(dataset[i]))
			output_dataset_1.append(deepcopy(output_dataset[i]))
		elif (i == tmp_arr[j]):
			new_dataset.append(deepcopy(dataset[i]))
			new_dataset_output.append(deepcopy(output_dataset[i]))
			j += 1
		else :
			dataset_1.append(deepcopy(dataset[i]))
			output_dataset_1.append(deepcopy(output_dataset[i]))

	dataset_1 = np.array(dataset_1)
	output_dataset_1 = np.array(output_dataset_1)
	new_dataset = np.array(new_dataset)
	new_dataset_output = np.array(new_dataset_output)

	return dataset_1,output_dataset_1,new_dataset,new_dataset_output

#function to standardize traning set
def standardize_traininng_set(X):
	mean_arr = []
	std_arr = []
	for i in range(len(X[0])):
		# mean = [:,i].mean()
		mean_arr.append(X[:,i].mean())
		if (X[:,i].std() != 0):
			std_arr.append(X[:,i].std())
		else:
			std_arr.append(0.0000001)
		X[:,i] = (X[:,i] - mean_arr[i])/std_arr[i]

	return X,mean_arr,std_arr

# function to standardize the validation dataset.
def standardize_test_set(X,mean_arr,std_arr):
	for i in range(len(X[0])):
		X[:,i] = (X[:,i] - mean_arr[i])/std_arr[i]
	return X


####################################################################

# creating the dataset.
folder_name = './steering'
file_name = './steering/data.txt'
print("Reading dataset")
output_arr = read_file(file_name)
dataset = read_images(folder_name)

traning_dataset, traning_output, validation_dataset,validation_output = partition_dataset(dataset, output_arr, 20)
print traning_dataset.shape , traning_output.shape, validation_dataset.shape , validation_output.shape

traning_dataset,mean_arr,std_arr = standardize_traininng_set(traning_dataset)
validation_dataset = standardize_test_set(validation_dataset,mean_arr,std_arr)

traning_dataset = np.append( np.ones( (len(traning_dataset) , 1))  ,traning_dataset  , axis = 1)
validation_dataset = np.append( np.ones( (len(validation_dataset) , 1))  ,validation_dataset  , axis = 1)


# ##################### Traning the neural network #############################

# ----------- Experiment No 1 -------------
alpha = 0.01
batch_size = 64
num_itter = 5000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

# ----------- Experiment No 2 ------------

alpha = 0.01
batch_size = 32
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

alpha = 0.01
batch_size = 64
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

alpha = 0.01
batch_size = 128
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

# ------------ Experiment No 3 -------------
alpha = 0.001
batch_size = 64
num_itter = 1000
dropout_percentage = 50
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

# ----------- Experiment No 4 --------------
alpha = 0.005
batch_size = 64
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

alpha = 0.001
batch_size = 64
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)

alpha = 0.05
batch_size = 64
num_itter = 1000
dropout_percentage = 0
nn = Neural_Network(alpha,batch_size,num_itter,dropout_percentage)
nn.train_network(traning_dataset,traning_output,validation_dataset,validation_output)



#pickel_file = open(str(alpha) + "_" + str(batch_size) + "_"+ str(num_itter) + "_" + str(dropout_percentage) + ".pkl" ,"wb")
#pickle.dump(nn,pickel_file)
#pickel_file.close()

