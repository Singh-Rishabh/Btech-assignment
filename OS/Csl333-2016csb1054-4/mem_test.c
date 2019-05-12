# include "test.c"
# include <stdio.h>
# include <string.h>

// Will be defined by your code
extern void* csl333_realloc(void* ptr, size_t size);
extern void csl333_free(void *ptr);
extern void *csl333_malloc(size_t size);

/**
This is a sample testing program to check your implementation.
*/
int main(int argc, char const *argv[]) {
  int* ip = NULL;
  char* str = NULL;
  
  ip = csl333_malloc(sizeof(int));
  
  str = csl333_malloc(sizeof(char)*10);
  
  if (ip != NULL && str != NULL)
    printf("Memory allocated.\n");
  *ip = 23000004;
  
  strcpy(str, "Hi there");
  printf("Value of str='%s'. Value of ip=%d\n", str, *ip);

  str = csl333_realloc(str, sizeof(char)*20);
  strcpy(str, "This is a neijdg;dg;sfsiufshufsfw value!");
  printf("New value of str='%s'\n", str);

  csl333_free(ip);

  csl333_free(str);

  printf("Freed the memory\n");

  ip = csl333_malloc(sizeof(int));
   *ip = 2304;
     printf("Value of str='%s'. Value of ip=%d\n", str, *ip);

  return 0;
}
