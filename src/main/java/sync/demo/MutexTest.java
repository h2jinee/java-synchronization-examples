package sync.demo;

import sync.resource.SharedResource;

public class MutexTest {
    public void runTest() {
        System.out.println("=== Testing Mutex ===");
        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    resource.increment();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    resource.increment();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final value: " + resource.getValue());
    }
}