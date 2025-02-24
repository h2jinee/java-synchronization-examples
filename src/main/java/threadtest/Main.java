package threadtest;

import threadtest.sync.CustomMonitor;
import threadtest.sync.CustomSemaphore;
import threadtest.shared.SharedResource;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting synchronization tests...");

        // 뮤텍스 테스트
        testMutex();

        // 세마포어 테스트
        testSemaphore();

        // 모니터 테스트
        testMonitor();
    }

    private static void testMutex() {
        System.out.println("\n=== Testing Mutex ===");
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

    private static void testSemaphore() {
        System.out.println("\n=== Testing Semaphore ===");
        CustomSemaphore semaphore = new CustomSemaphore(2); // 2개의 permit

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting for permit");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired permit");
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " released permit");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "Thread-" + i).start();
        }
    }

    private static void testMonitor() {
        System.out.println("\n=== Testing Monitor ===");
        CustomMonitor monitor = new CustomMonitor();

        Thread waiter = new Thread(() -> {
            try {
                System.out.println("Waiter waiting for condition");
                monitor.waitForCondition();
                System.out.println("Waiter received signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread signaler = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Signaler sending signal");
                monitor.signalCondition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        waiter.start();
        signaler.start();
    }
}