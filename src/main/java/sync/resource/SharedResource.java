package sync.resource;

import sync.core.Mutex;

public class SharedResource {
    private int value = 0;
    private final Mutex mutex = new Mutex();

    public void increment() throws InterruptedException {
        mutex.lock();
        try {
            value++;
            System.out.println(Thread.currentThread().getName() + " incremented value to " + value);
        } finally {
            mutex.unlock();
        }
    }

    public int getValue() {
        return value;
    }
}