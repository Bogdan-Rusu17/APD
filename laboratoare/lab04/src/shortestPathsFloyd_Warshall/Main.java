package shortestPathsFloyd_Warshall;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class MyThread implements Runnable {
    private int id;
    private final int[][] graph;

    public MyThread(int[][] graph, int id) {
        this.graph = graph;
        this.id = id;
    }
    public void run() {
        for (int step = 0; step < 5; step++) {
            for (int j = 0; j < 5; j++) {
                graph[id][j] = Math.min(graph[id][step] + graph[step][j], graph[id][j]);
            }
            try {
                Main.barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Main {

    public static CyclicBarrier barrier;

    public static void main(String[] args) {
        int M = 9;
        int[][] graph = {{0, 1, M, M, M},
                {1, 0, 1, M, M},
                {M, 1, 0, 1, 1},
                {M, M, 1, 0, M},
                {M, M, 1, M, 0}};
        barrier = new CyclicBarrier(5);
        Thread[] threads = new Thread[5];


        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(new MyThread(graph, i));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        // Parallelize me (You might want to keep the original code in order to compare)
        System.out.println("\n\n");
        for (int k = 0; k < 5; k++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}
