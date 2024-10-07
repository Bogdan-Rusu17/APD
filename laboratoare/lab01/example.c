#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define NUM_THREADS sysconf(_SC_NPROCESSORS_CONF)

void *f(void *arg) {
  long id = *(long *)arg;
  for (int i = 0; i < 100; i++) {
    printf("Hello World din thread-ul %ld la iteratia %ld!\n", id, i);
  }
  pthread_exit(NULL);
}

void *task1(void *arg) {
  long id = *(long *)arg;
  printf("Aici avem taskul 1 apelat din threadul %ld\n", id);
  pthread_exit(NULL);
}

void *task2(void *arg) {
  long id = *(long *)arg;
  printf("Aici avem taskul 2 apelat din threadul %ld\n", id);
  pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
  pthread_t threads[NUM_THREADS];
  int r;
  long id;
  void *status;
  long ids[NUM_THREADS];

  // for (id = 0; id < NUM_THREADS; id++) {
  //   ids[id] = id;
  //   r = pthread_create(&threads[id], NULL, f, &ids[id]);

  //   if (r) {
  //     printf("Eroare la crearea thread-ului %ld\n", id);
  //     exit(-1);
  //   }
  // }

  // for (id = 0; id < NUM_THREADS; id++) {
  //   r = pthread_join(threads[id], &status);

  //   if (r) {
  //     printf("Eroare la asteptarea thread-ului %ld\n", id);
  //     exit(-1);
  //   }
  // }

  ids[0] = 1;
  pthread_create(&threads[0], NULL, task1, &ids[0]);

  ids[1] = 2;
  pthread_create(&threads[1], NULL, task2, &ids[1]);

  pthread_join(threads[0], &status);
  pthread_join(threads[1], &status);

  return 0;
}
