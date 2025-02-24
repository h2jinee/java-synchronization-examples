package sync.core;

/**
 * Mutex 클래스는 상호 배제를 위한 뮤텍스를 구현합니다.
 * 한 번에 하나의 스레드만 critical section에 접근할 수 있도록 합니다.
 */
public class Mutex {
    private boolean locked = false; // 뮤텍스 잠금 상태

    /**
     * 뮤텍스를 잠급니다. 이미 잠겨 있다면 대기합니다.
     * @throws InterruptedException 스레드가 인터럽트되면 발생
     */
    public synchronized void lock() throws InterruptedException {
        while (locked) {
            wait(); // 뮤텍스가 잠겨 있으면 대기
        }
        locked = true; // 뮤텍스 잠금
    }

    /**
     * 뮤텍스를 해제하고, 대기 중인 스레드를 깨웁니다.
     */
    public synchronized void unlock() {
        locked = false;
        notifyAll(); // 모든 대기 스레드를 깨움
    }
}