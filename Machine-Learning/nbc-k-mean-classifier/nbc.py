# Import Statements
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

# Setting some defaults
matplotlib.rcParams.update({'figure.max_open_warning': 0})

sys.setrecursionlimit(1000000)
reload(sys)  
sys.setdefaultencoding('utf8')
# Font properties Change
rcParams['font.family'] = 'serif'
rcParams['font.sans-serif'] = ['Tahoma']
rcParams.update({'font.size':10})

# Function to read file and create dataset
def read_file(filename):
	Y = []
	X_spam = defaultdict(int)
	X_ham = defaultdict(int)
	
	n_spam = 0
	n_ham = 0

	with open(filename) as fp:
		line = fp.readline()
		while (line):
			temp_arr = line.split()
			Y.append(temp_arr[1])
			temp_imput = temp_arr[2:]
			if (temp_arr[1] == 'spam'):
				n_spam = n_spam + 1
			else :
				n_ham = n_ham + 1

			for j in range(0, len(temp_imput), 2):
				if (temp_arr[1] == 'spam'):
					X_spam[temp_imput[j]] += int(temp_imput[j+1])
				else :
					X_ham[temp_imput[j]] += int(temp_imput[j+1])
				
			line = fp.readline()
			
	return X_spam,X_ham,Y,n_spam,n_ham


# Function to calculate the liklehood
def p_xj_ck(X_spam,X_ham,m,p,total_words_spam,total_words_ham):
	for key,val in X_spam.items():
		X_spam[key] = float(val + m*p)/ (total_words_spam + m)

	for key,val in X_ham.items():
		X_ham[key] = float(val + m*p)/ (total_words_ham + m)

	return X_spam,X_ham

# function to caclulate accuracy.
def cacl_accuracy(filename ,X_spam, X_ham ,p_spam, p_ham, m, p):
	count = 0
	num_dataset = 0
	with open(filename) as fp:
		line = fp.readline()
		while (line):
			temp_arr = line.split()
			output = temp_arr[1]
			temp_imput = temp_arr[2:]

			posterior_spam = log(p_spam)
			posterior_ham = log(p_ham)
			cl = 'spam'

			for j in range(0, len(temp_imput), 2):
				if temp_imput[j] in X_spam:
					posterior_spam += log(X_spam[temp_imput[j]])
				else :
					posterior_spam += log(p*m/(m + total_words_spam))

				if temp_imput[j] in X_ham  :
					posterior_ham += log(X_ham[temp_imput[j]])
				else :
					posterior_ham += log(p*m/(m + total_words_ham))

			if (posterior_spam < posterior_ham):
				cl = 'ham'

			if (output == cl):
				count += 1
				
			line = fp.readline()
			num_dataset = num_dataset + 1
	return count/float(num_dataset)*100.0

# reading the file
filename_train = 'nbctrain'
filename_test = 'nbctest'
X_spam,X_ham,Y,n_spam,n_ham = read_file(filename_train)
p_spam = float(n_spam)/len(Y)
p_ham = float(n_ham)/len(Y)

X_spam_b = deepcopy(X_spam)
X_ham_b = deepcopy(X_ham)

# print(p_spam, p_ham)
h_Set = set()
total_words_spam = 0
total_words_ham = 0
for key,val in X_spam.items():
	h_Set.add(key)
	total_words_spam += val

for key,val in X_ham.items():
	h_Set.add(key)
	total_words_ham += val
total_vocabulary = len(h_Set)

X_spam ,X_ham = p_xj_ck(X_spam,X_ham,total_vocabulary, 1/float(total_vocabulary),total_words_spam , total_words_ham)

# performing the experiments
print ('\n******** Experiment 1 ********\n')
print ("Probability of spam = " + str(p_spam))
print ("Probability of ham = " + str(p_ham))

print ('\n******** Experiment 2 ********\n')
max_5_words_spam = dict(sorted(X_spam.iteritems(), key=operator.itemgetter(1), reverse=True)[:5])
max_5_words_ham = dict(sorted(X_ham.iteritems(), key=operator.itemgetter(1), reverse=True)[:5])

print ('Five most frequently words indicative of a spam mail are')
for key, val in max_5_words_spam.items():
	print ("\t Word is " + key + ' \tProbability(word/spam) = ' + str(val))

print ('Five most frequently words indicative of a ham mail are')
for key, val in max_5_words_ham.items():
	print ("\t Word is " + key + ' \tProbability(word/ham) = ' + str(val))

print ('\n******** Experiment 3 ********\n')

print ('Calculating the accuracy over Train and test Dataset')

print ('Accuracy over training Dataset............ : ' + str(cacl_accuracy(filename_train, X_spam,X_ham, p_spam,p_ham,total_vocabulary, 1/float(total_vocabulary))))
print ('Accuracy over test Dataset................ : ' + str(cacl_accuracy(filename_test, X_spam,X_ham, p_spam,p_ham,total_vocabulary, 1/float(total_vocabulary))))

print ('\n******** Experiment 4 ********\n')

train_acc = []
test_acc = []
m = [x for x in range(100,10000,50)]
for i in range(100,10000,50):
	X_spam_tmp = deepcopy(X_spam_b)
	X_ham_tmp = deepcopy(X_ham_b)
	X_spam_tmp ,X_ham_tmp = p_xj_ck(X_spam_tmp,X_ham_tmp, i , 1/float(total_vocabulary),total_words_spam , total_words_ham)

	tmp_train_acc = cacl_accuracy(filename_train, X_spam_tmp,X_ham_tmp, p_spam,p_ham,total_vocabulary, 1/float(total_vocabulary))
	tmp_test_acc = cacl_accuracy(filename_test, X_spam_tmp,X_ham_tmp, p_spam,p_ham,total_vocabulary, 1/float(total_vocabulary))
	print ('Calculating the accuracy over Train and test Dataset with m value = ' + str(i))
	print ('Accuracy over training Dataset............ : ' + str(tmp_train_acc))
	print ('Accuracy over test Dataset................ : ' + str(tmp_test_acc) + str('\n'))
	train_acc.append(tmp_train_acc)
	test_acc.append(tmp_test_acc)


fig, ax = plt.subplots()
ax.yaxis.grid(True)
ax.set_axisbelow(True)

ax.minorticks_on()
ax.tick_params(axis='x',which='minor',bottom='off')
ax.yaxis.grid(True)
ax.set_axisbelow(True)

ax.plot(m,train_acc,label = 'Traning accuracy')
ax.plot(m,test_acc,label = 'Test accuracy')

# ax.set_title('',fontweight= 'bold',fontsize=15)
ax.set_xlabel(r'm',fontsize=15)
ax.set_ylabel("Accuracy",fontsize=15)

ax.legend(loc="best")
# handles, labels = ax.get_legend_handles_labels()
# lgd = ax.legend(handles, labels, loc='center left',bbox_to_anchor=(1.05, 0.5)) 

plt.tight_layout()
# plt.savefig("frac_dependence"+".pdf", bbox_extra_artists=(lgd,), bbox_inches='tight')
plt.savefig("m_vs_accuracy_with_constant_p"+".jpg", bbox_inches='tight')




