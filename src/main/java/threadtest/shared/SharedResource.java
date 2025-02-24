package threadtest.shared;

import threadtest.sync.CustomMutex;

public class SharedResource {
    private int value = 0;
    private final CustomMutex mutex = new CustomMutex();

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