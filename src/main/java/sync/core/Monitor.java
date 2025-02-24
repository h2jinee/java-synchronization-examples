package sync.core;

public class Monitor {
    private final Object lock = new Object();
    private boolean condition = false;

    public void waitForCondition() throws InterruptedException {
        synchronized (lock) {
            while (!condition) {
                lock.wait();
            }
        }
    }

    public void signalCondition() {
        synchronized (lock) {
            condition = true;
            lock.notify();
        }
    }

    public void signalAllCondition() {
        synchronized (lock) {
            condition = true;
            lock.notifyAll();
        }
    }
}