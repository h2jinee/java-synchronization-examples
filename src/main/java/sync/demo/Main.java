package sync.demo;

import sync.core.Monitor;
import sync.core.Semaphore;
import sync.resource.SharedResource;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting synchronization tests...");

        testMutex();
        testSemaphore();
        testMonitor();
        testMonitorWithMultipleWaiters();
    }

    /**
     * 뮤텍스를 사용한 동기화 테스트
     */
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

    /**
     * 세마포어를 사용한 동기화 테스트
     * availablePermits()를 사용해 허가 상태를 출력
     */
    private static void testSemaphore() {
        System.out.println("\n=== Testing Semaphore ===");
        Semaphore semaphore = new Semaphore(2);
        Thread[] threads = new Thread[5];

        for (int i = 0; i < 5; i++) {
            threads[i] = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting for permit. Available permits: " + semaphore.availablePermits());
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " acquired permit. Available permits: " + semaphore.availablePermits());
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + " released permit. Available permits: " + semaphore.availablePermits());
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

    /**
     * 모니터를 사용한 동기화 테스트 (단일 대기 스레드)
     */
    private static void testMonitor() {
        System.out.println("\n=== Testing Monitor (Single Waiter) ===");
        Monitor monitor = new Monitor();

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

        try {
            waiter.join();
            signaler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 모니터를 사용한 동기화 테스트 (여러 대기 스레드)
     * signalAllCondition()을 사용해 모든 대기 스레드를 깨움
     */
    private static void testMonitorWithMultipleWaiters() {
        System.out.println("\n=== Testing Monitor (Multiple Waiters) ===");
        Monitor monitor = new Monitor();

        Thread waiter1 = new Thread(() -> {
            try {
                System.out.println("Waiter1 waiting for condition");
                monitor.waitForCondition();
                System.out.println("Waiter1 received signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread waiter2 = new Thread(() -> {
            try {
                System.out.println("Waiter2 waiting for condition");
                monitor.waitForCondition();
                System.out.println("Waiter2 received signal");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread signaler = new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("Signaler sending signal to all");
                monitor.signalAllCondition(2); // 2개의 조건을 설정
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        waiter1.start();
        waiter2.start();
        signaler.start();

        try {
            waiter1.join();
            waiter2.join();
            signaler.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}