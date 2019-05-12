# Impoet Statements

from collections import defaultdict, OrderedDict
from math import log
import operator
from copy import deepcopy
import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties
import matplotlib.patheffects as path_effects
from matplotlib import rcParams
import matplotlib
import sys
from sklearn.cluster import KMeans
import numpy as np
from numpy import genfromtxt


# Function to create database
def create_dataset(filename):
	X =	genfromtxt(filename, delimiter=',')
	return X

# function to get most frequent element in the list
def most_common(lst):
    return np.argmax(np.bincount(lst))

# Function to give class label of the given input
def get_cl(x):
	index = -1
	for i in range(len(x)):
		if (x[i] == 1):
			index = i
			break
	return index

# Function which counts the labels
def count_labels(p_cl,o_cl,Y):
	count = 0
	for i in range(len(Y)):
		if (p_cl == o_cl):
			count += 1
	return count

# Function to create the confusion matrix
def create_confuxion_matrix(num_cluster,label,Y):
	confusion_matrix = np.zeros((num_cluster,10))
	for i in range(len(Y)):
		confusion_matrix[label[i],get_cl(Y[i]) ] += 1
	return confusion_matrix

# function to calculate accuracy of a matrix
def accuracy(confusion_matrix, label):
	num_correctly_classified = 0
	labels_ = []
	for i in range(len(confusion_matrix)):
		labels_.append( np.argmax(confusion_matrix[i]) )
		num_correctly_classified += max(confusion_matrix[i])
	return (float(num_correctly_classified)* 100.0/len(label), labels_)

# function to perform clustering
def K_mean_clustering(num_cluster,X,Y):
	kmean = KMeans(n_clusters = num_cluster, random_state = 0).fit(X)
	label = kmean.predict(X)
	confusion_matrix = create_confuxion_matrix(num_cluster,label,Y)
	print ("\n****** Confusion Matrix (number of cluster - "+ str(num_cluster) +  " ) ******\n")
	print(confusion_matrix)
	(acc , labels_) = accuracy(confusion_matrix,label)
	print ("Labels: ")
	print (labels_)
	return acc


filename_train = 'data.txt'
filename_labels = 'label.txt'
X = create_dataset(filename_train)
Y = create_dataset(filename_labels)

acc0 = K_mean_clustering(1,X,Y)
acc3 = K_mean_clustering(5,X,Y)
acc1 = K_mean_clustering(10,X,Y)
acc2 = K_mean_clustering(15,X,Y)

acc4 = K_mean_clustering(100,X,Y)
acc5 = K_mean_clustering(1000,X,Y)
acc6 = K_mean_clustering(5000,X,Y)
print ("Accuracy in case of 0 clusers  = " + str(acc0))
print ("Accuracy in case of 5 clusers  = " + str(acc3))
print ("Accuracy in case of 10 clusers  = " + str(acc1))
print ("Accuracy in case of 15 clusers  = " + str(acc2))

print ("Accuracy in case of 100 clusers  = " + str(acc4))
print ("Accuracy in case of 1000 clusers  = " + str(acc5))
print ("Accuracy in case of 5000 clusers  = " + str(acc6))
# print (K_mean_clustering(1000,X,Y))