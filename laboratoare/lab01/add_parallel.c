#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define MAX_THREADS 16

/*
    schelet pentru exercitiul 5
*/
typedef struct {
  int start, end;
  int id;
} interval_t;

int *arr;
int sums[MAX_THREADS];
int array_size;
int num_threads;

void *task(void *arg) {
  interval_t interval = *(interval_t *)arg;
  int sum = 0;
  for (int i = interval.start; i < interval.end; i++) {
    sums[interval.id] += arr[i];
  }

  pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
  pthread_t threads[MAX_THREADS];
  if (argc < 3) {
    fprintf(stderr, "Specificati dimensiunea array-ului si numarul de thread-uri\n");
    exit(-1);
  }

  array_size = atoi(argv[1]);
  num_threads = atoi(argv[2]);

  arr = malloc(array_size * sizeof(int));
  for (int i = 0; i < array_size; i++) {
    arr[i] = i;
  }

  for (int i = 0; i < array_size; i++) {
    printf("%d", arr[i]);
    if (i != array_size - 1) {
      printf(" ");
    } else {
      printf("\n");
    }
  }

  // TODO: aceasta operatie va fi paralelizata cu num_threads fire de executie
  for (int i = 0; i < num_threads; i++) {
    interval_t interval;

    interval.start = i * (double)array_size / num_threads;
    interval.end = (i + 1) * ((double)array_size / num_threads);
    interval.id = i;

    if (interval.end > array_size)
      interval.end = array_size;
    pthread_create(&threads[i], NULL, task, &interval);
  }

  void *status;

  for (int i = 0; i < num_threads; i++) {
    pthread_join(threads[i], &status);
  }
  int sum = 0;
  for (int i = 0; i < num_threads; i++) {
    sum += sums[i];
  }

  printf("%d\n", sum);

  // for (int i = 0; i < array_size; i++) {
  //   arr[i] += 100;
  // }

  // for (int i = 0; i < array_size; i++) {
  //   printf("%d", arr[i]);
  //   if (i != array_size - 1) {
  //     printf(" ");
  //   } else {
  //     printf("\n");
  //   }
  // }

  return 0;
}
