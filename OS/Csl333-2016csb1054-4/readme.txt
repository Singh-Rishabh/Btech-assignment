/*-----------------------

	Rishabh Singh
	2016csb1054
	Assignemnt 4
	CSL-333

-----------------------*/

************** Sample run **************

	1. gcc <name_of_yourfile>

		"Include the test.c header in your code.

		Sample output :

			Memory allocated.
			Value of str='Hi there'. Value of ip=234
			New value of str='This is a new value!'
			Freed the memory





*************** Working ****************
	a) Malloc : I have implemented malloc using the concepts given in the programme description.  I  searched the first memory block that have enough free memory to allocate the new malloc. If this block has size greater than current block + the size of metadata then i split this node into two nodes of sizes equal to size,original_size - size. If there is no such free node then I used sbrk to increase the size.
	
	b) Free: I traverse the linked list to find the linked list node that corresponds to the given pointer and make it available for allocation. If the block just above/below this block is free then the two blocks are coalesced into one.
	
	c) Realloc : The pre-existing data is copied onto the stack and the heap pointer is freed. A new block with the new size is created and if successful the old data is copied back and new pointer is returned. If new block allocation fails then the old block is recreated and returned.

