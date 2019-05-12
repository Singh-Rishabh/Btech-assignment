// #include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <stdio.h>


typedef struct block_meta {
  size_t size;
  struct block_meta *next;
  struct block_meta *prev;
  int free;
} meta_data;

static meta_data *head = NULL;



meta_data* create_node(size_t size){

	meta_data* node = (meta_data*)sbrk(sizeof(meta_data) + size*sizeof(char));

	node->size = size;
	node->free = 0;
	node->prev = NULL;
	node->next = NULL;
	memset((void*)&node[1], 0, size*sizeof(char));

	if(node == (void*)-1 ){
		printf("sbrk failed returning null\n");
		return NULL;
	}
	else {
		return node;
	}

}


void *csl333_malloc(size_t size){
	if (size <= 0) return NULL;

	size_t overall_size = size + sizeof(meta_data);

	if (head == NULL){
		head = create_node(size);
		return head;
	}
	meta_data* curr = head;
	meta_data* prev = NULL;
	// printf("1\n");

	while(curr){
		// printf("inloop\n");
		if (curr->size >= size  && curr->free == 1){
			if (curr->size > size + sizeof (meta_data)){
				meta_data* m = (void*)curr + sizeof(meta_data) + size;
				m->size = curr->size - overall_size;
				m->free = 1;
				m->prev = curr;
				m->next = curr->next;
				curr->size = size;

				if (curr->next != NULL){
					curr->next->prev = m;
				}
				curr->next = m;
				curr->free = 0;				
				return (void*)curr + sizeof(meta_data);
			}
			curr->free = 0;
			return (void*)curr + sizeof(meta_data);
		}
		prev = curr;
		curr = curr->next;
		
	}

	prev->next = create_node(size);
	prev->next->prev = prev;
	return &(prev->next[1]);
}

void csl333_free(void *ptr) {
	//free(ptr);
	if (ptr == NULL){
		return ;
	}
	meta_data* curr = head;
	while (curr != NULL){
		if ((void*)curr + sizeof(meta_data) == ptr){
			
			if (curr->prev != NULL && curr->prev->free == 1){
				if (curr->prev + sizeof(meta_data) + curr->prev->size == curr ){
					meta_data* prev = curr->prev;
					prev->size += sizeof(meta_data) + curr->size;
					prev->next = curr->next;
					if (curr->next != NULL){
						curr->next->prev = prev;
					}
					curr = curr->prev;
				}
			}

			if (curr->next != NULL && curr->next->free == 1){
				if (curr + sizeof(meta_data) + curr->size == curr->next ){
					meta_data* next = curr->next;
					curr->size += sizeof(meta_data) + next->size;
					curr->next = next->next;
					if (next->next != NULL){
						next->next->prev = curr;
					}
					// curr = curr->prev;
				}
			}
			curr->free = 1;
		}
		curr = curr->next;
	}

}



void* csl333_realloc(void* ptr, size_t size) {
	if (ptr == NULL && size == 0){
		return NULL;
	}
	if (ptr == NULL && size > 0){
		return csl333_malloc(size);
	}
	if (ptr != NULL && size == 0){
		csl333_free(ptr);
		return NULL;
	}

	int copy_size;
	meta_data* curr = head;

	while(curr != NULL) {
		if ((void*)(curr) + sizeof(meta_data) == ptr)
		{
			copy_size = curr->size;
			
			if (copy_size == size) {
				return ptr;
			}			
			break;
		}
		curr = curr->next;
	}

	if (copy_size > size){
		copy_size = size;
		// memset((void*)p+ copy_size, '\0', size - copy_size);
	}

	void* p = csl333_malloc(size);
	memcpy(p, ptr, copy_size);
	
	memset((void*)p + copy_size, '\0', size - copy_size);
	csl333_free(ptr);
	//free ptr
	return p;


	// return realloc(ptr, size);
}

// void print(){
// 	meta_data* curr = head;
// 	while (curr != NULL){
// 		printf("Size %d , free %d\n", curr->size, curr->free);
// 		curr = curr->next;
// 	}
// 	printf("jgshcjsvhdc\n");
// }