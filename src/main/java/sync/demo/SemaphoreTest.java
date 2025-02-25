package sync.demo;

import sync.core.Semaphore;

public class SemaphoreTest {
    public void runTest() {
        System.out.println("=== Testing Semaphore ===");
        Semaphore semaphore = new Semaphore(2);
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting for permit.");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired permit.");
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " released permit.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i);
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