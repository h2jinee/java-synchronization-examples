package sync.resource;

import sync.core.Mutex;

/**
 * SharedResource 클래스는 뮤텍스를 사용해 공유 자원에 대한 접근을 동기화합니다.
 */
public class SharedResource {
    private int value = 0; // 공유 자원
    private final Mutex mutex = new Mutex(); // 뮤텍스

    /**
     * 공유 자원을 증가시킵니다. 뮤텍스를 사용해 동기화합니다.
     * @throws InterruptedException 스레드가 인터럽트되면 발생
     */
    public void increment() throws InterruptedException {
        mutex.lock();
        try {
            value++;
            System.out.println(Thread.currentThread().getName() + " incremented value to " + value);
        } finally {
            mutex.unlock();
        }
    }

    /**
     * 현재 공유 자원의 값을 반환합니다. 동기화되어 있습니다.
     * @return 공유 자원의 값
     */
    public synchronized int getValue() {
        return value;
    }
}