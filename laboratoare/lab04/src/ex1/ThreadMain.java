package ex1;

import java.io.InterruptedIOException;

class MyThread implements Runnable {
    private int id;

    public MyThread(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Thread " + id + " started");
    }

    public int getId() {
        return id;
    }
}

public class ThreadMain {
    public static void main(String[] args) {
        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyThread(i));
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
