package doubleVectorElements;

import static java.lang.Integer.min;

class DoublingThread implements Runnable {
    private final int left, right;
    private final int[] vector;

    public DoublingThread(int left, int right, int[] vector) {
        this.left = left;
        this.right = right;
        this.vector = vector;
    }

    public void run() {
        for (int i = left; i < right; i++) {
            vector[i] = vector[i] * 2;
        }
    }
}

public class Main {

    public static void main(String[] args) {
        int N = 100000013;
        int[] v = new int[N];
        int P = 4; // the program should work for any P <= N

        for (int i = 0; i < N; i++) {
            v[i] = i;
        }

        // Parallelize me using P threads
//        for (int i = 0; i < N; i++) {
//            v[i] = v[i] * 2;
//        }
        Thread[] threads = new Thread[P];
        for (int i = 0; i < P; i++) {
            threads[i] = new Thread(new DoublingThread((int)(i * (double)N / P), min((int)((i + 1) * (double)N / P), N), v));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < N; i++) {
            if (v[i] != i * 2) {
                System.out.println("Wrong answer");
                System.exit(1);
            }
        }
        System.out.println("Correct");
    }

}
