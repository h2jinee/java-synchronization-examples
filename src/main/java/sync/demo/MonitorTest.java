package sync.demo;

import sync.core.Monitor;

public class MonitorTest {
    public void runTest() {
        System.out.println("=== Testing Monitor (Single Waiter) ===");
        testSingleWaiter();
    }

    public void runTestWithMultipleWaiters() {
        System.out.println("=== Testing Monitor (Multiple Waiters) ===");
        testMultipleWaiters();
    }

    private void testSingleWaiter() {
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

    private void testMultipleWaiters() {
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
                monitor.signalAllCondition(2); // 2개의 스레드를 깨움
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