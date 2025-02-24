package sync.core;

/**
 * Semaphore 클래스는 여러 스레드가 공유 자원에 접근할 수 있도록 허용하는 세마포어를 구현합니다.
 */
public class Semaphore {
    private int permits; // 사용 가능한 허가 수

    /**
     * 초기 허가 수를 설정하여 세마포어를 생성합니다.
     * @param permits 초기 허가 수
     */
    public Semaphore(int permits) {
        this.permits = permits;
    }

    /**
     * 허가를 획득합니다. 허가가 없으면 대기합니다.
     * @throws InterruptedException 스레드가 인터럽트되면 발생
     */
    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait(); // 허가가 없으면 대기
        }
        permits--; // 허가 획득
    }

    /**
     * 허가를 해제하고, 대기 중인 스레드를 깨웁니다.
     */
    public synchronized void release() {
        permits++;
        notifyAll(); // 모든 대기 스레드를 깨움
    }

    /**
     * 현재 사용 가능한 허가 수를 반환합니다.
     * @return 사용 가능한 허가 수
     */
    public synchronized int availablePermits() {
        return permits;
    }
}